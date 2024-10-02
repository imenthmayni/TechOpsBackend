package tn.esprit.se.pispring.Service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.Repository.PayrollRepository;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.Payroll;
import tn.esprit.se.pispring.entities.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class PayrollReportImpl implements PayrollReportService{

    PayrollExportToExcel payrollExportToExcelService;
    UserRepository userRepository;
    PayrollService payrollService;
    // export to pdf
    @SneakyThrows
    @Override
    public void exportToExcel(HttpServletResponse response,int year) {
        Map<String, Float> data1 = payrollService.calculateTotalExpensesByUser(year);
        List<User> data = userRepository.findAll();
        payrollExportToExcelService.exportToExcel(response, data, data1);
    }
}
