package pl.chudziudgi.venuspvp.feature;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import pl.chudziudgi.venuspvp.config.PluginConfiguration;
import pl.chudziudgi.venuspvp.user.User;
import pl.chudziudgi.venuspvp.user.UserService;

public class OffHandController implements Listener {

    private final HandItemManager handItemManager;
    private final UserService userService;
    private final PluginConfiguration pluginConfiguration;

    public OffHandController(HandItemManager handItemManager, UserService userService, PluginConfiguration pluginConfiguration) {
        this.handItemManager = handItemManager;
        this.userService = userService;
        this.pluginConfiguration = pluginConfiguration;
    }

    @EventHandler
    public void onSwapHandItems(PlayerSwapHandItemsEvent event) {
        if (event.getPlayer().hasPermission("venuspvp.switch.hand")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission("venuspvp.switch.hand")) return;
        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR && event.getSlot() == 40) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = userService.findUserFromUuid(event.getPlayer().getUniqueId());
        if (user == null) return;
        if (user.getHandItem() == null) return;
        for (HandItem handItem : this.pluginConfiguration.feature.handItems) {
            if (handItem.getItemStack().getType().toString().equals(user.getHandItem())) {
                this.handItemManager.setHand(event.getPlayer(), handItem.getItemStack());
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        User user = userService.findUserFromUuid(event.getPlayer().getUniqueId());
        if (user == null) return;
        if (user.getHandItem() == null) return;
        for (HandItem handItem : this.pluginConfiguration.feature.handItems) {
            if (handItem.getItemStack().getType().toString().equals(user.getHandItem())) {
                this.handItemManager.setHand(event.getPlayer(), handItem.getItemStack());
            }
        }
    }
}
