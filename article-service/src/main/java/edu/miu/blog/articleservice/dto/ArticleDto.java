package edu.miu.blog.articleservice.dto;

import lombok.Data;

@Data
public class ArticleDto {
    private Long id;
    private String title;
    private String summary;
    private String content;
//    private String topic;
}
