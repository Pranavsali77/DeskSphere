package com.ithelpdesk.backend.service;

import com.ithelpdesk.backend.dto.KnowledgeRequest;
import com.ithelpdesk.backend.dto.KnowledgeResponse;

import java.util.List;

public interface KnowledgeBaseService {

    // Add new article
    KnowledgeResponse createArticle(KnowledgeRequest request);

    // Get all articles
    List<KnowledgeResponse> getAllArticles();

    // Get article by ID
    KnowledgeResponse getArticleById(Long id);

    // Update article
    KnowledgeResponse updateArticle(Long id, KnowledgeRequest request);

    // Delete article
    void deleteArticle(Long id);

    // Search articles
    List<KnowledgeResponse> searchKnowledge(String keyword);

}