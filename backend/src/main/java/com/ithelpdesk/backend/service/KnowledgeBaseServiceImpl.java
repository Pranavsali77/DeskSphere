package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.KnowledgeRequest;
import com.ithelpdesk.backend.dto.KnowledgeResponse;
import com.ithelpdesk.backend.entity.KnowledgeArticle;
import com.ithelpdesk.backend.repository.KnowledgeArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeArticleRepository repository;

    public KnowledgeBaseServiceImpl(KnowledgeArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public KnowledgeResponse createArticle(KnowledgeRequest request) {

        KnowledgeArticle article = new KnowledgeArticle();

        article.setTitle(request.getTitle());
        article.setKeywords(request.getKeywords());
        article.setSolution(request.getSolution());
        article.setCategory(request.getCategory());

        article = repository.save(article);

        return mapToResponse(article);
    }

    @Override
    public List<KnowledgeResponse> getAllArticles() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public KnowledgeResponse getArticleById(Long id) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Knowledge Article not found"));

        return mapToResponse(article);
    }

    @Override
    public KnowledgeResponse updateArticle(Long id,
                                           KnowledgeRequest request) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Knowledge Article not found"));

        article.setTitle(request.getTitle());
        article.setKeywords(request.getKeywords());
        article.setSolution(request.getSolution());
        article.setCategory(request.getCategory());

        repository.save(article);

        return mapToResponse(article);
    }

    @Override
    public void deleteArticle(Long id) {

        KnowledgeArticle article = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Knowledge Article not found"));

        repository.delete(article);
    }

    @Override
    public List<KnowledgeResponse> searchKnowledge(String keyword) {

        List<KnowledgeArticle> articles =
                repository.findByTitleContainingIgnoreCase(keyword);

        if (articles.isEmpty()) {
            articles = repository.findByKeywordsContainingIgnoreCase(keyword);
        }

        return articles.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ==========================
    // Helper Method
    // ==========================

    private KnowledgeResponse mapToResponse(KnowledgeArticle article) {

        KnowledgeResponse response = new KnowledgeResponse();

        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setKeywords(article.getKeywords());
        response.setSolution(article.getSolution());
        response.setCategory(article.getCategory());

        return response;
    }

}