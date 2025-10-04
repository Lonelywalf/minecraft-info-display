package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Objects;

public class PingDisplay extends InfoDisplay {
    public PingDisplay(boolean status) {
        super(status);
        this.displayName = "Ping";
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().togglePing;
    }

    @Override
    public void updateInformation(MinecraftClient client) {
        this.infoText = Config.HANDLER.instance().customPingText;
        try {
            assert client.player != null;
            // I love java
            this.infoText = String.format(this.infoText, Objects.requireNonNull(client.player.networkHandler.getPlayerListEntry(client.player.getUuid())).getLatency());
        } catch (Exception e) {
            assert client.player != null;
            client.player.sendMessage(
                    Text.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customPingText;
            Config.HANDLER.instance().customPingText = Config.HANDLER.defaults().customPingText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }
}
