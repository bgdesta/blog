package edu.miu.blog.topicservice.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
public class Topic {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;
    private Date createdAt;
    private Date updatedAt;
}
