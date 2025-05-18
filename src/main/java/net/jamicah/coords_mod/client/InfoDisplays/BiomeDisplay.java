package net.jamicah.coords_mod.client.InfoDisplays;

import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class BiomeDisplay extends InfoDisplay {
    public BiomeDisplay(boolean status) {
        super(status);
        this.displayName = "biome";
    }

    @Override
    public void updateInformation(MinecraftClient client) {
        this.infoText = Config.HANDLER.instance().customBiomeText;
        try {
            this.infoText = String.format(this.infoText, getCurrentBiome());
        } catch (Exception e) {
            assert client.player != null;
            client.player.sendMessage(
                    Text.translatable(
                            "config.coords_mod.category.appearance.custom_text.invalid_error",
                            this.infoText,
                            "%s"
                    ), false
            );
            this.infoText = Config.HANDLER.defaults().customBiomeText;
            Config.HANDLER.instance().customBiomeText = Config.HANDLER.defaults().customBiomeText;
            Config.HANDLER.save();
        }
        this.textLength = x * (isInRelativeMode ? 1 : 0) + dynamicSizeX(this.infoText);
        yCurrent += 10;
    }

    @Override
    public void loadConfiguration() {
        this.isEnabled = Config.HANDLER.instance().toggleBiome;
    }
}
