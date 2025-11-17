package managers;
import models.User;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.ArrayList;


public class UserJsonManager extends JsonManager {
    private List<User> users;
    private static final Type USER_LIST_TYPE=new TypeToken<List<User>>(){}.getType();
    public UserJsonManager(String filePath){
        super(filePath);
        loadFromJson();
    }
    @Override
    public void loadFromJson(){
        this.users=readFile(USER_LIST_TYPE);
        if (this.users==null) {
            this.users=new ArrayList<>();
        }

    }
    @Override
    public void saveToJson(){
        writeFile(users);
    }

    public User findById(String id){
        for(User u:this.users){
            if(u.getUserId().equals(id)){
                return u;
            }
        }
        return null;
    }
    public User findByUsername(String username){
        for(User u:this.users){
            if(u.getUsername().equalsIgnoreCase(username)){
                return u;
            }
        }
        return null;
    }
    public User findByEmail(String email){
        for(User u:this.users){
            if(u.getEmail().equalsIgnoreCase(email)){
                return u;
            }
        }
        return null;
    }
    public void addUser(User user){
        this.users.add(user);
        saveToJson();
    }

}
