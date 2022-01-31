package edu.miu.blog.articleservice.service;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.dto.request.ArticleDto;
import edu.miu.blog.articleservice.dto.request.PostArticleDto;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getAll();

    ArticleDto createArticle(PostArticleDto articleDto);

    void deleteArticleById(Long id);

    Article updateArticle(Long id, ArticleDto article);
}
