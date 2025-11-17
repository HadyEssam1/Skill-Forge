package managers;

import com.fasterxml.jackson.core.type.TypeReference;
import models.User;
import java.util.List;

public class UserJsonManager extends JsonManager<User> {

    public UserJsonManager() {
        super("data/users.json", new TypeReference<List<User>>() {});
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

    public void update(User updatedUser) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getUserId() == updatedUser.getUserId()) {
                data.set(i, updatedUser);
                save();
                return;
            }
        }
    }
}
