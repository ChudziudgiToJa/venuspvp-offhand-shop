package pl.chudziudgi.venuspvp.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.chudziudgi.venuspvp.feature.HandItem;
import pl.chudziudgi.venuspvp.util.ItemBuilder;

import java.util.List;

public class PluginConfiguration extends OkaeriConfig {

    public Feature feature = new Feature();

    public static class Feature extends OkaeriConfig {

        @Comment("#")
        @Comment("#UWAGA MAX ITEMÓW to 43-44 idk")
        @Comment("#")
        public String guiName = "&bsklep";
        public String noMoneyMessage = "&cnie masz tyle kasy mordeczko /kasa u bociana";
        public String buyMessage = "&aZakupiono item";
        public String useItemMessage = "&ado łapy myk";
        public String putOffHandMessage = "&cI łapa pusta";

        @Comment("Item do zamykania gui")
        public ItemStack closeGuiItem = new ItemBuilder(Material.RABBIT_FOOT)
                .setTitle("&ccofnij")
                .build();

        @Comment("Item do sciagania itemu z łapki")
        public ItemStack putOffGuiItem = new ItemBuilder(Material.RED_DYE)
                .setTitle("&csciągnij item z łapki ziomek")
                .build();

        @Comment("Item do do eq itemków w łapie moreczko")
        public ItemStack inventoryGuiItem = new ItemBuilder(Material.CHEST)
                .setTitle("&6ekwipunek")
                .build();

        @Comment("Item do cofania w eq")
        public ItemStack backGuiItem = new ItemBuilder(Material.FURNACE_MINECART)
                .setTitle("&6cofka")
                .build();


        @Comment("##### W SKLEPIE #####")
        @Comment("PLACEHOLDERS")
        @Comment("")
        @Comment("%name% - nazwa itemu")
        @Comment("")
        public String itemInInventoryName = "&8item: &c%name%";
        public List<String> itemInInventoryLore = List.of(
                "",
                "&aKliknij aby zalozyc"
        );


        @Comment("")
        @Comment("##### W SKLEPIE #####")
        @Comment("PLACEHOLDERS")
        @Comment("")
        @Comment("%name% - nazwa itemu")
        @Comment("%price% - cena itemu")
        @Comment("")
        public String itemInShopName = "&8item: &c%name%";
        public List<String> itemInShopLore = List.of(
                "",
                "&7Koszt: %price%",
                "",
                "&aKliknij aby kupić"
        );

        public List<HandItem> handItems = List.of(
                new HandItem(
                        new ItemBuilder(Material.BLAZE_ROD).build(),
                        4.0
                ),
                new HandItem(
                        new ItemBuilder(Material.CHORUS_FLOWER).build(),
                        3.0
                ),
                new HandItem(
                        new ItemBuilder(Material.GOAT_HORN).build(),
                        2.0
                )
        );
    }
}
