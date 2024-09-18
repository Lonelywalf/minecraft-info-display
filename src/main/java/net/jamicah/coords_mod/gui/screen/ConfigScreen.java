package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
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
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("General"))
                        .option(enableHUD)
                        //.option(orderList)
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
                                .collapsed(true)
                                .build()
                        )

                        .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("Appearance"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Background"))
                                .option(bgOpacity)
                                .option(bgColorR)
                                .option(bgColorG)
                                .option(bgColorB)
                                .build()
                        )
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Text"))
                                .option(textColorR)
                                .option(textColorG)
                                .option(textColorB)
                                .build()
                        )
                        .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.of("Position"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Absolute Position"))
                                .option(posX)
                                .option(posY)
                                .build()
                        )
                        .option(LabelOption.create(Text.of("More Options Coming Soon!")))
                        .build()
                )
                .save(this::save)
                .build()
                .generateScreen(parentScreen);
    }


    // General

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
                            newVal -> {
                                Config.HANDLER.instance().toggleFPS = newVal;
                                save();
                            }
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
                            newVal -> {
                                Config.HANDLER.instance().toggleCoords = newVal;
                                save();
                            }

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
                            newVal -> {
                                Config.HANDLER.instance().toggleBiome = newVal;
                                save();
                            }

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
                            newVal -> {
                                Config.HANDLER.instance().toggleDirection = newVal;
                                save();
                            }

                    )
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
                            newVal -> {
                                Config.HANDLER.instance().showAmPm = newVal;
                                save();
                            }
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
                            newVal -> {
                                Config.HANDLER.instance().showSeconds = newVal;
                                save();
                            }

                    )
                    .available(Config.HANDLER.instance().toggleTime)
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
                            newVal -> {
                                Config.HANDLER.instance().timeFormat12 = newVal;
                                save();
                            }

                    )
                    .available(Config.HANDLER.instance().toggleTime)
                    .instant(true)
                    .build();

    public Option<Boolean> enableTime =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Time"))
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ? Text.of("On") : Text.of("Off"))
                            .coloured(true)
                    )
                    .binding(
                            Config.HANDLER.defaults().toggleTime,
                            () -> Config.HANDLER.instance().toggleTime,
                            newVal -> {
                                Config.HANDLER.instance().toggleTime = newVal;
                                timeFormat12.setAvailable(newVal);
                                showAmPm.setAvailable(newVal);
                                showSeconds.setAvailable(newVal);
                                save();
                            }

                    )
                    .instant(true)
                    .build();



    // Appearance

    public Option<Integer> bgOpacity =
            Option.<Integer>createBuilder()
                    .name(Text.of("Background Opacity"))
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().bgOpacity,
                            () -> Config.HANDLER.instance().bgOpacity,
                            newVal -> {
                                Config.HANDLER.instance().bgOpacity = newVal;
                                save();
                            }
                    )
                    .instant(true)
                    .build();

    public Option<Integer> bgColorR =
            Option.<Integer>createBuilder()
                    .name(Text.of("Background Red"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().bgColorR,
                            () -> Config.HANDLER.instance().bgColorR,
                            newVal -> {
                                Config.HANDLER.instance().bgColorR = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> bgColorG =
            Option.<Integer>createBuilder()
                    .name(Text.of("Background Green"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().bgColorG,
                            () -> Config.HANDLER.instance().bgColorG,
                            newVal -> {
                                Config.HANDLER.instance().bgColorG = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> bgColorB =
            Option.<Integer>createBuilder()
                    .name(Text.of("Background Blue"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().bgColorB,
                            () -> Config.HANDLER.instance().bgColorB,
                            newVal -> {
                                Config.HANDLER.instance().bgColorB = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> textColorR =
            Option.<Integer>createBuilder()
                    .name(Text.of("Text Red"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().textColorR,
                            () -> Config.HANDLER.instance().textColorR,
                            newVal -> {
                                Config.HANDLER.instance().textColorR = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> textColorG =
            Option.<Integer>createBuilder()
                    .name(Text.of("Text Green"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().textColorG,
                            () -> Config.HANDLER.instance().textColorG,
                            newVal -> {
                                Config.HANDLER.instance().textColorG = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> textColorB =
            Option.<Integer>createBuilder()
                    .name(Text.of("Text Blue"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 255)
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().textColorB,
                            () -> Config.HANDLER.instance().textColorB,
                            newVal -> {
                                Config.HANDLER.instance().textColorB = newVal;
                                save();
                            }
                    )
                    .build();



    // Position

    public Option<Integer> posX =
            Option.<Integer>createBuilder()
                    .name(Text.of("X Position"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, MinecraftClient.getInstance().getWindow().getScaledWidth())
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().x,
                            () -> Config.HANDLER.instance().x,
                            newVal -> {
                                Config.HANDLER.instance().x = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<Integer> posY =
            Option.<Integer>createBuilder()
                    .name(Text.of("Y Position"))
                    .instant(true)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, MinecraftClient.getInstance().getWindow().getScaledHeight())
                            .step(1)
                    )
                    .binding(
                            Config.HANDLER.defaults().y,
                            () -> Config.HANDLER.instance().y,
                            newVal -> {
                                Config.HANDLER.instance().y = newVal;
                                save();
                            }
                    )
                    .build();





    // Order List
    List<String> optionsList = Arrays.asList(
            "FPS",
            "Coordinates",
            "Biome",
            "Facing Direction",
            "Clock"
    );
    public ListOption<String> orderList =
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
