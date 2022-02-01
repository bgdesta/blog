package edu.miu.blog.articleservice.domain;

import edu.miu.blog.articleservice.dto.request.ArticleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String summary;

    @Lob
    private String content;
    private boolean isPublished;    // Could also be an ENUM - PUBLISHED, DRAFT
    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;

    private Long topic_id;

    public Article(ArticleDto artDto){
        this.title = artDto.getTitle();
        this.summary = artDto.getSummary();
        this.content = artDto.getContent();
    }
}
