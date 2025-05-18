package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.rendering.v1.LayeredDrawerWrapper;
import net.jamicah.coords_mod.Coords_mod;
import net.jamicah.coords_mod.client.InfoDisplays.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class HUD_render implements HudLayerRegistrationCallback {
    public static final Identifier INFO_LAYER = Identifier.of(Coords_mod.MOD_ID, "info-layer");

    public static FPSDisplay fpsDisplay = new FPSDisplay(Config.HANDLER.instance().toggleFPS);
    public static CoordinatesDisplay coordsDisplay = new CoordinatesDisplay(Config.HANDLER.instance().toggleCoords);
    public static BiomeDisplay biomeDisplay = new BiomeDisplay(Config.HANDLER.instance().toggleBiome);
    public static DirectionDisplay directionDisplay = new DirectionDisplay(Config.HANDLER.instance().toggleDirection);
    public static ClockDisplay clockDisplay = new ClockDisplay(Config.HANDLER.instance().toggleTime);

    // information
    public static InfoDisplay[] infoDisplays = {
            fpsDisplay,
            coordsDisplay,
            biomeDisplay,
            directionDisplay,
            clockDisplay
    };

    public boolean load = false;

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

            // set order of infoDisplays
            // and also check if the list is still valid
            boolean isStillVailList = false;
            for (InfoDisplay infoDisplay : infoDisplays) {
                if (read.equals(infoDisplay.displayName)) {
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
                        clockDisplay
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
        2. create new InfoDisplay class
        3. add it to the infoDisplays array
     */

    public void renderInfoDisplay(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        InfoDisplay.setRelativePosition();

        // load the config on first run
        if (!load) {
            load = true;
            Config.HANDLER.load();
            infoDisplays = getOrder();
        }

        // load the config
        InfoDisplay.loadGlobalConfiguration();

        for (InfoDisplay infoDisplay : infoDisplays) {
            infoDisplay.loadConfiguration();
        }


        // hide hud when f1 or toggleHud is false
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

    @Override
    public void register(LayeredDrawerWrapper layeredDrawerWrapper) {
        layeredDrawerWrapper.attachLayerBefore(IdentifiedLayer.CROSSHAIR, INFO_LAYER, this::renderInfoDisplay);
    }
}
