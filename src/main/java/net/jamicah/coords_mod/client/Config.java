package net.jamicah.coords_mod.client;


import net.fabricmc.loader.api.FabricLoader;
import net.jamicah.coords_mod.Coords_mod;

import java.io.*;

public class Config {
    public static Boolean readConfig(String selection) {
        BufferedReader read;
        String configFileDir = FabricLoader.getInstance().getConfigDir().toString() + "\\config_coords_display.txt";
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

            writeConfig();

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
            } catch (IOException ex) {
                Coords_mod.LOGGER.info(ex.toString());
                return true;
            }

        } catch (IOException ignored) {

        }
        return true;
    }


    public static void writeConfig() {
        BufferedWriter write;
        try {   // write the config file if it hasn't been created yet
            write = new BufferedWriter(new FileWriter(FabricLoader.getInstance().getConfigDir().toString() + "\\config_coords_display.txt"));

            write.write("ToggleHUD:" + "true\n");
            write.write("ToggleBiome:" + "true\n");
            write.write("ToggleFPS:" + "true\n");
            write.write("ToggleBackground:" + "true\n");
            write.close();

        } catch (IOException ex) {
            Coords_mod.LOGGER.info(ex.toString());
        }
    }
}