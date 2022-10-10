package com.mcsyr.clearitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static com.mcsyr.clearitem.Dustbin.DustbinList;

/**
 * @author KlNon
 * @version 1.0
 * @className Share
 * @description
 * @date 2022/8/23 12:28
 **/
public class Share {
    public static ArrayList<Inventory> ShareList = new ArrayList<Inventory>();


    public static int ShareLarge=0;

    public Share() {
    }

    public static void page(){
        for(Inventory inv:ShareList){
            ItemStack prev = new ItemStack(Material.BOOK);
            ItemMeta prevMeta = prev.getItemMeta();
            assert prevMeta != null;
            prevMeta.setDisplayName(Main.SharePre);
            ArrayList<String> prevLore =new ArrayList<>();
            prevLore.add(Main.SharePreInfo);
            prevMeta.setLore(prevLore);
            prev.setItemMeta(prevMeta);
            ItemStack next = new ItemStack(Material.BOOK);

            ItemMeta nextMeta = next.getItemMeta();
            assert nextMeta != null;
            nextMeta.setDisplayName(Main.ShareNext);
            ArrayList<String> nextLore =new ArrayList<>();
            nextLore.add(Main.ShareNextInfo);
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

    public static void ClearShareInv() {
        for (Inventory inventory : ShareList) {
            inventory.clear();
        }
    }

    static {
        int Size=Main.ShareSize;
        int PageSize=Size/54;
        if(Size>54){
            for(int i = 0; i < PageSize; i++){
                ShareList.add(Bukkit.createInventory((InventoryHolder) null, 54, Main.ShareName + "第" + (i + 1) + "页"));
                ShareLarge++;
            }
            if(((Size%54+5)/9)*9!=0){
                ShareList.add(Bukkit.createInventory((InventoryHolder) null, ((Size%54+5)/9)*9, Main.ShareName + "第" + (ShareLarge + 1) + "页"));
                ShareLarge++;
            }
        }else{
            ShareList.add(Bukkit.createInventory((InventoryHolder) null, Size, Main.ShareName + "第" + 1 + "页"));
            ShareLarge++;
        }
        if(PageSize>1)
            page();
    }
}
