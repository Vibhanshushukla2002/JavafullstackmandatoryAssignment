package com.StudentLibrary.Studentlibrary.Services;

import com.StudentLibrary.Studentlibrary.Model.Card;
import com.StudentLibrary.Studentlibrary.Model.Student;
import com.StudentLibrary.Studentlibrary.Repositories.CardRepository;
import com.StudentLibrary.Studentlibrary.Repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.StudentLibrary.Studentlibrary.DTO.StudentProfileDTO;
import com.StudentLibrary.Studentlibrary.Model.User;
import com.StudentLibrary.Studentlibrary.Repositories.UserRepository;
import com.StudentLibrary.Studentlibrary.DTO.StudentUpdateProfileRequest;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StudentService {

    Logger logger= LoggerFactory.getLogger(StudentService.class);


    @Autowired
    StudentRepository studentRepository ;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardService cardService;


    @Autowired
    private UserRepository userRepository;




    public void createStudent (Student student){

        Card card=cardService.createCard(student);
        logger.info("The card for the student{} is created with the card details{}",student,card);


    }
    public int  updateStudent(Student student){
        return studentRepository.updateStudentDetails(student);


    }


    public void deleteStudent(int id ){

        cardService.deactivate(id);
        studentRepository.deleteCustom(id);

    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public long getStudentsCount() {
        return studentRepository.count();
    }
    public List<Student> searchStudent(String name) {

        return studentRepository.findByNameContainingIgnoreCase(name);

    }

    public Page<Student> getStudentsByPage(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return studentRepository.findAll(pageable);

    }

    public StudentProfileDTO getMyProfile(String username) {

        // 1. Logged-in username se User find karo
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 2. User ke email se Student find karo
        Student student = studentRepository.findByEmailId(user.getEmail());

        if (student == null) {
            throw new RuntimeException("Student profile not found");
        }

        // 3. Student ka Library Card find karo
        Card card = student.getCard();

        if (card == null) {
            throw new RuntimeException("Library card not found");
        }

        // 4. DTO create karo
        StudentProfileDTO profile = new StudentProfileDTO();

        profile.setStudentId(student.getId());
        profile.setCardId(card.getId());

        profile.setName(student.getName());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());

        profile.setAge(student.getAge());
        profile.setCountry(student.getCountry());

        profile.setRole(user.getRole().name());
        profile.setCardStatus(card.getCardStatus().name());

        profile.setMemberSince(card.getCreatedOn());

        profile.setProfilePicture(user.getProfilePicture());

        return profile;
    }

    @Transactional
    public StudentProfileDTO updateMyProfile(
            String username,
            StudentUpdateProfileRequest request
    ) {

        // Step 1: Logged-in username se User find karo
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Step 2: User ke email se Student find karo
        Student student =
                studentRepository.findByEmailId(user.getEmail());

        if (student == null) {
            throw new RuntimeException("Student profile not found");
        }

        // Step 3: Sirf allowed fields update karo
        student.setName(request.getName());
        student.setAge(request.getAge());
        student.setCountry(request.getCountry());

        // Step 4: Updated Student save karo
        Student updatedStudent =
                studentRepository.save(student);

        // Step 5: Card fetch karo
        Card card = updatedStudent.getCard();

        if (card == null) {
            throw new RuntimeException("Library card not found");
        }

        // Step 6: Updated profile response create karo
        StudentProfileDTO profile = new StudentProfileDTO();

        profile.setStudentId(updatedStudent.getId());
        profile.setCardId(card.getId());

        profile.setName(updatedStudent.getName());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());

        profile.setAge(updatedStudent.getAge());
        profile.setCountry(updatedStudent.getCountry());

        profile.setRole(user.getRole().name());
        profile.setCardStatus(card.getCardStatus().name());

        profile.setMemberSince(card.getCreatedOn());

        profile.setProfilePicture(user.getProfilePicture());

        return profile;
    }

    @Transactional
    public StudentProfileDTO uploadProfilePicture(
            String username,
            MultipartFile file
    ) throws IOException {

        // 1. Logged-in user find karo
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 2. Check karo file empty to nahi hai
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please select a profile picture");
        }

        // 3. File type validate karo
        String contentType = file.getContentType();

        if (contentType == null ||
                (!contentType.equals("image/jpeg") &&
                        !contentType.equals("image/png"))) {

            throw new RuntimeException(
                    "Only JPG, JPEG and PNG images are allowed"
            );
        }

        // 4. Maximum size = 5 MB
        long maxFileSize = 5 * 1024 * 1024;

        if (file.getSize() > maxFileSize) {
            throw new RuntimeException(
                    "Profile picture must be smaller than 5 MB"
            );
        }

        // 5. Original filename se extension nikalo
        String originalFilename = file.getOriginalFilename();

        String extension = "";

        if (originalFilename != null &&
                originalFilename.contains(".")) {

            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        // 6. Unique filename create karo
        String uniqueFilename =
                UUID.randomUUID().toString() + extension;

        // 7. Upload directory create karo
        Path uploadDirectory = Paths.get(
                "uploads",
                "profile-pictures"
        );

        Files.createDirectories(uploadDirectory);

        // 8. Complete destination path
        Path destinationPath =
                uploadDirectory.resolve(uniqueFilename);

        // 9. Image disk par save karo
        Files.copy(
                file.getInputStream(),
                destinationPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        // 10. Purani profile picture ka filename save karo
        String oldProfilePicture = user.getProfilePicture();

        // 11. New filename database mein save karo
        user.setProfilePicture(uniqueFilename);
        userRepository.save(user);

        // 12. Agar purani image thi to use delete karo
        if (oldProfilePicture != null &&
                !oldProfilePicture.trim().isEmpty()) {

            try {

                Path oldFilePath =
                        uploadDirectory.resolve(oldProfilePicture);

                Files.deleteIfExists(oldFilePath);

            } catch (IOException exception) {

                logger.warn(
                        "Unable to delete old profile picture: {}",
                        oldProfilePicture
                );
            }
        }

        // 13. Updated profile return karo
        return getMyProfile(username);
    }


}
