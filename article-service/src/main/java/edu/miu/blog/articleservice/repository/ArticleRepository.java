package edu.miu.blog.articleservice.repository;

import edu.miu.blog.articleservice.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository <Article, Long> {
}
