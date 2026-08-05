package com.StudentLibrary.Studentlibrary.Controllers;


import com.StudentLibrary.Studentlibrary.DTO.RegisterRequest;
import com.StudentLibrary.Studentlibrary.Model.User;
import com.StudentLibrary.Studentlibrary.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.StudentLibrary.Studentlibrary.DTO.LoginRequest;
import com.StudentLibrary.Studentlibrary.DTO.LoginResponse;
import com.StudentLibrary.Studentlibrary.Security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        try {

            userService.save(request);

            return ResponseEntity.ok(
                    "User Registered Successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userService.findByUsername(request.getUsername());

        String token = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(
                new LoginResponse(
                        token,
                        user.getUsername(),
                        user.getRole().name()
                )
        );
    }
}
