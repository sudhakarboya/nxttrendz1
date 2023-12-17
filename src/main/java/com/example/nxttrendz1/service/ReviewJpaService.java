/*
 *
 * You can use the following import statements
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here
package com.example.nxttrendz1.service;

import com.example.nxttrendz1.model.*;
import com.example.nxttrendz1.repository.*;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ReviewJpaService implements ReviewRepository {
    @Autowired
    private ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Review> getReviews() {
        return (ArrayList<Review>) reviewJpaRepository.findAll();
    }

    @Override
    public Review getReviewById(int reviewId) {
        try {
            return reviewJpaRepository.findById(reviewId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public Review addReview(Review review) {
        int productId = review.getProduct().getProductId();
        Product product = productJpaRepository.findById(productId).get();
        review.setProduct(product);
        reviewJpaRepository.save(review);
        return review;
    }

    @Override
    public Review updateReview(int reviewId, Review review) {
        try {
            Review existReview = reviewJpaRepository.findById(reviewId).get();
            if (review.getReviewContent() != null) {
                existReview.setReviewContent(review.getReviewContent());
            }
            if (review.getRating() != 0) {
                existReview.setRating(review.getRating());
            }
            if (review.getProduct() != null) {
                int productId = review.getProduct().getProductId();
                Product product = productJpaRepository.findById(productId).get();
                existReview.setProduct(product);
            }
            reviewJpaRepository.save(existReview);
            return existReview;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void deleteReview(int reviewId) {
        try {
            reviewJpaRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Product getProductByReviewId(int reviewId) {
        try {
            Review review = reviewJpaRepository.findById(reviewId).get();
            return review.getProduct();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}