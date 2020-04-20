package com.raus.reusableEnderPearls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportListener implements Listener
{
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event)
	{
		if (event.getCause() == TeleportCause.ENDER_PEARL)
		{
			// Get stuff
			Player ply = event.getPlayer();
			
			// Did player throw stable ender pearl?
	    	int count = ThrowListener.thrownPearls.containsKey(ply.getUniqueId()) ? ThrowListener.thrownPearls.get(ply.getUniqueId()) : 0;
	    	
	    	if (count > 0)
	    	{
	    		// Reduce count
	    		ThrowListener.thrownPearls.put(ply.getUniqueId(), count - 1);
	    		ply.getInventory().addItem(Main.item);
	    	}
		}
	}
}