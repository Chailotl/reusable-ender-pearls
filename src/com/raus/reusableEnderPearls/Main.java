package com.raus.reusableEnderPearls;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin
{
	public static NamespacedKey key;
	public static ItemStack item;
	
	@Override
	public void onEnable()
	{
		// Namespace
		key = new NamespacedKey(this, "stable_ender_pearl");
		
		// Save config
		saveDefaultConfig();
		
		// Listeners
		getServer().getPluginManager().registerEvents(new ThrowListener(), this);
		getServer().getPluginManager().registerEvents(new TeleportListener(), this);
		
		// Register command
		this.getCommand("rep").setExecutor(new ReloadCommand());
		
		
		// Creating stable ender pearl item
		item = new ItemStack(Material.ENDER_PEARL);
		
		// Setting meta
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RESET + "Stable Ender Pearl");
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addEnchant(Enchantment.LURE, 1, false); // Make it glow
		meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "stable");
		item.setItemMeta(meta);
		
		// Read config
		List<String> list = getConfig().getStringList("recipe");
		ConfigurationSection ingredients = getConfig().getConfigurationSection("ingredients");
		
		// Create recipe
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(list.get(0), list.get(1), list.get(2));
		for (String str : ingredients.getKeys(false))
		{
		    String name = getConfig().getString("ingredients." + str);
		    recipe.setIngredient(str.charAt(0), Material.getMaterial(name));
		}
		
		// Add recipe
		Bukkit.addRecipe(recipe);
	}
	
	@Override
	public void onDisable()
	{
		// Remove recipe on disable
		Bukkit.removeRecipe(key);
	}
}