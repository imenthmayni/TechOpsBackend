package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.entities.Recruitment;

import java.util.List;

public interface IRecruitmentService {
    Recruitment addOrUpdateRecruitment(Recruitment R);

    void removeRecruitment(Long offerId);

    Recruitment retrieveRecruitment(Long offerId);

    List<Recruitment> retrieveAllRecruitments();

}
