package net.jamicah.coords_mod.client;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class Config {

    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("coords_mod", "info_display_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("info_display_config.json5"))
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
    public int bgOpacity = 100;

    @SerialEntry
    public int bgColorR = 0x00;

    @SerialEntry
    public int bgColorG = 0x00;

    @SerialEntry
    public int bgColorB = 0x00;



    @SerialEntry
    public int textColorR = 255;

    @SerialEntry
    public int textColorG = 255;

    @SerialEntry
    public int textColorB = 255;



    @SerialEntry
    public int x = 2;

    @SerialEntry
    public int y = 2;

}
