package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Rating.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}