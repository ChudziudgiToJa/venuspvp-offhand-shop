package pl.chudziudgi.venuspvp.feature;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HandItemManager {

    public void setHand(Player player, ItemStack itemStack) {
        player.getInventory().setItemInOffHand(itemStack);
    }
}
