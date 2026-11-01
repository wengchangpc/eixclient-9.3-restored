本目录是本次还原所用的全部工具（可 javac 直接编译，无第三方依赖，Vineflower 除外）。

核心管线
  CpProbe.java          常量池探针：统计 InvokeDynamic(18)/MethodHandle(15)/String/Dynamic 条目、
                        bootstrap 目标。用于判定"是否存在字符串加密层"这一关键问题。
  AttrAudit.java        属性审计：SourceFile / LocalVariableTable / MethodParameters /
                        LineNumberTable / InnerClasses / 注解 的留存情况。
  MetaExtract2.java     从 @ModuleInfo / @CommandInfo 注解提取语义名，生成改名映射表。
  RenameBytecode.java   字节码层类改名器（本管线的核心）。只改常量池里的 Utf8 类名与
                        descriptor/signature，其余字节逐字复制；Long/Double 槽位、
                        modified UTF-8、CONSTANT_InvokeDynamic 的 u2 宽度均按 JVMS 处理。
  RoundTrip.java        无损性证明：空映射跑一遍，要求 410 个文件字节完全一致。
  VerifyBytecode.java   引用闭合校验：this_class 与路径一致、所有 Class/Fieldref/Methodref/
                        InvokeDynamic/InnerClasses/BootstrapMethods 引用的自有类都存在于磁盘。
  LoadCheck.java        交给 JDK 自己的类校验器 defineClass()，把"字节缺陷"与
                        "外部依赖缺失"分开统计。
  LoadDiff.java         对基线树与改名树分别跑 LoadCheck 并比对缺失类型集合（证明改名
                        没有破坏任何内部链接）。
  Verify.java           class -> 源码 覆盖核对（每个 class 都能在 .java 里找到类型声明）。
  StructCheck.java      源码结构体检：包名/类型名冲突、关键字命名成员/类型。
  Deobf.java            SRG -> Mojang 官方名 重写（joined.tsrg + client.txt 两级链接）。
  Renamer.java          Vineflower IIdentifierRenamer 实现。经实测**不可用**：
                        toBeRenamed() 只能拿到简单类名，无法区分不同包下的同名类
                        （如 obsoverlay.a 与 utils.a），所以最终改用 RenameBytecode。
  InspectRenamer*.java / InspectWiring*.java   为弄清 Vineflower 改名器契约而做的反射探查。

  已删除的两个原型（SiteProbe.java / NameProbe.java）：它们的方法属性遍历漏读了
  descriptor_index（应为 access/name/descriptor 三个 u2），导致部分类解析出 EOF。
  结论改由宽度正确的 CpProbe.java / AttrAudit.java 产出——"原始文件名是否还能捞回来"
  这一问，AttrAudit 的答案是 410/410 全是字面量 "SourceFile"，即不可恢复。

vineflower.jar          Vineflower 1.11.1（https://vineflower.org/），反编译器本体。
                        注意这是有版权的第三方分发物，交付包内为原始上游 jar。

复现顺序
  1. 从 jar 解出 class                      ->  work/classes_only
  2. CpProbe + AttrAudit                    ->  判定混淆实况（indy 全为 JDK 标准元工厂；无字符串加密）
  3. MetaExtract2 (在基线反编译产物上跑)     ->  rename_map.tsv
  4. 手工补 17 条包/类同名冲突 -> fix_map.tsv, 合并为 rename_all.tsv
  5. RenameBytecode classes_only -> classes_fixed
  6. RoundTrip / VerifyBytecode / LoadCheck + LoadDiff  ->  全部 0 缺陷
  7. Vineflower classes_fixed -> 源码
  8. Deobf 源码 (SRG -> 官方名)  ->  最终 MOD-SOURCE/src/main/java
