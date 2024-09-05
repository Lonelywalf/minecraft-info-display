package net.jamicah.coords_mod.configuration;

import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.awt.*;

// TODO: Refactor the entire gui for better readability and maintainability
public class CustomConfigScreen extends Screen {
    public CustomConfigScreen(Text title, Screen parent) {
        super(title);
        SCREEN_HEIGHT = this.height;
        MIDDLE_POS = this.width;
        this.parent = parent;
    }

    public static MinecraftClient client = MinecraftClient.getInstance();

    public static int MIDDLE_POS;
    public static int SCREEN_HEIGHT;

    public static int SIZE_BIG_BUTTON = 20;
    public static int SIZE_SMALL_BUTTON = 15;

    public Screen parent;
    public static int X_POS_COLUMN1;
    public static int X_POS_COLUMN2;
    public static int WIDTH_COLUMN1 = 150;
    public static int widghtColumn2 = 150;

    ButtonWidget toggleHud = ButtonWidget.builder(
            Text.of("Enable Info Display: " + (HUD_render.toggleHud ? "§aOn" : "§cOff")),
            (btn) -> {

                HUD_render.toggleHud = !HUD_render.toggleHud;
                btn.setMessage(Text.of(
                        "Enable Info Display: " +
                                (HUD_render.toggleHud ? "§aOn" : "§cOff")
                ));
                Config.saveConfig();

            })
            .dimensions(X_POS_COLUMN1, 40, WIDTH_COLUMN1, 20)
            .build();

    ButtonWidget toggleFPS = ButtonWidget.builder(
            Text.of("Show FPS: " + (HUD_render.toggleFPS ? "§aOn" : "§cOff")),
            (btn) -> {

                HUD_render.toggleFPS = !HUD_render.toggleFPS;
                btn.setMessage(Text.of(
                        "Show FPS: " +
                                (HUD_render.toggleFPS ? "§aOn" : "§cOff")
                ));
                Config.saveConfig();
                updateTextField("f");
            })
            .dimensions(X_POS_COLUMN1, 60, WIDTH_COLUMN1, 15)
            .build();

    ButtonWidget toggleCoords = ButtonWidget.builder(
            Text.of("Show Coordinates: " + (HUD_render.toggleCoords ? "§aOn" : "§cOff")),
            (btn) -> {

                HUD_render.toggleCoords = !HUD_render.toggleCoords;
                btn.setMessage(Text.of(
                        "Show Coordinates: " +
                                (HUD_render.toggleCoords ? "§aOn" : "§cOff")
                ));

                Config.saveConfig();
                updateTextField("c");
            })
            .dimensions(X_POS_COLUMN1, 75, WIDTH_COLUMN1, 15)
            .build();
    ButtonWidget toggleBiome = ButtonWidget.builder(
            Text.of("Show Biome: " + (HUD_render.toggleBiome ? "§aOn" : "§cOff")),
            (btn) -> {

                HUD_render.toggleBiome = !HUD_render.toggleBiome;
                btn.setMessage(Text.of(
                        "Show Biome: " +
                                (HUD_render.toggleBiome ? "§aOn" : "§cOff")
                ));
                Config.saveConfig();
                updateTextField("b");
            })
            .dimensions(X_POS_COLUMN1, 90, WIDTH_COLUMN1, 15)
            .build();

    ButtonWidget toggleDirection = ButtonWidget.builder(
            Text.of("Show Direction: " + (HUD_render.toggleDirection ? "§aOn" : "§cOff")),
            (btn) -> {
                HUD_render.toggleDirection = !HUD_render.toggleDirection;
                btn.setMessage(Text.of(
                        "Show Direction: " +
                                (HUD_render.toggleDirection ? "§aOn" : "§cOff")
                ));
                Config.saveConfig();
                updateTextField("d");
            })
            .dimensions(X_POS_COLUMN1, 105, WIDTH_COLUMN1, 15)
            .build();

    ButtonWidget clockSettings = ButtonWidget.builder(
            Text.of("+"),
            (btn) -> {
                assert this.client != null;
                this.client.setScreen(new ClockConfigScreen(Text.of("Clock Settings"), this));
            })
            .tooltip(Tooltip.of(Text.of("Clock Settings")))
            .dimensions(X_POS_COLUMN1 + WIDTH_COLUMN1 + 1, 120, 15, 15)
            .build();

    ButtonWidget toggleClock = ButtonWidget.builder(
            Text.of("Show Time: " + (HUD_render.toggleClock ? "§aOn" : "§cOff")),
            (btn) -> {
                HUD_render.toggleClock = !HUD_render.toggleClock;
                btn.setMessage(Text.of(
                        "Show Time: " +
                                (HUD_render.toggleClock ? "§aOn" : "§cOff")
                ));
                Config.saveConfig();
                updateTextField("C");

                clockSettings.visible = HUD_render.toggleClock;

            })
            .dimensions(X_POS_COLUMN1, 120, WIDTH_COLUMN1, 15)
            .build();

