package net.jamicah.coords_mod.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.jamicah.coords_mod.gui.screen.ConfigScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;

public class Commands implements ClientCommandRegistrationCallback {

    /**
     * Called when registering client commands.
     *
     * @param dispatcher     the command dispatcher to register commands to
     * @param registryAccess object exposing access to the game's registries
     */

    // "/infodisplay" to open the config screen
    @Override
    public void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(ClientCommandManager.literal("infodisplay")
                .executes(context -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    client.send(() ->
                                    client.setScreen(
                                            (new ConfigScreen())
                                                    .createGui(MinecraftClient
                                                            .getInstance().currentScreen
                                                    )
                                            )
                            );
                    return 0;
                })
        );
    }
}
