package me.dragoneisbaer.minecraft.givemending.Listeners;

import me.dragoneisbaer.minecraft.givemending.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.Objects;

public class EnchantGUI implements Listener {

    GameUtils plugin = JavaPlugin.getPlugin(GameUtils.class);

    @EventHandler
    public void onGiveItem(InventoryClickEvent event) {
        String title = ChatColor.stripColor(event.getView().getTitle());
        if (title.equalsIgnoreCase("EnchantGUI")) {
            if (event.getCurrentItem() != null) {
                if ((event.getCurrentItem().getType().equals(Material.OAK_SIGN) || (event.getCurrentItem().getType().equals(Material.BOOK)) || event.getCurrentItem().getType().equals(Material.BOOKSHELF) || event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS_PANE))) {
                    event.setCancelled(true);
                    return;
                }else if (event.getCurrentItem().getMaxStackSize() > 1) {
                    event.setCancelled(true);
                    return;
                }
                HumanEntity human = event.getWhoClicked();
                if (human instanceof Player) {
                    Player player = (Player) human;
                    ItemStack item = event.getCurrentItem();
                    if (!Objects.equals(item, new ItemStack(Material.AIR))) {
                        plugin.getItemstacks().put(player, event.getCurrentItem().clone());
                        Bukkit.getServer().broadcastMessage(plugin.getItemstacks().values().toString());
                    }
                }
            }
        }
    }


    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        String title = ChatColor.stripColor(event.getView().getTitle());
        if (title.equalsIgnoreCase("EnchantGUI")) {
            if (event.getPlayer() instanceof Player) {
                Player player = (Player) event.getPlayer();
                ItemStack storeditem = plugin.getItemstacks().get(player);
                if (storeditem != null) {
                    Bukkit.getServer().broadcastMessage("ReadItem: " + plugin.getItemstacks().values());
                    player.getInventory().addItem(storeditem);
                    plugin.getItemstacks().remove(player);
                }
            }
        }
    }
}
