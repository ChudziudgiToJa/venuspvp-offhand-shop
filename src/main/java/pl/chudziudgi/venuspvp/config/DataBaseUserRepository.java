package pl.chudziudgi.venuspvp.config;

import eu.okaeri.configs.OkaeriConfig;
import pl.chudziudgi.venuspvp.user.User;
import pl.chudziudgi.venuspvp.user.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataBaseUserRepository extends OkaeriConfig implements UserRepository {

    public Map<UUID, User> uuidUserMap = new ConcurrentHashMap<>();

    @Override
    public void addUser(User user) {
        this.uuidUserMap.put(user.uuid(), user);
        this.save();
    }

    @Override
    public Collection<User> fetchAll() {
        return new ArrayList<>(this.uuidUserMap.values());
    }
}
