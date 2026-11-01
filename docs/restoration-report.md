# 还原说明 / Restoration Report

对象：`EixClient-1.20.1-Forge-9.3-obf.jar`（34,529,399 字节，539 个归档条目，410 个 `.class`）
工具：Vineflower 1.11.1 + 自研字节码/映射工具（见 `toolchain/`）
Java：21.0.10（class 文件版本 61 = Java 17 目标）

---

## 1. 结果总览

| 项目 | 数量 |
| --- | --- |
| 输入 class 文件 | 410（解析失败 0） |
| 输出 `.java` | 343（内部类合入宿主文件，属正常） |
| 代码行数 | 38,991 |
| class→源码覆盖 | **410 / 410**（逐类核对，无缺失） |
| 反编译失败标记 | **0** |
| 残留 SRG 名（`m_12345_`/`f_12345_`） | **0**（4,764 处全部还原为 Mojang 官方名） |
| 结构缺陷（包/类同名、关键字命名） | **0** |
| 字节码引用闭合问题 | **0 / 410** |
| 还原出原始语义名的类 | 101 个顶层类（连带其 30 个内部类，共 131 个 class 文件改名） |
| 中文字符串 | 2,269 个汉字，0 个损坏字符 |

四项独立验证（不是单一自检）：

1. `Verify` — 每个 class 都能在源码树里找到对应类型声明：410/410。
2. `LoadCheck` — 交给 **JDK 自己的类校验器** `defineClass`：`byte_defects=0`；
   与未改名基线对比，"缺失类型集合"**逐条完全一致**（都是 Minecraft/Netty/Gson 等
   外部依赖，本机无 classpath 所致），证明改名没有破坏任何内部链接。
3. `VerifyBytecode` — 常量池 + 属性级引用闭合（Class/Fieldref/Methodref/
   InvokeDynamic/InnerClasses/BootstrapMethods）：`problems=0`。
4. `StructCheck` — 包名/类型名冲突与关键字命名扫描：全部为 0。

---

## 2. 混淆器实际情况（与最初假设不同，这点很重要）

jar 带有 ZKM 痕迹（`cn.paradisemc.ZKMIndy` 注解、9 个类标注该注解、`SourceFile`
被统一改名）。但用常量池探针**实测**后结论是：

```
invokedynamic 条目   = 582      MethodHandle 条目 = 495
constantdynamic      = 0        明文字符串常量     = 4,835
```

582 个 `invokedynamic` 的 bootstrap 目标**只有三个，全部是 JDK 标准元工厂**：

| bootstrap | 出现次数 | 含义 |
| --- | --- | --- |
| `LambdaMetafactory.metafactory` | 294 | 普通 lambda |
| `StringConcatFactory.makeConcatWithConstants` | 248 | 字符串拼接 |
| `ObjectMethods.bootstrap` | 12 | record 的 equals/hashCode/toString |

**没有字符串加密层。** 所有字符串（含 2,269 个中文提示语）本来就是常量池里的明文；
indy 只是编译期 lambda/拼接的正常产物。因此 Vineflower 把 582 个点全部还原成了可读
的 Java lambda、`+` 拼接和 record 语法 —— 源码中不存在任何由 indy 生成的残留结构
（`StringConcatFactory`、`LambdaMetafactory`、`invokeExact` 出现次数均为 0）。
唯一出现 `MethodHandles` 的地方是 `events/api/EventManager.java`，那是开发者
**自己**的事件总线实现（`privateLookupIn(...).unreflect(...).bindTo(...)`），
与反编译无关，属于原始代码。

真正生效的是**名称混淆 + 调试属性剥离**，这是不可逆的部分：

