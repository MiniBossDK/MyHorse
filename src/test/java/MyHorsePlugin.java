import org.bukkit.plugin.java.JavaPlugin;

public class MyHorsePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("MyHorsePlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MyHorsePlugin has been disabled!");
    }

}
