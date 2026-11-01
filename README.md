# EixClient (Naven) — reconstructed Forge 1.20.1 client source

Reconstructed source for the **EixClient / Naven** Minecraft client mod
(`EixClient-1.20.1-Forge-9.3-obf.jar`). The shipped jar was name-obfuscated with its debug
attributes stripped; this tree is the result of decompiling it and reversing the naming back
to something readable.

- Minecraft **1.20.1** / Forge **47.x** (`mods.toml`: `modId = "naven"`, `version = "9.3"`)
- Java 17 bytecode (class file version 61)
- 343 `.java` files, ~39,000 lines

## Repository layout

```
src/main/java/          reconstructed mod source (Java package tree)
src/main/resources/     assets/, META-INF/mods.toml, EixClient.mixins.json, EixClient.refmap.json, pack.mcmeta
lib/                    the 9 third-party jars the original bundled under META-INF/jarjar/
docs/                   restoration report, rename evidence, mapping provenance
tools/                  the verification/rename tooling used (plain javac, no deps)
build.gradle / settings.gradle
```

The 410 renamed `.class` files this source was generated from are **not** committed (they
duplicate the source in binary form); the tooling in `tools/` regenerates them from the
original jar.

## What the source looks like, and why

| Aspect | State |
| --- | --- |
| Minecraft / Forge member names | **Restored to Mojang official names** (`Minecraft.getInstance()`, `ItemStack#getDisplayName`, …). All 4,764 `m_12345_` / `f_12345_` SRG identifiers were rewritten using the official mappings. |
| Module / command class names | **101 restored** from the names the obfuscator left inside `@ModuleInfo` / `@CommandInfo` annotations (`impl.a.a → AimAssist`, `render.g → ClickGUI`, …). |
| Remaining class names | 151 files still carry single-letter names. The obfuscator destroyed their original names and nothing in the jar records them, so they are left as-is rather than invented. |
| Field / method names of the mod itself | **Still `a`, `b`, `c`.** No original member names survive anywhere in the jar. Renaming them would mean inventing names, not restoring them. |
| Local variables / parameters | `var0`, `var1`, … `LocalVariableTable` and `MethodParameters` were stripped (present in 0 of 410 classes for LVT). |
| String literals | Original text, incl. **2,269 Chinese characters**, intact UTF-8. |
| The `obsoverlay.a → EixClient` client entry point | renamed; `NavenModLoader` (`@Mod("naven")`) is the Forge entry point. |

There is **no string encryption** in this jar. All 582 `invokedynamic` sites resolve to three
stock JDK bootstrap methods (`LambdaMetafactory` 294, `StringConcatFactory` 248,
`ObjectMethods` 12); the 4,835 string constants are plain constant-pool entries. What the
obfuscator actually did was rename identifiers and delete debug metadata. Details and evidence:
[`docs/restoration-report.md`](docs/restoration-report.md).

## Reproducing the restoration

```bash
cd tools && javac -cp vineflower.jar *.java     # vineflower.jar not committed; get it from https://vineflower.org
```

Then follow the 8-step order in [`tools/README.md`](tools/README.md). Every claim in the table
above comes from a tool in that directory, and the four verification gates
(class→source coverage, JDK `defineClass`, constant-pool reference closure, source structure)
each report **0 defects**.

## Building

`build.gradle` targets ForgeGradle 6 with the **`official`** mapping channel, which is what the
source names now match. It also needs `net.minecraftforge:forge:1.20.1-47.2.0` plus the jars in
`lib/`.

> This build script is a starting point, **not a verified build** — it was authored offline
> against the shipped metadata and has never been executed. Expect to work through the
> remaining `reference to a is ambiguous` errors (141 sites; an inherent consequence of the
> single-letter member names, not a defect introduced here — the pre-rename tree had more,
> 7,195 vs 5,751 total javac errors). See §5 of the restoration report.

Notes if you rebuild:

1. Keep `mappings channel: 'official'`. Switching to `searge`/`mcp` breaks every reference.
2. Regenerate the mixin refmap. The `@Inject(method = {"render"})` / `@At(target = …)` strings
   use the original names; `src/main/resources/EixClient.refmap.json` is the shipped one, and the
   annotation processor produces a fresh one at build time.
3. Don't rename the `assets/heypixel/VcX6svVqmeT8/` directory — code references that path as a
   constant.
4. `lombok` is `compileOnly`; only `@lombok.Generated` and one `@NonNull` survive.

## Third-party code

`lib/` holds the upstream jars exactly as the original shipped them (Skija, jlayer, jorbis,
mp3spi, tritonus, vorbisspi, aio-core, humbleui-types). They are **build inputs, not part of this
reconstruction** — no decompiled third-party source is included here. Each retains its own
upstream license.

## License

No license file is committed yet. The original jar declared `All-Rights-Reserved`; a license for
this reconstructed tree needs to be chosen deliberately.
