package service;

import managers.UserJsonManager;
import models.Instructor;
import models.Student;
import models.User;
import security.HashingService;

import java.util.List;

public class UserService {

    private UserJsonManager userJsonManager;
    private HashingService hashingService;

    public UserService() {
        this.userJsonManager = new UserJsonManager();
        this.hashingService = new HashingService();
    }

    // ✓ Check if email exists correctly
    public boolean emailExist(String email) {
        return userJsonManager.getByEmail(email) != null;
    }

    // ✓ Signup
    public User signup(String email, String username, String password, String role) throws Exception {
            if (email == null || username == null || password == null ||
                    email.isEmpty() || username.isEmpty() || password.isEmpty()) {

                throw new Exception("Invalid! All fields must be filled!");
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9-]+$")) {
                throw new Exception("Invalid email format!");
            }

            if (emailExist(email)) {
                throw new Exception("Email already exists!");
            }
            // hash password
            String passwordHash = hashingService.hash(password);
            User newUser;
            // Create Student
            if (role.equalsIgnoreCase("student")) {
                newUser = new Student(
                        generateId(),
                        username,
                        passwordHash,
                        email
                );
            }

            // Create Instructor
            else if (role.equalsIgnoreCase("instructor")) {
                newUser = new Instructor(
                        generateId(),
                        username,
                        passwordHash,
                        email
                );
            }

            else {
                throw new Exception("Invalid role!");
            }
            // Save user
            userJsonManager.add(newUser);
            return newUser;

    }

    // ✓ Login
    public User login(String email, String password) throws Exception {
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                throw new Exception("All fields must be filled!");
            }
            User savedUser = userJsonManager.getByEmail(email);
            if (savedUser == null) {
                throw new Exception("Email not found!");
            }

            if (!hashingService.verify(password, savedUser.getHashPass())) {
                throw new Exception("Invalid password!");
            }
            return savedUser;
    }

    public void logout() {
        userJsonManager.save();
    }
    public int generateId() {
        List<User> users = userJsonManager.getAll();

        if (users == null) {
            return 1;
        }
        int maxId = 0;
        for (User u : users) {
            if (u.getUserId() > maxId) {
                maxId = u.getUserId();
            }
        }
        return maxId + 1;
    }

}
