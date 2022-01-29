package edu.miu.blog.articleservice.service;

import edu.miu.blog.articleservice.domain.Article;
import edu.miu.blog.articleservice.dto.ArticleDto;
import edu.miu.blog.articleservice.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ArticleDto articleDto = new ArticleDto();
        articleDto = modelMapper.map(article, ArticleDto.class);

        return articleDto;
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
