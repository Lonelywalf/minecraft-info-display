package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HUD_render implements HudRenderCallback {
    public static Boolean toggleHud = Config.readConfig("HUD");
    public static Boolean toggleBackground = Config.readConfig("Background");

    public static Boolean toggleBiome = Config.readConfig("Biome");
    public static Boolean toggleFPS = Config.readConfig("FPS");
    public static Boolean toggleCoords = Config.readConfig("Coords");
    public static Boolean toggleDirection = Config.readConfig("Direction");

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {

        MinecraftClient client = MinecraftClient.getInstance();

        // hide hud when f1 or toggleHud is false
        if (MinecraftClient.
                getInstance().
                options.hudHidden || !toggleHud) {
            return;
        }


        // pos of the gui
        int x = 5;
        int y = 5;

        // dynamic y position
        int yCurrent = y;

        // the background depends on the length of the text
        // so the background is drawn first
        // gather information

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
            int x_pos = (int) client.player.getX();
            int y_pos = (int) client.player.getY();
            int z_pos = (int) client.player.getZ();
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

        // render rectangle bg
        if (toggleBackground && yCurrent != y) {
            // compare which of the text is the longest
            drawContext.fill(x-3,
                    y-3,
                    Math.max(currentFPSx,
                            Math.max(currentCoordsX, Math.max(currentBiomeX, currentDirectionX))),
                    yCurrent,
                    0x4D000000
            );
        }
        // move "pointer" back to the top
        yCurrent = y;



        // render the information (text)

        // render "FPS"
        if (toggleFPS) {
            drawContext.drawText(client.textRenderer,
                    currentFPS,
                    x,
                    yCurrent,
                    0xFFFFFFFF,
                    false
            );
            yCurrent += 10;
        }

        // render "Coordinates"
        if (toggleCoords) {
            drawContext.drawText(client.textRenderer,
                    currentCoords,
                    x,
                    yCurrent,
                    0xFFFFFFFF,
                    false
            );
            yCurrent += 10;
        }

        // render "Biome"
        if (toggleBiome) {
            drawContext.drawText(client.textRenderer,
                    currentBiome,
                    x,
                    yCurrent,
                    0xFFFFFFFF,
                    false
            );
            yCurrent += 10;
        }

        // render "Direction"
        if (toggleDirection) {
            drawContext.drawText(client.textRenderer,
                    currentDirection,
                    x,
                    yCurrent,
                    0xFFFFFFFF,
                    false
            );
        }
    }


    public static String getCurrentBiome() {
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
