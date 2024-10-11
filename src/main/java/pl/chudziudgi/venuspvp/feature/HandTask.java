package pl.chudziudgi.venuspvp.feature;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import pl.chudziudgi.venuspvp.config.PluginConfiguration;
import pl.chudziudgi.venuspvp.user.User;
import pl.chudziudgi.venuspvp.user.UserService;

public class HandTask extends BukkitRunnable {

    private final UserService userService;
    private final HandItemManager handItemManager;
    private final PluginConfiguration pluginConfiguration;

    public HandTask(UserService userService, HandItemManager handItemManager, PluginConfiguration pluginConfiguration) {
        this.userService = userService;
        this.handItemManager = handItemManager;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userService.findUserFromUuid(player.getUniqueId());
            if (user.getHandItem() == null) return;
            for (HandItem handItem : this.pluginConfiguration.feature.handItems) {
                if (handItem.getItemStack().getType().name().equals(user.getHandItem())) {
                    if (player.getInventory().getItemInOffHand().equals(handItem.getItemStack())) return;
                    this.handItemManager.setHand(player, handItem.getItemStack());
                }
            }
        });
    }
}
