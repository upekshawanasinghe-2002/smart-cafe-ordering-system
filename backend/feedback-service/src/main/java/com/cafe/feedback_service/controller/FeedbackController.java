package com.cafe.feedback_service.controller;

import com.cafe.feedback_service.entity.Feedback;
import com.cafe.feedback_service.service.FeedbackService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // ===== Add Review =====
    @PostMapping
    public Feedback addFeedback(@RequestBody Feedback feedback) {
        return feedbackService.addFeedback(feedback);
    }

    // ===== View Reviews =====
    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    // ===== View Single Review =====
    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    // ===== Update Review =====
    @PutMapping("/{id}")
    public Feedback updateFeedback(
            @PathVariable Long id,
            @RequestBody Feedback feedback) {

        return feedbackService.updateFeedback(id, feedback);
    }

    // ===== Delete Review =====
    @DeleteMapping("/{id}")
    public String deleteFeedback(@PathVariable Long id) {

        feedbackService.deleteFeedback(id);

        return "Feedback deleted successfully";
    }
}