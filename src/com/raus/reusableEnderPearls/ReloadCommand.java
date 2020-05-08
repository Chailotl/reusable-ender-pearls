package com.raus.reusableEnderPearls;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ShapedRecipe;

import net.md_5.bungee.api.ChatColor;

public class ReloadCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length == 0 || !args[0].equals("reload"))
		{
			return false;
		}
		
		// Reload config
		Main main = Main.getInstance();
		main.reloadConfig();
		
		// Read config
		List<String> list = main.getConfig().getStringList("recipe");
		ConfigurationSection ingredients = main.getConfig().getConfigurationSection("ingredients");
		
		// Create recipe
		ShapedRecipe recipe = new ShapedRecipe(Main.key, Main.item);
		recipe.shape(list.get(0), list.get(1), list.get(2));
		for (String str : ingredients.getKeys(false))
		{
		    String name = main.getConfig().getString("ingredients." + str);
		    recipe.setIngredient(str.charAt(0), Material.getMaterial(name));
		}
		
		// Replace recipe
		Bukkit.removeRecipe(Main.key);
		Bukkit.addRecipe(recipe);
		
		// Send message
		sender.sendMessage(ChatColor.DARK_PURPLE + "[REP]" + ChatColor.GRAY + " Config reloaded!");
		
		return true;
	}
}