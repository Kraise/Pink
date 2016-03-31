package com.kookykraftmc.pink;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CommandPink implements CommandExecutor
{
    static Plugin pink;
    static Logger log = Pink.log;
    static int[] colList = {2,10,6};
    static Random rdm = new Random();
    CommandPink(Pink p){
        pink = p;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if(!(sender instanceof Player)) 
        {
            log.info(Pink.cPrefix + "Currently, only players can do the pink.");
            return true;
        }
        Player p = (Player) sender;

        if(args.length == 0)
        {
            Pink.pinkinate(p.getLocation(), 3);
            return true;
        }
        switch (args[0])
        {
        case "grenade":
            ItemStack i = p.getItemInHand();
            i.setAmount(1);
            i.setType(Material.getMaterial("INK_SACK"));
            i.setDurability((short) 13);
            ItemMeta mData = i.getItemMeta();
            mData.setDisplayName(ChatColor.DARK_PURPLE + "Pink Grenade");
            i.setItemMeta(mData);
            p.getInventory().addItem(i);
            return true;
        }
        return false;
    }

}
