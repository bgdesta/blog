package edu.miu.blog.articleservice.controller;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public List<Article> getAllPosts(){
        return articleService.getAll();
    }

    // Create an article
    @PostMapping("/")
    public Article createArticle(@Valid @RequestBody Article article){
        return articleService.createArticle(article);
    }

    // Update article
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article){
        return articleService.updateArticle(id, article);
    }

    // Delete article
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id){
        articleService.deleteArticleById(id);
    }
}
