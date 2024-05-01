package net.jamicah.coords_mod.event;

import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    // this determines in what category the keybind is
    public static final String KEY_CATEGORY = "key.category.coords_mod";

    // these determine the names of the keybindings (actual name which will be displayed in "en_us.json" file)
    public static final String KEY_TOGGLEHUD = "key.coords_mod.toggle_coordsHud";
    public static final String KEY_TOGGLEBIOME = "key.coords_mod.toggle_coordsHud_BIOME";
    public static final String KEY_TOGGLEFPS = "key.coords_mod.toggle_coordsHud_FPS";
    public static final String KEY_TOGGLEBACKGROUND = "key.coords_mod.toggle_coordsHud_BG";

    // keybinding keys
    public static KeyBinding toggle_hud;
    public static KeyBinding toggle_biome;
    public static KeyBinding toggle_fps;
    public static KeyBinding toggle_Background;
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // toggle coords display
            if (toggle_hud.wasPressed()) {
                if (HUD_render.toggleHud) {
                    HUD_render.toggleHud = false;
                } else if (!HUD_render.toggleHud) {
                    HUD_render.toggleHud = true;
                }
                Config.writeConfig();
            }

            // toggle biome info
            if (toggle_biome.wasPressed()) {
                if (HUD_render.toggleBiome) {
                    HUD_render.toggleBiome = false;
                } else if (!HUD_render.toggleBiome) {
                    HUD_render.toggleBiome = true;
                }
                Config.writeConfig();
            }

            if (toggle_fps.wasPressed()) {
                if (HUD_render.toggleFPS) {
                    HUD_render.toggleFPS = false;
                } else if (!HUD_render.toggleFPS) {
                    HUD_render.toggleFPS = true;
                }
                Config.writeConfig();
            }

            if (toggle_Background.wasPressed()) {
                if (HUD_render.toggleBackground) {
                    HUD_render.toggleBackground = false;
                } else if (!HUD_render.toggleBackground) {
                    HUD_render.toggleBackground = true;
                }
                Config.writeConfig();
            }
        });
    }
    public static void register() {
        toggle_hud = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEHUD,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                KEY_CATEGORY
        ));
        toggle_biome = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEBIOME,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                KEY_CATEGORY
        ));
        toggle_fps = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEFPS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F8,
                KEY_CATEGORY
        ));
        toggle_Background = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEBACKGROUND,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                KEY_CATEGORY
        ));
        registerKeyInputs();
    }
}
