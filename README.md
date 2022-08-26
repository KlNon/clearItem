# ClearItem
拥有聊天栏点击打开共享垃圾桶以及翻页垃圾桶的Minecraft Bukkit服务器轻量级自动清理垃圾桶插件,适用于绝大多数Bukkit服务器
拥有扫地大妈功能,扫除的垃圾自动放入垃圾桶,垃圾桶大小可设置
拥有共享空间功能,让玩家将物品放入共享空间与其它玩家交易吧! 共享空间大小可设置
拥有私人垃圾桶功能,私人垃圾桶垃圾将被清理到公共垃圾桶.
绝大多数信息皆可定制.

## 垃圾桶清理提示
![image](https://user-images.githubusercontent.com/58141387/181935393-7ad64fc7-add3-4869-b07c-9018c1c29e80.png)

## 垃圾桶按钮
![image](https://user-images.githubusercontent.com/58141387/181935418-5ddd8c9c-ae23-404d-8da3-af521c6e5261.png)

## 垃圾桶详情
### 第一页:
![image](https://user-images.githubusercontent.com/58141387/181935525-8358a50e-173c-4e80-9398-4cd774450bf6.png)


### 第二页:
![image](https://user-images.githubusercontent.com/58141387/181935534-fe28cdeb-4c85-49ff-8430-ccaa6f4006d8.png)


### 最后一页:
![image](https://user-images.githubusercontent.com/58141387/181935539-2cb1c8c8-c9a1-422d-841f-c181231fe886.png)

## 配置文件相关:
>### 公共垃圾箱
>## PublicDustbin:
>  ### Enable: true
>  - 是否开启公共垃圾箱(true为开启，false为关闭)
>  ### Action: "&b打开了"
>  - 打开公共垃圾桶的动作
>  ### Name: "&c&l公共垃圾桶"
>  - 公共垃圾箱名称
>  ### Size: 135
>  - 垃圾箱大小(必须是9的倍数)
>  ### PrePageName: "&9上一页"
>  - 上一页页码的文字
>  ### PrePageDes: "&9点此上一页"
>  - 上一页页码的描述
>  ### NextPageName: "&9下一页"
>  - 下一页页码的文字
>  ### NextPageDes: "&9点此下一页"
>  - 下一页页码的描述
>  ### ClearInterval: 4
>  - 自动清理垃圾箱间隔
>  - 当垃圾被清理x次后就开始清理垃圾箱
>  ### WhiteListName:
>    -"§q§s"
>  - 垃圾白名单
>  - 如果垃圾的名称包含在下方列表则不放进垃圾箱(例如防qs插件的悬浮物)
>  ### Message:
>  - 公共垃圾箱提示
>    #### Reminder: '&3&l[公共垃圾箱] &a垃圾箱突然多出 &e%amount% &a件垃圾&7'
>    - 公共垃圾箱增加物品的提示
>    #### Button: '&b[&e点此打开垃圾桶&b]'
>    - 公共垃圾桶按钮的提示
>    #### ButtonInfo: '这是个垃圾桶'
>    - 按钮的提示
>    #### Clear: "&3&l[公共垃圾箱] &a避免垃圾过多, 垃圾箱清理完毕!">
>    - 清理公共垃圾箱的提示
>
>
>### 私人垃圾箱
>### 玩家把物品放进垃圾箱后关闭GUI就会自动转移进公共垃圾箱
>### 当公共垃圾箱满了则会把私人垃圾箱多出的物品清除
>### PrivateDustbin:
>  ### Enable: true
>  - 是否开启私人垃圾箱功能(true为开启，false为关闭)
>  ### Name: "&3&l你的垃圾箱(丢弃后垃圾会自动转移进公共垃圾箱)"
>  - 垃圾箱名称
>  ### Size: 54
>  - 垃圾箱大小
>  ### WhiteListName:
>    -"§q§s"
>  - 垃圾白名单
>  - 如果垃圾的名称包含在下方列表则不放进垃圾箱(例如防qs插件的悬浮物)
>  ### WhiteListLore:
>    -"绑定"
>  - 如果垃圾的Lore包含在下方列表则不放进垃圾箱(例如绑定了玩家的物品)
>  ### Message:
>  - 私人垃圾箱提示
>  ### Clear: "&3&l[你的垃圾箱] &a本次清理了 %clear% 件物品, 保留了 %preserve% 件物品."
>  - 私人垃圾箱清理后的提示
>
>
>### 防丢弃功能
>### Drop:
>  ### Enable: false
>  - 是否开启(true为开启，false为关闭)
>  - 开启后玩家需要输入指令解锁才能丢弃物品
>  ### Time: 300
>  - 玩家关闭防丢弃功能后多久恢复开启状态(秒)
>  ### Message:
>  - 防丢弃的提示
>    #### Open: "&8[&7+&8] &7已开启防丢弃功能，接下来你不能直接把物品扔出来(如想关闭输入/citem drop)"
>    - 切换到开启防丢弃时的提示
>    #### Close: "&8[&7-&8] &7已关闭防丢弃功能，接下来你可以直接把物品扔出来(如想开启输入/citem drop)"
>    - 切换到关闭防丢弃时的提示
>    #### DiscardInOpen: "&8[&3?&8] &7如果想丢弃物品，请输入/citem drop进行解锁"
>    - 开启防丢弃后如果丢出物品的提示
>
>### 清理垃圾
>### ClearItem:
>  ### Time: 60
>  - 异步清理垃圾检测周期 (秒)
>  ### ChunkMaxItems: 500
>  - 当一块大小为 16*32*16 的区域掉落物数量达到进行清理
>  - 请根据你服务器的情况设置，设置太低可能会导致玩家物品的物品被清理
>  ### WhiteList:
>    -"EXTRABOTANY_GAIATABLET"
>    -"BOTANIA_MANATABLET"
>    -"APPLIEDENERGISTICS2_ITEMITEMCRYSTALSEED"
>    -"BEACON"
>    -"BOTANIA_MANARINGGREATER"
>    -"THAUMCRAFT_ITEMELDRITCHOBJECT"
>    -"BOTANIA_TERRAPICK"
>  - 白名单物品，仅支持Type
>  - 可使用指令/citem type查询手上物品的Type
>
>  ### ItemFrame: false
>  - 是否清理展示框
>  ### Boat: false
>  - 是否清理船
>  ### ExpBall: true
>  - 是否清理经验球
>  ### FallingBlock: false
>  - 是否清理正在坠落的方块
>  ### Painting: false
>  - 是否清理画
>  ### Minecart: false
>  - 是否清理矿车
>  ### Arrow: true
>  - 是否清理箭头
>  ### Snowball: true
>  - 是否清理雪球
>  ### Message:
>  - 清理垃圾提示
>    #### ClearPre: "&c[警告] &6风暴将会在 &e%time%秒 &6后经过，请及时捡起掉落物!
>    - 清理垃圾前的提示"
>    #### ClearStart: "&c[警告] &6风暴正在经过，请保管好您的贵重物品，注意安全!"
>    - 开始清理垃圾时的提示
>    #### Clear: "&d[提示] &6本次风暴共卷走了 &e%count% &6件掉落物."
>    - 清理完全部地图垃圾后的提示信息
>    #### ClearWorld: "&d[提示] &6地图 &e%world% &6被风暴卷走了 &e%count% &6件掉落物."
>    - 当每清理完毕一个地图后的提示信息
>    #### ClearChunkMaxItems: "&4[警告] &b世界：%world%  &e坐标：%X% %Y% %Z%  &4附近出现大量掉落物，系统清理完毕&d(请勿乱扔垃圾)"
>    - 当一个区块掉落物超额时的清理提示(会受到垃圾白名单影响)
>
>
>### 清理垃圾提示方式
>### CleaningTips:
>  ### Enable: true
>  - 是否开启分地图提示
>  - true表示统计全部地图的垃圾总和，只显示全部地图清理了多少垃圾
>  - false表示分地图提示，每个有垃圾的地图都会提示清理了多少垃圾
>  ### WorldAlias:
>    world: 主世界
>    DIM1: 末地
>    DIM-1: 地狱
>    zc: 主城
>    sc: 生存世界
>    dp: 地皮世界
>  - 每个世界的别名，如果不希望清理垃圾的时候显示的是英文世界名称可以添加或修改
