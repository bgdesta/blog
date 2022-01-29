package edu.miu.blog.topicservice.Service;

import edu.miu.blog.topicservice.domain.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAll();

    Topic createTopic(Topic topic);

    void deleteById(Long id);

    Topic updateTopic(Long id, Topic topic);
}
