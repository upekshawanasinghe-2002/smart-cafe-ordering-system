package com.cafe.feedback_service.service;

import com.cafe.feedback_service.entity.Feedback;
import com.cafe.feedback_service.repository.FeedbackRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // ===== Add Review =====
    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    // ===== View All Reviews =====
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // ===== View Single Review =====
    public Feedback getFeedbackById(Long id) {

        return feedbackRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Feedback not found with id: " + id)
                );
    }

    // ===== Update Review =====
    public Feedback updateFeedback(Long id, Feedback updatedFeedback) {

        Feedback existingFeedback = getFeedbackById(id);

        existingFeedback.setFoodId(updatedFeedback.getFoodId());
        existingFeedback.setRating(updatedFeedback.getRating());
        existingFeedback.setReview(updatedFeedback.getReview());
        existingFeedback.setCustomer(updatedFeedback.getCustomer());

        return feedbackRepository.save(existingFeedback);
    }

    // ===== Delete Review =====
    public void deleteFeedback(Long id) {

        Feedback existingFeedback = getFeedbackById(id);

        feedbackRepository.delete(existingFeedback);
    }
}