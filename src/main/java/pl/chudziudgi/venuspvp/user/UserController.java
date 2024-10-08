package pl.chudziudgi.venuspvp.user;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class UserController implements Listener {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user  = userService.findUserFromUuid(player.getUniqueId());

        if (user == null) {
            userService.uuidUserMap.put(player.getUniqueId(), new User(player.getUniqueId()));
            return;
        }
    }
}
