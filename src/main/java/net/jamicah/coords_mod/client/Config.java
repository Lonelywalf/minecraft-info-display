package net.jamicah.coords_mod.client;

import dev.isxander.yacl3.api.NameableEnum;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {

    private static final Logger LOGGER = Logger.getLogger("coords_mod");

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.fromNamespaceAndPath("coords_mod", "info_display_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("infoDisplay.json5"))
                    .setJson5(true)
                    .build()
            )
            .build();


    @SerialEntry
    public boolean toggleHud = true;

    @SerialEntry
    public boolean toggleFPS = true;

    @SerialEntry
    public boolean toggleCoords = true;

    @SerialEntry
    public boolean toggleBiome = true;

    @SerialEntry
    public boolean toggleDirection = true;

    @SerialEntry
    public boolean toggleTime = false;

    @SerialEntry
    public boolean togglePing = false;


    @SerialEntry
    public boolean timeFormat12 = false;

    @SerialEntry
    public boolean showAmPm = true;

    @SerialEntry
    public boolean showSeconds = false;

    @SerialEntry
    public boolean toggleTextShadow = false;



    @SerialEntry
    public Color bgColor = new Color(0, 0, 0, 100);

    @SerialEntry
    public Color textColor = new Color(255, 255, 255, 255);




    @SerialEntry
    public int x = 2;

    @SerialEntry
    public int y = 2;

    @SerialEntry
    public RelativePositions relativePosition = RelativePositions.TOP_LEFT;

    public enum RelativePositions implements NameableEnum {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT;

        @Override
        public Component getDisplayName() {
            return Component.translatable(
                    "config.coords_mod.category.position.relativePosition."
                            + name().toLowerCase()
            );
        }
    }

    @SerialEntry
    public TextAlignment textAlignment = TextAlignment.LEFT;

    public enum TextAlignment implements NameableEnum {
        LEFT,
        CENTER,
        RIGHT;

        @Override
        public Component getDisplayName() {
            return Component.translatable(
                    "config.coords_mod.category.position.absolutePosition.textAlignment."
                            + name().toLowerCase()
            );
        }
    }

    @SerialEntry
    public boolean absoluteMode = false;

    @SerialEntry(comment = "Order for the Text labels")
    public List<Component> optionsList = Arrays.asList(
        Component.translatable("config.coords_mod.order_list.FPS"),
        Component.translatable("config.coords_mod.order_list.coords"),
        Component.translatable("config.coords_mod.order_list.biome"),
        Component.translatable("config.coords_mod.order_list.direction"),
        Component.translatable("config.coords_mod.order_list.time"),
        Component.translatable("config.coords_mod.order_list.ping")
    );

    @SerialEntry
    public String customFPSText = "%s FPS";

    @SerialEntry
    public String customCoordsText = "%s %s %s";

    @SerialEntry
    public String customBiomeText = "Biome: %s";

    @SerialEntry
    public String customDirectionText = "Facing: %s";

    @SerialEntry
    public String customTimeText = "%s";

    @SerialEntry
    public String customPingText = "Ping: %s ms";

    // --- Migration helpers ---
    // Default order as Texts for reference
    public static final List<Component> DEFAULT_OPTIONS = Arrays.asList(
            Component.translatable("config.coords_mod.order_list.FPS"),
            Component.translatable("config.coords_mod.order_list.coords"),
            Component.translatable("config.coords_mod.order_list.biome"),
            Component.translatable("config.coords_mod.order_list.direction"),
            Component.translatable("config.coords_mod.order_list.time"),
            Component.translatable("config.coords_mod.order_list.ping")
    );

    // normalize a Text entry to a key-like suffix used in the rest of the code
    private static String normalizeOptionText(Component t) {
        if (t == null) return "";
        String s = t.toString();
        s = s.replaceAll("translation\\{key='config\\.coords_mod\\.order_list\\.", "");
        s = s.replaceAll("', args=\\[]}", "");
        return s.trim();
    }

    // Ensure any newly added defaults are present in optionsList. This appends missing defaults
    // (so existing user order is preserved). Call this after loading config from disk.
    public void migrateOptionsListIfNeeded() {
        try {
            if (optionsList == null) {
                optionsList = DEFAULT_OPTIONS;
                // try to persist
                trySaveHandler();
                return;
            }

            // Build a set of normalized existing keys for fast lookup
            java.util.Set<String> existing = new java.util.HashSet<>();
            for (Component t : optionsList) {
                existing.add(normalizeOptionText(t).toLowerCase());
            }

            boolean changed = false;
            java.util.List<Component> newList = new java.util.ArrayList<>(optionsList);
            for (Component def : DEFAULT_OPTIONS) {
                String key = normalizeOptionText(def).toLowerCase();
                if (!existing.contains(key)) {
                    newList.add(def);
                    changed = true;
                }
            }

            if (changed) {
                optionsList = newList;
                trySaveHandler();
            }
        } catch (Exception e) {
            // don't break the client if migration fails; log to console
            LOGGER.log(Level.SEVERE, "coords_mod: failed to migrate optionsList", e);
        }
    }

    private void trySaveHandler() {
        try {
            if (HANDLER != null) {
                // The ConfigClassHandler API usually exposes save/load methods. Try common ones.
                try {
                    // preferred: save()
                    java.lang.reflect.Method m = HANDLER.getClass().getMethod("save");
                    m.invoke(HANDLER);
                    return;
                } catch (NoSuchMethodException ignored) {}

                try {
                    // alternative: store() or write()
                    java.lang.reflect.Method m2 = HANDLER.getClass().getMethod("store");
                    m2.invoke(HANDLER);
                    return;
                } catch (NoSuchMethodException ignored) {}

                try {
                    java.lang.reflect.Method m3 = HANDLER.getClass().getMethod("write");
                    m3.invoke(HANDLER);
                    return;
                } catch (NoSuchMethodException ignored) {}

                // fallback: if none exist, try to load serializer and write file directly
                try {
                    // get serializer and path via reflection only if present
                    java.lang.reflect.Field f = HANDLER.getClass().getDeclaredField("serializer");
                    f.setAccessible(true);
                    Object serializer = f.get(HANDLER);
                    if (serializer != null) {
                        // try to call a 'save' method on serializer
                        try {
                            java.lang.reflect.Method ms = serializer.getClass().getMethod("save", Object.class);
                            ms.invoke(serializer, HANDLER.instance());
                            return;
                        } catch (NoSuchMethodException ignored2) {}
                    }
                } catch (NoSuchFieldException | IllegalAccessException ignored) {}

                // if none of the reflection options worked, fall back to printing a notice
                LOGGER.warning("coords_mod: Config handler doesn't expose a known save method; manual save may be required.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "coords_mod: error while attempting to save migrated config", e);
        }
    }

}