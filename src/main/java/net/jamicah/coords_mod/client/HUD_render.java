package net.jamicah.coords_mod.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HUD_render implements HudRenderCallback {
    public static Boolean toggleHud = Config.readConfig("HUD");

    // show biome or not
    public static Boolean toggleBiome = Config.readConfig("Biome");
    public static Boolean toggleFPS = Config.readConfig("FPS");
    public static Boolean toggleBackground = Config.readConfig("Background");
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        // pos of the gui
        int x = 5;
        int y = 5;

        if (toggleFPS) {
            y += 10;
        }

        int rectX = x-3;
        int rectY;
        if (toggleFPS) {
            rectY = y-13;
        } else {
            rectY = y-3;
        }

        int rectYBiome = -10;

        // don't draw full rectangle if biome info is not enabled
        if (toggleBiome) {
            rectYBiome = 0;
        }



        String currentBiome = "";
        MinecraftClient client = MinecraftClient.getInstance();
        /*if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }*/



        int x_pos;
        int y_pos;
        int z_pos;

        int ysizefps = 0;
        if (toggleHud) {
            // show coordinates
            x_pos = (int) client.player.getX();
            y_pos = (int) client.player.getY();
            z_pos = (int) client.player.getZ();

            currentBiome = getCurrentBiome();
            currentBiome = currentBiome.substring(0, 1).toUpperCase() + currentBiome.substring(1);
            currentBiome = currentBiome.replace('_', ' ');
            if (toggleFPS) {    // shift eveything by 10 when fps is enabled
                ysizefps = 10;
            }
            if (toggleBiome && toggleBackground) {

                drawContext.fill(rectX, rectY, rectX+dynamicSizeX(currentBiome, x_pos, y_pos, z_pos), rectY+23+ysizefps, 0x4D000000);
            } else {
                if (toggleBackground) drawContext.fill(rectX, rectY, rectX+dynamicSizeX(x_pos, y_pos, z_pos), rectY+13+ysizefps, 0x4D000000);
            }

            if (toggleFPS) {
                drawContext.drawText(client.textRenderer, client.getCurrentFps() + " FPS", x, y-10, 0xFFFFFFFF, false);
            }

            // coordinates
            drawContext.drawText(client.textRenderer,
                               x_pos +
                            " " +  y_pos +
                            " " +  z_pos,
                    x, y, 0xFFFFFFFF, false);

            // show biome only if biome is toggled
            if (toggleBiome) {
                drawContext.drawText(client.textRenderer, "Biome: " +
                        currentBiome, x, y + 10, 0xFFFFFFFF, false);

            }


        }
    }


    public static String getCurrentBiome() {
        MinecraftClient client = MinecraftClient.getInstance();
        String biomeGibberish = client.world.getBiome(client.player.getBlockPos()).toString();
        String biomeToString = biomeGibberish.substring(biomeGibberish.indexOf("/ minecraft:")+12 , biomeGibberish.indexOf(']'));
        return biomeToString;



    }

    // method to dynamically change the size of the rectangle based on the length of the biome name,
    // shorter char like i, l, t, I, k and f are also taken into account
    public static int dynamicSizeX(String biome, int x, int y, int z) {

        int total_length = biome.length();
        int total_pixels = 0;
        for (int i = 0; i < total_length; i++) {

            char c = biome.charAt(i);
            if (c == 'i') {
                total_pixels += 2;
            } else if (c == 'l') {
                total_pixels += 3;
            } else if (c == 't' || c == 'I') {
                total_pixels += 4;
            } else if (c == 'k' || c == 'f') {
                total_pixels += 5;
            } else {
                total_pixels += 6;
            }
        }
        total_pixels = total_pixels+35+2;
        int size_coords = dynamicSizeX(x, y, z);
        if (size_coords > (total_pixels)) return size_coords;       // check if coords aren't longer than biome
        return total_pixels;   // 3 padding on each side + the word "Biome: "
    }

    // method to dynamically change the size of the rectangle based on the length of the x, y, z coordinates
    public static int dynamicSizeX(int x, int y, int z) {
        int x_length = String.valueOf(x).length();
        int y_length = String.valueOf(y).length();
        int z_length = String.valueOf(z).length();
        int total_length = x_length + y_length + z_length;
        return ((total_length)*6)+14;       // not sure to be honest why this works, but it does
    }


}
