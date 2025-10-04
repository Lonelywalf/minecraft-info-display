package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.jamicah.coords_mod.Coords_mod;
import net.jamicah.coords_mod.client.InfoDisplays.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class HUD_render implements HudElementRegistry {
    public static final Identifier INFO_LAYER = Identifier.of(Coords_mod.MOD_ID, "info-layer");

    public static FPSDisplay fpsDisplay = new FPSDisplay(Config.HANDLER.instance().toggleFPS);
    public static CoordinatesDisplay coordsDisplay = new CoordinatesDisplay(Config.HANDLER.instance().toggleCoords);
    public static BiomeDisplay biomeDisplay = new BiomeDisplay(Config.HANDLER.instance().toggleBiome);
    public static DirectionDisplay directionDisplay = new DirectionDisplay(Config.HANDLER.instance().toggleDirection);
    public static ClockDisplay clockDisplay = new ClockDisplay(Config.HANDLER.instance().toggleTime);
    public static PingDisplay pingDisplay = new PingDisplay(Config.HANDLER.instance().togglePing);

    // information
    public static InfoDisplay[] infoDisplays = {
            fpsDisplay,
            coordsDisplay,
            biomeDisplay,
            directionDisplay,
            clockDisplay,
            pingDisplay
    };

    public static boolean load = false;

    public static InfoDisplay[] getOrder() {
        InfoDisplay[] readOrder = new InfoDisplay[Config.HANDLER.instance().optionsList.size()];
        for (int i = 0; i < Config.HANDLER.instance().optionsList.size(); i++) {
            String read =  Config.HANDLER.instance()
                    .optionsList
                    .get(i)
                    .toString()
                    .replaceAll(
                            "translation\\{key='config\\.coords_mod\\.order_list\\.",
                            ""
                    )
                    .replaceAll(
                            "', args=\\[]}",
                            ""
                    );

            // normalize parsed string
            read = read.trim();

            // set order of infoDisplays
            // and also check if the list is still valid
            boolean isStillVailList = false;
            for (InfoDisplay infoDisplay : infoDisplays) {
                // compare case-insensitive to avoid mismatches like "Ping" vs "ping"
                if (read.equalsIgnoreCase(infoDisplay.displayName)) {
                    isStillVailList = true;
                    readOrder[i] = infoDisplay;
                    break;
                }
            }
            // reset to default order if the list is not valid
            if (!isStillVailList) {
                readOrder = new InfoDisplay[]{
                        fpsDisplay,
                        coordsDisplay,
                        biomeDisplay,
                        directionDisplay,
                        clockDisplay,
                        pingDisplay
                };
                break;
            }
        }
        return readOrder;
    }
    /*
        how to add a new config:
        1. add it to Config

        how to add a new info:
        1. add it to Config
            a) create a toggle boolean
            b) add it to the optionsList
            c) add custom text
            d) add to lang file
        2. ConfigScreen.java
            a) custom text
            b) toggle button
        3. Keybind
        4. create new InfoDisplay class
        5. add it to the infoDisplays array
            a) also create a static instance of it
            b) add it to the getOrder() default array
     */

    public static void renderInfoDisplay(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        // reference tickCounter to avoid unused-parameter warnings (no-op)
        if (tickCounter != null) tickCounter.hashCode();

        InfoDisplay.setRelativePosition();

        // load the config on first run
        if (!load) {
            load = true;
            Config.HANDLER.load();
            // migrate old configs: append any newly added default options (e.g. Ping)
            try {
                Config.HANDLER.instance().migrateOptionsListIfNeeded();
            } catch (Exception e) {
                System.err.println("coords_mod: failed to migrate optionsList on first load: " + e.getMessage());
            }
            infoDisplays = getOrder();
        }

        // load the config
        InfoDisplay.loadGlobalConfiguration();

        for (InfoDisplay infoDisplay : infoDisplays) {
            infoDisplay.loadConfiguration();
        }


        // hide hud when f1 or toggleHud is false or debug (F3) is shown
        if (client.options.hudHidden || !InfoDisplay.isHudEnabled) {
            return;
        }

        // dynamic y position
        InfoDisplay.yCurrent = InfoDisplay.y;


        // iterate through all existing infoDisplays and update
        // their information
        for (InfoDisplay infoDisplay : infoDisplays) {
            infoDisplay.textLength = 0;
            if (infoDisplay.isEnabled) {
                infoDisplay.updateInformation(client);
            }
        }

        int longestX = 0;
        // get the longest text length
        for (InfoDisplay infoDisplay : infoDisplays) {
            if (infoDisplay.textLength > longestX) {
                longestX = infoDisplay.textLength;
            }
        }

        if (InfoDisplay.isInRelativeMode) {
            InfoDisplay.updateRelativePosition(client);
        }

        // render rectangle bg
        InfoDisplay.renderRectangle(drawContext, longestX);

        // render the text
        for (InfoDisplay infoDisplay : infoDisplays) {
            if (infoDisplay.isEnabled) {
                infoDisplay.drawText(drawContext, client);
            }
        }
    }
}
