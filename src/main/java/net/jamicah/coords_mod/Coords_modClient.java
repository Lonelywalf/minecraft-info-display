package net.jamicah.coords_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.jamicah.coords_mod.client.HUD_render;
import net.jamicah.coords_mod.commands.Commands;
import net.jamicah.coords_mod.event.KeyInputHandler;

public class Coords_modClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register(new Commands());
        KeyInputHandler.register();
        HudElementRegistry.attachElementBefore(VanillaHudElements.CROSSHAIR, HUD_render.INFO_LAYER, HUD_render::renderInfoDisplay);
        // HudLayerRegistrationCallback.EVENT.register(new HUD_render());
        // HudRenderCallback.EVENT.register(new HUD_render());
    }
}
