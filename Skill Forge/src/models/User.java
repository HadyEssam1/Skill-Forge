package models;
import java.util.Arrays;

public abstract class User {
    
    protected final String role;
    protected final int id;
    protected String username;
    protected String email;
    protected String hashPass;


public static final String[] ROLES={"instructor","student"};

    public User (String role,int id,String username,String pass,String email){
        validateUser(role,id,username,pass,email);
        this.role=role;
        this.id=id;
        this.username=username;
        this.hashPass=security.HashingService.hash(pass);
        this.email=email;
    }
    
    public static void validateUser(String role,int id,String username,String pass,String email){
        if (id < 0) 
            throw new IllegalArgumentException("User id must be positive number!");
        if (username == null || username.isEmpty()||username.matches(".*\\d.*"))
            throw new IllegalArgumentException("Invalid username");
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9-]+$"))
            throw new IllegalArgumentException("Invalid email");
        if (role==null||role.isEmpty()||!Arrays.asList(ROLES).contains(role.toLowerCase()))
            throw new IllegalArgumentException("Invalid role");
        if (pass==null||pass.length()<8)
        throw new IllegalArgumentException("Password must be atleast 8 characters");
    };

    public int getUserId(){return id;}
    public String getRole(){return role;}
    public String getUsername(){return username;}
    public String getEmail(){return email;}
    public String getHashPass(){return hashPass;}

    public void setUsername(String username){this.username=username;}
    public void setEmail(String email){this.email=email;}
    public void setPass(String pass){this.hashPass=security.HashingService.hash(pass);}
}
