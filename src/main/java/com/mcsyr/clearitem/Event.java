package com.mcsyr.clearitem;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Event implements Listener {
  public Event() {
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    tools.initPlayerData(event.getPlayer());
  }

  @EventHandler(
          priority = EventPriority.HIGHEST
  )
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (Main.DropEnable && (Boolean)Main.PlayerDropLock.get(event.getPlayer())) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(Main.DropMessageDiscardInOpen);
    }

  }

  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    List<Entity> Entities = event.getEntity().getNearbyEntities(16.0, 32.0, 16.0);
    if (Entities.size() >= Main.ClearItemChunkMaxItems) {
      List<LivingEntity> worldEntities = event.getEntity().getWorld().getLivingEntities();
      Iterator var4 = Entities.iterator();

      while(var4.hasNext()) {
        Entity ent = (Entity)var4.next();
        tools.clearEntityItem(ent);
      }

      Player player = null;
      Iterator var8 = worldEntities.iterator();

      while(var8.hasNext()) {
        Entity entity = (Entity)var8.next();
        if (entity instanceof Player) {
          player = (Player)entity;
          break;
        }
      }

      String message = Main.ClearItemMessageClearChunkMaxItems.replace("%world%", "%multiverse_world_alias%");
      message = PlaceholderAPI.setPlaceholders(player, message);
      DecimalFormat df = new DecimalFormat("0.0 ");
      message = message.replace("%X%", df.format(event.getLocation().getX())).replace("%Y%", df.format(event.getLocation().getY())).replace("%Z%", df.format(event.getLocation().getZ()));
      Bukkit.getServer().broadcastMessage(message);
    }

  }

  @EventHandler(
          priority = EventPriority.HIGHEST
  )
  public void onInventoryClick(InventoryClickEvent event) {
    ItemStack itemStack = event.getCurrentItem();
    if (itemStack != null && itemStack.getType() != null && !"AIR".equals(itemStack.getType().name())) {
      String Title = event.getView().getTitle();
      if (Title.equals(Main.PublicDustbinName) && Main.DustbinLock) {
        ((Player)event.getWhoClicked()).sendMessage(Main.PublicDustbinName + "垃圾箱已被锁住，请稍等1秒后操作...");
        event.setCancelled(true);
      } else if (Main.DropEnable && Title.equals(Main.PrivateDustbinName)) {
        String name = itemStack.getItemMeta().getDisplayName();
        if (tools.isIncludedString(Main.PrivateDustbinWhiteListName, name)) {
          event.setCancelled(true);
        }

        if (itemStack.getItemMeta() == null) {
          return;
        }

        List<String> lores = itemStack.getItemMeta().getLore();
        if (lores == null) {
          return;
        }

        Iterator var6 = lores.iterator();

        while(var6.hasNext()) {
          String lore = (String)var6.next();
          if (tools.isIncludedString(Main.PrivateDustbinWhiteListLore, lore)) {
            event.setCancelled(true);
            return;
          }
        }
      }

    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (Main.PrivateDustbinEnable && event.getView().getTitle().equals(Main.PrivateDustbinName)) {
      Player player = (Player)event.getPlayer();
      Inventory inventory = (Inventory)Main.PlayerPrivateDustbin.get(player);
      ItemStack[] itemStacks = inventory.getContents();
      int clear = 0;
      int preserve = 0;

      for(int i = 0; i < itemStacks.length; ++i) {
        if (itemStacks[i] != null) {
          if (Dustbin.addItem(itemStacks[i])) {
            inventory.remove(itemStacks[i]);
            ++clear;
          } else {
            ++preserve;
          }
        }
      }

      if (clear > 0 || preserve > 0) {
        player.sendMessage(Main.PrivateDustbinMessageClear.replaceAll("%clear%", String.valueOf(clear)).replaceAll("%preserve%", String.valueOf(preserve)));
      }
    }

  }
}
