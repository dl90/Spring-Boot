package local.restweb.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class UserDAO {

    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Foo", new Date(), "Foo msg", null));
        users.add(new User(2, "Bar", new Date(), null, "Bar msg"));
        users.add(new User(3, "Baz", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) throws UserNotFoundException {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        throw new UserNotFoundException("id=%d".formatted(id));
    }

    public int add(User user) {
        user.setId(users.size() + 1);
        users.add(user);
        return user.getId();
    }

    public User delete(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return user;
            }
        }
        return null;
    }

}
