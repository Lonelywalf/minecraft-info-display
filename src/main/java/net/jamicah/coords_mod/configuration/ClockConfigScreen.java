package net.jamicah.coords_mod.configuration;

import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.awt.*;

public class ClockConfigScreen extends Screen {
    public Screen parent;

    public ClockConfigScreen(Text title, CustomConfigScreen prevScreen) {
        super(title);
        this.parent = prevScreen;
    }
    ButtonWidget timeFormat = ButtonWidget.builder(
                    Text.of("Time Format: " + (HUD_render.timeFormat12 ? "24h" : "12h")),
                    (btn) -> {
                        HUD_render.timeFormat12 = !HUD_render.timeFormat12;
                        btn.setMessage(Text.of("Time Format: " + (HUD_render.timeFormat12 ? "24h" : "12h")));
                        Config.saveConfig();
                    })
            .dimensions(CustomConfigScreen.X_POS_COLUMN1, 40, CustomConfigScreen.WIDTH_COLUMN1, 20)
            .build();

    ButtonWidget toggleAmPm = ButtonWidget.builder(
                    Text.of("Show AM/PM: " + (HUD_render.showAmPm ? "On" : "Off")),
                    (btn) -> {
                        HUD_render.showAmPm = !HUD_render.showAmPm;
                        btn.setMessage(Text.of("Show AM/PM: " + (HUD_render.showAmPm ? "On" : "Off")));
                        Config.saveConfig();
                    })
            .dimensions(CustomConfigScreen.X_POS_COLUMN1, 60, CustomConfigScreen.WIDTH_COLUMN1, 20)
            .build();

    ButtonWidget showSeconds = ButtonWidget.builder(
                    Text.of("Show Seconds: " + (HUD_render.showSeconds ? "On" : "Off")),
                    (btn) -> {
                        HUD_render.showSeconds = !HUD_render.showSeconds;
                        btn.setMessage(Text.of("Show Seconds: " + (HUD_render.showSeconds ? "On" : "Off")));
                        Config.saveConfig();
                    })
            .dimensions(CustomConfigScreen.X_POS_COLUMN1, 80, CustomConfigScreen.WIDTH_COLUMN1, 20)
            .build();


    @Override
    protected void init() {

        CustomConfigScreen.MIDDLE_POS = this.width/2;
        CustomConfigScreen.SCREEN_HEIGHT = this.height/2;

        CustomConfigScreen.X_POS_COLUMN1 = CustomConfigScreen.MIDDLE_POS - 15 - CustomConfigScreen.WIDTH_COLUMN1;
        CustomConfigScreen.X_POS_COLUMN2 = CustomConfigScreen.MIDDLE_POS + 15;

        timeFormat.setDimensionsAndPosition(
                CustomConfigScreen.WIDTH_COLUMN1,
                CustomConfigScreen.SIZE_BIG_BUTTON,
                CustomConfigScreen.X_POS_COLUMN1,
                40
        );
        toggleAmPm.setDimensionsAndPosition(
                CustomConfigScreen.WIDTH_COLUMN1,
                CustomConfigScreen.SIZE_BIG_BUTTON,
                CustomConfigScreen.X_POS_COLUMN1,
                60
        );
        showSeconds.setDimensionsAndPosition(
                CustomConfigScreen.WIDTH_COLUMN1,
                CustomConfigScreen.SIZE_BIG_BUTTON,
                CustomConfigScreen.X_POS_COLUMN1,
                80
        );



        ButtonWidget doneButton = ButtonWidget.builder(
                Text.of("Done"),
                (btn) -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }).dimensions(this.width / 2 - 50, this.height - 30, 100, 20).build();
        this.addDrawableChild(showSeconds);
        this.addDrawableChild(timeFormat);
        this.addDrawableChild(toggleAmPm);
        this.addDrawableChild(doneButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(
                this.textRenderer,
                "Clock Settings:",
                CustomConfigScreen.X_POS_COLUMN1,
                48 - this.textRenderer.fontHeight - 10,
                0xFFFFFFFF,
                true
        );
        context.drawText(
                this.textRenderer,
                HUD_render.getCurrentTime(),
                CustomConfigScreen.X_POS_COLUMN1 + CustomConfigScreen.WIDTH_COLUMN1 + 40,
                80 - this.textRenderer.fontHeight - 10,
                new Color(
                        HUD_render.textColorR,
                        HUD_render.textColorG,
                        HUD_render.textColorB
                ).getRGB(),
                true
        );
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }
}
