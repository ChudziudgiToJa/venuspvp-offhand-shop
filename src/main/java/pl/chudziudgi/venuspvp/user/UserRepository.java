package pl.chudziudgi.venuspvp.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public interface UserRepository {

    void addUser(User user);
    Collection<User> fetchAll();
}
