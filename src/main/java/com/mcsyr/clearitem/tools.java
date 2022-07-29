//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mcsyr.clearitem;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class tools {
  public tools() {
  }

  public static void Scheduler() {
    if (Main.ClearItemTime != 0)
      Bukkit.getScheduler().runTaskTimerAsynchronously(Main.plugin, () -> {
        Main.time = Main.time + 1;
        if (Main.ClearItemTime - Main.time == 60) {
          Bukkit.getServer().broadcastMessage(Main.ClearItemMessageClearPre.replace("%time%", "60"));
        } else if (Main.ClearItemTime - Main.time == 10) {
          Bukkit.getServer().broadcastMessage(Main.ClearItemMessageClearPre.replace("%time%", "10"));
        } else if (Main.ClearItemTime - Main.time <= 0) {
          Bukkit.getServer().broadcastMessage(Main.ClearItemMessageClearStart);
          tools.clearWorld();
          Main.time = 0;
          Main.DustbinClearFrequency = Main.DustbinClearFrequency + 1;
          if (Main.PublicDustbinEnable && Main.DustbinClearFrequency % Main.PublicDustbinClearInterval == 0) {
            Dustbin.ClearDustbin();
            Bukkit.getServer().broadcastMessage(Main.PublicDustbinMessageClear);
          }
        }
        tools.CheckPlayerDropLock();
      },200L, 20L);
  }
  public static void CheckPlayerDropLock() {
    long date = (new Date()).getTime();
    long playerDate = 0L;

    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
      if (!(Boolean) Main.PlayerDropLock.get(player)) {
        playerDate = ((Date) Main.PlayerDropLockTime.get(player)).getTime();
        if (date - playerDate > (long) Main.DropTime) {
          Main.PlayerDropLock.put(player, true);
          player.sendMessage(Main.DropMessageOpen);
        }
      }
    }

  }

  public static void clearWorld() {
    int index = 0;
    for (World world : Bukkit.getWorlds()) {
      final int finalIndex = ++index;
      Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plugin, () -> tools.clearWorldItem(world, (finalIndex == Bukkit.getWorlds().size())),10L * index);
    }
  }

  public static void clearWorldItem(World world, Boolean isDustbin) {
    int count = 0;
    int DustbinCount = 0;
    List<Entity> Entities = world.getEntities();
    Iterator var5 = Entities.iterator();

    while(true) {
      while(var5.hasNext()) {
        Entity ent = (Entity)var5.next();
        if (ent instanceof Item) {
          Item item = (Item)ent;
          if (!Main.ClearItemWhiteList.contains(item.getItemStack().getType().name())) {
            if (Dustbin.addItem(item.getItemStack())) {
              ++DustbinCount;
            }

            ++count;
            ent.remove();
          }
        } else if (ent instanceof ItemFrame && Main.ClearItemItemFrame) {
          ++count;
          ent.remove();
        } else if (ent instanceof Boat && Main.ClearItemBoat) {
          ++count;
          ent.remove();
        } else if (ent instanceof ExperienceOrb && Main.ClearItemExpBall) {
          ++count;
          ent.remove();
        } else if (ent instanceof FallingBlock && Main.ClearItemFallingBlock) {
          ++count;
          ent.remove();
        } else if (ent instanceof Painting && Main.ClearItemPainting) {
          ++count;
          ent.remove();
        } else if (ent instanceof Minecart && Main.ClearItemMinecart) {
          ++count;
          ent.remove();
        } else if (ent instanceof Arrow && Main.ClearItemArrow) {
          ++count;
          ent.remove();
        } else if (ent instanceof Snowball && Main.ClearItemSnowball) {
          ++count;
          ent.remove();
        }
      }

      Main.DustbinCount += DustbinCount;
      Main.WasteTotal = Main.WasteTotal + count;
      if (!Main.CleaningTipsEnable && count > 0) {
        Bukkit.getServer().broadcastMessage(Main.ClearItemMessageClearWorld.replaceAll("%world%", IncludeWorldAlias(world.getName())).replaceAll("%count%", String.valueOf(count)));
      }

      if (isDustbin) {
        if (Main.PublicDustbinEnable) {
          Bukkit.getServer().broadcastMessage(Main.PublicDustbinMessageReminder.replace("%amount%", String.valueOf(Main.DustbinCount)));
        }

        if (Main.CleaningTipsEnable) {
          Bukkit.getServer().broadcastMessage(Main.ClearItemMessageClear.replaceAll("%count%", String.valueOf(Main.WasteTotal)));
        }

        Main.DustbinCount = 0;
        Main.WasteTotal = 0;
      }

      return;
    }
  }

  public static String IncludeWorldAlias(String name) {
    return Main.Config.getString("CleaningTips.WorldAlias." + name) == null ? name : Main.Config.getString("CleaningTips.WorldAlias." + name);
  }

  public static void clearEntityItem(Entity ent) {
    if (ent instanceof Item) {
      Item item = (Item)ent;
      if (!Main.ClearItemWhiteList.contains(item.getItemStack().getType().name())) {
        ent.remove();
      }
    } else if (ent instanceof ItemFrame && Main.ClearItemItemFrame) {
      ent.remove();
    } else if (ent instanceof Boat && Main.ClearItemBoat) {
      ent.remove();
    } else if (ent instanceof ExperienceOrb && Main.ClearItemExpBall) {
      ent.remove();
    } else if (ent instanceof FallingBlock && Main.ClearItemFallingBlock) {
      ent.remove();
    } else if (ent instanceof Painting && Main.ClearItemPainting) {
      ent.remove();
    } else if (ent instanceof Minecart && Main.ClearItemMinecart) {
      ent.remove();
    } else if (ent instanceof Arrow && Main.ClearItemArrow) {
      ent.remove();
    } else if (ent instanceof Snowball && Main.ClearItemSnowball) {
      ent.remove();
    }

  }

  public static boolean isIncludedString(List<String> list, String string) {
    if (string == null) {
      return false;
    } else {
      Iterator var2 = list.iterator();

      String listString;
      do {
        if (!var2.hasNext()) {
          return false;
        }

        listString = (String)var2.next();
      } while(!string.contains(listString));

      return true;
    }
  }

  public static void TraversePlayer() {
    Iterator var0 = Bukkit.getServer().getOnlinePlayers().iterator();

    while(var0.hasNext()) {
      Player player = (Player)var0.next();
      initPlayerData(player);
    }

  }

  public static void initPlayerData(Player player) {
    Main.PlayerDropLock.putIfAbsent(player, true);
    Main.PlayerDropLockTime.putIfAbsent(player, new Date());
    Main.PlayerPrivateDustbin.putIfAbsent(player, Bukkit.createInventory(player, Main.PrivateDustbinSize, Main.PrivateDustbinName));
  }
}
