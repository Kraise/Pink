package com.kookykraftmc.pink;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Pink extends JavaPlugin 
{
	public static String prefix = ChatColor.DARK_PURPLE + "[Pink]" + ChatColor.LIGHT_PURPLE;
	public static String cPrefix = "[Pink]";
	static Logger log = Logger.getLogger("Pink");
    static int[] colList = {2,10,6};
    static Random rdm = new Random();

	public void onEnable() 
	{
        getServer().getPluginManager().registerEvents(new PinkListener(this), this);
        this.getCommand("pink").setExecutor(new CommandPink(this));
		PluginDescriptionFile pdf = getDescription();
		log.info(ChatColor.DARK_PURPLE + pdf.getName() + " " + pdf.getVersion() + " is now enabled");
		
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

    @SuppressWarnings("deprecation")
    public static void pinkinate(Location loc, int amount)
    {
        int pX = loc.getBlockX();
        int pY = loc.getBlockY();
        int pZ = loc.getBlockZ();
        World w = loc.getWorld();
        Location newLoc;
        for(int xCount = -amount; xCount<=amount; xCount++)
        {
            for(int yCount = -amount; yCount<=amount; yCount++)
            {
                for(int zCount = -amount; zCount<=amount; zCount++)
                {
                    newLoc = new Location(w, pX + xCount, pY + yCount, pZ + zCount);
                    if(!newLoc.getBlock().getType().isTransparent())
                    {
                        newLoc.getBlock().setTypeIdAndData(35, (byte) colList[rdm.nextInt(3)], true);
                    }
                }
            }
        }
    }
}