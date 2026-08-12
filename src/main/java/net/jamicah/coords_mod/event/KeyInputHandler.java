package net.jamicah.coords_mod.event;

import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.gui.screen.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    // this determines in what category the keybind is
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.register(Identifier.parse("coords_mod"));

    // these determine the names of the keybindings (actual name which will be displayed in "en_us.json" file)
    public static final String KEY_TOGGLEHUD = "key.coords_mod.toggle_coordsHud";
    public static final String KEY_TOGGLEBIOME = "key.coords_mod.toggle_coordsHud_BIOME";
    public static final String KEY_TOGGLEFPS = "key.coords_mod.toggle_coordsHud_FPS";
    public static final String KEY_TOGGLECOORDS = "key.coords_mod.toggle_coordsHud_COORDS";
    public static final String KEY_TOGGLEDIRECTION = "key.coords_mod.toggle_coordsHud_DIRECTION";
    public static final String KEY_TOGGLEPING = "key.coords_mod.toggle_coordsHud_PING";
    public static final String KEY_CLOCK = "key.coords_mod.toggle_coordsHud_CLOCK";
    public static final String KEY_OPENCONFIG = "key.coords_mod.open_config";

    // keybinding keys
    public static KeyMapping toggle_hud;
    public static KeyMapping toggle_biome;
    public static KeyMapping toggle_fps;
    public static KeyMapping togggle_coords;
    public static KeyMapping open_config;
    public static KeyMapping toggle_direction;
    public static KeyMapping toggle_clock;
    public static KeyMapping toggle_ping;
    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // toggle entire hud
            if (toggle_hud.consumeClick()) {
                Config.HANDLER.instance().toggleHud = !Config.HANDLER.instance().toggleHud;
                Config.HANDLER.save();
            }

            // toggle fps info
            if (toggle_fps.consumeClick()) {
                Config.HANDLER.instance().toggleFPS = !Config.HANDLER.instance().toggleFPS;
                Config.HANDLER.save();
            }

            // toggle coords info
            if (togggle_coords.consumeClick()) {
                Config.HANDLER.instance().toggleCoords = !Config.HANDLER.instance().toggleCoords;
                Config.HANDLER.save();
            }

            // toggle biome info
            if (toggle_biome.consumeClick()) {
                Config.HANDLER.instance().toggleBiome = !Config.HANDLER.instance().toggleBiome;
                Config.HANDLER.save();
            }

            // toggle direction info
            if (toggle_direction.consumeClick()) {
                Config.HANDLER.instance().toggleDirection = !Config.HANDLER.instance().toggleDirection;
                Config.HANDLER.save();
            }

            // toggle time info
            if (toggle_clock.consumeClick()) {
                Config.HANDLER.instance().toggleTime = !Config.HANDLER.instance().toggleTime;
                Config.HANDLER.save();
            }

            // toggle ping info
            if (toggle_ping.consumeClick()) {
                Config.HANDLER.instance().togglePing = !Config.HANDLER.instance().togglePing;
                Config.HANDLER.save();
            }

            // open config screen
            if (open_config.consumeClick()) {
                client.setScreen(
                        new ConfigScreen()
                                .createGui(
                                        client.screen
                                )
                        );
            }
        });
    }
    public static void register() {
        toggle_hud = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLEHUD,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_biome = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLEBIOME,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_fps = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLEFPS,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        togggle_coords = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLECOORDS,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_direction = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLEDIRECTION,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_clock = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_CLOCK,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        open_config = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_OPENCONFIG,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        toggle_ping = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TOGGLEPING,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_DONT_CARE,
                KEY_CATEGORY
        ));
        registerKeyInputs();
    }
}
