package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.controllers.LabelController;
import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


public class ConfigScreen {

    public void save() {
        Config.HANDLER.save();
    }


    public Screen createGui(Screen parentScreen) {
        AtomicReference<Option<Boolean>> showAmPm = new AtomicReference<>();
        AtomicReference<Option<Boolean>> showSeconds = new AtomicReference<>();
        AtomicReference<Option<Boolean>> timeFormat12 = new AtomicReference<>();

        AtomicReference<Option<Integer>> x = new AtomicReference<>();
        AtomicReference<Option<Integer>> y = new AtomicReference<>();

        AtomicReference<Option<Config.RelativePositions>> relativePosition = new AtomicReference<>();
        return YetAnotherConfigLib.create(Config.HANDLER, (defaults, config, builder) -> builder
                .title(Text.translatable("config.coords_mod.title"))
                // general
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.coords_mod.category.general"))
                        .option(Option.<Boolean>createBuilder()
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
                                        defaults.toggleHud,
                                        () -> config.toggleHud,
                                        newVal ->  {
                                            config.toggleHud = newVal;
                                            save();
                                        }
                                )
                                .instant(true)
                                .build()
                        )
                        // HUD info
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.general.hud_info"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable(
                                                "config.coords_mod.category.general.hud_info.description"))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleFPS,
                                                () -> config.toggleFPS,
                                                newVal -> {
                                                    config.toggleFPS = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleCoords,
                                                () -> config.toggleCoords,
                                                newVal -> {
                                                    config.toggleCoords = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleBiome,
                                                () -> config.toggleBiome,
                                                newVal -> {
                                                    config.toggleBiome = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleDirection,
                                                () -> config.toggleDirection,
                                                newVal -> {
                                                    config.toggleDirection = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleTime,
                                                () -> config.toggleTime,
                                                newVal -> {
                                                    config.toggleTime = newVal;
                                                    save();
                                                    showAmPm.get().setAvailable(newVal);
                                                    showSeconds.get().setAvailable(newVal);
                                                    timeFormat12.get().setAvailable(newVal);
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .build()
                        )
                        // time settings
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.general.time_settings"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.category.general.time_settings.description"))
                                        .build()
                                )
                                .option(Util.make(() -> {
                                    var option = Option.<Boolean>createBuilder()
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
                                                    defaults.timeFormat12,
                                                    () -> config.timeFormat12,
                                                    newVal -> {
                                                        config.timeFormat12 = newVal;
                                                        save();
                                                    }
                                            )
                                            .available(Config.HANDLER.instance().toggleTime)
                                            .instant(true)
                                            .build();
                                    timeFormat12.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option =
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
                                                            defaults.showAmPm,
                                                            () -> config.showAmPm,
                                                            newVal -> {
                                                                config.showAmPm = newVal;
                                                                save();
                                                            }
                                                    )
                                                    .available(Config.HANDLER.instance().toggleTime)
                                                    .instant(true)
                                                    .build();
                                    showAmPm.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option = Option.<Boolean>createBuilder()
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
                                                    defaults.showSeconds,
                                                    () -> config.showSeconds,
                                                    newVal -> {
                                                        config.showSeconds = newVal;
                                                        save();
                                                    }
                                            )
                                            .available(Config.HANDLER.instance().toggleTime)
                                            .instant(true)
                                            .build();
                                    showSeconds.set(option);
                                    return option;
                                }))
                                .collapsed(true)
                                .build()
                        )
                        .group(ListOption.<Text>createBuilder()
                                .name(Text.translatable("config.coords_mod.order_list"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.order_list.description"))
                                        .build()
                                )
                                .binding(
                                        defaults.optionsList,
                                        () -> config.optionsList,
                                        newVal -> {
                                            config.optionsList = newVal;
                                            save();
                                            saveOrder();
                                        }

                                )
                                .customController(LabelController::new)
                                .initial(Text.of(""))
                                .maximumNumberOfEntries(5)
                                .minimumNumberOfEntries(5)
                                .collapsed(false)
                                .build()
                        )
                        .build()
                )
                // appearance
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("config.coords_mod.category.appearance"))
                        // appearance
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.appearance.color"))
                                .description(OptionDescription.createBuilder()
                                        .text(Text.translatable("config.coords_mod.category.appearance.description"))
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("config.coords_mod.bg_color"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.bg_color.description"))
                                                .build()
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .binding(
                                                defaults.bgColor,
                                                () -> config.bgColor,
                                                newVal -> {
                                                    config.bgColor = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("config.coords_mod.text_color"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.text_color.description"))
                                                .build()
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .binding(
                                                defaults.textColor,
                                                () -> config.textColor,
                                                newVal -> {
                                                    config.textColor = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
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
                                                defaults.toggleTextShadow,
                                                () -> config.toggleTextShadow,
                                                newVal -> {
                                                    config.toggleTextShadow = newVal;
                                                    save();
                                                }
                                        )
                                        .instant(true)
                                        .build()
                                )
                                .build()
                        )
                        // text customization
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.appearance.text_customization"))
                                .description(OptionDescription.createBuilder()
                                        .image(Identifier.of("coords_mod", "textures/gui/text_customization.png"), 253, 85)
                                        .text(Text.translatable("config.coords_mod.category.appearance.text_customization.description"))
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Text.translatable("config.coords_mod.custom_fps_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.custom_fps_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customFPSText,
                                                () -> config.customFPSText,
                                                newVal -> {
                                                    config.customFPSText = newVal;
                                                    save();
                                                }
                                        )
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Text.translatable("config.coords_mod.custom_coords_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.custom_coords_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customCoordsText,
                                                () -> config.customCoordsText,
                                                newVal -> {
                                                    config.customCoordsText = newVal;
                                                    save();
                                                }
                                        )
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Text.translatable("config.coords_mod.custom_biome_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.custom_biome_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customBiomeText,
                                                () -> config.customBiomeText,
                                                newVal -> {
                                                    config.customBiomeText = newVal;
                                                    save();
                                                }
                                        )
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Text.translatable("config.coords_mod.custom_direction_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.custom_direction_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customDirectionText,
                                                () -> config.customDirectionText,
                                                newVal -> {
                                                    config.customDirectionText = newVal;
                                                    save();
                                                }
                                        )
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Text.translatable("config.coords_mod.custom_time_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("config.coords_mod.custom_time_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customTimeText,
                                                () -> config.customTimeText,
                                                newVal -> {
                                                    config.customTimeText = newVal;
                                                    save();
                                                }
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                // position

                .category(ConfigCategory.createBuilder()
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("config.coords_mod.category.position.positionMode"))
                                .binding(
                                        defaults.absoluteMode,
                                        () -> config.absoluteMode,
                                        newVal -> {
                                            config.absoluteMode = newVal;

                                            relativePosition.get().setAvailable(!newVal);

                                            x.get().setAvailable(newVal);
                                            y.get().setAvailable(newVal);

                                            // if relative mode is enabled, set x and y to 2
                                            if (!newVal) {
                                                config.x = 2;
                                                config.y = 2;
                                            }

                                            save();
                                        }
                                )
                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                        .formatValue(val -> val ?
                                                Text.translatable("config.coords_mod.category.position.absolute") :
                                                Text.translatable("config.coords_mod.category.position.relativeMode")
                                        )
                                        .coloured(false)
                                )
                                .instant(true)
                                .build()
                        )
                        .name(Text.translatable("config.coords_mod.category.position"))

                        // relative position
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.position.relativePosition"))
                                .option(Util.make(() -> {
                                    var option = Option.<Config.RelativePositions>createBuilder()
                                            .name(Text.translatable("config.coords_mod.relative_position"))
                                            .binding(
                                                    defaults.relativePosition,
                                                    () -> config.relativePosition,
                                                    newVal -> {
                                                        config.relativePosition = newVal;
                                                        save();
                                                    }
                                            )
                                            .controller(opt -> EnumControllerBuilder.create(opt)
                                                    .enumClass(Config.RelativePositions.class)
                                                    .formatValue(value -> Text.translatable("config.coords_mod.category.position.relativePosition." + value.name().toLowerCase()))
                                            )
                                            .available(!config.absoluteMode)
                                            .instant(true)
                                            .build();
                                    relativePosition.set(option);
                                    return option;
                                }))
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("config.coords_mod.category.position.absolutePosition"))
                                .option(Util.make(() -> {
                                    var option = Option.<Integer>createBuilder()
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
                                                    defaults.x,
                                                    () -> config.x,
                                                    newVal -> {
                                                        config.x = newVal;
                                                        save();
                                                    }
                                            )
                                            .available(config.absoluteMode)
                                            .build();
                                    x.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option = Option.<Integer>createBuilder()
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
                                                    defaults.y,
                                                    () -> config.y,
                                                    newVal -> {
                                                        config.y = newVal;
                                                        save();
                                                    }
                                            )
                                            .available(config.absoluteMode)
                                            .build();
                                    y.set(option);
                                    return option;
                                }))
                                .build()
                        )
                         /*
                        .option(Option.<Config.RelativePositions>createBuilder()
                                .name(Text.translatable("config.coords_mod.relative_position"))
                                .binding(
                                        defaults.relativePosition,
                                        () -> config.relativePosition,
                                        newVal -> {
                                            Config.HANDLER.instance().relativePosition = newVal;
                                            save();
                                        }
                                )
                                .controller(opt -> EnumDropdownControllerBuilder.create(opt)
                                        .formatValue(
                                                formatting -> Text.literal(
                                                        capitalizeWords(
                                                                formatting.name().replaceAll(
                                                                        "_", " "
                                                                )
                                                        )
                                                )
                                        )
                                )
                                .build())
                          */
                        .build()
                )
        ).generateScreen(parentScreen);
    }

    // separate order for HUD_render
    // since orderlist can vary from language to language
    private void saveOrder() {
        for (int i = 0; i < Config.HANDLER.instance().optionsList.size(); i++) {
            HUD_render.order[i] =
                    Config.HANDLER.instance()
                            .optionsList
                            .get(i)
                            .toString()
                            .replaceAll(
                                    "translation\\{key='config\\.coords_mod\\.order_list\\.",
                                    ""
                            )
                            .replaceAll(
                                    "', args=\\[]}",
                                    ""
                            );
        }

    }

    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String[] words = str.split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return capitalized.toString().trim();
    }
}

