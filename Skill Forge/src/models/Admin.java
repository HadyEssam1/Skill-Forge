package models;

public class Admin extends User{
    public Admin( int id, String username, String pass, String email) {
        super("admin", id, username, pass, email);
    }
    public Admin() {
    }
}
