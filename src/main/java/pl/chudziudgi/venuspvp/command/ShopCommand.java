package pl.chudziudgi.venuspvp.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chudziudgi.venuspvp.feature.HandItemInventory;

@Command(name = "sklep")
public class ShopCommand {

    private final HandItemInventory handItemInventory;

    public ShopCommand(HandItemInventory handItemInventory) {
        this.handItemInventory = handItemInventory;
    }

    @Execute
    void command(@Context CommandSender sender) {
        this.handItemInventory.openShop((Player) sender);
    }
}
