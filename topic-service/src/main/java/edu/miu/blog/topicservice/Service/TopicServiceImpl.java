package edu.miu.blog.topicservice.Service;

import edu.miu.blog.topicservice.domain.Topic;
import edu.miu.blog.topicservice.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public Topic updateTopic(Long id, Topic topic) {
        Topic tpc = topicRepository.findById(id).get();
        tpc.setName(topic.getName());
//        tpc.setUpdatedAt(new Date());

        return topicRepository.save(tpc);
    }
}
