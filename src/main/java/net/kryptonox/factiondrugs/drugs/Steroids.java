package net.kryptonox.factiondrugs.drugs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.kryptonox.factiondrugs.FactionDrugs;

public class Steroids extends Drug implements Listener {
	
	static int task;
	int pure;
	
	public Steroids(FactionDrugs plugin, String name) {
		super(plugin, name);
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCraftingTableOpen(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();
		Action a = e.getAction();
		
		if(a == Action.RIGHT_CLICK_BLOCK && b.getType().equals(Material.WORKBENCH)) {
			createRecipe("&8Steroids");
		}
	}
	

	@SuppressWarnings("deprecation")
	public void createRecipe(String name) {
		ItemStack steroids = new ItemStack(Material.GHAST_TEAR, 2);
		ItemMeta steroidsMeta = steroids.getItemMeta();
	
		pure = purity.nextInt(100) + 1;
		steroidsMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + pure + "% Pure"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7Right Click To Swallow!"));
		steroidsMeta.setLore(lore);
		lore.clear();
		steroids.setItemMeta(steroidsMeta);
		
		ShapelessRecipe steroidsRecipe = new ShapelessRecipe(steroids);
		steroidsRecipe.addIngredient(Material.WATER_BUCKET);
		steroidsRecipe.addIngredient(Material.COAL, (byte) 1);
		steroidsRecipe.addIngredient(Material.GLASS_BOTTLE);
		plugin.getServer().addRecipe(steroidsRecipe);
	}
	
	@SuppressWarnings("deprecation")
	public static void giveEffects(final Player p, final int peakTime, final int comedownTime) {
		p.sendMessage(ChatColor.GREEN + "You begin to feel stronger...");
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, peakTime * 20, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, peakTime * 20, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, peakTime * 20, 1));
		
		 task = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BukkitRunnable() {
			public void run() {
				p.sendMessage(ChatColor.RED + "You begin to feel paranoid...");
				p.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK, comedownTime * 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, comedownTime * 20, 1));
				p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, comedownTime * 5, 1));
				Bukkit.getScheduler().cancelTask(task);
			}
		}, (peakTime + 10) * 20L);
		 
	}

}
