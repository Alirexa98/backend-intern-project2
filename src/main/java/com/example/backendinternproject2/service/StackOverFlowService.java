package com.example.backendinternproject2.service;

import com.example.backendinternproject2.controller.StackOverFlowController;
import com.example.backendinternproject2.entity.*;
import com.example.backendinternproject2.model.common.PostModel;
import com.example.backendinternproject2.model.request.PostRequestModel;
import com.example.backendinternproject2.model.response.PostResponseModel;
import com.example.backendinternproject2.model.response.TagResponseModel;
import com.example.backendinternproject2.repository.*;
import com.example.backendinternproject2.xml.XmlCommentModel;
import com.example.backendinternproject2.xml.XmlPostModel;
import com.example.backendinternproject2.xml.XmlTagModel;
import com.example.backendinternproject2.xml.XmlUserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@EnableKafka
public class StackOverFlowService {
  private final String USERS = "users";
  private final String TAGS = "tags";
  private final String COMMENTS = "comments";
  private final String POSTS = "posts";

  public StackOverFlowService(QuestionRepository questionRepository, AnswerRepository answerRepository, TagRepository tagRepository, CommentRepository commentRepository, UserRepository userRepository, Executor executor, KafkaTemplate<String, String> kafkaTemplate) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
    this.tagRepository = tagRepository;
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.executor = executor;
    this.kafkaTemplate = kafkaTemplate;
    var mapper = new XmlMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    this.xmlMapper = mapper;
  }

  private QuestionRepository questionRepository;
  private AnswerRepository answerRepository;
  private TagRepository tagRepository;
  private CommentRepository commentRepository;
  private UserRepository userRepository;
  private KafkaTemplate<String, String> kafkaTemplate;
  private Executor executor;
  private XmlMapper xmlMapper;

  public TagResponseModel getTag(String tagName) {
    var tagOptional = tagRepository.getTagByName(tagName);
    if (tagOptional.isEmpty()) {
      return null;
    }
    var tag = tagOptional.get();
    return new TagResponseModel(tag.getId(), tag.getName(), tag.getCount());
  }

  public PostResponseModel searchPosts(PostRequestModel requestModel) {
    var posts = questionRepository.searchQuestions(requestModel.getKeywords(), String.join("%", requestModel.getTags()), requestModel.getFromDate(), requestModel.getMinScore(), PageRequest.of(requestModel.getPageNumber(), requestModel.getPageCount()));
    return new PostResponseModel(posts.stream().map((item) -> {
      if (item.getBestAnswerId() != null) {
        var bestAnswer = answerRepository.findById(item.getBestAnswerId());
        return PostModel.of(item, bestAnswer.get());
      } else {
        return PostModel.of(item);
      }
    }).collect(Collectors.toList()));
  }

  public void start() {
    executor.execute(() -> {
      long start = System.currentTimeMillis();
      readTags();
      readUsers();
      readPosts();
      readComments();
      System.out.println("Time Spend: " + (System.currentTimeMillis() - start) / 1000);
    });
  }

  private void readUsers() {
    readFile("src/main/data/Users.xml", USERS);
  }

  private void readTags() {
    readFile("src/main/data/Tags.xml", TAGS);
  }

  private void readComments() {
    readFile("src/main/data/Comments.xml", COMMENTS);
  }

  private void readPosts() {
    readFile("src/main/data/Posts.xml", POSTS);
  }

  private void readFile(String filePath, String ignoreTag) {
    System.out.println(filePath);
    try (var linesStream = Files.lines(Paths.get(filePath))) {
      linesStream.skip(1).filter((line) -> !(line.equals("<" + ignoreTag + ">") || line.equals("</" + ignoreTag + ">"))).forEach(line -> {
        kafkaTemplate.send("backend_project2",ignoreTag + line);
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println("Ended");
  }

  @KafkaListener(topics = "backend_project2", groupId = "test_group")
  public void listener1(String message) {
    processKafkaMessage(message);
  }

  @KafkaListener(topics = "backend_project2", groupId = "test_group")
  public void listener2(String message) {
    processKafkaMessage(message);
  }

  @KafkaListener(topics = "backend_project2", groupId = "test_group")
  public void listener3(String message) {
    processKafkaMessage(message);
  }

  private void processKafkaMessage(String message) {
    if (message.startsWith(USERS)) {
      insertUser(message.substring(USERS.length()));
    } else if (message.startsWith(TAGS)) {
      insertTag(message.substring(TAGS.length()));
    } else if (message.startsWith(COMMENTS)) {
      insertComment(message.substring(COMMENTS.length()));
    } else if (message.startsWith(POSTS)) {
      insertPost(message.substring(POSTS.length()));
    }
  }

  @SneakyThrows
  private void insertUser(String userXml) {
    userRepository.save(UserEntity.of(xmlMapper.readValue(userXml, XmlUserModel.class)));
  }

  @SneakyThrows
  private void insertComment(String commentXml) {
    var commentModel = xmlMapper.readValue(commentXml, XmlCommentModel.class);

    if (commentModel.getUserId() == null) {
      System.out.println("User Id Null");
      return;
    }
    var user = userRepository.findById(commentModel.getUserId());
    if (user.isEmpty()) {
      System.out.println("User Not Found: " + commentModel.getUserId());
      return;
    }
    if (commentModel.getPostId() == null) {
      System.out.println("Post Id Null");
      return;
    }
    var question = questionRepository.findById(commentModel.getPostId());
    if (question.isEmpty()) {
      System.out.println("Question Not Found: " + commentModel.getPostId());
      return;
    }

    commentRepository.save(CommentEntity.of(commentModel, user.get(), question.get()));
  }

  @SneakyThrows
  private void insertTag(String tagXml) {
    tagRepository.save(TagEntity.of(xmlMapper.readValue(tagXml, XmlTagModel.class)));
  }

  @SneakyThrows
  private void insertPost(String postXml) {
    var postModel = xmlMapper.readValue(postXml, XmlPostModel.class);
    switch (postModel.getPostType()) {
      case 1:
        questionRepository.save(QuestionEntity.of(postModel));
        break;
      case 2:
        answerRepository.save(AnswerEntity.of(postModel));
        break;
    }
  }

}
