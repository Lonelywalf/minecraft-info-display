package net.jamicah.coords_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.jamicah.coords_mod.client.HUD_render;
import net.jamicah.coords_mod.event.KeyInputHandler;

public class Coords_modClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        HudRenderCallback.EVENT.register(new HUD_render());
    }
}
