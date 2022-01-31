package edu.miu.blog.topicservice.repository;

import edu.miu.blog.topicservice.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