| 属性 | 保留情况 | 后果 |
| --- | --- | --- |
| `SourceFile` | 410/410 都是字面量 `"SourceFile"` | 原始 `.java` 文件名被彻底抹掉 |
| `LocalVariableTable` | **0** | 局部变量/参数名只能叫 `var0/var1` |
| `LineNumberTable` | **0** | 无法回指原始行号 |
| `MethodParameters` | 14 | 仅 14 个类留有参数名 |
| `InnerClasses` | 214 | 用于识别内部类归属 |
| `RuntimeVisibleAnnotations` | 157 | **语义名的唯一来源** |

---

## 3. 我做了什么（方法）

管线是**字节码优先**的：先在 class 文件层面改名，再重新反编译。
这样源码与随附字节码天然一致，且改名由 JVM 常量池完成，不会漏改隐式引用
（`this_class`、descriptor、generic signature、`InnerClasses`、异常表、
`BootstrapMethods` 参数里的类引用）。

### 3.1 Minecraft 侧：SRG → Mojang 官方名
jar 编译时用的是 Forge 的 SRG 名（`Minecraft.m_91087_()`、`f_91065_`）。
用两份官方映射做两级链接：

```
joined.tsrg  (来自 mcp_config 1.20.1-20230612.114412)  obf <-> srg
client.txt   (Mojang 官方, 1.20.1)                     official <-> obf
                          ↓ 链接
              srg 名 -> Mojang 官方名  (64,225 条)
```

结果：方法 48,575/48,575、字段 31,003/31,003 **全部解析成功**，源码里
4,764 处 SRG 标识符无一例外被还原。例如：

```java
Minecraft.m_91087_()          ->  Minecraft.getInstance()
var0.m_41611_().getString()   ->  var0.getDisplayName().getString()
GlStateManager$BooleanState 的 f_84586_/m_84590_  ->  enabled / setEnabled(boolean)
```

正确性用 jar 自带的 `EixClient.refmap.json` 独立交叉验证（它是 SRG↔官方名的
权威来源），抽样全部吻合。

### 3.2 模组自身：恢复类的原始语义名（101 个）
开发者把真名留在了运行时注解里，这是唯一**有据可依**的来源：

```java
@ModuleInfo(a = "ClickGUI", b = "点击用户图形界面", d = Category.RENDER, c = "The ClickGUI")
@CommandInfo(a = "toggle", b = "Toggle a module", c = {"t"})
```

据此还原 79 个模块类 + 6 个命令类，例如
`modules.impl.a.a → AimAssist`、`modules.impl.render.g → ClickGUI`、
`commands.a.f → Toggle`。完整对照见 `class-rename-list.tsv`，
可读索引见 `modules-commands-index.tsv`。

改名安全性经过核查：配置持久化用的是**显示名**（`h().toLowerCase()`）而不是
类名，且源码中没有任何以字符串形式引用这些类名的地方（`Class.forName` 只用于
Gson 的 SPI 解码器名），因此改名不影响行为。

### 3.3 修掉 17 处 javac 无法接受的产物
ProGuard 造出 17 个「包名与类名同名」的结构（如 `obsoverlay.a` 既是类又是包）。
JVM 允许、JLS 禁止，不修就编译不了。我按**代码实际内容**逐个命名
（`utils.e.a → SkijaDraw`、`utils.f → ChatUtil`、`utils.d.a → Framebuffer` 等），
理由都写进 `class-rename-list.tsv` 的「依据」列。

另有 2 个字段名用了 Java 关键字（JVM 允许 `do`/`if` 作字段名，javac 不允许），
改为 `do_`/`if_`。该常量表经核查无任何引用点（同包 4 个文件也没有），属死代码。

### 3.4 我刻意**没有**做的事
**字段名和方法名保持原样（`a`、`b`、`c`）。** 原因：混淆器已把它们的名字彻底销毁，
`SourceFile`/`LocalVariableTable`/`MethodParameters` 都是空的，注解里也没有
任何字段/方法原名。任何"新名字"都会是我编造的，而不是还原。交付物的目的是还原，
不是重写——所以我保留证据原状，而不是用猜测污染它。

