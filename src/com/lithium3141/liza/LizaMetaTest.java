package com.lithium3141.liza;

import junit.framework.Assert;

import net.minecraft.server.MinecraftServer;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.junit.Before;
import org.junit.Test;

public class LizaMetaTest extends LizaTest {
	
	@Before
	public void _adjustStartupWait() {
		//LizaTest.STARTUP_WAIT = 100L;
	}
	
	@Test
	public void testFramework() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void testMinecraftServer() {
		MinecraftServer mcServer = Liza.getMinecraftServer();
		
		Assert.assertNotNull("No running Minecraft server found", mcServer);
		Assert.assertTrue("Minecraft server found, but not running", MinecraftServer.isRunning(mcServer));
	}
	
	@Test
	public void testCraftServer() {
		MinecraftServer mcServer = Liza.getMinecraftServer();
		CraftServer craftServer = mcServer.server;
		
		Assert.assertNotNull("No running craft server found for MC server", craftServer);
		Assert.assertEquals("Craft server's MC server does not match Liza's", craftServer.getServer(), mcServer);
		
		// Start testing properties of the CB server
		Assert.assertEquals("Fresh craft server should not have players", 0, craftServer.getOnlinePlayers().length);
		Assert.assertEquals("Craft server is not running on testing port", 31415, craftServer.getPort());
		Assert.assertEquals("Craft server is not running on localhost", "127.0.0.1", craftServer.getIp());
		
		Assert.assertNotNull("Fresh craft server should have a plugin loader", craftServer.getPluginManager());
		Assert.assertEquals("Fresh craft server should not have plugins loaded", 0, craftServer.getPluginManager().getPlugins().length);
	}
	
	@Test
	public void testMockPlayer() {
		LizaPlayer mockPlayer = new LizaPlayer("Liza");
		
		Assert.assertNotNull("Liza player has no internal EntityPlayer", mockPlayer.getEntityPlayer());
		Assert.assertEquals("Liza player did not retain name", "Liza", mockPlayer.getName());
		
		Assert.assertEquals("Unexpected number of players online", 1, Liza.getCraftServer().getOnlinePlayers().length);
		Assert.assertTrue("Online player is unexpected type", Liza.getCraftServer().getOnlinePlayers()[0] instanceof CraftPlayer);
		
		CraftPlayer craftPlayer = (CraftPlayer)Liza.getCraftServer().getOnlinePlayers()[0];
		
		Assert.assertEquals("Server-registered player did not retain name", "Liza", craftPlayer.getName());
		Assert.assertEquals("Server player and Liza player have different entities", mockPlayer.getEntityPlayer(), craftPlayer.getHandle());
	}
	
}
