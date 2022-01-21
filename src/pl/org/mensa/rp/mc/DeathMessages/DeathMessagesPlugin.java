package pl.org.mensa.rp.mc.DeathMessages;

import org.bukkit.plugin.java.JavaPlugin;

public class DeathMessagesPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
	}
}
