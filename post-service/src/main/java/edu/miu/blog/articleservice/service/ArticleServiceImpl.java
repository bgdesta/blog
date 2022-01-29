package edu.miu.blog.articleservice.service;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> getAll() {
        return articleRepository.findAll();
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article updateArticle(Long id, Article article) {
        Article art = articleRepository.findById(id).get();
        art.setTitle(article.getTitle());
        art.setSummary(article.getSummary());

        return articleRepository.save(art);
    }
}
