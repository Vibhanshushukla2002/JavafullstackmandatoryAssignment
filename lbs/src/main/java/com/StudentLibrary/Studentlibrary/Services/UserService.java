package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.StudentLibrary.Studentlibrary.Model.User;
import com.StudentLibrary.Studentlibrary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.StudentLibrary.Studentlibrary.DTO.LoginRequest;
import com.StudentLibrary.Studentlibrary.Model.Student;
import org.springframework.transaction.annotation.Transactional;
import com.StudentLibrary.Studentlibrary.DTO.RegisterRequest;



@Service
public class UserService {
    @Autowired
   private  UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentService studentService;

    @Transactional
    public User save(RegisterRequest request) {

        // Step 1: Check duplicate username
        User existing = userRepository.findByUsername(request.getUsername());

        if (existing != null) {
            throw new RuntimeException("Username already exists");
        }

        // Step 2: Validate email
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        // Step 3: Check duplicate email
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already exists");
        }

        // Step 4: Create User object
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(UserRole.STUDENT);

        // Step 5: Save User
        User savedUser = userRepository.save(user);

        // Step 6: Create Student automatically
        Student student = new Student();

        student.setName(request.getFullName());
        student.setEmailId(request.getEmail());
        student.setAge(request.getAge());
        student.setCountry(request.getCountry());

        // Step 7: Student + Card creation
        studentService.createStudent(student);

        return savedUser;
    }
    public User findByUsername(String usernameOrEmail) {

        if (usernameOrEmail.contains("@")) {
            return userRepository.findByEmail(usernameOrEmail);
        }

        return userRepository.findByUsername(usernameOrEmail);
    }
    public boolean login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            return false;
        }

        return passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );
    }
    public String findRole(String username){

        User user = userRepository.findByUsername(username);

        return user.getRole().name();

    }

}
