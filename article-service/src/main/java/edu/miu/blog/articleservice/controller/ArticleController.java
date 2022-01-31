package edu.miu.blog.articleservice.controller;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.dto.request.ArticleDto;
import edu.miu.blog.articleservice.dto.request.PostArticleDto;
import edu.miu.blog.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public List<ArticleDto> getAllArticles(){

        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Article> getArticleById(@PathVariable Long id){
        return articleService.getArticleById(id);
    }
    // Create an article
    @PostMapping()
    public String createArticle(@Valid @RequestBody PostArticleDto articleDto, BindingResult result){
        if (result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();

            for (ObjectError temp : allErrors) {
                return temp.getDefaultMessage();
            }
        }
        articleService.createArticle(articleDto);
        return "Article saved successfully";
    }

    // Update article
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody ArticleDto articleDto){
        return articleService.updateArticle(id, articleDto);
    }

    // Delete article
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id){
        articleService.deleteArticleById(id);
    }
}
