package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FPSDisplay extends InfoDisplay {
    public FPSDisplay(boolean status) {
        super(status);
        // for ordering
        this.displayName = "FPS";
    }
    @Override
    public void updateInformation(Minecraft client) {
        this.infoText = Config.HANDLER.instance().customFPSText;
        try {
            this.infoText = String.format(this.infoText, client.getFps());
        } catch (Exception e) {
            assert client.player != null;
            client.player.displayClientMessage(
                    Component.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customFPSText;
            Config.HANDLER.instance().customFPSText = Config.HANDLER.defaults().customFPSText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().toggleFPS;
    }
}