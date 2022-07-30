package com.mcsyr.clearitem;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

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
    if (Main.DropEnable && Main.PlayerDropLock.get(event.getPlayer())) {
      event.setCancelled(true);
      event.getPlayer().sendMessage(Main.DropMessageDiscardInOpen);
    }

  }

  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    List<Entity> Entities = event.getEntity().getNearbyEntities(16.0, 32.0, 16.0);
    if (Entities.size() >= Main.ClearItemChunkMaxItems) {
      List<LivingEntity> worldEntities = event.getEntity().getWorld().getLivingEntities();

      for (Entity ent : Entities) {
        tools.clearEntityItem(ent);
      }

      Player player = null;

      for (LivingEntity worldEntity : worldEntities) {
        if (worldEntity instanceof Player) {
          player = (Player) worldEntity;
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
    if (itemStack != null) {
      itemStack.getType();
      if (!"AIR".equals(itemStack.getType().name())) {
        String Title = event.getView().getTitle();
        if (Title.equals(Main.PublicDustbinName) && Main.DustbinLock) {
          event.getWhoClicked().sendMessage(Main.PublicDustbinName + "垃圾箱已被锁住，请稍等1秒后操作...");
          event.setCancelled(true);
        } else if (Main.DropEnable && Title.equals(Main.PrivateDustbinName)) {
          String name = Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
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

          for (String lore : lores) {
            if (tools.isIncludedString(Main.PrivateDustbinWhiteListLore, lore)) {
              event.setCancelled(true);
              return;
            }
          }
        }
        else {
          onPageClick(event);
        }
      }
    }
  }

  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event) {
    if (Main.PrivateDustbinEnable && event.getView().getTitle().equals(Main.PrivateDustbinName)) {
      Player player = (Player)event.getPlayer();
      Inventory inventory = Main.PlayerPrivateDustbin.get(player);
      ItemStack[] itemStacks = inventory.getContents();
      int clear = 0;
      int preserve = 0;

      for (ItemStack itemStack : itemStacks) {
        if (itemStack != null) {
          if (Dustbin.addItem(itemStack)) {
            inventory.remove(itemStack);
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

//  上下页(标题相同)
  public void onPageClick(InventoryClickEvent event){
    if(event.getView().getTitle().startsWith(Main.PublicDustbinName)){
      if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Main.PublicDustbinPrePageName)){
        event.setCancelled(true);
        int count=Integer.parseInt(event.getView().getTitle().substring(event.getView().getTitle().length()-2,event.getView().getTitle().length()-1))-1;
        if(count>0){
          count--;
        }
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();
        player.openInventory(Dustbin.DustbinList.get(count));
      }else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Main.PublicDustbinNextPageName)){
        event.setCancelled(true);
        int count=Integer.parseInt(event.getView().getTitle().substring(event.getView().getTitle().length()-2,event.getView().getTitle().length()-1))-1;
        if(count<Dustbin.DustbinList.size()-1){
          count++;
        }
        Player player = (Player) event.getWhoClicked();
        player.closeInventory();
        player.openInventory(Dustbin.DustbinList.get(count));
      }
    }
  }
}
