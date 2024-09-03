package net.jamicah.coords_mod.configuration;

import net.jamicah.coords_mod.client.HUD_render;
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
        this.parent = parent;
    }

    public Screen parent;

    public static int xPosToggles = 30;
    public static int xPosDesign = 200;
    public static int widthToggles = 150;
    public static int widthDesign = 150;

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
            .dimensions(xPosToggles, 40, widthToggles, 20)
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
            .dimensions(xPosToggles, 60, widthToggles, 15)
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
            .dimensions(xPosToggles, 75, widthToggles, 15)
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
            .dimensions(xPosToggles, 90, widthToggles, 15)
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
            .dimensions(xPosToggles, 105, widthToggles, 15)
            .build();

    ButtonWidget clockSettings = ButtonWidget.builder(
            Text.of("+"),
            (btn) -> {
                assert this.client != null;
                this.client.setScreen(new ClockConfigScreen(Text.of("Clock Settings"), this));
            })
            .tooltip(Tooltip.of(Text.of("Clock Settings")))
            .dimensions(xPosToggles + widthToggles + 1, 120, 15, 15)
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
            .dimensions(xPosToggles, 120, widthToggles, 15)
            .build();

    TextFieldWidget orderField;
    private void updateTextField(String selection) {
        // remove the letter f, c, b or d from the text field
        String text = orderField.getText();
        switch (selection) {
            case "f":
                if (HUD_render.toggleFPS) {
                    text += "f,";
                } else {
                    if (text.contains("f,")) text = text.replace("f,", "");
                    else text = text.replace("f", "");
                }
                break;
            case "c":
                if (HUD_render.toggleCoords) {
                    text += "c,";
                } else {
                    if (text.contains("c,")) text = text.replace("c,", "");
                    else text = text.replace("c", "");
                }
                break;
            case "b":
                if (HUD_render.toggleBiome) {
                    text += "b,";
                } else {
                    if (text.contains("b,")) text = text.replace("b,", "");
                    else text = text.replace("b", "");
                }
                break;
            case "d":
                if (HUD_render.toggleDirection) {
                    text += "d,";
                } else {
                    if (text.contains("d,")) text = text.replace("d,", "");
                    else text = text.replace("d", "");
                }
                break;
            case "C":
                if (HUD_render.toggleClock) {
                    text += "C,";
                } else {
                    if (text.contains("C,")) text = text.replace("C,", "");
                    else text = text.replace("C", "");
                }
                break;
        }
        orderField.setText(text);
        Config.saveConfig();
        orderField.isActive();
    }




    @Override
    protected void init() {

        clockSettings.visible = HUD_render.toggleClock;


        Slider opacitySlider = new Slider(xPosDesign,
                40,
                widthDesign,
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


        Slider rColorSlider = new Slider(xPosDesign,
                55,
                widthDesign,
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

        Slider gColorSlider = new Slider(xPosDesign,
                70,
                widthDesign,
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

        Slider bColorSlider = new Slider(xPosDesign,
                85,
                widthDesign,
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

        // slider for text color
        Slider rTextColorSlider = new Slider(xPosDesign,
                105,
                widthDesign,
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

        Slider gTextColorSlider = new Slider(xPosDesign,
                120,
                widthDesign,
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

        Slider bTextColorSlider = new Slider(xPosDesign,
                135,
                widthDesign,
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

        // change order of the displayed infos
        orderField = new TextFieldWidget(this.textRenderer, xPosToggles, 148, 150, 15, Text.of("Info Order")) {
            @Override
            public void write(String string) {
                super.write(string);
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                HUD_render.updateInfoOrder();

                updateAllButtons();
            }

            @Override
            public boolean isActive() {
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                return super.isActive();
            }

            @Override
            public void eraseCharacters(int characterOffset) {
                super.eraseCharacters(characterOffset);
                HUD_render.infoOrder = this.getText();
                Config.saveConfig();
                HUD_render.updateInfoOrder();

                updateAllButtons();
            }

            private void updateAllButtons() {
                toggleFPS.setMessage(Text.of("Show FPS: " + (HUD_render.toggleFPS ? "§aOn" : "§cOff")));
                toggleCoords.setMessage(Text.of("Show Coordinates: " + (HUD_render.toggleCoords ? "§aOn" : "§cOff")));
                toggleBiome.setMessage(Text.of("Show Biome: " + (HUD_render.toggleBiome ? "§aOn" : "§cOff")));
                toggleDirection.setMessage(Text.of("Show Direction: " + (HUD_render.toggleDirection ? "§aOn" : "§cOff")));
                toggleClock.setMessage(Text.of("Show Clock: " + (HUD_render.toggleClock ? "§aOn" : "§cOff")));
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

        // reset button next to the slider
        ButtonWidget resetBGColor = ButtonWidget.builder(
                Text.of("Default"),
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
                }).dimensions(xPosDesign + widthDesign + 5, 40, 50, 20).build();

        // the same for the text color
        ButtonWidget resetTextColor = ButtonWidget.builder(
                Text.of("Default"),
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
                }).dimensions(xPosDesign + widthDesign + 5, 105, 50, 20).build();

        // done button in the bottom center
        ButtonWidget doneButton = ButtonWidget.builder(
                Text.of("Done"),
                (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }).dimensions(this.width / 2 - 50, this.height - 30, 100, 20).build();

        // slider for x position under text field
        Slider xPositionSlider = new Slider(
                xPosDesign,
                165,
                widthDesign+10,
                15,
                Text.of("x Position: " + HUD_render.x),
                (double) HUD_render.x / HUD_render.screenSizeX) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("x Position: " + (int)(this.value*HUD_render.screenSizeX)));
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
                                "Only changeable while in a world"
                        )
                )
        );
        // slider below the y position slider
        Slider yPositionSlider = new Slider(
                xPosDesign,
                180,
                widthDesign+10,
                15,
                Text.of("y Position: " + HUD_render.y),
                (double) HUD_render.y / HUD_render.screenSizeY) {
            @Override
            public void updateMessage() {
                this.setMessage(Text.of("y Position: " + (int)(this.value*HUD_render.screenSizeY)));
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
                                "Only changeable while in a world"
                        )
                )
        );

        // default button next to the x position slider
        ButtonWidget resetPosition = ButtonWidget.builder(
                Text.of("Default"),
                (btn) -> {
                    HUD_render.x = 2;
                    HUD_render.y = 2;

                    xPositionSlider.setValue(2.0 / HUD_render.screenSizeX);
                    yPositionSlider.setValue(2.0 / HUD_render.screenSizeY);

                    xPositionSlider.updateMessage();
                    yPositionSlider.updateMessage();

                    Config.saveConfig();
                }).dimensions(xPosDesign + widthDesign + 13, 165, 42, 20)
                .build();


        // Register the button widget.
        this.addDrawableChild(toggleHud);
        this.addDrawableChild(toggleFPS);
        this.addDrawableChild(toggleCoords);
        this.addDrawableChild(toggleBiome);
        this.addDrawableChild(toggleDirection);
        this.addDrawableChild(toggleClock);

        this.addDrawableChild(clockSettings);

        this.addDrawableChild(opacitySlider);
        this.addDrawableChild(resetBGColor);
        this.addDrawableChild(rColorSlider);
        this.addDrawableChild(gColorSlider);
        this.addDrawableChild(bColorSlider);
        this.addDrawableChild(resetTextColor);

        this.addDrawableChild(rTextColorSlider);
        this.addDrawableChild(gTextColorSlider);
        this.addDrawableChild(bTextColorSlider);

        this.addDrawableChild(orderField);

        this.addDrawableChild(doneButton);

        this.addDrawableChild(xPositionSlider);
        this.addDrawableChild(yPositionSlider);
        this.addDrawableChild(resetPosition);


    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, "Displayed Infos:", 30, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, "Design:", 200, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, "Custom Order:", 30, 158 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);

        context.fill(xPosToggles, 170, xPosToggles+70, 170+23, new Color(HUD_render.bgColorR, HUD_render.bgColorG, HUD_render.bgColorB, HUD_render.bgOpacity).getRGB());
        context.drawText(this.textRenderer, "Preview Text", xPosToggles+3, 173, new Color(HUD_render.textColorR, HUD_render.textColorG, HUD_render.textColorB).getRGB(), false);
        context.drawText(this.textRenderer, "Another Text", xPosToggles+3, 183, new Color(HUD_render.textColorR, HUD_render.textColorG, HUD_render.textColorB).getRGB(), false);
        context.drawText(this.textRenderer, "Position:", xPosDesign, 173 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
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
