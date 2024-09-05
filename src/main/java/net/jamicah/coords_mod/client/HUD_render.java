package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jamicah.coords_mod.configuration.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HUD_render implements HudRenderCallback {
    public static Boolean toggleHud = Config.readToggleConfigBool("HUD");

    public static Boolean toggleBiome = Config.readToggleConfigBool("Biome");
    public static Boolean toggleFPS = Config.readToggleConfigBool("FPS");

    public static Boolean toggleCoords = Config.readToggleConfigBool("Coords");
    public static Boolean toggleDirection = Config.readToggleConfigBool("Direction");
    public static Boolean toggleClock = Config.readToggleConfigBool("Clock");
    public static String infoOrder = Config.readToggleConfigBool();

    public static int bgOpacity = Config.readConfigInt("bgOpacity");
    public static int bgColorR = Config.readConfigInt("bgColorR");
    public static int bgColorG = Config.readConfigInt("bgColorG");
    public static int bgColorB = Config.readConfigInt("bgColorB");

    public static int textColorR = Config.readConfigInt("textColorR");
    public static int textColorG = Config.readConfigInt("textColorG");
    public static int textColorB = Config.readConfigInt("textColorB");

    public static int x = Config.readConfigInt("xPos");
    public static int y = Config.readConfigInt("yPos");

    public static boolean timeFormat12 = Config.readOtherConfigBool("timeFormat12");
    public static boolean showAmPm = Config.readOtherConfigBool("showAmPm");
    public static boolean showSeconds = Config.readOtherConfigBool("showSeconds");

    public static int screenSizeX;
    public static int screenSizeY;

    // TODO: enable/disable text shadow

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
                    toggleFPS = true;
                    break;
                case "c":
                    c = true;
                    toggleCoords = true;
                    break;
                case "b":
                    b = true;
                    toggleBiome = true;
                    break;
                case "d":
                    d = true;
                    toggleDirection = true;
                    break;
                case "C":
                    cl = true;
                    toggleClock = true;
                    break;
            }
        }

        if (!f) {
            toggleFPS = false;
        }
        if (!c) {
            toggleCoords = false;
        }
        if (!b) {
            toggleBiome = false;
        }
        if (!d) {
            toggleDirection = false;
        }
        if (!cl) {
            toggleClock = false;
        }
        Config.saveConfig();
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {

        MinecraftClient client = MinecraftClient.getInstance();

        screenSizeX = client.getWindow().getScaledWidth();
        screenSizeY = client.getWindow().getScaledHeight();

        // hide hud when f1 or toggleHud is false
        if (MinecraftClient
                .getInstance()
                .options.hudHidden || !toggleHud) {
            return;
        }


        // pos of the gui
        int x = HUD_render.x;
        int y = HUD_render.y;

        // dynamic y position
        int yCurrent = y;

        // the background depends on the length of the text
        // so the background is drawn first

        // gather information

        String[] order =
                removeString(infoOrder.split(","),
                " "
                );
        order = removeInvalidChars(order);
        order = removeDoubleChars(order);
        /*
        int length = order.length;
        if (length > getEnabledInfoCount()) {
            length = length * 10 + 3;
        } else {
            length = getEnabledInfoCount() * 10 + 3;
        }
         */
        int length = getEnabledInfoCount() * 10 + 3;

        // FPS info
        String currentFPS = "";
        int currentFPSx = 0;
        if (toggleFPS) {
            // gather the required information
            currentFPS = client.getCurrentFps() + " FPS";
            currentFPSx = x + dynamicSizeX(currentFPS);
            yCurrent += 10;
        }

        // Coordinates info
        String currentCoords = "";
        int currentCoordsX = 0;
        if (toggleCoords) {
            assert client.player != null;

            // gather the required information
            int x_pos = (int) client.getCameraEntity().getX();
            int y_pos = (int) client.getCameraEntity().getY();
            int z_pos = (int) client.getCameraEntity().getZ();
            currentCoords = x_pos + " "
                        + y_pos + " "
                        + z_pos;
            currentCoordsX = x + dynamicSizeX(currentCoords);
            yCurrent += 10;
        }

        // Biomes info
        String currentBiome = "";
        int currentBiomeX = 0;
        if (toggleBiome) {
            // gather the required information
            currentBiome = "Biome: " + getCurrentBiome();
            currentBiomeX = x + dynamicSizeX(currentBiome);
            yCurrent += 10;
        }

        // Facing Direction info
        assert client.player != null;
        String currentDirection = client.player.getMovementDirection().asString();
        int currentDirectionX = 0;
        if (toggleDirection) {
            String dir = client.player.getMovementDirection().asString();
            currentDirection = "Facing: " + Character.toUpperCase(dir.charAt(0))
                    + dir.substring(1
            );

            currentDirectionX = x + dynamicSizeX(currentDirection);
            yCurrent += 10;
        }


        String currentTime = getCurrentTime();
        int currentTimeX = 0;
        if (toggleClock) {
            currentTimeX = x + dynamicSizeX(currentTime);
            yCurrent += 10;
        }



        // render rectangle bg
        if (yCurrent != y) {
            // compare which of the text is the longest
            drawContext.fill(
                    x,
                    y,
                    Math.max(
                            Math.max(
                                    currentFPSx,
                                    currentCoordsX
                            ),
                            Math.max(
                                    currentBiomeX,
                                    Math.max(currentDirectionX, currentTimeX)
                            )
                    ) + 3,
                    y + length,
                    new Color(bgColorR, bgColorG, bgColorB, bgOpacity).getRGB()
            );
        }
        // move "pointer" back to the top
        yCurrent = y;


        // render the information (text)

        for (String pos : order) {
            switch (pos) {
                case "f":
                    if (toggleFPS) {
                        drawContext.drawText(client.textRenderer,
                                currentFPS,
                                x+3,
                                yCurrent+3,
                                new Color(textColorR, textColorG, textColorB).getRGB(),
                                false
                        );
                        yCurrent += 10;
                    }
                    break;
                case "c":
                    if (toggleCoords) {
                        drawContext.drawText(client.textRenderer,
                                currentCoords,
                                x+3,
                                yCurrent+3,
                                new Color(textColorR, textColorG, textColorB).getRGB(),
                                false
                        );
                        yCurrent += 10;
                    }
                    break;
                case "C":
                    if (toggleClock) {
                        drawContext.drawText(client.textRenderer,
                                currentTime,
                                x+3,
                                yCurrent+3,
                                new Color(textColorR, textColorG, textColorB).getRGB(),
                                false
                        );
                        yCurrent += 10;
                    }
                    break;
                case "b":
                    if (toggleBiome) {
                        drawContext.drawText(client.textRenderer,
                                currentBiome,
                                x+3,
                                yCurrent+3,
                                new Color(textColorR, textColorG, textColorB).getRGB(),
                                false
                        );
                        yCurrent += 10;
                    }
                    break;
                case "d":
                    if (toggleDirection) {
                        drawContext.drawText(client.textRenderer,
                                currentDirection,
                                x+3,
                                yCurrent+3,
                                new Color(textColorR, textColorG, textColorB).getRGB(),
                                false
                        );
                        yCurrent += 10;
                    }
                    break;
            }
        }
    }

    public static String getCurrentTime() {
        String patern;
        if (timeFormat12) {
            patern = "HH:mm";
        } else {
            patern = "hh:mm";
        }

        if (showSeconds) {
            patern += ":ss";
        }

        if (showAmPm) {
            patern += " aa";
        }

        SimpleDateFormat time = new SimpleDateFormat(patern);
        Date date = new Date();

        return time.format(date);
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

    public static int getEnabledInfoCount() {
        int count = 0;
        if (toggleFPS) count++;
        if (toggleCoords) count++;
        if (toggleBiome) count++;
        if (toggleDirection) count++;
        if (toggleClock) count++;
        return count;
    }


    public String getCurrentBiome() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.world != null;
        assert client.player != null;
        String biomeGibberish = client
                .world
                .getBiome(client.player.getBlockPos())
                .toString();
        String biomeToString = biomeGibberish.substring(biomeGibberish.indexOf("/ minecraft:")+12 ,
                biomeGibberish.indexOf(']')
        );

        biomeToString = biomeToString.replace('_', ' ');

        // capitalize first letter
        biomeToString = Character.toUpperCase(
                biomeToString.charAt(0))
                + biomeToString.substring(1
        );

        // capitalize every letter after space
        for (int i = 0; i < biomeToString.length(); i++) {
            if (biomeToString.length() > 1 && biomeToString.charAt(i) == ' ') {
                biomeToString = biomeToString.substring(0, i+1)
                        + Character.toUpperCase(biomeToString.charAt(i+1))
                        + biomeToString.substring(i+2
                );
            }
        }
        return biomeToString;
    }

    // method to dynamically change the size of the rectangle based on the length of the biome name,
    // shorter char like i, l, t, I, k and f are also taken into account
    public static int dynamicSizeX(String text) {

        int total_length = text.length();
        int totalPixelWidth = 0;
        for (int i = 0; i < total_length; i++) {

            char c = text.charAt(i);
            if (c == 'i' || c == ':') {
                totalPixelWidth += 1;
            } else if (c == 'l') {
                totalPixelWidth += 2;
            } else if (c == 't' || c == 'I' || c == ' ') {
                totalPixelWidth += 3;
            } else if (c == 'k' || c == 'f') {
                totalPixelWidth += 4;
            } else {
                totalPixelWidth += 5;
            }
            totalPixelWidth++;
        }
        return totalPixelWidth+2; // 3 padding on the right side (+2 because of the extra pixel of the empty char)
    }

}
