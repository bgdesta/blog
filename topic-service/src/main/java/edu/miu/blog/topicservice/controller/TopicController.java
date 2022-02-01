package edu.miu.blog.topicservice.controller;

import edu.miu.blog.topicservice.Service.TopicService;
import edu.miu.blog.topicservice.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    // Get all topics
    @GetMapping()
    public List<Topic> getAllTopics(){
        return topicService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id){
        return ResponseEntity.ok(topicService.getTopicById(id));
    }

    @GetMapping("/search")
    public Topic getTopicByName(@RequestParam String name){
        return topicService.getTopicByName(name);
    }
    // create a topic
    @PostMapping()
    public Topic createTopic(@RequestBody Topic topic){
        return topicService.createTopic(topic);
    }

    // Update topic
    @PutMapping("/{topicId}")
    public Topic updateTopic(@PathVariable("topicId") Long id, @RequestBody Topic topic){
        return topicService.updateTopic(id, topic);
    }
    // Delete topic
    @DeleteMapping("/{id}")
    public void deleteTopic(@PathVariable Long id){
        topicService.deleteById(id);
    }
}
