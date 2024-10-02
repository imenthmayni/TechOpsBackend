package tn.esprit.se.pispring.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.Service.PayrollReportService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class PayrollReportController {

    PayrollReportService payrollReportService;

    /*@GetMapping("/pdf/all")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        this.userReportService.exportToPdf(response);
    }*/
    @GetMapping("/excel/all")
    public void exportToExcel(HttpServletResponse response, @RequestParam int year) throws IOException {
        this.payrollReportService.exportToExcel(response, year);
    }
}
