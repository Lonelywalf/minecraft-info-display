package net.jamicah.coords_mod;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen implements ModMenuApi {

    public static Screen createConfigScreen() {
        ConfigBuilder builder = ConfigBuilder.create().setTitle(Text.of("Coords Display Config"));

        builder.setSavingRunnable(Config::writeConfig);

        ConfigCategory general = builder.getOrCreateCategory(Text.of("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Toggle Entire HUD"),
                        HUD_render.toggleHud
                )
                .setDefaultValue(true)
                // it reads the value from the config file and sets it to the new value (when clicked on)
                .setSaveConsumer(newValue -> HUD_render.toggleHud = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Toggle Background"),
                        HUD_render.toggleBackground
                )
                .setDefaultValue(true)
                // it reads the value from the config file and sets it to the new value (when clicked on)
                .setSaveConsumer(newValue -> HUD_render.toggleBackground = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Toggle FPS"),
                        HUD_render.toggleFPS
                )
                .setDefaultValue(true)
                // it reads the value from the config file and sets it to the new value (when clicked on)
                .setSaveConsumer(newValue -> HUD_render.toggleFPS = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Toggle Coordinates"),
                        HUD_render.toggleCoords
                )
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> HUD_render.toggleCoords = newValue)
                .build()
        );

        general.addEntry(entryBuilder
                .startBooleanToggle(
                        Text.of("Toggle Biome"),
                        HUD_render.toggleBiome
                )
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> HUD_render.toggleBiome = newValue)
                .build()
        );


        return builder.build();
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen();
    }
}
