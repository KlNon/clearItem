package com.mcsyr.clearitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class Dustbin {
  public static ArrayList<Inventory> DustbinList = new ArrayList<Inventory>();
  public static int binSize=0;

  public Dustbin() {
  }

  public static void page(){
    for(Inventory inv:DustbinList){
      ItemStack prev = new ItemStack(Material.BOOK);
      ItemMeta prevMeta = prev.getItemMeta();
      assert prevMeta != null;
      prevMeta.setDisplayName(Main.PublicDustbinPrePageName);
      ArrayList<String> prevLore =new ArrayList<>();
      prevLore.add(Main.PublicDustbinPrePageDes);
      prevMeta.setLore(prevLore);
      prev.setItemMeta(prevMeta);
      ItemStack next = new ItemStack(Material.BOOK);

      ItemMeta nextMeta = next.getItemMeta();
      assert nextMeta != null;
      nextMeta.setDisplayName(Main.PublicDustbinNextPageName);
      ArrayList<String> nextLore =new ArrayList<>();
      nextLore.add(Main.PublicDustbinNextPageDes);
      nextMeta.setLore(nextLore);
      next.setItemMeta(nextMeta);
      if(!inv.contains(prev)){
        inv.setItem(inv.getSize()-9, prev);
      }
      if(!inv.contains(next)){
        inv.setItem(inv.getSize()-1, next);
      }
    }
  }

  public static Boolean addItem(ItemStack itemStack) {
    Main.DustbinLock = true;
    if (!tools.isIncludedString(Main.PublicDustbinWhiteListName, Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName())) {
      for(int page=0;page<binSize;page++){
        ItemStack[] contents = DustbinList.get(page).getContents();

        for (int i = 0; i < contents.length; ++i) {
          if (contents[i] == null) {

            DustbinList.get(page).setItem(i, itemStack);
            Main.DustbinLock = false;
            return true;
          }

          if (contents[i].isSimilar(itemStack)) {
            int remainingQuantity = contents[i].getMaxStackSize() - contents[i].getAmount();
            int itemStackAmount = itemStack.getAmount();
            if (remainingQuantity > 0) {
              if (itemStackAmount <= remainingQuantity) {
                contents[i].setAmount(contents[i].getAmount() + itemStackAmount);
                DustbinList.get(page).setItem(i, contents[i]);
                Main.DustbinLock = false;
                return true;
              }

              itemStack.setAmount(itemStackAmount - remainingQuantity);
              contents[i].setAmount(contents[i].getAmount() + remainingQuantity);
              DustbinList.get(page).setItem(i, contents[i]);
            }
          }
        }
      }
    }
    Main.DustbinLock = false;
    return false;
  }

  public static void ClearDustbin() {
    for (Inventory inventory : DustbinList) {
      inventory.clear();
    }
  }

  static {
    int Size=Main.PublicDustbinSize;
    int PageSize=Size/54;
    if(Size>54){
      for(int i = 0; i < PageSize; i++){
        DustbinList.add(Bukkit.createInventory((InventoryHolder) null, 54, Main.PublicDustbinName + "第" + (i + 1) + "页"));
        binSize++;
      }
      if(((Size%54+5)/9)*9!=0){
        DustbinList.add(Bukkit.createInventory((InventoryHolder) null, ((Size%54+5)/9)*9, Main.PublicDustbinName + "第" + (binSize + 1) + "页"));
        binSize++;
      }
    }else{
      DustbinList.add(Bukkit.createInventory((InventoryHolder) null, Size, Main.PublicDustbinName + "第" + 1 + "页"));
      binSize++;
    }
    page();
}
}