    TextFieldWidget orderField;

    // update the text field when a button is pressed
    // only happens when the text is shorter than what is actually enabled
    private void updateTextField(String selection) {
        // remove the letter f, c, b or d from the text field
        String text = orderField.getText();

        // in case the text field is empty
        // a few lines below, text.length will be -1
        // so this is here to prevent that
        if (text.isEmpty()) {
            text += ',';
        }
        switch (selection) {
            case "f":
                // check if the selection is enabled and if the text field already contains the letter
                if (HUD_render.toggleFPS && !text.contains("f")) {
                    // if the last character is a comma, just add the letter
                    if (text.charAt(text.length()-1) == ',') {
                        text += "f,";
                        break;
                    }
                    // if the last character is not a comma, add a comma and the letter
                    text += ",f,";
                }
                break;
            case "c":
                if (HUD_render.toggleCoords && !text.contains("c")) {
                    if (text.charAt(text.length()-1) == ',') {
                        text += "c,";
                        break;
                    }
                    text += ",c,";
                }
                break;
            case "b":
                if (HUD_render.toggleBiome && !text.contains("b")) {
                    if (text.charAt(text.length()-1) == ',') {
                        text += "b,";
                        break;
                    }
                    text += ",b,";
                }
                break;
            case "d":
                if (HUD_render.toggleDirection && !text.contains("d")) {
                    if (text.charAt(text.length()-1) == ',') {
                        text += "d,";
                        break;
                    }
                    text += ",d,";
                }
                break;
            case "C":
                if (HUD_render.toggleClock && !text.contains("C")) {
                    if (text.charAt(text.length()-1) == ',') {
                        text += "C,";
                        break;
                    }
                    text += ",C,";
                }
                break;
        }
        orderField.setText(text);
        Config.saveConfig();
        orderField.isActive();
    }

