package pl.chudziudgi.venuspvp.user;

import pl.chudziudgi.venuspvp.config.DataBaseUserRepository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {
    private final DataBaseUserRepository dataBaseUserRepository;

    public Map<UUID, User> uuidUserMap = new ConcurrentHashMap<>();

    public UserService(DataBaseUserRepository dataBaseUserRepository) {
        this.dataBaseUserRepository = dataBaseUserRepository;

    }

    public void saveUser(User user) {
        this.uuidUserMap.remove(user.uuid());
        this.uuidUserMap.put(user.uuid(), user);
    }

    public void createUser(UUID uuid) {
        User value = new User(uuid);
        this.uuidUserMap.put(uuid, value);
        this.dataBaseUserRepository.addUser(value);
    }

    public void addUser(User user) {
        this.uuidUserMap.put(user.uuid(), user);
    }

    public User findUserFromUuid(UUID uuid) {
        return uuidUserMap.get(uuid);
    }

}
