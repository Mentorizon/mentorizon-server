package com.mapthree.mentorizonserver.controller;

import com.mapthree.mentorizonserver.dto.RatingResponse;
import com.mapthree.mentorizonserver.model.Rating;
import com.mapthree.mentorizonserver.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody Rating rating) {
        RatingResponse ratingResponse = ratingService.saveRating(rating);
        return ResponseEntity.ok(ratingResponse);
    }

}
