package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;


// TODO: Add the rest of the options
// TODO: Add descriptions and tooltips
// TODO: enable/disable text shadow

public class ConfigScreen {


    public void save() {
        Config.HANDLER.save();
    }

    public void load() {
        Config.HANDLER.load();
    }

    public Screen createGui(Screen parentScreen) {
        load();
        return YetAnotherConfigLib.createBuilder()
                .title(Text.of("Info Display Configuration"))
                .category(ConfigCategory.createBuilder().name(Text.of("General"))
                        .option(enableHUD)
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("HUD Information"))
                                .option(enableFPS)
                                .option(enableCoords)
                                .option(enableBiome)
                                .option(enableDirection)
                                .option(enableTime)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Time Settings"))
                                .option(timeFormat12)
                                .option(showAmPm)
                                .option(showSeconds)
                                .collapsed(false)
                                .build()
                        )

                        .option(listThing)
                        .build()
                )
                .save(this::save)
                .build()
                .generateScreen(parentScreen);
    }


    public Option<Boolean> enableHUD =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Entire Info Display"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleHud,
                            () -> Config.HANDLER.instance().toggleHud,
                            newVal -> Config.HANDLER.instance().toggleHud = newVal
                    )
                    .instant(true)
                    .build();

    public Option<Boolean> enableFPS =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable FPS"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleFPS,
                            () -> Config.HANDLER.instance().toggleFPS,
                            newVal -> Config.HANDLER.instance().toggleFPS = newVal

                    )
                    .instant(true)
                    .build();

    public Option<Boolean> enableCoords =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Coordinates"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleCoords,
                            () -> Config.HANDLER.instance().toggleCoords,
                            newVal -> Config.HANDLER.instance().toggleCoords = newVal

                    )
                    .instant(true)
                    .build();

    public Option<Boolean> enableBiome =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Biome"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleBiome,
                            () -> Config.HANDLER.instance().toggleBiome,
                            newVal -> Config.HANDLER.instance().toggleBiome = newVal

                    )
                    .instant(true)
                    .build();

    public Option<Boolean> enableDirection =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Direction"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleDirection,
                            () -> Config.HANDLER.instance().toggleDirection,
                            newVal -> Config.HANDLER.instance().toggleDirection = newVal

                    )
                    .instant(true)
                    .build();

    public Option<Boolean> enableTime =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Time"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().toggleTime,
                            () -> Config.HANDLER.instance().toggleTime,
                            newVal -> Config.HANDLER.instance().toggleTime = newVal

                    )
                    .instant(true)
                    .build();

    public Option<Boolean> timeFormat12 =
            Option.<Boolean>createBuilder()
                    .name(Text.of("12 Hour Time Format"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("24 Hour") : Text.of("12 Hour"))
                    )
                    .binding(
                            Config.HANDLER.defaults().timeFormat12,
                            () -> Config.HANDLER.instance().timeFormat12,
                            newVal -> Config.HANDLER.instance().timeFormat12 = newVal

                    )
                    .available(Config.HANDLER.instance().toggleTime)
                    .instant(true)
                    .build();

    public Option<Boolean> showAmPm =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Show AM/PM"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().showAmPm,
                            () -> Config.HANDLER.instance().showAmPm,
                            newVal -> Config.HANDLER.instance().showAmPm = newVal

                    )
                    .available(Config.HANDLER.instance().toggleTime)
                    .instant(true)
                    .build();

    public Option<Boolean> showSeconds =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Show Seconds"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true))
                    .binding(
                            Config.HANDLER.defaults().showSeconds,
                            () -> Config.HANDLER.instance().showSeconds,
                            newVal -> Config.HANDLER.instance().showSeconds = newVal

                    )
                    .available(Config.HANDLER.instance().toggleTime)
                    .instant(true)
                    .build();


    // Order List
    List<String> optionsList = Arrays.asList(
            "FPS",
            "Coordinates",
            "Biome",
            "Facing Direction",
            "Clock"
    );
    public ListOption<String> listThing =
            ListOption.<String>createBuilder()
                    .name(Text.of("Order of Info Display"))
                    .binding(
                            optionsList,
                            () -> optionsList,
                            newVal -> optionsList = newVal
                    )
                    .controller(StringControllerBuilder::create)
                    .initial("")
                    .maximumNumberOfEntries(5)
                    .minimumNumberOfEntries(5)
                    .collapsed(true)
                    .build();








}
