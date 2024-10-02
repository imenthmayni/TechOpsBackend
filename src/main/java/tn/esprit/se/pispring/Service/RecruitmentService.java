package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.RecruitmentRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Recruitment;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitmentService implements IRecruitmentService {

    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;
    @Override
    public Recruitment addOrUpdateRecruitment(Recruitment R) {
        return recruitmentRepository.save(R);
    }

    @Override
    public void removeRecruitment(Long offerId) {
        recruitmentRepository.deleteById(offerId);
    }

    @Override
    public Recruitment retrieveRecruitment(Long offerId) {
        return recruitmentRepository.findById(offerId).orElse(null);
    }

    @Override
    public List<Recruitment> retrieveAllRecruitments() {
        return recruitmentRepository.findAll();
    }
}
