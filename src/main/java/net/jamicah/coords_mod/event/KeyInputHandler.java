package net.jamicah.coords_mod.event;

import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.gui.screen.ConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
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
    public static final String KEY_TOGGLECOORDS = "key.coords_mod.toggle_coordsHud_COORDS";
    public static final String KEY_TOGGLEDIRECTION = "key.coords_mod.toggle_coordsHud_DIRECTION";
    public static final String KEY_CLOCK = "key.coords_mod.toggle_coordsHud_CLOCK";
    public static final String KEY_OPENCONFIG = "key.coords_mod.open_config";

    // keybinding keys
    public static KeyBinding toggle_hud;
    public static KeyBinding toggle_biome;
    public static KeyBinding toggle_fps;
    public static KeyBinding togggle_coords;
    public static KeyBinding open_config;
    public static KeyBinding toggle_direction;
    public static KeyBinding toggle_clock;
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // toggle coords display
            if (toggle_hud.wasPressed()) {
                Config.HANDLER.instance().toggleHud = !Config.HANDLER.instance().toggleHud;
                Config.HANDLER.save();
            }

            // toggle biome info
            if (toggle_biome.wasPressed()) {
                Config.HANDLER.instance().toggleBiome = !Config.HANDLER.instance().toggleBiome;
                Config.HANDLER.save();
            }

            // toggle fps info
            if (toggle_fps.wasPressed()) {
                Config.HANDLER.instance().toggleFPS = !Config.HANDLER.instance().toggleFPS;
                Config.HANDLER.save();
            }

            // toggle direction info
            if (toggle_biome.wasPressed()) {
                Config.HANDLER.instance().toggleDirection = !Config.HANDLER.instance().toggleDirection;
                Config.HANDLER.save();
            }

            // toggle clock info
            if (togggle_coords.wasPressed()) {
                Config.HANDLER.instance().toggleTime = !Config.HANDLER.instance().toggleTime;
                Config.HANDLER.save();
            }

            // toggle coords info
            if (toggle_clock.wasPressed()) {
                Config.HANDLER.instance().toggleCoords = !Config.HANDLER.instance().toggleCoords;
                Config.HANDLER.save();
            }

            // open config screen
            if (open_config.wasPressed()) {
                MinecraftClient
                        .getInstance().
                        setScreen(
                                new ConfigScreen()
                                        .createGui(
                                                MinecraftClient.getInstance().currentScreen
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
        togggle_coords = KeyBindingHelper.registerKeyBinding(new KeyBinding(
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
        toggle_clock = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CLOCK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        open_config = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPENCONFIG,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                KEY_CATEGORY
        ));
        registerKeyInputs();
    }
}
