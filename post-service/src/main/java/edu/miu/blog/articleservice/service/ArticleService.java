package edu.miu.blog.articleservice.service;

import edu.miu.blog.articleservice.domain.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAll();

    Article createArticle(Article article);

    void deleteArticleById(Long id);

    Article updateArticle(Long id, Article article);
}
