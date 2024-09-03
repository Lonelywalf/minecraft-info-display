package net.jamicah.coords_mod.configuration;


import net.fabricmc.loader.api.FabricLoader;
import net.jamicah.coords_mod.Coords_mod;
import net.jamicah.coords_mod.client.HUD_render;

import java.io.*;

public class Config {
    public static Boolean readToggleConfigBool(String selection) {
        BufferedReader read;
        String configFileDir = FabricLoader
                .getInstance()
                .getConfigDir()
                .toString() + "\\coordsDisplay_config.txt";
        try {
            read = new BufferedReader(new FileReader(configFileDir));
            String text;


            while ((text = read.readLine()) != null) {
                if (text.equals("Toggle" + selection + ":true")) {
                    read.close();

                    return true;
                }
            }
            read.close();
            return false;

        } catch (FileNotFoundException e) { // write config file if it wasn't created yet
            Coords_mod.LOGGER.info("Config file not found, creating one...");

            writeNewConfig();

            readToggleConfigBool(selection);

        } catch (IOException ignored) {

        }
        return true;
    }

    public static Boolean readOtherConfigBool(String selection) {
        BufferedReader read;
        String configFileDir = FabricLoader
                .getInstance()
                .getConfigDir()
                .toString() + "\\coordsDisplay_config.txt";
        try {
            read = new BufferedReader(new FileReader(configFileDir));
            String text;


            while ((text = read.readLine()) != null) {
                if (text.equals(selection + ":true")) {
                    read.close();
                    return true;
                }
            }
            read.close();
            return false;

        } catch (FileNotFoundException e) { // write config file if it wasn't created yet
            Coords_mod.LOGGER.info("Config file not found, creating one...");

            writeNewConfig();

            readToggleConfigBool(selection);

        } catch (IOException ignored) {

        }
        return true;
    }

    public static int readConfigInt(String selection) {
        BufferedReader read;
        String configFileDir = FabricLoader
                .getInstance()
                .getConfigDir()
                .toString() + "\\coordsDisplay_config.txt";
        try {
            read = new BufferedReader(new FileReader(configFileDir));
            String text;


            while ((text = read.readLine()) != null) {
                if (text.contains(selection)) {
                    read.close();

                    // return the value after the colon
                    return Integer.parseInt(text.substring(selection.length() + 1));
                }
            }
            read.close();
            // return default value if no value was found
            switch (selection) {
                case "bgOpacity":
                    return 77;
                case "bgColor":
                    return 0;
                case "textColorR", "textColorG", "textColorB":
                    return 255;
                case "xPos", "yPos":
                    return 2;
            }

        } catch (FileNotFoundException e) { // write config file if it wasn't created yet
            Coords_mod.LOGGER.info("Config file not found, creating one...");

            writeNewConfig();

            readToggleConfigBool(selection);

        } catch (IOException ignored) {

        }
        return 0;
    }

    // read InfoOrder
    public static String readToggleConfigBool() {
        String configFileDir = FabricLoader
                .getInstance()
                .getConfigDir()
                .toString() + "\\coordsDisplay_config.txt";
        try {
            BufferedReader read = new BufferedReader(new FileReader(configFileDir));
            String text;
            String infoOrder;
            while ((text = read.readLine()) != null) {
                if (text.contains("InfoOrder")) {
                    read.close();

                    // return the value after the colon
                    return text.substring(10);
                }
            }

        } catch (FileNotFoundException e) {
            Coords_mod.LOGGER.info("Config file not found, creating one...");

            writeNewConfig();

            readToggleConfigBool();
        } catch (IOException e) {
            Coords_mod.LOGGER.info(e.toString());
        }

        // return default value if no value was found
        return "f,c,b,d";
    }

    // save config
    public static void saveConfig() {
        BufferedWriter write;
        try {
            write = new BufferedWriter(
                    new FileWriter(
                            FabricLoader.getInstance().
                                    getConfigDir().
                                    toString() + "\\coordsDisplay_config.txt")
            );

            write.write("InfoOrder:" + HUD_render.infoOrder + "\n");
            write.write("ToggleHUD:" + HUD_render.toggleHud + "\n");
            write.write("ToggleBiome:" + HUD_render.toggleBiome + "\n");
            write.write("ToggleFPS:" + HUD_render.toggleFPS + "\n");
            write.write("ToggleClock:" + HUD_render.toggleClock + "\n");
            write.write("ToggleCoords:" + HUD_render.toggleCoords + "\n");
            write.write("ToggleDirection:" + HUD_render.toggleDirection + "\n");
            write.write("\nbgOpacity:" + HUD_render.bgOpacity + "\n");
            write.write("bgColor:" + HUD_render.bgColorR + "\n");
            write.write("bgColor:" + HUD_render.bgColorG + "\n");
            write.write("bgColor:" + HUD_render.bgColorB + "\n");
            write.write("\ntextColorR:" + HUD_render.textColorR + "\n");
            write.write("textColorG:" + HUD_render.textColorG + "\n");
            write.write("textColorB:" + HUD_render.textColorB + "\n");
            write.write("\nxPos:" + HUD_render.x + "\n");
            write.write("yPos:" + HUD_render.y + "\n");

            write.write("timeFormat12:" + HUD_render.timeFormat12 + "\n");
            write.write("showAmPm:" + HUD_render.showAmPm + "\n");
            write.write("showSeconds:" + HUD_render.showSeconds + "\n");
            write.close();

        } catch (IOException ex) {
            Coords_mod.LOGGER.info(ex.toString());
        }
    }

    public static void writeNewConfig() {
        BufferedWriter write;
        // write the config file if it hasn't been created yet
        try {
            write = new BufferedWriter(
                    new FileWriter(
                            FabricLoader.getInstance().
                                    getConfigDir().
                                    toString() + "\\coordsDisplay_config.txt")
            );
            write.write("InfoOrder:" + "f,c,b,d\n");
            write.write("ToggleHUD:" + "true\n");
            write.write("ToggleBiome:" + "true\n");
            write.write("ToggleFPS:" + "true\n");
            write.write("ToggleCoords:" + "true\n");
            write.write("ToggleClock:" + "false\n");
            write.write("ToggleDirection:" + "true\n");

            write.write("\nbgOpacity:" + "100\n");
            write.write("bgColor:" + "0\n");
            write.write("bgColor:" + "0\n");
            write.write("bgColor:" + "0\n");

            write.write("\ntextColorR:" + "255\n");
            write.write("textColorG:" + "255\n");
            write.write("textColorB:" + "255\n");

            write.write("\nxPos:" + "2\n");
            write.write("yPos:" + "2\n");

            write.write("timeFormat12:" + "false\n");
            write.write("showAmPm:" + "true\n");
            write.write("showSeconds:" + "false\n");
            write.close();

        } catch (IOException ex) {
            Coords_mod.LOGGER.info(ex.toString());
        }
    }
}