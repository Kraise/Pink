package com.kookykraftmc.pink;

import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
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
            giveItem(p, "INK_SACK", ChatColor.DARK_PURPLE + "Pink Grenade", args, 13);
            break;
        case "seed":
            giveItem(p, "SEEDS", ChatColor.LIGHT_PURPLE + "Pink Seeds", args, 0);
            break;
        default:
            p.sendMessage(Pink.prefix + "Unknown parameter.");
        }
        return true;
    }

    public void giveItem(Player p, String matName, String customName, String[] args, int durability)
    {
        int amount = 1;
        if(args.length>1)
        {
            if(StringUtils.isNumeric(args[1]))
                amount = Integer.parseInt(args[1]);
            else
                p.sendMessage(Pink.prefix + "You can only use positive integers. Giving you 1 anyway ;)");
        }
        ItemStack i = new ItemStack(Material.getMaterial(matName), amount);
        i.setDurability((short) durability);
        ItemMeta mData = i.getItemMeta();
        mData.setDisplayName(customName);
        i.setItemMeta(mData);
        p.getInventory().addItem(i);
    }
}
