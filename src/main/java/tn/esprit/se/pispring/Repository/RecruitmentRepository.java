package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.se.pispring.entities.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment,Long> {
}
