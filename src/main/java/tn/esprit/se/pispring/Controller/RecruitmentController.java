package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.IRecruitmentService;
import tn.esprit.se.pispring.entities.Leav;
import tn.esprit.se.pispring.entities.Recruitment;

import java.util.List;

@CrossOrigin(origins ="http://localhost:8089")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/recruitment")
public class RecruitmentController {
    private final IRecruitmentService recruitmentService;

    @PostMapping("/addRecruitment")
    Recruitment addRecruitment(@RequestBody Recruitment recruitment)
    {
        return recruitmentService.addOrUpdateRecruitment(recruitment);
    }
    @PutMapping("/updateRecruitment")
    Recruitment updateRecruitment(@RequestBody Recruitment recruitment)
    {
        return recruitmentService.addOrUpdateRecruitment(recruitment);
    }

    @GetMapping("/get/{offerId}")
    Recruitment getRecruitment(@PathVariable("offerId") Long offerId)
    {
        return recruitmentService.retrieveRecruitment(offerId);
    }

    @GetMapping("/allRecruitments")
    List<Recruitment> getAllRecruitments()
    {
        return recruitmentService.retrieveAllRecruitments();
    }

    @DeleteMapping("/delete/{offerId}")
    void deleteRecruitment(@PathVariable("offerId") Long offerId)
    {
        recruitmentService.removeRecruitment(offerId);
    }

}
