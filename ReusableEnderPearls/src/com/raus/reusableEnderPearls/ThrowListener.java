package com.raus.reusableEnderPearls;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ThrowListener implements Listener
{
	public static HashMap<UUID, Integer> thrownPearls = new HashMap<UUID, Integer>();
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event)
	{
		// Get stuff
		Player ply = event.getPlayer();
		ItemStack item = event.getItem();
		Action act = event.getAction();
		
		if ((act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK)
			&& item != null && item.getType() == Material.ENDER_PEARL
			&& ply.getCooldown(Material.ENDER_PEARL) <= 0)
		{
			// Check to see if we right clicked an interactible
			if (act == Action.RIGHT_CLICK_BLOCK)
			{
				Block block = event.getClickedBlock();
				if (block.getType().isInteractable())
				{
					// Cancel
					return;
				}
			}
			
			// Get item meta
			ItemMeta meta = item.getItemMeta();
			PersistentDataContainer container = meta.getPersistentDataContainer();

			// Is it our custom ender pearl?
			if (container.has(Main.key, PersistentDataType.STRING))
			{
			    if (container.get(Main.key, PersistentDataType.STRING).equals("stable"))
			    {
			    	// Mark player as having thrown a stable ender pearl
			    	int count = thrownPearls.containsKey(ply.getUniqueId()) ? thrownPearls.get(ply.getUniqueId()) : 0;
			    	thrownPearls.put(ply.getUniqueId(), count + 1);
			    }
			}
		}
	}
}