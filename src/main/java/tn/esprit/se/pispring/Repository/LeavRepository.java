package tn.esprit.se.pispring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.se.pispring.entities.Leav;

import java.util.Date;
import java.util.List;

public interface LeavRepository extends JpaRepository<Leav,Long> {
    @Query("SELECT l FROM Leav l WHERE l.leaveStartdate BETWEEN :startTime AND :endTime")
    List<Leav> findUpcomingLeave(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

}

