package com.mcsyr.clearitem;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

  public static Main plugin;
  
  public static FileConfiguration Config;
  
  public static Boolean DustbinLock = Boolean.FALSE;


  public static String PublicDustbinPrePageName;

  public static String PublicDustbinNextPageName;

  public static String PublicDustbinPrePageDes;

  public static String PublicDustbinNextPageDes;
  public static int DustbinCount = 0;
  
  public static Boolean PublicDustbinEnable;

  public static String PublicDustbinAction;

  public static String PublicDustbinName;
  
  public static Integer PublicDustbinSize;
  
  public static Integer PublicDustbinClearInterval;
  
  public static List<String> PublicDustbinWhiteListName;
  
  public static String PublicDustbinMessageReminder;

  public static String PublicDustbinMessageButton;

  public static String PublicDustbinMessageInfo;
  
  public static String PublicDustbinMessageClear;
  
  public static Boolean PrivateDustbinEnable;
  
  public static String PrivateDustbinName;
  
  public static Integer PrivateDustbinSize;
  
  public static List<String> PrivateDustbinWhiteListName;
  
  public static List<String> PrivateDustbinWhiteListLore;
  
  public static String PrivateDustbinMessageClear;
  
  public static Boolean DropEnable;
  
  public static Integer DropTime;
  
  public static String DropMessageOpen;
  
  public static String DropMessageClose;
  
  public static String DropMessageDiscardInOpen;
  
  public static Integer ClearItemTime;
  
  public static List<String> ClearItemWhiteList;

  public static List<String> BlockBlackList;
  public static Integer ClearItemChunkMaxItems;
  
  public static Boolean ClearItemItemFrame;
  
  public static Boolean ClearItemBoat;
  
  public static Boolean ClearItemExpBall;
  
  public static Boolean ClearItemFallingBlock;
  
  public static Boolean ClearItemPainting;
  
  public static Boolean ClearItemMinecart;
  
  public static Boolean ClearItemArrow;
  
  public static Boolean ClearItemSnowball;
  
  public static String ClearItemMessageClearPre;
  
  public static String ClearItemMessageClearStart;
  
  public static String ClearItemMessageClear;
  
  public static String ClearItemMessageClearWorld;
  
  public static String ClearItemMessageClearChunkMaxItems;
  
  public static Boolean CleaningTipsEnable;
  
  public static Integer time = 0;
  
  public static Integer WasteTotal = 0;
  
  public static Integer DustbinClearFrequency = 0;
  
  public static Map<Player, Boolean> PlayerDropLock = new HashMap<>();
  
  public static Map<Player, Date> PlayerDropLockTime = new HashMap<>();
  
  public static Map<Player, Inventory> PlayerPrivateDustbin = new HashMap<>();

  public static String Version = "3.2.2";

  public Main() {
  }

  public void onEnable() {
    plugin = this;
    this.saveDefaultConfig();
    Bukkit.getServer().getConsoleSender().sendMessage("§b[ClearItem] 异步清理垃圾 插件启动");
    Bukkit.getPluginManager().registerEvents(new Event(), this);
    Objects.requireNonNull(Bukkit.getPluginCommand("clearitem")).setExecutor(new command());
    loadConfig();
    tools.Scheduler();
    tools.TraversePlayer();
  }

  public static void loadConfig() {
    plugin.reloadConfig();
    Config = plugin.getConfig();
    PublicDustbinEnable = Config.getBoolean("PublicDustbin.Enable");
    PublicDustbinPrePageName = Objects.requireNonNull(Config.getString("PublicDustbin.PrePageName")).replaceAll("&", "§");
    PublicDustbinPrePageDes = Objects.requireNonNull(Config.getString("PublicDustbin.PrePageDes")).replaceAll("&", "§");
    PublicDustbinNextPageName = Objects.requireNonNull(Config.getString("PublicDustbin.NextPageName")).replaceAll("&", "§");
    PublicDustbinNextPageDes = Objects.requireNonNull(Config.getString("PublicDustbin.NextPageDes")).replaceAll("&", "§");
    PublicDustbinAction = Objects.requireNonNull(Config.getString("PublicDustbin.Action")).replaceAll("&", "§");
    PublicDustbinName = Objects.requireNonNull(Config.getString("PublicDustbin.Name")).replaceAll("&", "§");
    PublicDustbinSize = Config.getInt("PublicDustbin.Size");
    PublicDustbinClearInterval = Config.getInt("PublicDustbin.ClearInterval");
    PublicDustbinWhiteListName = Config.getStringList("PublicDustbin.WhiteListName");
    PublicDustbinMessageReminder = Objects.requireNonNull(Config.getString("PublicDustbin.Message.Reminder")).replaceAll("&", "§");
    PublicDustbinMessageButton = Objects.requireNonNull(Config.getString("PublicDustbin.Message.Button")).replaceAll("&", "§");
    PublicDustbinMessageInfo = Objects.requireNonNull(Config.getString("PublicDustbin.Message.ButtonInfo")).replaceAll("&", "§");
    PublicDustbinMessageClear = Objects.requireNonNull(Config.getString("PublicDustbin.Message.Clear")).replaceAll("&", "§");
    PrivateDustbinEnable = Config.getBoolean("PrivateDustbin.Enable");
    PrivateDustbinName = Objects.requireNonNull(Config.getString("PrivateDustbin.Name")).replaceAll("&", "§");
    PrivateDustbinSize = Config.getInt("PrivateDustbin.Size");
    PrivateDustbinWhiteListName = Config.getStringList("PrivateDustbin.WhiteListName");
    PrivateDustbinWhiteListLore = Config.getStringList("PrivateDustbin.WhiteListLore");
    PrivateDustbinMessageClear = Objects.requireNonNull(Config.getString("PrivateDustbin.Message.Clear")).replaceAll("&", "§");
    DropEnable = Config.getBoolean("Drop.Enable");
    DropTime = Config.getInt("Drop.Time") * 1000;
    DropMessageOpen = Objects.requireNonNull(Config.getString("Drop.Message.Open")).replaceAll("&", "§");
    DropMessageClose = Objects.requireNonNull(Config.getString("Drop.Message.Close")).replaceAll("&", "§");
    DropMessageDiscardInOpen = Objects.requireNonNull(Config.getString("Drop.Message.DiscardInOpen")).replaceAll("&", "§");
    ClearItemTime = Config.getInt("ClearItem.Time");
    ClearItemChunkMaxItems = Config.getInt("ClearItem.ChunkMaxItems");
    BlockBlackList = Config.getStringList("ClearItem.BlockBlackList");
    ClearItemWhiteList = Config.getStringList("ClearItem.WhiteList");
    ClearItemItemFrame = Config.getBoolean("ClearItem.ItemFrame");
    ClearItemBoat = Config.getBoolean("ClearItem.Boat");
    ClearItemExpBall = Config.getBoolean("ClearItem.ExpBall");
    ClearItemFallingBlock = Config.getBoolean("ClearItem.FallingBlock");
    ClearItemPainting = Config.getBoolean("ClearItem.Painting");
    ClearItemMinecart = Config.getBoolean("ClearItem.Minecart");
    ClearItemArrow = Config.getBoolean("ClearItem.Arrow");
    ClearItemSnowball = Config.getBoolean("ClearItem.Snowball");
    ClearItemMessageClearPre = Objects.requireNonNull(Config.getString("ClearItem.Message.ClearPre")).replaceAll("&", "§");
    ClearItemMessageClearStart = Objects.requireNonNull(Config.getString("ClearItem.Message.ClearStart")).replaceAll("&", "§");
    ClearItemMessageClear = Objects.requireNonNull(Config.getString("ClearItem.Message.Clear")).replaceAll("&", "§");
    ClearItemMessageClearWorld = Objects.requireNonNull(Config.getString("ClearItem.Message.ClearWorld")).replaceAll("&", "§");
    ClearItemMessageClearChunkMaxItems = Objects.requireNonNull(Config.getString("ClearItem.Message.ClearChunkMaxItems")).replaceAll("&", "§");
    CleaningTipsEnable = Config.getBoolean("CleaningTips.Enable");
  }
}
