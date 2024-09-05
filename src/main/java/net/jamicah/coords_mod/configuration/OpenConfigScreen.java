package net.jamicah.coords_mod.configuration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class OpenConfigScreen implements ModMenuApi {

    public static Screen createConfigScreen() {
        // TODO: relative position
        return new CustomConfigScreen(Text.of("Hello World"), MinecraftClient.getInstance().currentScreen);
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> createConfigScreen();
    }
}
