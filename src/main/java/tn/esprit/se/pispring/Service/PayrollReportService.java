package tn.esprit.se.pispring.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PayrollReportService {
    public void exportToExcel(HttpServletResponse response, int year);
}
