package com.StudentLibrary.Studentlibrary.Controllers;


import com.StudentLibrary.Studentlibrary.DTO.DashboardResponse;
import com.StudentLibrary.Studentlibrary.DTO.StudentDashboardResponse;
import com.StudentLibrary.Studentlibrary.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/admin")
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }

    @GetMapping("/student")
    public StudentDashboardResponse getStudentDashboard(){

        return dashboardService.getStudentDashboard();

    }

}
