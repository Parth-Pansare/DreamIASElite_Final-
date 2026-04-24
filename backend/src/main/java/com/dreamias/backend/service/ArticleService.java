package com.dreamias.backend.service;

import com.dreamias.backend.entity.Article;
import com.dreamias.backend.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findByOrderByPublishedAtDesc();
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }
}
