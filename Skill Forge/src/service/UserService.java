package service;
import managers.UserJsonManager;
import models.Instructor;
import models.Student;
import models.User;
import security.HashingService;

import java.util.ArrayList;
import java.util.UUID;

public class UserService {
    private UserJsonManager userJsonManager;
    private HashingService hashingService;

    public UserService(){
        userJsonManager = new UserJsonManager();
        hashingService = new HashingService();
    }

    public boolean emailExist(String email){
        return userJsonManager.getUserByEmail != null;
    }
    public String generateUserId(){
        return UUID.randomUUID().toString();
    }

    public User signup(String email, String username, String password, String role){
        try {
            if (email==null || email.isEmpty() || username==null || username.isEmpty() || password==null || password.isEmpty()){
                System.out.println("Invalid! All fields should be filled!");
                return null;
            }
            else if (emailExist(email)){
                System.out.println("Invalid! Email is already existed!");
                return null;
            }
            else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9-]+$")) {         //    .........to be cont
                System.out.println("Invalid! Write the correct format of the email!");
                return null;
            }

            String passwordHash = hashingService.hash(password);
            String userId = generateUserId();

            User newUser;
            if (role.equalsIgnoreCase("student")){
                newUser = new Student(userId, role, username, email, passwordHash, enrolledCourses, progress);
            }
            else if (role.equalsIgnoreCase("instructor")){
                newUser = new Instructor(userId, role, username, email, passwordHash, createdCourses);
            }
            else {
                System.out.println("Invalid role !");
                return null;
            }

            ArrayList<User>users = userJsonManager.loadFromFile();
            users.add(newUser);
            userJsonManager.saveToFile();
            return newUser;

        }
        catch (Exception e){
            System.out.println("Sign up failed! "+e.getMessage());
            return null;
        }
    }



    public User login(String email, String password){
        try {
            if (email==null || email.isEmpty() || password==null || password.isEmpty()){
                System.out.println("All fields must be filled!");
                return null;
            }
            User savedUser = userJsonManager.getUserByEmail(email);
            if(savedUser == null){
                System.out.println("Email not found !");
                return null;
            }
            String passwordHash = hashingService.hash(password);
            if(!savedUser.getPasswordHash().equals(passwordHash)){
                System.out.println("Wrong password !");
                return null;
            }
            if (savedUser.getRole().equalsIgnoreCase("student")){
                return new Student(
                        savedUser.getUserId(),
                        savedUser.getUsername(),
                        savedUser.getEmail(),
                        savedUser.getPasswordHash(),
                        savedUser.getEnrolledCourses(),
                        savedUser.getProgress()
                );
            }
            if (savedUser.getRole().equalsIgnoreCase("instructor")){
                return new Instructor(
                        savedUser.getUserId(),
                        savedUser.getUsername(),
                        savedUser.getEmail(),
                        savedUser.getPasswordHash(),
                        savedUser.getCreatedCousrses()
                );
            }
            System.out.println("Invalid role in database!");
            return null;
        }
        catch (Exception e){
            System.out.println("Log in failed! "+e.getMessage());
            return null;
        }
    }

    public void logout(){
        userJsonManager.saveToFile;
        System.out.println("Logged out successfully !");
    }

}
