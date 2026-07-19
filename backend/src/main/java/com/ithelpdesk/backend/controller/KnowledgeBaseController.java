package com.ithelpdesk.backend.controller;

import com.ithelpdesk.backend.dto.KnowledgeRequest;
import com.ithelpdesk.backend.dto.KnowledgeResponse;
import com.ithelpdesk.backend.service.KnowledgeBaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    /**
     * Create Knowledge Article
     */
    @PostMapping
    public ResponseEntity<KnowledgeResponse> createArticle(
            @RequestBody KnowledgeRequest request) {

        KnowledgeResponse response =
                knowledgeBaseService.createArticle(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get All Articles
     */
    @GetMapping
    public ResponseEntity<List<KnowledgeResponse>> getAllArticles() {

        return ResponseEntity.ok(
                knowledgeBaseService.getAllArticles()
        );
    }

    /**
     * Get Article By ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeResponse> getArticleById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                knowledgeBaseService.getArticleById(id)
        );
    }

    /**
     * Update Article
     */
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeResponse> updateArticle(
            @PathVariable Long id,
            @RequestBody KnowledgeRequest request) {

        return ResponseEntity.ok(
                knowledgeBaseService.updateArticle(id, request)
        );
    }

    /**
     * Delete Article
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable Long id) {

        knowledgeBaseService.deleteArticle(id);

        return ResponseEntity.ok("Knowledge Article deleted successfully.");
    }

    /**
     * Search Knowledge Base
     */
    @GetMapping("/search")
    public ResponseEntity<List<KnowledgeResponse>> searchKnowledge(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                knowledgeBaseService.searchKnowledge(keyword)
        );
    }

}