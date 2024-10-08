package pl.chudziudgi.venuspvp.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class ChatUtil {

    public static String fixColor(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> fixColor(final List<String> strings) {
        strings.replaceAll(ChatUtil::fixColor);
        return strings;
    }

    public static void sendActionbar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(fixColor(message)));
    }

    public static void sendTitle(Player player, String title, String subTitle, int fadeInTime, int stayTime, int fadeOutTime) {
        player.sendTitle(fixColor(title), fixColor(subTitle), fadeInTime, stayTime, fadeOutTime);
    }
}