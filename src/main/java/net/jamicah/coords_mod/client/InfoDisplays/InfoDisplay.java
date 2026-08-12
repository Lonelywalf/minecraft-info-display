package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import java.text.SimpleDateFormat;
import java.util.Date;

// this class contains all the settings and methods of the HUD, like the position, color, etc.
// this is also the base class for all the info displays
public abstract class InfoDisplay {
    /*
        Standard Layout of the HUD:
        - 2 padding left and right from the edge of the screen for the rectangle
        - 3 padding on all sides from the inside of the rectangle to the text


       2px..+---------------------+
         3px|...FPS: 60           |
            |   12 23 34          |
            |   Biome: Forest     |
            +---------------------+
     */

    public static int totalInfoDisplayInstances = 0;

    public static boolean isHudEnabled = false;

    public String displayName = "";
    public String infoText = "";
    public boolean isEnabled = false;
    public int textLength = 0;


    public static int yCurrent = 0;
    public static boolean isRelativeRight = false;
    public static boolean isRelativeTop = false;
    public static boolean isInRelativeMode = false;

    public static int textColor = 0xFFFFFF;

    public static int x = 0;
    public static int y = 0;

    // rectangle values
    public static int xPosRectangle = 0;
    public static int lengthRectangle = 0;
    public static int bgColorRectangle = 0x000000;

    public InfoDisplay(boolean status) {
        totalInfoDisplayInstances++;
        this.isEnabled = status;
    }


    public abstract void loadConfiguration();

    public static void loadGlobalConfiguration() {
        isHudEnabled = Config.HANDLER.instance().toggleHud;
        isInRelativeMode = !Config.HANDLER.instance().absoluteMode;
        bgColorRectangle = Config.HANDLER.instance().bgColor.getRGB();
        textColor = Config.HANDLER.instance().textColor.getRGB();
        x = (!InfoDisplay.isInRelativeMode ? Config.HANDLER.instance().x : 2);
        y = Config.HANDLER.instance().y;
    }

    public static void renderRectangle(GuiGraphicsExtractor drawContext, int longestX) {
        InfoDisplay.xPosRectangle = 0;
        if (InfoDisplay.yCurrent != InfoDisplay.y) {
            // determine the x length of the rectangle
            if (InfoDisplay.isInRelativeMode) {
                InfoDisplay.xPosRectangle = InfoDisplay.x + (InfoDisplay.isRelativeRight ? -1 : 1) * (longestX + 3);
            } else {
                InfoDisplay.xPosRectangle = InfoDisplay.x + longestX + 3 + 2;
            }
            drawContext.fill(
                    InfoDisplay.x,
                    InfoDisplay.y,
                    // 3 padding left right
                    // if it's in absolute mode, it should just add the longestX (+3 padding)
                    InfoDisplay.xPosRectangle,
                    InfoDisplay.y + InfoDisplay.lengthRectangle,
                    bgColorRectangle
            );
        }
        // move "pointer" back to the top
        InfoDisplay.yCurrent = InfoDisplay.y;
    }

    public static void updateRelativePosition(Minecraft client) {
        lengthRectangle = getEnabledInfoCount() * 10 + 3;

        if (InfoDisplay.isRelativeRight) {
            // -2 padding
            InfoDisplay.x = client.getWindow().getGuiScaledWidth() - 2;
        } else {
            InfoDisplay.x = 2;
        }

        if (InfoDisplay.isRelativeTop) {
            InfoDisplay.y = 2;
        } else {
            InfoDisplay.y = client.getWindow().getGuiScaledHeight() - lengthRectangle - 2;
        }
    }

    public abstract void updateInformation(Minecraft client);

    public void drawText(GuiGraphicsExtractor drawContext, Minecraft client) {
        drawContext.text(
                client.font,
                this.infoText,
                // if the pos is relative right, then subtract the length of the text
                // -2 padding for relative right because IDK
                (isInRelativeMode) ?
                        (x + (dynamicSizeX(this.infoText) * (isRelativeRight ? -1 : 0)) + (isRelativeRight ? -2 : 3)) :
                        getTextAlignmentXPosition(this.infoText, xPosRectangle),
                yCurrent + 3,
                textColor,
                Config.HANDLER.instance().toggleTextShadow
        );

        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    // method to dynamically change the size of the rectangle based on the length of the biome name,
    // shorter char like i, l, t, I, k and f are also taken into account
    public static int dynamicSizeX(String text) {
        return Minecraft.getInstance().font.width(text);
    }

    // gets the x position of the text alignment
    // relative to the background
    // parameter x2 is the end x position of the rectangle
    public static int getTextAlignmentXPosition(String text, int x2) {
        int xPos;
        Config.TextAlignment
                alignment = Config.HANDLER.instance().textAlignment;
        if (Config.HANDLER.instance().absoluteMode) {
            xPos = switch (alignment) {
                case LEFT -> x + 3;
                case CENTER -> x + ((x2 - x) / 2)+1 - dynamicSizeX(text) / 2;
                case RIGHT -> x2 - dynamicSizeX(text) - 2;
            };
        } else {
            xPos = x + 3;
        }
        return xPos;
    }

    public static String getCurrentBiome() {
        Minecraft client = Minecraft.getInstance();
        assert client.level != null;
        assert client.player != null;
        String biomeGibberish = client
                .level
                .getBiome(client.player.blockPosition())
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

    public static void setRelativePosition() {
        var instance = Config.HANDLER.instance();
        switch (instance.relativePosition) {
            case TOP_RIGHT -> {
                isRelativeRight = true;
                isRelativeTop = true;
            }
            case BOTTOM_LEFT -> {
                isRelativeRight = false;
                isRelativeTop = false;
            }
            case BOTTOM_RIGHT -> {
                isRelativeRight = true;
                isRelativeTop = false;
            }
            default -> {
                isRelativeRight = false;
                isRelativeTop = true;
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

    public static int getEnabledInfoCount() {
        int count = 0;
        for (InfoDisplay infodisplay : HUD_render.infoDisplays) {
            if (infodisplay.isEnabled) {
                count++;
            }
        }
        return count;
    }
}
