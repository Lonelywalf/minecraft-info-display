package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class CoordinatesDisplay extends InfoDisplay {
    public CoordinatesDisplay(boolean status) {
        super(status);
        this.displayName = "coords";
    }

    @Override
    public void updateInformation(MinecraftClient client) {
        this.infoText = Config.HANDLER.instance().customCoordsText;
        assert client.player != null;

        String x_pos = String.valueOf((int)client.getCameraEntity().getX());
        String y_pos = String.valueOf((int)client.getCameraEntity().getY());
        String z_pos = String.valueOf((int)client.getCameraEntity().getZ());

        try {
            this.infoText = String.format(this.infoText, x_pos, y_pos, z_pos);
        } catch (Exception e) {
            client.player.sendMessage(
                    Text.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s %s %s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customCoordsText;
            Config.HANDLER.instance().customCoordsText = Config.HANDLER.defaults().customCoordsText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().toggleCoords;
    }

}
