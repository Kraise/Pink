package com.kookykraftmc.pink;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class PinkListener implements Listener 
{
	static public Pink plugin;
	static public Random rdm = new Random();
	static Logger log = Pink.log;
	static public List<Character> woolCol;
	static public int col;
	static int[] colList = {2,10,6};
	static char[] lowPink = {'p','i','n','k'};
	static char[] upPink = {'P','I','N','K'};
	static DyeColor[] shoopColour = {DyeColor.PINK, DyeColor.MAGENTA, DyeColor.PURPLE};
	
	public PinkListener(Pink main)
	{
		plugin = main;
		//loadCfg();
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onFlow(BlockFromToEvent e)
	{
		Block b = e.getBlock();
		Block toB = e.getToBlock();
		
		int by = b.getY();
		Location loc = toB.getLocation();
		col = colList[rdm.nextInt(colList.length)];
		if(by > toB.getY())
		{
			loc.setY(loc.getY()- 4*(rdm.nextDouble()-0.5));
			loc.getBlock().setTypeIdAndData(35, (byte) col, true);
		}
	}

	@EventHandler
	public void onSpeak(AsyncPlayerChatEvent e)
	{
		Player p = e.getPlayer();
		char[] msg = e.getMessage().toCharArray();
		int pinkCount = 0;
		int sheepCount = 0;
		int i;
		for(i=0;i<msg.length;i++)
		{
			char c = msg[i];
			if(c >= 'a' && c <= 'z')
				msg[i] = lowPink[pinkCount++];
			else if(c >= 'A' && c <= 'Z')
				msg[i] = upPink[pinkCount++];
			if(pinkCount>3)
			{
				pinkCount = 0;
				sheepCount++;
			}
			for(i=sheepCount;i>0;i--)
			{
log.info("About to spawn a sheep");
				spawnSheep(p);
log.info("Sheep deployed");
			}
		}
		e.setMessage(ChatColor.LIGHT_PURPLE + String.valueOf(msg));
		p.sendMessage(Pink.prefix + "Congratulations, you won " + sheepCount + " sheep!");
	}
	/*
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e)
	{
		Entity ent = e.getEntity();
		if(ent.getType().equals(EntityType.SHEEP))
		{
			Sheep shoop = (Sheep) ent;
			shoop.setColor(shoopColour[rdm.nextInt(shoopColour.length)]);
		}
	}
	*/
	public static void loadCfg()
	{
		//plugin.reloadCfg();
		//woolCol = plugin.getConfig().getCharacterList("WoolColours");
	}
	
	public static void spawnSheep(Player p)
	{
log.info("Preparing to deploy sheep");
		//Sheep shoop
		//Location loc = new Location(plugin.getServer().getWorld("world"), 0, 100, 0);
		Entity e = plugin.getServer().getWorld("world").spawnEntity(p.getLocation(), EntityType.SHEEP);
		e.teleport(p.getLocation());
log.info("Sheep deployed");
		//shoop.setColor(shoopColour[rdm.nextInt(shoopColour.length)]);
log.info("Sheep is now pink");
	}
}