    @Override
    protected void init() {

        // -20 because of the reset buttons
        MIDDLE_POS = this.width/2 - 20;

        X_POS_COLUMN1 = MIDDLE_POS - 15 - WIDTH_COLUMN1;
        X_POS_COLUMN2 = MIDDLE_POS + 15;

        int toggleButtonYPos = 40;

        toggleHud.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_BIG_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos
        );
        toggleFPS.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos += 20
        );
        toggleCoords.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos += 15
        );
        toggleBiome.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos += 15
        );
        toggleDirection.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos += 15
        );
        toggleClock.setDimensionsAndPosition(
                WIDTH_COLUMN1,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1,
                toggleButtonYPos += 15
        );
        clockSettings.setDimensionsAndPosition(
                SIZE_SMALL_BUTTON,
                SIZE_SMALL_BUTTON,
                X_POS_COLUMN1 + WIDTH_COLUMN1 + 1,
                toggleButtonYPos
        );


        // only show the clock settings when the clock is enabled
        clockSettings.visible = HUD_render.toggleClock;


        // change order of the displayed infos
        orderField = new TextFieldWidget(
                this.textRenderer,
                X_POS_COLUMN1,
                150,
                150,
                15,
                Text.of("Info Order")) {
            @Override
            public void write(String string) {
                super.write(string);
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                HUD_render.updateInfoOrder();

                updateAllButtons();
            }

            // name of the method is misleading, this is called when
            // the button is pressed, so basically to know if you change
            // the toggles from either the text field or the buttons
            @Override
            public boolean isActive() {
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                return super.isActive();
            }

            // this is called when you press backspace
            @Override
            public void eraseCharacters(int characterOffset) {
                super.eraseCharacters(characterOffset);
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                HUD_render.updateInfoOrder();

                updateAllButtons();
            }

            private void updateAllButtons() {
                toggleFPS.setMessage(
                        Text.of(
                                "Show FPS: " + (HUD_render.toggleFPS ? "§aOn" : "§cOff")
                        )
                );
                toggleCoords.setMessage(
                        Text.of(
                                "Show Coordinates: " + (HUD_render.toggleCoords ? "§aOn" : "§cOff")
                        )
                );
                toggleBiome.setMessage(
                        Text.of(
                                "Show Biome: " + (HUD_render.toggleBiome ? "§aOn" : "§cOff")
                        )
                );
                toggleDirection.setMessage(
                        Text.of(
                                "Show Direction: " + (HUD_render.toggleDirection ? "§aOn" : "§cOff")
                        )
                );
                toggleClock.setMessage(
                        Text.of(
                                "Show Clock: " + (HUD_render.toggleClock ? "§aOn" : "§cOff")
                        )
                );
            }
        };
        orderField.setText(HUD_render.infoOrder);
        orderField.setTooltip(
                Tooltip.of(
                        Text.of(
                                "This allows you to change the order of the displayed infos.\n" +
                                        "Keywords:\n" +
                                        "f = FPS\n" +
                                        "c = Coordinates\n" +
                                        "b = Biome\n" +
                                        "d = Direction\n" +
                                        "C = Clock\n" +
                                        "§lYou have to separate them using commas.\n" +
                                        "§r§oExample: f,c,b,d,C"
                        )
                )
        );

        // sliders for the background
        Slider opacitySlider = new Slider(X_POS_COLUMN2,
                40,
                widghtColumn2,
                15,
                Text.of("Background Opacity: " + HUD_render.bgOpacity),
                (double) HUD_render.bgOpacity /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Background Opacity: " + (int)(this.value*255)));
            }
            @Override
            protected void applyValue() {
                HUD_render.bgOpacity = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        Slider rColorSlider = new Slider(X_POS_COLUMN2,
                55,
                widghtColumn2,
                15,
                Text.of("Background Color R: " + HUD_render.bgColorR),
                (double) HUD_render.bgColorR /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Background Color R: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.bgColorR = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        Slider gColorSlider = new Slider(X_POS_COLUMN2,
                70,
                widghtColumn2,
                15,
                Text.of("Background Color G: " + HUD_render.bgColorG),
                (double) HUD_render.bgColorG /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Background Color G: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.bgColorG = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        Slider bColorSlider = new Slider(X_POS_COLUMN2,
                85,
                widghtColumn2,
                15,
                Text.of("Background Color B: " + HUD_render.bgColorB),
                (double) HUD_render.bgColorB /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Background Color B: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.bgColorB = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        // reset button next to the bg slider
        ButtonWidget resetBGColor = ButtonWidget.builder(
                Text.of("Reset"),
                (btn) -> {
                    HUD_render.bgOpacity = 77;
                    HUD_render.bgColorR = 0;
                    HUD_render.bgColorG = 0;
                    HUD_render.bgColorB = 0;

                    opacitySlider.setValue((double) 77 / 255);
                    rColorSlider.setValue(0);
                    gColorSlider.setValue(0);
                    bColorSlider.setValue(0);

                    opacitySlider.updateMessage();
                    rColorSlider.updateMessage();
                    gColorSlider.updateMessage();
                    bColorSlider.updateMessage();


                    Config.saveConfig();
                }).dimensions(X_POS_COLUMN2 + widghtColumn2 + 1, 40, 40, 20).build();


        // slider for text color
        Slider rTextColorSlider = new Slider(X_POS_COLUMN2,
                105,
                widghtColumn2,
                15,
                Text.of("Text Color R: " + HUD_render.textColorR),
                (double) HUD_render.textColorR /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Text Color R: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.textColorR = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        Slider gTextColorSlider = new Slider(X_POS_COLUMN2,
                120,
                widghtColumn2,
                15,
                Text.of("Text Color G: " + HUD_render.textColorG),
                (double) HUD_render.textColorG /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Text Color G: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.textColorG = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        Slider bTextColorSlider = new Slider(X_POS_COLUMN2,
                135,
                widghtColumn2,
                15,
                Text.of("Text Color B: " + HUD_render.textColorB),
                (double) HUD_render.textColorB /255) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("Text Color B: " + (int)(this.value*255)));
            }

            @Override
            protected void applyValue() {
                HUD_render.textColorB = (int)(this.value*255);
                Config.saveConfig();
            }
        };

        // reset button for the color slider
        ButtonWidget resetTextColor = ButtonWidget.builder(
                Text.of("Reset"),
                (btn) -> {
                    HUD_render.textColorR = 255;
                    HUD_render.textColorG = 255;
                    HUD_render.textColorB = 255;

                    rTextColorSlider.setValue(1);
                    gTextColorSlider.setValue(1);
                    bTextColorSlider.setValue(1);

                    rTextColorSlider.updateMessage();
                    gTextColorSlider.updateMessage();
                    bTextColorSlider.updateMessage();

                    Config.saveConfig();
                }).dimensions(X_POS_COLUMN2 + widghtColumn2 + 1, 105, 40, 20).build();


        // slider for x position under text field
        Slider xPositionSlider = new Slider(
                X_POS_COLUMN2,
                165,
                widghtColumn2 +10,
                15,
                Text.of("x Position: " + HUD_render.x),
                (double) HUD_render.x / client.getWindow().getScaledWidth()) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("x Position: " + (int)(this.value * client.getWindow().getScaledWidth())));
            }

            @Override
            protected void applyValue() {
                HUD_render.x = (int)(this.value*HUD_render.screenSizeX);
                Config.saveConfig();
            }
        };
        xPositionSlider.setTooltip(
                Tooltip.of(
                        Text.of(
                                "Set absolute x position of the HUD"
                        )
                )
        );

        // slider below the y position slider
        Slider yPositionSlider = new Slider(
                X_POS_COLUMN2,
                180,
                widghtColumn2 +10,
                15,
                Text.of("y Position: " + HUD_render.y),
                (double) HUD_render.y / client.getWindow().getScaledHeight()) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("y Position: " + (int)(this.value * client.getWindow().getScaledHeight())));
            }

            @Override
            protected void applyValue() {
                HUD_render.y = (int)(this.value*HUD_render.screenSizeY);
                Config.saveConfig();
            }
        };
        yPositionSlider.setTooltip(
                Tooltip.of(
                        Text.of(
                                "Set absolute x position of the HUD"
                        )
                )
        );

        // default button next to the x position slider
        ButtonWidget resetPosition = ButtonWidget.builder(
                        Text.of("Reset"),
                        (btn) -> {
                            HUD_render.x = 2;
                            HUD_render.y = 2;

                            xPositionSlider.setValue(2.0 / client.getWindow().getScaledWidth());
                            yPositionSlider.setValue(2.0 / client.getWindow().getScaledHeight());

                            xPositionSlider.updateMessage();
                            yPositionSlider.updateMessage();

                            Config.saveConfig();
                        }).dimensions(X_POS_COLUMN2 + widghtColumn2 + 11, 165, 32, 20)
                .build();



        // done button in the bottom center
        ButtonWidget doneButton = ButtonWidget.builder(
                Text.of("Done"),
                (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }).dimensions(this.width/2 - 100, this.height - 30, 200, 20).build();


        // Register the button widget.
        this.addDrawableChild(toggleHud);
        this.addDrawableChild(toggleFPS);
        this.addDrawableChild(toggleCoords);
        this.addDrawableChild(toggleBiome);
        this.addDrawableChild(toggleDirection);
        this.addDrawableChild(toggleClock);
        this.addDrawableChild(clockSettings);

        this.addDrawableChild(orderField);

        this.addDrawableChild(opacitySlider);
        this.addDrawableChild(rColorSlider);
        this.addDrawableChild(gColorSlider);
        this.addDrawableChild(bColorSlider);
        this.addDrawableChild(resetBGColor);

        this.addDrawableChild(rTextColorSlider);
        this.addDrawableChild(gTextColorSlider);
        this.addDrawableChild(bTextColorSlider);
        this.addDrawableChild(resetTextColor);

        this.addDrawableChild(xPositionSlider);
        this.addDrawableChild(yPositionSlider);
        this.addDrawableChild(resetPosition);

        this.addDrawableChild(doneButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);


        context.drawText(
                this.textRenderer,
                "Info Display Configuration",
                this.width/2 - this.textRenderer.getWidth("Info Display Configuration")/2,
                10,
                0xFFFFFFFF,
                true
        );
        context.drawText(
                this.textRenderer,
                "Displayed Infos:",
                X_POS_COLUMN1,
                48 - this.textRenderer.fontHeight - 10,
                0xFFFFFFFF,
                true
        );
        context.drawText(
                this.textRenderer,
                "Custom Order:",
                X_POS_COLUMN1,
                158 - this.textRenderer.fontHeight - 10,
                0xFFFFFFFF,
                true
        );
        context.drawText(
                this.textRenderer,
                "Design:",
                X_POS_COLUMN2,
                48 - this.textRenderer.fontHeight - 10,
                0xFFFFFFFF,
                true
        );
        context.fill(
                X_POS_COLUMN1,
                170,
                X_POS_COLUMN1 +70,
                170+23,
                new Color(
                        HUD_render.bgColorR,
                        HUD_render.bgColorG,
                        HUD_render.bgColorB,
                        HUD_render.bgOpacity
                ).getRGB()
        );
        context.drawText(
                this.textRenderer,
                "Position:",
                X_POS_COLUMN2,
                173 - this.textRenderer.fontHeight - 10,
                0xFFFFFFFF,
                true
        );
        context.drawText(this.textRenderer,
                "Preview Text",
                X_POS_COLUMN1 +3,
                173,
                new Color(
                        HUD_render.textColorR,
                        HUD_render.textColorG,
                        HUD_render.textColorB
                ).getRGB(),
                false
        );
        context.drawText(
                this.textRenderer,
                "Another Text",
                X_POS_COLUMN1 +3,
                183,
                new Color(
                        HUD_render.textColorR,
                        HUD_render.textColorG,
                        HUD_render.textColorB
                ).getRGB(),
                false
        );
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }
}

// so I can edit the values from outside the class
class Slider extends SliderWidget {
    public Slider(int x, int y, int width, int height, Text message, double value) {
        super(x, y, width, height, message, value);
    }


    @Override
    public void updateMessage() {
    }

    @Override
    protected void applyValue() {

    }

    public void setValue(double value) {
        this.value = value;
    }
}
