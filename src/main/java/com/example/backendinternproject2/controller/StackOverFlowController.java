package com.example.backendinternproject2.controller;

import com.example.backendinternproject2.model.request.PostRequestModel;
import com.example.backendinternproject2.service.StackOverFlowService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StackOverFlowController {

  private StackOverFlowService stackOverFlowService;

  @PostMapping("/start")
  public ResponseEntity start() {
    stackOverFlowService.start();
    return ResponseEntity.ok().build();
  }

  @PostMapping("/posts")
  public ResponseEntity getPosts(@RequestBody PostRequestModel postRequestModel) {
    return ResponseEntity.ok(stackOverFlowService.searchPosts(postRequestModel));
  }

  @GetMapping("/tag/{tag_name}")
  public ResponseEntity getTag(@PathVariable("tag_name") String tagName) {
    var result = stackOverFlowService.getTag(tagName);
    if (result == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(result);
  }
}
