package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;


// TODO: make all texts translatable

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
                .title(Text.translatable("config.coords_mod.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.coords_mod.category.general"))
                        .option(enableHUD)
                        //.option(orderList)
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.general.hud_info"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.category.general.hud_info.description"))
                                        .build()
                                )
                                .option(enableFPS)
                                .option(enableCoords)
                                .option(enableBiome)
                                .option(enableDirection)
                                .option(enableTime)
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.of("Time Settings"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.of("Appearance and format of the time"))
                                        .build()
                                )
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
                                .option(showTextShadow)
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Toggles the entire HUD"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
                    .binding(
                            Config.HANDLER.defaults().toggleHud,
                            () -> Config.HANDLER.instance().toggleHud,
                            newVal -> Config.HANDLER.instance().toggleHud = newVal
                    )
                    .instant(true)
                    .build();


    public Option<Boolean> enableFPS =
            Option.<Boolean>createBuilder()
                    .name(Text.translatable("config.coords_mod.category.general.hud_info.FPS"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.category.general.hud_info.FPS.description"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows the player's coordinates"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows the player's current biome"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .name(Text.of("Enable Direction")
                    )
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows the player's facing direction (without up and down)"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows AM or PM when using 12 hour time format"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows the seconds in the time info"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
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
                    .name(Text.of("Time Format"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Toggle between 12 hour and 24 hour time format"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Shows the real life time"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Opacity of the background"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Red value of the background color"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Green value of the background color"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Blue value of the background color"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Red value of the text color"))
                            .build()
                    )
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Green value of the text color"))
                            .build())
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Blue value of the text color"))
                            .build())
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

    public Option<Boolean> showTextShadow =
            Option.<Boolean>createBuilder()
                    .name(Text.of("Enable Text Shadow"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Show shadow behind the text"))
                            .image(Identifier.of("coords_mod", "textures/gui/textshadow.png"), 1, 1)
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("options.on") :
                                    Text.translatable("options.off")
                            )
                            .coloured(true)
                    )
                    .binding(
                            Config.HANDLER.defaults().toggleTextShadow,
                            () -> Config.HANDLER.instance().toggleTextShadow,
                            newVal -> {
                                Config.HANDLER.instance().toggleTextShadow = newVal;
                                save();
                            }
                    )
                    .instant(true)
                    .build();



    // Position

    public Option<Integer> posX =
            Option.<Integer>createBuilder()
                    .name(Text.of("X Position"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Absolute X position of the HUD. Position may change on screen resize"))
                            .build())
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
                    .description(OptionDescription.createBuilder()
                            .text(Text.of("Absolute Y position of the HUD. Position may change on screen resize"))
                            .build())
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
            "Time"
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
