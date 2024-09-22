package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.gui.controllers.LabelController;
import net.jamicah.coords_mod.client.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

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
                                .name(Text.translatable("config.coords_mod.category.general.time_settings"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.category.general.time_settings.description"))
                                        .build()
                                )
                                .option(timeFormat12)
                                .option(showAmPm)
                                .option(showSeconds)
                                .collapsed(true)
                                .build()
                        )
                        .group(orderList)
                        .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.coords_mod.category.appearance"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.appearance.color"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.category.appearance.description"))
                                        .build()
                                )
                                .option(bgColor)
                                .option(textColor)
                                .option(showTextShadow)
                                .build()
                        )
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.appearance.text_customization"))
                                .description(OptionDescription.createBuilder()
                                        .image(Identifier.of("coords_mod", "textures/gui/text_customization.png"), 253, 85)
                                        .text(Text.translatable("config.coords_mod.category.appearance.text_customization.description"))
                                        .build()
                                )
                                .option(customFPSText)
                                .option(customCoordsText)
                                .option(customBiomeText)
                                .option(customDirectionText)
                                .option(customTimeText)
                                .build()
                        )
                        .build()
                )
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.coords_mod.category.position"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.position.absolute"))
                                .option(posX)
                                .option(posY)
                                .build()
                        )
                        .option(LabelOption.create(Text.translatable("config.coords_mod.more_options_coming_soon")))
                        .build()
                )
                .save(this::save)
                .build()
                .generateScreen(parentScreen);
    }


    // General

    public Option<Boolean> enableHUD =
            Option.<Boolean>createBuilder()
                    .name(Text.translatable("config.coords_mod.enable_hud"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.enable_hud.description"))
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
                    .name(Text.translatable("config.coords_mod.enable_coords"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.enable_coords.description"))
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
                    .name(Text.translatable("config.coords_mod.enable_biome"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.enable_biome.description"))
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
                    .name(Text.translatable("config.coords_mod.enable_direction"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.enable_direction.description"))
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
                    .name(Text.translatable("config.coords_mod.show_ampm"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.show_ampm.description"))
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
                    .name(Text.translatable("config.coords_mod.show_seconds"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.show_seconds.description"))
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
                    .name(Text.translatable("config.coords_mod.time_format"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.time_format.description"))
                            .build()
                    )
                    .controller(opt -> BooleanControllerBuilder.create(opt)
                            .formatValue(val -> val ?
                                    Text.translatable("config.coords_mod.time_format_24hour") :
                                    Text.translatable("config.coords_mod.time_format_12hour"))
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
                    .name(Text.translatable("config.coords_mod.enable_time"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.enable_time.description"))
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
    public Option<Color> bgColor =
            Option.<Color>createBuilder()
                    .name(Text.translatable("config.coords_mod.bg_color"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.bg_color.description"))
                            .build()
                    )
                    .controller(opt -> ColorControllerBuilder.create(opt)
                            .allowAlpha(true))
                    .binding(
                            Config.HANDLER.defaults().bgColor,
                            () -> Config.HANDLER.instance().bgColor,
                            newVal -> {
                                Config.HANDLER.instance().bgColor = newVal;
                                save();
                            }
                    )
                    .instant(true)
                    .build();

    public Option<Color> textColor =
            Option.<Color>createBuilder()
                    .name(Text.translatable("config.coords_mod.text_color"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.text_color.description"))
                            .build()
                    )
                    .controller(opt -> ColorControllerBuilder.create(opt)
                            .allowAlpha(true))
                    .binding(
                            Config.HANDLER.defaults().textColor,
                            () -> Config.HANDLER.instance().textColor,
                            newVal -> {
                                Config.HANDLER.instance().textColor = newVal;
                                save();
                            }
                    )
                    .instant(true)
                    .build();


    public Option<Boolean> showTextShadow =
            Option.<Boolean>createBuilder()
                    .name(Text.translatable("config.coords_mod.show_text_shadow"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.show_text_shadow.description"))
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

    public Option<String> customFPSText =
            Option.<String>createBuilder()
                    .name(Text.translatable("config.coords_mod.custom_fps_text"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.custom_fps_text.description"))
                            .build()
                    )
                    .controller(StringControllerBuilder::create)
                    .binding(
                            Config.HANDLER.defaults().customFPSText,
                            () -> Config.HANDLER.instance().customFPSText,
                            newVal -> {
                                Config.HANDLER.instance().customFPSText = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<String> customCoordsText =
            Option.<String>createBuilder()
                    .name(Text.translatable("config.coords_mod.custom_coords_text"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.custom_coords_text.description"))
                            .build()
                    )
                    .controller(StringControllerBuilder::create)
                    .binding(
                            Config.HANDLER.defaults().customCoordsText,
                            () -> Config.HANDLER.instance().customCoordsText,
                            newVal -> {
                                Config.HANDLER.instance().customCoordsText = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<String> customBiomeText =
            Option.<String>createBuilder()
                    .name(Text.translatable("config.coords_mod.custom_biome_text"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.custom_biome_text.description"))
                            .build()
                    )
                    .controller(StringControllerBuilder::create)
                    .binding(
                            Config.HANDLER.defaults().customBiomeText,
                            () -> Config.HANDLER.instance().customBiomeText,
                            newVal -> {
                                Config.HANDLER.instance().customBiomeText = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<String> customDirectionText =
            Option.<String>createBuilder()
                    .name(Text.translatable("config.coords_mod.custom_direction_text"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.custom_direction_text.description"))
                            .build()
                    )
                    .controller(StringControllerBuilder::create)
                    .binding(
                            Config.HANDLER.defaults().customDirectionText,
                            () -> Config.HANDLER.instance().customDirectionText,
                            newVal -> {
                                Config.HANDLER.instance().customDirectionText = newVal;
                                save();
                            }
                    )
                    .build();

    public Option<String> customTimeText =
            Option.<String>createBuilder()
                    .name(Text.translatable("config.coords_mod.custom_time_text"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.custom_time_text.description"))
                            .build()
                    )
                    .controller(StringControllerBuilder::create)
                    .binding(
                            Config.HANDLER.defaults().customTimeText,
                            () -> Config.HANDLER.instance().customTimeText,
                            newVal -> {
                                Config.HANDLER.instance().customTimeText = newVal;
                                save();
                            }
                    )
                    .build();


    // Position

    public Option<Integer> posX =
            Option.<Integer>createBuilder()
                    .name(Text.translatable("config.coords_mod.pos_x"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.pos_x.description"))
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
                    .name(Text.translatable("config.coords_mod.pos_y"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.pos_y.description"))
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
    // change the order of the list of options to display on the HUD


    // TODO: Show actual displayed text in the list
    public ListOption<Text> orderList =
            ListOption.<Text>createBuilder()
                    .name(Text.translatable("config.coords_mod.order_list"))
                    .description(OptionDescription.createBuilder()
                            .text(Text.translatable("config.coords_mod.order_list.description"))
                            .build()
                    )
                    .binding(
                            Config.HANDLER.defaults().optionsList,
                            () -> Config.HANDLER.instance().optionsList,
                            newVal -> Config.HANDLER.instance().optionsList = newVal
                    )
                    .customController(LabelController::new)
                    .initial(Text.of(""))
                    .maximumNumberOfEntries(5)
                    .minimumNumberOfEntries(5)
                    .collapsed(false)
                    .build();
}
