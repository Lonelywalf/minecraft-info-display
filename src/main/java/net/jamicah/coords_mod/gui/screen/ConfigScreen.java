package net.jamicah.coords_mod.gui.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.controllers.LabelController;
import net.jamicah.coords_mod.client.Config;
import net.jamicah.coords_mod.client.HUD_render;
import net.jamicah.coords_mod.client.InfoDisplays.InfoDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
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
        AtomicReference<Option<Config.TextAlignment>> textAlignment = new AtomicReference<>();

        AtomicReference<Option<Config.RelativePositions>> relativePosition = new AtomicReference<>();
        return YetAnotherConfigLib.create(Config.HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("config.coords_mod.title"))
                // general
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.coords_mod.category.general"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("config.coords_mod.enable_hud"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.enable_hud.description"))
                                        .build()
                                )
                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                        .formatValue(val -> val ?
                                                Component.translatable("options.on") :
                                                Component.translatable("options.off")
                                        )
                                        .coloured(true)
                                )
                                // instantly update instead of the deprecated .instant(boolean)
                                .stateManager(StateManager.createInstant(
                                        defaults.toggleHud,
                                        () -> config.toggleHud,
                                        newVal -> {
                                            config.toggleHud = newVal;
                                            save();
                                        }))
                                .build()
                        )
                        // HUD info
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.general.hud_info"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable(
                                                "config.coords_mod.category.general.hud_info.description"))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.category.general.hud_info.FPS"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.category.general.hud_info.FPS.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.toggleFPS,
                                                () -> config.toggleFPS,
                                                newVal -> {
                                                    config.toggleFPS = newVal;
                                                    save();
                                                }))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.enable_coords"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.enable_coords.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(defaults.toggleCoords,
                                                () -> config.toggleCoords,
                                                newVal -> {
                                                    config.toggleCoords = newVal;
                                                    save();
                                                }))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.enable_biome"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.enable_biome.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.toggleBiome,
                                                () -> config.toggleBiome,
                                                newVal -> {
                                                    config.toggleBiome = newVal;
                                                    save();
                                                }))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.enable_direction"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.enable_direction.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.toggleDirection,
                                                () -> config.toggleDirection,
                                                newVal -> {
                                                    config.toggleDirection = newVal;
                                                    save();
                                                }))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.enable_time"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.enable_time.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.toggleTime,
                                                () -> config.toggleTime,
                                                newVal -> {
                                                    config.toggleTime = newVal;
                                                    save();
                                                    showAmPm.get().setAvailable(newVal);
                                                    showSeconds.get().setAvailable(newVal);
                                                    timeFormat12.get().setAvailable(newVal);
                                                }
                                        ))
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.enable_ping"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.enable_ping.description"))
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.togglePing,
                                                () -> config.togglePing,
                                                newVal -> {
                                                    config.togglePing = newVal;
                                                    save();
                                                }))
                                        .build()
                                )
                                .build()
                        )
                        // time settings
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.general.time_settings"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.category.general.time_settings.description"))
                                        .build()
                                )
                                .option(Util.make(() -> {
                                    var option = Option.<Boolean>createBuilder()
                                            .name(Component.translatable("config.coords_mod.time_format"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.time_format.description"))
                                                    .build()
                                            )
                                            .controller(opt -> BooleanControllerBuilder.create(opt)
                                                    .formatValue(val -> val ?
                                                            Component.translatable("config.coords_mod.time_format_24hour") :
                                                            Component.translatable("config.coords_mod.time_format_12hour"))
                                            )
                                            .available(Config.HANDLER.instance().toggleTime)
                                            .stateManager(StateManager.createInstant(
                                                    defaults.timeFormat12,
                                                    () -> config.timeFormat12,
                                                    newVal -> {
                                                        config.timeFormat12 = newVal;
                                                        save();
                                                    }))
                                            .build();
                                    timeFormat12.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option =
                                            Option.<Boolean>createBuilder()
                                                    .name(Component.translatable("config.coords_mod.show_ampm"))
                                                    .description(OptionDescription.createBuilder()
                                                            .text(Component.translatable("config.coords_mod.show_ampm.description"))
                                                            .build()
                                                    )
                                                    .controller(opt -> BooleanControllerBuilder.create(opt)
                                                            .formatValue(val -> val ?
                                                                    Component.translatable("options.on") :
                                                                    Component.translatable("options.off")
                                                            )
                                                            .coloured(true)
                                                    )
                                                    .available(Config.HANDLER.instance().toggleTime)
                                                    .stateManager(StateManager.createInstant(
                                                            defaults.showAmPm,
                                                            () -> config.showAmPm,
                                                            newVal -> {
                                                                config.showAmPm = newVal;
                                                                save();
                                                            }
                                                    ))
                                                    .build();
                                    showAmPm.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option = Option.<Boolean>createBuilder()
                                            .name(Component.translatable("config.coords_mod.show_seconds"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.show_seconds.description"))
                                                    .build()
                                            )
                                            .controller(opt -> BooleanControllerBuilder.create(opt)
                                                    .formatValue(val -> val ?
                                                            Component.translatable("options.on") :
                                                            Component.translatable("options.off")
                                                    )
                                                    .coloured(true)
                                            )
                                            .available(Config.HANDLER.instance().toggleTime)
                                            .stateManager(StateManager.createInstant(
                                                    defaults.showSeconds,
                                                    () -> config.showSeconds,
                                                    newVal -> {
                                                        config.showSeconds = newVal;
                                                        save();
                                                    }
                                            ))
                                            .build();
                                    showSeconds.set(option);
                                    return option;
                                }))
                                .collapsed(true)
                                .build()
                        )
                        .group(ListOption.<Component>createBuilder()
                                .name(Component.translatable("config.coords_mod.order_list"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.order_list.description"))
                                        .build()
                                )
                                .state(StateManager.createInstant(
                                        defaults.optionsList,
                                        () -> config.optionsList,
                                        newVal -> {
                                            config.optionsList = newVal;
                                            save();
                                            saveOrder();
                                        }
                                ))
                                .customController(LabelController::new)
                                .initial(Component.nullToEmpty(""))
                                .maximumNumberOfEntries(InfoDisplay.totalInfoDisplayInstances)
                                .minimumNumberOfEntries(InfoDisplay.totalInfoDisplayInstances)
                                .collapsed(false)
                                .build()
                        )
                        .build()
                )
                // appearance
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.coords_mod.category.appearance"))
                        // appearance
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.appearance.color"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.category.appearance.description"))
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("config.coords_mod.bg_color"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.bg_color.description"))
                                                .build()
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .stateManager(StateManager.createInstant(
                                                defaults.bgColor,
                                                () -> config.bgColor,
                                                newVal -> {
                                                    config.bgColor = newVal;
                                                    save();
                                                })
                                        )
                                        .build()
                                )
                                .option(Option.<Color>createBuilder()
                                        .name(Component.translatable("config.coords_mod.text_color"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.text_color.description"))
                                                .build()
                                        )
                                        .controller(opt -> ColorControllerBuilder.create(opt)
                                                .allowAlpha(true))
                                        .stateManager(StateManager.createInstant(
                                                defaults.textColor,
                                                () -> config.textColor,
                                                newVal -> {
                                                    config.textColor = newVal;
                                                    save();
                                                })
                                        )
                                        .build()
                                )
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.translatable("config.coords_mod.show_text_shadow"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.show_text_shadow.description"))
                                                .image(Identifier.fromNamespaceAndPath("coords_mod", "textures/gui/textshadow.png"), 1, 1)
                                                .build()
                                        )
                                        .controller(opt -> BooleanControllerBuilder.create(opt)
                                                .formatValue(val -> val ?
                                                        Component.translatable("options.on") :
                                                        Component.translatable("options.off")
                                                )
                                                .coloured(true)
                                        )
                                        .stateManager(StateManager.createInstant(
                                                defaults.toggleTextShadow,
                                                () -> config.toggleTextShadow,
                                                newVal -> {
                                                    config.toggleTextShadow = newVal;
                                                    save();
                                                })
                                        )
                                        .build()
                                )
                                .build()
                        )
                        // text customization
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.appearance.text_customization"))
                                .description(OptionDescription.createBuilder()
                                        .image(Identifier.fromNamespaceAndPath("coords_mod", "textures/gui/text_customization.png"), 253, 85)
                                        .text(Component.translatable("config.coords_mod.category.appearance.text_customization.description"))
                                        .build()
                                )
                                .option(Option.<String>createBuilder()
                                        .name(Component.translatable("config.coords_mod.custom_fps_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_fps_text.description"))
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
                                        .name(Component.translatable("config.coords_mod.custom_coords_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_coords_text.description"))
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
                                        .name(Component.translatable("config.coords_mod.custom_biome_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_biome_text.description"))
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
                                        .name(Component.translatable("config.coords_mod.custom_direction_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_direction_text.description"))
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
                                        .name(Component.translatable("config.coords_mod.custom_time_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_time_text.description"))
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
                                .option(Option.<String>createBuilder()
                                        .name(Component.translatable("config.coords_mod.custom_ping_text"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Component.translatable("config.coords_mod.custom_ping_text.description"))
                                                .build()
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .binding(
                                                defaults.customPingText,
                                                () -> config.customPingText,
                                                newVal -> {
                                                    config.customPingText = newVal;
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
                                .name(Component.translatable("config.coords_mod.category.position.positionMode"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.category.position.positionMode.description"))
                                        .build()
                                )
                                .controller(opt -> BooleanControllerBuilder.create(opt)
                                        .formatValue(val -> val ?
                                                Component.translatable("config.coords_mod.category.position.absolute") :
                                                Component.translatable("config.coords_mod.category.position.relativeMode")
                                        )
                                        .coloured(false)
                                )
                                .stateManager(StateManager.createInstant(
                                        defaults.absoluteMode,
                                        () -> config.absoluteMode,
                                        newVal -> {
                                            config.absoluteMode = newVal;

                                            relativePosition.get().setAvailable(!newVal);
                                            textAlignment.get().setAvailable(newVal);

                                            x.get().setAvailable(newVal);
                                            y.get().setAvailable(newVal);


                                            save();
                                        })
                                )
                                .build()
                        )
                        .name(Component.translatable("config.coords_mod.category.position"))

                        // relative position
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.position.relativePosition"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.category.position.relativePosition.description2"))
                                        .build()
                                )
                                .option(Util.make(() -> {
                                    var option = Option.<Config.RelativePositions>createBuilder()
                                            .name(Component.translatable("config.coords_mod.category.position.relativePosition"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.category.position.relativePosition.description"))
                                                    .build()
                                            )
                                            .controller(opt -> EnumControllerBuilder.create(opt)
                                                    .enumClass(Config.RelativePositions.class)
                                            )
                                            .available(!config.absoluteMode)
                                            .stateManager(StateManager.createInstant(
                                                    defaults.relativePosition,
                                                    () -> config.relativePosition,
                                                    newVal -> {
                                                        config.relativePosition = newVal;
                                                        save();
                                                    }))
                                            .build();
                                    relativePosition.set(option);
                                    return option;
                                }))
                                .build())
                        // absolute position
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.coords_mod.category.position.absolutePosition"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("config.coords_mod.category.position.absolutePosition.description"))
                                        .build()
                                )
                                .option(Util.make(() -> {
                                    var option = Option.<Config.TextAlignment>createBuilder()
                                            .name(Component.translatable("config.coords_mod.category.position.absolutePosition.textAlignment"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.category.position.absolutePosition.textAlignment.description"))
                                                    .build()
                                            )
                                            .controller(opt -> EnumControllerBuilder.create(opt)
                                                    .enumClass(Config.TextAlignment.class)
                                            )
                                            .available(config.absoluteMode)
                                            .stateManager(StateManager.createInstant(
                                                    defaults.textAlignment,
                                                    () -> config.textAlignment,
                                                    newVal -> {
                                                        config.textAlignment = newVal;
                                                        save();
                                                    }))
                                            .build();
                                    textAlignment.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option = Option.<Integer>createBuilder()
                                            .name(Component.translatable("config.coords_mod.category.position.absolutePosition.xPos"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.category.position.absolutePosition.xPos.description"))
                                                    .build())
                                            .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                    .range(0, Minecraft.getInstance().getWindow().getGuiScaledWidth())
                                                    .step(1)
                                            )
                                            .stateManager(StateManager.createInstant(
                                                    defaults.x,
                                                    () -> config.x,
                                                    newVal -> {
                                                        config.x = newVal;
                                                        save();
                                                    })
                                            )
                                            .available(config.absoluteMode)
                                            .build();
                                    x.set(option);
                                    return option;
                                }))
                                .option(Util.make(() -> {
                                    var option = Option.<Integer>createBuilder()
                                            .name(Component.translatable("config.coords_mod.category.position.absolutePosition.yPos"))
                                            .description(OptionDescription.createBuilder()
                                                    .text(Component.translatable("config.coords_mod.category.position.absolutePosition.yPos.description"))
                                                    .build())
                                            .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                    .range(0, Minecraft.getInstance().getWindow().getGuiScaledHeight())
                                                    .step(1)
                                            )
                                            .stateManager(StateManager.createInstant(
                                                    defaults.y,
                                                    () -> config.y,
                                                    newVal -> {
                                                        config.y = newVal;
                                                        save();
                                                    })
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
        HUD_render.infoDisplays = HUD_render.getOrder();
        /*
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

         */

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

