package models;

import java.util.Arrays;


public abstract class User {

    protected String role;
    protected int id;
    protected String username;
    protected String email;
    protected String hashPass;

    public static final String[] ROLES = {"instructor", "student","admin"};

    public User() {}

    public User(String role, int id, String username, String pass, String email) {
        validateRole(role);
        validateId(id);
        validateUsername(username);
        validatePassword(pass);
        validateEmail(email);

        this.role = role;
        this.id = id;
        this.username = username;
        this.hashPass = pass;
        this.email = email;
    }

    private static void validateRole(String role) {
        if (role == null || role.isEmpty() || !Arrays.asList(ROLES).contains(role.toLowerCase()))
            throw new IllegalArgumentException("Invalid role");
    }

    private static void validateId(int id) {
        if (id < 0)
            throw new IllegalArgumentException("User id must be positive number!");
    }

    private static void validateUsername(String username) {
        if (username == null || username.isEmpty() || username.matches(".*\\d.*"))
            throw new IllegalArgumentException("Invalid username");
    }

    private static void validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9-]+$"))
            throw new IllegalArgumentException("Invalid email");
    }

    private static void validatePassword(String pass) {
        if (pass == null || pass.length() < 8)
            throw new IllegalArgumentException("Password must be at least 8 characters");
    }

    public int getUserId() { return id; }
    public String getRole() { return role; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getHashPass() { return hashPass; }
    public void setUserId(int id) {
        validateId(id);
        this.id = id;
    }
    public void setRole(String role) {
        validateRole(role);
        this.role = role;
    }

    public void setUsername(String username) {
        validateUsername(username);
        this.username = username;
    }
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }
    public void setPass(String pass) {
        validatePassword(pass);
        this.hashPass = pass;
    }
}
