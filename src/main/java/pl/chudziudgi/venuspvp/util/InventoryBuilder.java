package pl.chudziudgi.venuspvp.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.function.Consumer;

public class InventoryBuilder implements InventoryHolder {

    private final Inventory inventory;
    private final HashMap<Integer, Consumer<InventoryClickEvent>> actions = new HashMap<>();

    public InventoryBuilder(final String name, final int size ) {
        inventory = Bukkit.createInventory(this, size, ChatUtil.fixColor(name));
    }

    public InventoryBuilder(final String name,final Player player, final int size ) {
        inventory = Bukkit.createInventory(player, size, ChatUtil.fixColor(name));
    }

    public InventoryBuilder(final String name, final InventoryType type) {
        inventory = Bukkit.createInventory(this, type, ChatUtil.fixColor(name));
    }

    public void setItem(int slot, ItemStack itemStack) {
        inventory.setItem(slot, itemStack);
    }

    public final void setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        setItem(slot, itemStack);
        actions.put(slot, consumer);
    }

    public void open(final Player player, final ItemStack itemStack) {
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack item = inventory.getItem(i);
            if (item == null) inventory.setItem(i, itemStack);
        }
        open(player);
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void handleClickEvent(InventoryClickEvent event) {
        final InventoryAction action = event.getAction();
        if (action != InventoryAction.COLLECT_TO_CURSOR && action != InventoryAction.MOVE_TO_OTHER_INVENTORY && (action != InventoryAction.NOTHING || event.getClick() == ClickType.MIDDLE)) {
            if (event.getRawSlot() < inventory.getSize()) {
                final Consumer<InventoryClickEvent> consumer = actions.get(event.getSlot());
                if (consumer != null) consumer.accept(event);
                event.setCancelled(true);
            }
        } else event.setCancelled(true);
    }

    public void handleDragEvent(InventoryDragEvent event) {
        for (Integer slot : event.getRawSlots()) {
            if (slot < inventory.getSize()) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public static class Listeners implements Listener {
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            final InventoryHolder it = event.getInventory().getHolder();
            if (it instanceof InventoryBuilder) {
                ((InventoryBuilder) it).handleClickEvent(event);
            }
        }

        @EventHandler
        public final void onInventoryDrag(InventoryDragEvent event) {
            final InventoryHolder it = event.getInventory().getHolder();
            if (it instanceof InventoryBuilder)
                ((InventoryBuilder) it).handleDragEvent(event);
        }
    }
}