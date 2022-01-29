package edu.miu.blog.topicservice.controller;

import edu.miu.blog.topicservice.Service.TopicService;
import edu.miu.blog.topicservice.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
