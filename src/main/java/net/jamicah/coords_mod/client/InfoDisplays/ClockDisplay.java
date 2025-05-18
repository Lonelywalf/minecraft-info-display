package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ClockDisplay extends InfoDisplay {
    public ClockDisplay(boolean status) {
        super(status);
        this.displayName = "time";
    }

    @Override
    public void updateInformation(MinecraftClient client) {
        this.infoText = Config.HANDLER.instance().customTimeText;
        try {
            this.infoText = String.format(this.infoText, getCurrentTime());
        } catch (Exception e) {
            assert client.player != null;
            client.player.sendMessage(
                    Text.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customTimeText;
            Config.HANDLER.instance().customTimeText = Config.HANDLER.defaults().customTimeText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().toggleTime;
    }
}
