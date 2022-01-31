package edu.miu.blog.articleservice.service;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.dto.request.ArticleDto;
import edu.miu.blog.articleservice.dto.request.PostArticleDto;
import edu.miu.blog.articleservice.repository.ArticleRepository;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ArticleDto> getAll() {

        return articleRepository.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

//    private ArticleDto convertEntityToDto(Article article){
//        ArticleDto articleDto = new ArticleDto();
//        articleDto.setId(article.getId());
//        articleDto.setTitle(article.getTitle());
//        articleDto.setSummary(article.getSummary());
//        articleDto.setContent(article.getContent());
//
////        articleDto.setTopic(article.getTopic_id().getName());
//
//        return articleDto;
//    }

    private ArticleDto convertEntityToDto(Article article){
        ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);

        return articleDto;
    }
    private Article convertDtoToEntity(ArticleDto articleDto){
        Article article = modelMapper.map(articleDto, Article.class);

        return article;
    }

    @Override
    public ArticleDto createArticle(@RequestBody PostArticleDto articleDto) {
        Article article = convertDtoToEntity(articleDto);
        article.setCreatedAt(new Date());
        article.setUpdatedAt(new Date());
        article = articleRepository.save(article);
        return convertEntityToDto(article);
    }

    @Override
    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article updateArticle(Long id, ArticleDto articleDto) {

        // Skip mapping null properties
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        Article article = articleRepository.findById(id).orElseThrow();
        article.setUpdatedAt(new Date());
        modelMapper.map(articleDto, article);

        return articleRepository.save(article);
    }
}