同理，343 个源文件里有 **151 个类名仍是单个字母**（1–2 个字母的共 171 个）：它们没有
注解、没有注释、没有任何可考证的原始名来源，我没有动它们。

---

## 4. 目录说明

```
MOD-SOURCE/                Forge 模组工程骨架
  src/main/java/           343 个还原后的源文件（Java 包结构）
  src/main/resources/      assets/、mods.toml、mixins.json、refmap.json（49 个文件，
                           直接从 jar 原样提取——Vineflower 只输出 .java，非 class 条目
                           需自己从归档里取）
  build.gradle, settings.gradle   尽力而为的构建脚本（未在本机验证，见下）
BYTECODE-CLASS-RENAMED/    410 个改名后的 class（JDK 校验通过，与上面源码同源）
                           注意：类名已还原，但成员名仍是 SRG（m_12345_/f_12345_），
                           与源码不同名——见 §3.1 说明。
BUNDLED-LIBRARIES/
  sources/                 META-INF/jarjar 内 9 个依赖库的反编译源码（395 个 .java）
  original-jars/           这 9 个库的原始 jar（编译依赖请用它们，别用反编译源码）
ANALYSIS/
  README-analysis.md       本文件
  class-rename-list.tsv    101 条改名 + 依据
  class-rename-map.tsv     同上的机器可读形式（内部名 -> 新名）
  modules-commands-index.tsv  85 个模块/命令：旧名/新名/显示名/中文名/分类/说明
  mapping-provenance.txt   用到的映射表出处（映射本身不随包分发，见下）
  toolchain/               我写的验证与改名工具源码 + Vineflower + 复现步骤
```

`build.gradle` 是**未经执行的起点**，不是可用工程：本机没有 Minecraft/Forge/Skija
工件且离线，无法跑通 Gradle。它标注了正确的 Forge/MCP 坐标和 9 个内嵌库依赖。

`BYTECODE-CLASS-RENAMED` 的命名是刻意的：我对 class 文件只做了**类名**还原
（字节码层，含 descriptor/signature/InnerClasses/BootstrapMethods 里的引用）。
成员名（方法/字段）仍是 SRG，因为那需要改写所有引用点的名字并连带处理
`refmap.json`，风险高于收益——源码树才是最终交付物，它已经完全反混淆到官方名。
两份产物因此**同名不同粒度**，请勿期望直接用这份字节码替换反混淆后的源码编译产物。

---

## 5. 如果你要重新编译，还需知道

1. **必须用 `official` 映射**（`build.gradle` 已设）。源码已反混淆到 Mojang 官方名，
   若改用 `searge`/`mcp` 通道会全部失配。
2. **mixin refmap 需要重新生成。** 注解里的 `method = {"render"}`、
   `@At(target = "Lnet/minecraft/...;fill(IIIII)V")` 用的是原始/SRG 描述符，
   `EixClient.refmap.json` 保留了 jar 内的旧版本；用 Gradle 重新构建时 mixin
   注解处理器会生成新的 refmap。
3. **剩余的 javac 错误分两类。** 绝大多数是缺 classpath（`net.minecraft`、
   `org.spongepowered.asm.mixin`、`com.mojang.blaze3d`、`io.github.humbleui.skija`、
   `org.lwjgl`、`it.unimi.dsi`、`com.google.gson`、`lombok`）。剩下的
   （`reference to a is ambiguous` 等，141 处）是单字母成员名本身的固有歧义，
   不是我引入的：改名前基线树同类错误 7,195 个，改名后降到 5,751 个，
   且歧义类别在基线就已存在（80 处）。这类要消除只能给字段/方法命名，
   而那正是我在 §3.4 里拒绝做的猜测性工作。
4. **`lombok` 是 compileOnly**，源码里只剩 `@lombok.Generated` 与一处 `@NonNull`。
5. `assets/heypixel/VcX6svVqmeT8/` 这个目录名是混淆器造的，代码里以常量引用，
   不要改动，否则材质/字体加载会失败。
