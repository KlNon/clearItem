package com.mcsyr.clearitem;

import java.util.Date;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class command implements CommandExecutor {
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        Player player = (Player)sender;
        if (args[0].equalsIgnoreCase("open")) {
          if (Main.PublicDustbinEnable) {

            ((Player)sender).openInventory(Dustbin.DustbinList.get(0));
            sender.sendMessage(Main.PublicDustbinAction + Main.PublicDustbinName);
          } else {
            sender.sendMessage( "公共垃圾箱已被服务器禁用!");
          }

          return true;
        }

        if (args[0].equalsIgnoreCase("drop")) {
          if (Main.DropEnable) {
            if (Main.PlayerDropLock.get(player)) {
              Main.PlayerDropLock.put(player, false);
              Main.PlayerDropLockTime.put(player, new Date());
              player.sendMessage(Main.DropMessageClose);
            } else {
              Main.PlayerDropLock.put(player, true);
              player.sendMessage(Main.DropMessageOpen);
            }
          } else {
            sender.sendMessage("§c防丢弃功能服务器已禁用");
          }

          return true;
        }

        if (args[0].equalsIgnoreCase("discard")) {
          if (Main.PrivateDustbinEnable) {
            player.openInventory(Main.PlayerPrivateDustbin.get(player));
            sender.sendMessage("打开了" + Main.PrivateDustbinName);
          } else {
            sender.sendMessage("私人垃圾箱已被服务器禁用!");
          }

          return true;
        }

        if (sender.isOp()) {
          if (args[0].equalsIgnoreCase("reload")) {
            Main.loadConfig();
            sender.sendMessage("§b[ClearItem] §f插件重载成功!");
            return true;
          }

          if (args[0].equalsIgnoreCase("type")) {
            sender.sendMessage("§b[ClearItem] §fType: " + player.getItemInHand().getType().name());
            return true;
          }
        }
      }

      this.showHelp(sender);
    } else if (sender.isOp()) {
      if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
        Main.loadConfig();
        sender.sendMessage("§b[ClearItem] §f插件重载成功!");
        return true;
      }

      this.showHelp(sender);
    }

    return false;
  }



  private void showHelp(CommandSender sender) {
    sender.sendMessage("§7======================" + Main.PublicDustbinName + "§7======================");
    sender.sendMessage("§7");
    sender.sendMessage(Main.PublicDustbinName + " §f打开公共垃圾箱");
    sender.sendMessage(Main.PublicDustbinName + " §f§l/citem open");
    sender.sendMessage("§7");
    sender.sendMessage(Main.PublicDustbinName + " §f打开私人垃圾箱");
    sender.sendMessage(Main.PublicDustbinName + " §f§l/citem discard");
    sender.sendMessage("§7");
    sender.sendMessage(Main.PublicDustbinName + " §f切换防丢弃模式");
    sender.sendMessage(Main.PublicDustbinName + " §f§l/citem drop");
    sender.sendMessage("§7");
    if (sender.isOp()) {
      sender.sendMessage(Main.PublicDustbinName + " §f查询手上物品的Type");
      sender.sendMessage(Main.PublicDustbinName + " §f§l/citem type");
      sender.sendMessage("§7");
      sender.sendMessage(Main.PublicDustbinName + " §f重载插件");
      sender.sendMessage(Main.PublicDustbinName + " §f§l/citem reload");
      sender.sendMessage("§7");
    }

    sender.sendMessage("§7======================§b" + Main.Version + "§7======================");
  }
}
