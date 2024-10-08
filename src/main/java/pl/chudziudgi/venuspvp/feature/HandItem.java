package pl.chudziudgi.venuspvp.feature;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@Getter
public class HandItem implements Serializable {

    private final ItemStack itemStack;
    private final double price;

    public HandItem(ItemStack itemStack, double price) {
        this.itemStack = itemStack;
        this.price = price;
    }
}
