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


    /* deprecated
    how to add a new config value:
    1. add it here as a variable
    2. go to Config and update
       saveConfig() and writeNewConfig

    how to add a new info:
    1. add it here as a variable
    2. update onHudRender()
    3. update updateInfoOrder()
    4. update removeInvalidChars()
    5. go to Config and update
       saveConfig() and writeNewConfig
    */

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

    /* old methods
    // toggles the values based on the infoOrder String
    public static void updateInfoOrder() {

        boolean f = false;
        boolean c = false;
        boolean b = false;
        boolean d = false;
        boolean cl = false;

        for (String pos : infoOrder.split(",")) {
            switch (pos) {
                case "f":
                    f = true;
                    Config.HANDLER.instance().toggleFPS = true;
                    break;
                case "c":
                    c = true;
                    Config.HANDLER.instance().toggleCoords = true;
                    break;
                case "b":
                    b = true;
                    Config.HANDLER.instance().toggleBiome = true;
                    break;
                case "d":
                    d = true;
                    Config.HANDLER.instance().toggleDirection = true;
                    break;
                case "C":
                    cl = true;
                    Config.HANDLER.instance().toggleTime = true;
                    break;
            }
        }

        if (!f) {
            Config.HANDLER.instance().toggleFPS = false;
        }
        if (!c) {
            Config.HANDLER.instance().toggleCoords = false;
        }
        if (!b) {
            Config.HANDLER.instance().toggleBiome = false;
        }
        if (!d) {
            Config.HANDLER.instance().toggleDirection = false;
        }
        if (!cl) {
            Config.HANDLER.instance().toggleTime = false;
        }
        //ConfigOld.saveConfig();
    }
        private String[] removeInvalidChars(String[] order) {
        for (int i = 0; i < order.length; i++) {
            if (!order[i].equals("f") && !order[i].equals("c") && !order[i].equals("b") && !order[i].equals("d") && !order[i].equals("C")) {
                order = removeString(order, order[i]);
            }
        }

        return order;
    }

    // remove double characters
    private String[] removeDoubleChars(String[] order) {
        for (int i = 0; i < order.length; i++) {
            if (order[i].equals(" ")) {
                order = removeString(order, " ");
            }
        }
        return order;
    }

        // method to remove specific string from string array
    public static String[] removeString(String[] arr, String str) {
        int length = arr.length;
        int count = 0;
        for (String s : arr) {
            if (s.equals(str)) {
                count++;
            }
        }
        String[] newArr = new String[length-count];
        int i = 0;
        for (String s : arr) {
            if (!s.equals(str)) {
                newArr[i] = s;
                i++;
            }
        }
        return newArr;
    }
    */
}
