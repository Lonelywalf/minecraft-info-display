package net.jamicah.coords_mod.event;

import net.jamicah.coords_mod.configuration.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.jamicah.coords_mod.configuration.CustomConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    // this determines in what category the keybind is
    public static final String KEY_CATEGORY = "key.category.coords_mod";

    // these determine the names of the keybindings (actual name which will be displayed in "en_us.json" file)
    public static final String KEY_TOGGLEHUD = "key.coords_mod.toggle_coordsHud";
    public static final String KEY_TOGGLEBIOME = "key.coords_mod.toggle_coordsHud_BIOME";
    public static final String KEY_TOGGLEFPS = "key.coords_mod.toggle_coordsHud_FPS";
    public static final String KEY_TOGGLECOORDS = "key.coords_mod.toggle_coordsHud_COORDS";
    public static final String KEY_TOGGLEDIRECTION = "key.coords_mod.toggle_coordsHud_DIRECTION";
    public static final String KEY_CLOCK = "key.coords_mod.toggle_coordsHud_CLOCK";
    public static final String KEY_OPENCONFIG = "key.coords_mod.open_config";

    // keybinding keys
    public static KeyBinding toggle_hud;
    public static KeyBinding toggle_biome;
    public static KeyBinding toggle_fps;
    public static KeyBinding toggle_Background;
    public static KeyBinding open_config;
    public static KeyBinding toggle_direction;
    public static KeyBinding toggle_coords;
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // toggle coords display
            if (toggle_hud.wasPressed()) {
                HUD_render.toggleHud = !HUD_render.toggleHud;
                Config.saveConfig();
            }

            // toggle biome info
            if (toggle_biome.wasPressed()) {
                HUD_render.toggleBiome = !HUD_render.toggleBiome;
                Config.saveConfig();
            }

            // toggle fps info
            if (toggle_fps.wasPressed()) {
                HUD_render.toggleFPS = !HUD_render.toggleFPS;
                Config.saveConfig();
            }

            // toggle direction info
            if (toggle_biome.wasPressed()) {
                HUD_render.toggleDirection = !HUD_render.toggleDirection;
                Config.saveConfig();
            }

            // toggle clock info
            if (toggle_Background.wasPressed()) {
                HUD_render.toggleClock = !HUD_render.toggleClock;
                Config.saveConfig();
            }

            // toggle coords info
            if (toggle_Background.wasPressed()) {
                HUD_render.toggleCoords = !HUD_render.toggleCoords;
                Config.saveConfig();
            }

            // open config screen
            if (open_config.wasPressed()) {
                MinecraftClient
                        .getInstance().
                        setScreen(
                                new CustomConfigScreen(
                                        Text.of("Info Display Config"),
                                        MinecraftClient
                                                .getInstance()
                                                .currentScreen
                                )
                        );
            }
        });
    }
    public static void register() {
        toggle_hud = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEHUD,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_biome = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEBIOME,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_fps = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEFPS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_Background = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLECOORDS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_direction = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLEDIRECTION,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_coords = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CLOCK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        open_config = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPENCONFIG,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                KEY_CATEGORY
        ));
        registerKeyInputs();
    }
}
