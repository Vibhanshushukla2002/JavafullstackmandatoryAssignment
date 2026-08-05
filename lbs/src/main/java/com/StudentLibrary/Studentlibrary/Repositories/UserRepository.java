package com.StudentLibrary.Studentlibrary.Repositories;


import com.StudentLibrary.Studentlibrary.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserRepository extends JpaRepository<User ,Integer> {

    User findByUsername(String username);
    User findByEmail(String email);


}
