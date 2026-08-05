package com.StudentLibrary.Studentlibrary.Controllers;



import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.StudentLibrary.Studentlibrary.Model.Student;
import com.StudentLibrary.Studentlibrary.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.StudentLibrary.Studentlibrary.DTO.StudentProfileDTO;
import org.springframework.security.core.Authentication;
import com.StudentLibrary.Studentlibrary.DTO.StudentUpdateProfileRequest;


import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:5174"})
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;


    @PostMapping("/createStudent")
    public ResponseEntity createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);
        return new ResponseEntity("Student Successfully added to the system", HttpStatus.CREATED);

    }

    @GetMapping("/getStudents")
    public ResponseEntity getStudents() {

        return new ResponseEntity(
                studentService.getStudents(),
                HttpStatus.OK
        );
    }

    @PutMapping("/updateStudent")
    public ResponseEntity updateStudent(@Valid @RequestBody Student student){
        int lines=studentService.updateStudent(student);
        return new ResponseEntity("Student updated",HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudent")
    public ResponseEntity deleteStudent( @RequestParam("id")int id){
        studentService.deleteStudent(id);
        return new ResponseEntity("student successfully deleted!!",HttpStatus.OK);
    }

    @GetMapping("/students/count")
    public ResponseEntity<Long> getStudentsCount() {
        return new ResponseEntity<>(
                studentService.getStudentsCount(),
                HttpStatus.OK
        );
    }
    @GetMapping("/searchStudent")
    public ResponseEntity<List<Student>> searchStudent(
            @RequestParam String name
    ) {

        return ResponseEntity.ok(
                studentService.searchStudent(name)
        );

    }

    @GetMapping("/getStudentsByPage")
    public ResponseEntity<Page<Student>> getStudentsByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {

        return ResponseEntity.ok(
                studentService.getStudentsByPage(page, size)
        );

    }

    @GetMapping("/student/profile")
    public ResponseEntity<StudentProfileDTO> getMyProfile(
            Authentication authentication
    ) {

        String username = authentication.getName();

        StudentProfileDTO profile =
                studentService.getMyProfile(username);

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/student/profile")
    public ResponseEntity<StudentProfileDTO> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody StudentUpdateProfileRequest request
    ) {

        String username = authentication.getName();

        StudentProfileDTO updatedProfile =
                studentService.updateMyProfile(
                        username,
                        request
                );

        return ResponseEntity.ok(updatedProfile);
    }
    @PostMapping(
            value = "/student/profile/picture",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<StudentProfileDTO> uploadProfilePicture(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        String username = authentication.getName();

        StudentProfileDTO updatedProfile =
                studentService.uploadProfilePicture(
                        username,
                        file
                );

        return ResponseEntity.ok(updatedProfile);
    }









}
