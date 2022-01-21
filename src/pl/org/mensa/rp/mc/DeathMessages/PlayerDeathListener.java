package pl.org.mensa.rp.mc.DeathMessages;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	static final DateFormat date_formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	DeathMessagesPlugin plugin;
	
	public PlayerDeathListener(DeathMessagesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (e.getEntity().hasPermission("deathmessages.exempt")) {
			e.setDeathMessage("");
		}
		else {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (player.hasPermission("deathmessages.info")) {
					player.sendMessage(("&c&l" + e.getDeathMessage()).replaceAll("&", "§"));
				}
			}
			
			try {
				plugin.getDataFolder().mkdirs();
				File log_file = new File(plugin.getDataFolder().getPath() + "/logs.txt");
				if (!log_file.exists()) log_file.createNewFile();
				
				Files.write(log_file.toPath(), (date_formatter.format(Calendar.getInstance().getTime()) + " " + e.getDeathMessage() + "\n").getBytes(), StandardOpenOption.APPEND);
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			
			System.out.println(("&c&l" + e.getDeathMessage()).replaceAll("&", "§"));
			e.setDeathMessage(("&c&l" + e.getEntity().getDisplayName() + " has died and left this world").replaceAll("&", "§"));
		}
	}
}
