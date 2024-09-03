package net.jamicah.coords_mod.configuration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class OpenConfigScreen implements ModMenuApi {

    public static Screen createConfigScreen() {

        /*
        ConfigBuilder builder = ConfigBuilder
                .create()
                .setTitle(
                        Text.of("Info Display Config")
                );

        // action when pressing "save & quit"
        builder.setSavingRunnable(Config::saveConfig);

        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
        ConfigCategory design = builder.getOrCreateCategory(Text.of("Design"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // toggles
        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Enable Entire Info Display"),
                        HUD_render.toggleHud
                )
                .setDefaultValue(true)
                // it reads the value from the config file and sets it to the new value (when clicked on)
                .setSaveConsumer(newValue -> HUD_render.toggleHud = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Show FPS"),
                        HUD_render.toggleFPS
                )
                .setDefaultValue(true)
                // it reads the value from the config file and sets it to the new value (when clicked on)
                .setSaveConsumer(newValue -> HUD_render.toggleFPS = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Show Coordinates"),
                        HUD_render.toggleCoords
                )
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> HUD_render.toggleCoords = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Show Biome"),
                        HUD_render.toggleBiome
                )
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> HUD_render.toggleBiome = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Show Direction"),
                        HUD_render.toggleDirection
                )
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> HUD_render.toggleDirection = newValue)
                .build()
        );

        // design

        design.addEntry(entryBuilder
                .startIntSlider(Text.of("Set Background Opacity"), HUD_render.backgroundOpacity, 0, 255)
                .setSaveConsumer(newValue -> HUD_render.backgroundOpacity = newValue)
                .setDefaultValue(77)
                .build()
        );
        */

        // set absolute position
        /*
        general.addEntry(entryBuilder
                .startIntSlider(Text.of("Set x position"), HUD_render.x, 0, HUD_render.screenSizeX)
                .setSaveConsumer(newValue -> HUD_render.x = newValue)
                .setDefaultValue(5)
                .build()
        );

        general.addEntry(entryBuilder
                .startIntSlider(Text.of("Set y position"), HUD_render.y, 0, HUD_render.screenSizeY)
                .setSaveConsumer(newValue -> HUD_render.y = newValue)
                .setDefaultValue(5)
                .build()
        );
         */

        // TODO: relative position
        return new CustomConfigScreen(Text.of("Hello World"), MinecraftClient.getInstance().currentScreen);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen();
    }
}
