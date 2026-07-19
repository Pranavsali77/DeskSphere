package com.ithelpdesk.backend.repository;

import com.ithelpdesk.backend.entity.KnowledgeArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {

    List<KnowledgeArticle> findByTitleContainingIgnoreCase(String keyword);

    List<KnowledgeArticle> findByKeywordsContainingIgnoreCase(String keyword);

    List<KnowledgeArticle> findByCategoryIgnoreCase(String category);

}