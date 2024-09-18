package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
// TODO: relative position
public class HUD_render implements HudRenderCallback {

    // TODO: don't forget to implement this
    public static String infoOrder = "f,c,b,d,C";

    // toggleable options
    public Boolean toggleHud;
    public Boolean toggleFPS;
    public Boolean toggleBiome;
    public Boolean toggleCoords;
    public Boolean toggleDirection;
    public Boolean toggleClock;

    // background color
    public int bgOpacity;
    public int bgColorR;
    public int bgColorG;
    public int bgColorB;

    // text color
    public int textColorR;
    public int textColorG;
    public int textColorB;

    // position
    public int x;
    public int y;


    /*
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



    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();

        toggleHud = Config.HANDLER.instance().toggleHud;
        toggleFPS = Config.HANDLER.instance().toggleFPS;
        toggleBiome = Config.HANDLER.instance().toggleBiome;
        toggleCoords = Config.HANDLER.instance().toggleCoords;
        toggleDirection = Config.HANDLER.instance().toggleDirection;
        toggleClock = Config.HANDLER.instance().toggleTime;

        bgOpacity = Config.HANDLER.instance().bgOpacity;
        bgColorR = Config.HANDLER.instance().bgColorR;
        bgColorG = Config.HANDLER.instance().bgColorG;
        bgColorB = Config.HANDLER.instance().bgColorB;

        textColorR = Config.HANDLER.instance().textColorR;
        textColorG = Config.HANDLER.instance().textColorG;
        textColorB = Config.HANDLER.instance().textColorB;

        x = Config.HANDLER.instance().x;
        y = Config.HANDLER.instance().y;


        // hide hud when f1 or toggleHud is false
        if (MinecraftClient
                .getInstance()
                .options.hudHidden || !toggleHud) {
            return;
        }

        // pos of the gui
        int x = Config.HANDLER.instance().x;
        int y = Config.HANDLER.instance().y;

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
                    Collections.max(
                            Arrays.asList(
                                    currentFPSx,
                                    currentCoordsX,
                                    currentBiomeX,
                                    currentDirectionX,
                                    currentTimeX
                            )
                    ) + 3,
                    y + length,
                    new Color(
                            bgColorR,
                            bgColorG,
                            bgColorB,
                            bgOpacity
                    ).getRGB()
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
        patern = Config.HANDLER.instance().timeFormat12 ? "HH:mm" : "hh:mm";

        if (Config.HANDLER.instance().showSeconds) {
            patern += ":ss";
        }

        if (Config.HANDLER.instance().showAmPm) {
            patern += " aa";
        }

        SimpleDateFormat time = new SimpleDateFormat(patern);
        Date date = new Date();

        return time.format(date);
    }

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
        if (Config.HANDLER.instance().toggleFPS) count++;
        if (Config.HANDLER.instance().toggleCoords) count++;
        if (Config.HANDLER.instance().toggleBiome) count++;
        if (Config.HANDLER.instance().toggleDirection) count++;
        if (Config.HANDLER.instance().toggleTime) count++;
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
