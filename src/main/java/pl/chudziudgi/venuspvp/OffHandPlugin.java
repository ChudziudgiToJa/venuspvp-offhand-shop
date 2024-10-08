package pl.chudziudgi.venuspvp;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteCommandsBukkit;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.chudziudgi.venuspvp.command.ShopCommand;
import pl.chudziudgi.venuspvp.config.DataBaseUserRepository;
import pl.chudziudgi.venuspvp.config.PluginConfiguration;
import pl.chudziudgi.venuspvp.feature.HandItemInventory;
import pl.chudziudgi.venuspvp.feature.HandItemManager;
import pl.chudziudgi.venuspvp.feature.OffHandController;
import pl.chudziudgi.venuspvp.user.UserController;
import pl.chudziudgi.venuspvp.user.UserService;
import pl.chudziudgi.venuspvp.util.InventoryBuilder;

import java.io.File;

public final class OffHandPlugin extends JavaPlugin {

    @Getter
    public static Economy econ = null;
    private PluginConfiguration pluginConfiguration;
    private UserService userService;
    @Getter
    private HandItemInventory handItemInventory;
    private DataBaseUserRepository dataBaseUserRepository;
    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onLoad() {
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.dataBaseUserRepository = ConfigManager.create(DataBaseUserRepository.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(this.getDataFolder(), "database.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });

        this.userService = new UserService(this.dataBaseUserRepository);
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Brak valuta na serwie dodaj go!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        HandItemManager handItemManager = new HandItemManager();
        this.handItemInventory = new HandItemInventory(this.pluginConfiguration, userService);

        this.dataBaseUserRepository.fetchAll().forEach(user -> {
            this.userService.addUser(user);
        });

        this.liteCommands = LiteCommandsBukkit.builder()

                .settings(settings -> settings.fallbackPrefix("venuspvp-shop").nativePermissions(false)).commands(new ShopCommand(this.handItemInventory)).build();

        this.getServer().getPluginManager().registerEvents(new InventoryBuilder.Listeners(), this);

        this.getServer().getPluginManager().registerEvents(new OffHandController(handItemManager, this.userService, this.pluginConfiguration), this);
        this.getServer().getPluginManager().registerEvents(new UserController(userService), this);
    }

    @Override
    public void onDisable() {
        this.userService.uuidUserMap.forEach((uuid, user) -> {
            this.dataBaseUserRepository.addUser(user);
        });
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
