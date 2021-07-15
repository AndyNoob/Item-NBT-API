package dev.tr7zw.nbtapi.plugin.tests.injector;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.nbtinjector.NBTInjector;
import dev.tr7zw.nbtapi.NbtApiException;
import dev.tr7zw.nbtapi.Writeable;
import dev.tr7zw.nbtapi.plugin.tests.Test;

public class TilesCustomNBTInjectorTest implements Test {

	@Override
	public void test() throws Exception {
		if(!NBTInjector.isInjected())return;
		if (!Bukkit.getWorlds().isEmpty()) {
			World world = Bukkit.getWorlds().get(0);
			try {
				Block block = world.getBlockAt(world.getSpawnLocation().getBlockX(), 255,
						world.getSpawnLocation().getBlockZ());
				if (block.getType() == Material.AIR) {
					block.setType(Material.CHEST);
					Writeable comp = NBTInjector.getNbtData(block.getState());
					comp.setString("Foo", "Bar");
					if (!new NBTTileEntity(block.getState()).toString().contains("__extraData:{Foo:\"Bar\"}")) {
						block.setType(Material.AIR);
						throw new NbtApiException("Custom Data did not save to the Tile!");
					}
					block.setType(Material.AIR);
				}
			} catch (Exception ex) {
				throw new NbtApiException("Wasn't able to use NBTTiles!", ex);
			}
		}
	}

}
