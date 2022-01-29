package edu.miu.blog.articleservice.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @NotEmpty(message = "* Summary for articles are needed")
    private String summary;

    @Lob
    @NotEmpty(message = "* Article content can't be empty")
    private String content;
    private boolean isPublished;    // Could also be an ENUM - PUBLISHED, DRAFT
    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;

    private Long topic_id;
}
