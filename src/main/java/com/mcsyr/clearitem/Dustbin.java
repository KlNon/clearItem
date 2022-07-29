package com.mcsyr.clearitem;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Dustbin {
  public static Inventory Dustbin;

  public Dustbin() {
  }

  public static Boolean addItem(ItemStack itemStack) {
    Main.DustbinLock = true;
    if (tools.isIncludedString(Main.PublicDustbinWhiteListName, itemStack.getItemMeta().getDisplayName())) {
      Main.DustbinLock = false;
      return false;
    } else {
      ItemStack[] contents = Dustbin.getContents();

      for(int i = 0; i < contents.length; ++i) {
        if (contents[i] == null) {
          Dustbin.setItem(i, itemStack);
          Main.DustbinLock = false;
          return true;
        }

        if (contents[i].isSimilar(itemStack)) {
          int remainingQuantity = contents[i].getMaxStackSize() - contents[i].getAmount();
          int itemStackAmount = itemStack.getAmount();
          if (remainingQuantity > 0) {
            if (itemStackAmount <= remainingQuantity) {
              contents[i].setAmount(contents[i].getAmount() + itemStackAmount);
              Dustbin.setItem(i, contents[i]);
              Main.DustbinLock = false;
              return true;
            }

            itemStack.setAmount(itemStackAmount - remainingQuantity);
            contents[i].setAmount(contents[i].getAmount() + remainingQuantity);
            Dustbin.setItem(i, contents[i]);
          }
        }
      }

      Main.DustbinLock = false;
      return false;
    }
  }

  public static void ClearDustbin() {
    Dustbin.clear();
  }

  static {
    Dustbin = Bukkit.createInventory((InventoryHolder)null, Main.PublicDustbinSize, Main.PublicDustbinName);
  }
}
