package pl.chudziudgi.venuspvp.feature;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.venuspvp.config.PluginConfiguration;
import pl.chudziudgi.venuspvp.user.User;
import pl.chudziudgi.venuspvp.user.UserService;
import pl.chudziudgi.venuspvp.util.ChatUtil;
import pl.chudziudgi.venuspvp.util.InventoryBuilder;
import pl.chudziudgi.venuspvp.util.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static pl.chudziudgi.venuspvp.OffHandPlugin.econ;

public class HandItemInventory {

    private final PluginConfiguration pluginConfiguration;
    private final UserService userService;


    private final HandItemManager handItemManager = new HandItemManager();

    public HandItemInventory(PluginConfiguration pluginConfiguration, UserService userService) {
        this.pluginConfiguration = pluginConfiguration;
        this.userService = userService;
    }

    public void openShop(final Player player) {
        final InventoryBuilder inventory = new InventoryBuilder(ChatUtil.fixColor(this.pluginConfiguration.feature.guiName), 9 * 6);
        User user = this.userService.findUserFromUuid(player.getUniqueId());

        if (user == null) {
            return;
        }

        Integer[] glassBlueSlots = {45, 46, 47, 48, 49, 50, 51, 52, 53};

        Arrays.stream(glassBlueSlots).forEach(slot -> inventory.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setTitle("").build()));

        inventory.setItem(47, this.pluginConfiguration.feature.inventoryGuiItem, event -> {
            this.openInventory(player);
        });

        inventory.setItem(49, this.pluginConfiguration.feature.closeGuiItem, event -> {
            player.closeInventory();
        });

        inventory.setItem(51, this.pluginConfiguration.feature.putOffGuiItem, event -> {
            user.setHandItem(null);
            this.handItemManager.setHand(player, new ItemStack(Material.AIR));
            ChatUtil.sendTitle(player, "", this.pluginConfiguration.feature.putOffHandMessage, 20, 50, 20);
            player.closeInventory();
        });

        int itemIndex = 0;

        for (HandItem handItem : this.pluginConfiguration.feature.handItems) {
            if (user.getHandItems().contains(handItem.getItemStack().getType().name())) {
                continue;
            }
            ItemStack shopItem = new ItemBuilder(handItem.getItemStack()).setTitle(this.pluginConfiguration.feature.itemInShopName.replace("%name%", handItem.getItemStack().getType().name())).addLore(this.pluginConfiguration.feature.itemInShopLore.stream().map(lore -> lore.replace("%price%", String.valueOf(handItem.getPrice()))).collect(Collectors.toList())).build();

            inventory.setItem(itemIndex++, shopItem, event -> {
                EconomyResponse response = econ.withdrawPlayer(player, handItem.getPrice());

                if (response.transactionSuccess()) {
                    user.setHandItem(handItem.getItemStack().getType().name());
                    user.getHandItems().add(handItem.getItemStack().getType().name());
                    this.userService.saveUser(user);
                    player.closeInventory();
                    ChatUtil.sendTitle(player, "", this.pluginConfiguration.feature.buyMessage, 20, 50, 20);
                    this.handItemManager.setHand(player, new ItemStack(handItem.getItemStack().getType()));
                } else {
                    ChatUtil.sendTitle(player, "", this.pluginConfiguration.feature.noMoneyMessage, 20, 50, 20);
                    player.closeInventory();
                }
            });
        }
        inventory.open(player);
    }


    public void openInventory(final Player player) {
        final InventoryBuilder inv = new InventoryBuilder(ChatUtil.fixColor(this.pluginConfiguration.feature.guiName), 9 * 6);
        User user = this.userService.findUserFromUuid(player.getUniqueId());
        if (user == null) {
            return;
        }

        Integer[] glassBlueSlots = new Integer[]{45, 46, 47, 48, 49, 50, 51, 52, 53};

        Arrays.stream(glassBlueSlots).forEach(slot -> inv.setItem(slot, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setTitle("").build()));

        inv.setItem(48, this.pluginConfiguration.feature.backGuiItem, event -> {
            this.openShop(player);
        });

        if (user.getHandItem() != null) {
            inv.setItem(50, this.pluginConfiguration.feature.putOffGuiItem, event -> {
                this.handItemManager.setHand(player, new ItemStack(Material.AIR));
                user.setHandItem(null);
                ChatUtil.sendTitle(player, "", this.pluginConfiguration.feature.putOffHandMessage, 20, 50, 20);
                player.closeInventory();
            });
        }

        int i = 0;
        for (HandItem handItem : this.pluginConfiguration.feature.handItems) {

            if (user.getHandItem().equals(handItem.getItemStack().getType().name())) {
                continue;
            }

            if (!user.getHandItems().contains(handItem.getItemStack().getType().name())) {
                continue;
            }

            inv.setItem(i, new ItemBuilder(handItem.getItemStack()).setTitle(this.pluginConfiguration.feature.itemInInventoryName.replaceAll("%name%", handItem.getItemStack().getType().name())).addLore(new ArrayList<>(this.pluginConfiguration.feature.itemInInventoryLore.stream().map(string -> string.replaceAll("%price%", String.valueOf(handItem.getPrice()))).collect(Collectors.toList()))).build(), event -> {
                user.setHandItem(handItem.getItemStack().getType().name());
                this.userService.saveUser(user);
                player.closeInventory();
                ChatUtil.sendTitle(player, "", this.pluginConfiguration.feature.useItemMessage, 20, 50, 20);
                this.handItemManager.setHand(player, new ItemStack(handItem.getItemStack().getType()));
            });
            i++;
        }

        inv.open(player);
    }
}
