# Third-party and non-source components

This repository is **not** entirely original work by the repository owner. The shipped jar
bundled third-party code and binary resources; they were carried through the reconstruction
untouched and they keep their **own** licenses. The `GPL-3.0` in `LICENSE` applies to the
reconstructed mod source under `src/main/java/` — it does **not** relicense the items below.

## 1. Bundled Java libraries (`lib/`)

These are the upstream artifacts exactly as the original jar shipped them under
`META-INF/jarjar/` (per `META-INF/jarjar/metadata.json` in the source jar). They are present
so the project can build; they are not decompiled and not modified.

| Artifact | Upstream | Coordinates from `metadata.json` |
| --- | --- | --- |
| `skija-shared-0.116.4.jar` | Skija (io.github.humbleui) | `io.github.humbleui:skija-shared:[0.116,0.117)` |
| `skija-windows-x64-0.116.4.jar` | Skija native binding | `io.github.humbleui:skija-windows-x64` |
| `types-0.2.0.jar` | humbleui types | `io.github.humbleui:types:[0.2,0.3)` |
| `aio-core-1.5.38.jar` | smartboot aio | `org.smartboot.socket:aio-core:[1.5,1.6)` |
| `jlayer-1.0.1.4.jar` | JLayer (javazoom) | `com.googlecode.soundlibs:jlayer` |
| `mp3spi-1.9.5.4.jar` | MP3SPI (javazoom) | `com.googlecode.soundlibs:mp3spi` |
| `jorbis-0.0.17.4.jar` | JOrbis (javazoom) | `com.googlecode.soundlibs:jorbis` |
| `vorbisspi-1.0.3.3.jar` | VorbisSPI (javazoom) | `com.googlecode.soundlibs:vorbisspi` |
| `tritonus-share-0.3.7.4.jar` | Tritonus (sun) | `com.googlecode.soundlibs:tritonus-share` |

> **Action required before redistributing this repository further:** each of these carries its
> own license terms, which are inside the jars (`META-INF/LICENSE*`, `META-INF/MANIFEST.MF`,
> or upstream repos). I did not verify them individually as part of the reconstruction, so this
> table records **provenance**, not a license determination. Confirm each one, and drop the jars
> from redistribution if their terms require it — rebuilding can fetch them from Maven Central
> instead.

## 2. Binary resources (`src/main/resources/assets/`)

Non-code payloads copied verbatim out of the shipped jar:

- **Fonts** — `MiSans-Regular.ttf`, `MaterialSymbolsRounded.ttf`,
  `MaterialSymbolsRounded_Fill.ttf`, `Urbanist-VariableFont_wght.ttf`, `icon.ttf`. These are
  third-party typefaces with their own licenses (typically SIL OFL / Apache for the Material
  Symbols and Urbanist families, and a vendor license for MiSans). **Not covered by this
  repository's GPL grant.** Verify each before shipping.
- **Images** — `mainmenu/cat.png`, `particles/*.png`, `targetesp/*.png`, `yuzhe/*.{png,jpg}`.
  Artwork of unknown provenance; the `yuzhe/` set in particular looks like user-supplied art.
  **Not covered by the GPL grant.**
- **Audio** — `sounds/*.wav`. Provenance unknown; **not covered by the GPL grant.**
- **`potion_effects.dat`** — 2.9 MB data blob, format-specific to this client.
- **Shaders** — `heypixel/VcX6svVqmeT8/{shader,shaders}/*.{vert,frag}`, and
  `minecraft/shaders/**/motion_blur.{fsh,vsh,json}` (the latter overrides vanilla Minecraft
  shader files, which are themselves Mojang-licensed).
- `assets/heypixel/VcX6svVqmeT8/` — the directory name is an obfuscator artifact; code
  references it as a literal path, so do not rename it.

## 3. Minecraft / Forge

The source references `net.minecraft.*`, `com.mojang.blaze3d.*`, `net.minecraftforge.*` and
Mixin APIs. Those are **not** included here and remain under Mojang's EULA and Forge's own
license. Class-file mappings used during restoration are recorded in
`docs/mapping-provenance.txt` — Mojang's mappings header permits development use but forbids
unmodified redistribution, which is why the mapping data itself is not committed.

## 4. Tooling (`tools/`)

The deobfuscation/verification tools I wrote for this reconstruction. `vineflower.jar` was
deliberately **not** committed — it is a third-party binary available from
<https://vineflower.org/> (Apache-2.0); the tools reference it as a compile-time classpath entry.

---

**Bottom line:** if you redistribute this repository, redistribute the source and your own
documentation, and treat `lib/` and the binary assets under `assets/` as third-party material
whose terms you must check independently.
