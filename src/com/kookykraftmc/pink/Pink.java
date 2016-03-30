package com.kookykraftmc.pink;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Pink extends JavaPlugin 
{
	public static String prefix = ChatColor.LIGHT_PURPLE + "[Pink]";
	public static String cPrefix = "[Pink]";
	static Logger log = Logger.getLogger("Pink");
	public void onEnable() 
	{
        getServer().getPluginManager().registerEvents(new PinkListener(this), this);
		PluginDescriptionFile pdf = getDescription();
		log.info(ChatColor.LIGHT_PURPLE + pdf.getName() + " " + pdf.getVersion() + " is now enabled");
		
	}
	public void onDisable()
	{
		PluginDescriptionFile pdf = getDescription();
		log.info(pdf.getName() + pdf.getVersion() + " is now disabled");
	}
	public void reloadCfg()
	{
		this.reloadConfig();
		PinkListener.loadCfg();
	}
	
}