package managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Admin;
import models.Instructor;
import models.Student;
import models.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserJsonManager extends JsonManager<User> {

    public UserJsonManager() throws Exception {
        super("data/users.json", null);
    }
    @Override
    public final ArrayList<User> load() throws Exception {
        try {
            File file= new File(filePath);
            if(!file.exists())
            {
                throw new Exception("file not found");
            }
            else if (file.length()==0)
            {
                data =new ArrayList<>();
            }
            else {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(file);
            List<User> list = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                String role = node.get("role").asText().toLowerCase();
                User user;
                switch (role) {
                    case "student" ->
                            user = mapper.treeToValue(node, Student.class);
                    case "instructor" ->
                            user = mapper.treeToValue(node, Instructor.class);
                    case "admin" ->
                            user = mapper.treeToValue(node, Admin.class) ;
                    default -> {
                        continue;
                    }
                }
                list.add(user);
            }
            data = list;
        }}
        catch (Exception e)
        {
            throw new Exception("Error parsing JSON file: " + filePath + "\n" + e.getMessage());

        }
        return null;
    }
    public User getById(int id) {
        for (User u : data) {
            if (u.getUserId() == id)
                return u;
        }
        return null;
    }

    public User getByUsername(String username) {
        for (User u : data) {
            if (u.getUsername().equalsIgnoreCase(username))
                return u;
        }
        return null;
    }
    public User getByEmail(String email) {
        for (User u : data) {
            if (u.getEmail().equalsIgnoreCase(email))
                return u;
        }
        return null;
    }
}
