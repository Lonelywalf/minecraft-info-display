package net.jamicah.coords_mod.client;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Config {

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("coords_mod", "info_display_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("infoDisplay.json5"))
                    .setJson5(true)
                    .build()
            )
            .build();


    @SerialEntry
    public boolean toggleHud = true;

    @SerialEntry
    public boolean toggleFPS = true;

    @SerialEntry
    public boolean toggleCoords = true;

    @SerialEntry
    public boolean toggleBiome = true;

    @SerialEntry
    public boolean toggleDirection = true;

    @SerialEntry
    public boolean toggleTime = false;


    @SerialEntry
    public boolean timeFormat12 = false;

    @SerialEntry
    public boolean showAmPm = true;

    @SerialEntry
    public boolean showSeconds = false;

    @SerialEntry
    public boolean toggleTextShadow = false;



    @SerialEntry
    public Color bgColor = new Color(0, 0, 0, 100);

    @SerialEntry
    public Color textColor = new Color(255, 255, 255, 255);




    @SerialEntry
    public int x = 2;

    @SerialEntry
    public int y = 2;

    @SerialEntry
    public List<Text> optionsList = Arrays.asList(
        Text.translatable("config.coords_mod.order_list.FPS"),
        Text.translatable("config.coords_mod.order_list.coords"),
        Text.translatable("config.coords_mod.order_list.biome"),
        Text.translatable("config.coords_mod.order_list.direction"),
        Text.translatable("config.coords_mod.order_list.time")
    );

    @SerialEntry
    public String customFPSText = "%s FPS";

    @SerialEntry
    public String customCoordsText = "%s %s %s";

    @SerialEntry
    public String customBiomeText = "Biome: %s";

    @SerialEntry
    public String customDirectionText = "Facing: %s";

    @SerialEntry
    public String customTimeText = "%s";
}
