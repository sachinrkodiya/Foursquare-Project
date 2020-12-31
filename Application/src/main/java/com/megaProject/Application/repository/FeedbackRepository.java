package com.megaProject.Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.megaProject.Application.model.Feedback;



@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

}
