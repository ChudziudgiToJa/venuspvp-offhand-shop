package pl.chudziudgi.venuspvp.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.itemStack = is;
    }

    public ItemBuilder(Material m, int amount) {
        itemStack = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, short data) {
        itemStack = new ItemStack(m, amount, data);
    }

    public ItemBuilder(Material m, short data) {
        itemStack = new ItemStack(m, 1, data);
    }

    public ItemBuilder addLore(final String... lores) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            List<String> loreList = new ArrayList<>(ChatUtil.fixColor(Arrays.asList(lores)));
            itemMeta.setLore(loreList);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder addLore(final List<String> loreList) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            List<String> mutableLoreList = new ArrayList<>(ChatUtil.fixColor(loreList));
            itemMeta.setLore(mutableLoreList);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }


    public ItemBuilder addEnchant(final Enchantment enchant, final int level) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchant, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemBuilder setTitle(final String title) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatUtil.fixColor(title));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setGlow(final boolean b) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (b) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    // Removed the ArrayList specific method
    // Replaced with the more flexible List<String> method

    public final ItemBuilder setHeadOwner(final String newowner) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        ((SkullMeta) itemMeta).setOwner(newowner);
        itemStack.setItemMeta(itemMeta);
        return this;
    }
}