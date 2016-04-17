package com.kookykraftmc.pink;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

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
		final Player p = e.getPlayer();
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
		}
		final int shoopCount = sheepCount;
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(int j=0;j<shoopCount;j++)
                {
                Sheep shoop = (Sheep) plugin.getServer().getWorld("world").spawnEntity(p.getLocation(), EntityType.SHEEP);
                //shoop.teleport(p.getLocation());
                shoop.setColor(shoopColour[rdm.nextInt(shoopColour.length)]);
                }
            }
       });
		e.setMessage(ChatColor.LIGHT_PURPLE + String.valueOf(msg));
		p.sendMessage(Pink.prefix + "Congratulations, you won " + sheepCount + " sheep!");
	}

/*
 * This will do something in the future!
	@EventHandler
	public void useItem(PlayerInteractEvent e)
	{
	    Player p = e.getPlayer();
	    ItemStack i = p.getItemInHand();
	    ItemMeta mData = i.getItemMeta();
	    if(!i.getType().name().equals("INK_SACK")||mData == null) return;
	    i.setAmount(i.getAmount()-1);
	    p.setItemInHand(i);
	    Location loc = p.getLocation();
	    
	}
*/
	@EventHandler
	public void bowFire(EntityShootBowEvent e)
	{
	    if(e.getEntity() instanceof Player) return;
	    Entity arrow = e.getEntity();
	    Vector velocity = arrow.getVelocity();
	    ItemStack i = e.getBow();
	    i.setType(Material.getMaterial("INK_SACK"));
	    i.setDurability((short) 13);
	    Item item = arrow.getWorld().dropItem(arrow.getLocation(), i);
	    item.setVelocity(velocity);
	}

	@EventHandler
	public void arrowHit(ProjectileHitEvent e)
	{
	    Pink.pinkinate(e.getEntity().getLocation(),rdm.nextInt(3)+4);
	}

	@SuppressWarnings("deprecation")
    @EventHandler
	public void dropGrenade(final PlayerDropItemEvent e)
	{
	    final ItemStack i = e.getItemDrop().getItemStack();
	    ItemMeta mData = i.getItemMeta();
	    if(mData==null) return;
	    else if(mData.getDisplayName()==null) return;

	    switch(ChatColor.stripColor(mData.getDisplayName()))
	    {
	    case "Pink Grenade":
	        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	            @Override
	            public void run() {
	                Location loc = e.getItemDrop().getLocation();
	                loc.getWorld().createExplosion(loc, 50);
	                i.setType(Material.getMaterial("AIR"));
	                for(int x = 0; x<15;x++)
	                {
	                    int bX = loc.getBlockX();
	                    int bY = loc.getBlockY();
	                    int bZ = loc.getBlockZ();
	                    World w = loc.getWorld();
	                    Location newLoc;
	                    for(int y = 0; y<30;y++)
	                    {
	                        bX += rdm.nextInt(3)-1;
	                        bY += rdm.nextInt(3)-1;
	                        bZ += rdm.nextInt(3)-1;
	                        newLoc = new Location(w, bX, bY, bZ);
	                        newLoc.getBlock().setTypeIdAndData(35, (byte) colList[rdm.nextInt(3)], true);
	                    }
	                }
	            }
	        }, 60L);
	        break;
	    case "Pink Seeds":

           i.setAmount(0);
           final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable()
           {
                Location loc = e.getItemDrop().getLocation();
                World w = loc.getWorld();
                int bX = loc.getBlockX();
                int bY = loc.getBlockY() + 5;
                int bZ = loc.getBlockZ();
                public void run() 
                {
                    bX += rdm.nextInt(3)-1;
                    bY += rdm.nextInt(3)-1;
                    bZ += rdm.nextInt(3)-1;
                    Location newLoc = new Location(w, bX, bY, bZ);
                    if(!newLoc.getBlock().getType().isTransparent()&&!newLoc.getBlock().getType().name().equals("WOOL")) return;
                    newLoc.getBlock().setTypeIdAndData(35, (byte) colList[rdm.nextInt(3)], true);
                }
           }, 1L, 4L);
           
           //No more snek after 600 ticks/~30 secs :(
           plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
           {
			@Override
			public void run() {
				plugin.getServer().getScheduler().cancelTask(taskID);
			}
           }, 600L);
        	   
           
	    }

 
	}

	
	public static void loadCfg()
	{
		//plugin.reloadCfg();
		//woolCol = plugin.getConfig().getCharacterList("WoolColours");
	}
}