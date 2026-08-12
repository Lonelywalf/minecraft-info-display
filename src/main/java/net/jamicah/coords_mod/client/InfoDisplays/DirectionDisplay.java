package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class DirectionDisplay extends InfoDisplay {
    public DirectionDisplay(boolean status) {
        super(status);
        this.displayName = "direction";
    }

    @Override
    public void updateInformation(Minecraft client) {
        this.infoText = Config.HANDLER.instance().customDirectionText;
        assert client.player != null;
        String dir = client.player.getMotionDirection().getSerializedName();
        try {
            this.infoText = String.format(this.infoText, Character.toUpperCase(dir.charAt(0)) + dir.substring(1));
        } catch (Exception e) {
            client.player.displayClientMessage(
                    Component.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customDirectionText;
            Config.HANDLER.instance().customDirectionText = Config.HANDLER.defaults().customDirectionText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().toggleDirection;
    }
}
