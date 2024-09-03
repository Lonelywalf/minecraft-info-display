package net.jamicah.coords_mod.configuration;

import net.jamicah.coords_mod.client.HUD_render;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

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
            .dimensions(CustomConfigScreen.xPosToggles, 40, CustomConfigScreen.widthToggles, 20)
            .build();



    ButtonWidget toggleAmPm = ButtonWidget.builder(
                    Text.of("Show AM/PM: " + (HUD_render.showAmPm ? "On" : "Off")),
                    (btn) -> {
                        HUD_render.showAmPm = !HUD_render.showAmPm;
                        btn.setMessage(Text.of("Show AM/PM: " + (HUD_render.showAmPm ? "On" : "Off")));
                        Config.saveConfig();
                    })
            .dimensions(CustomConfigScreen.xPosToggles, 60, CustomConfigScreen.widthToggles, 20)
            .build();

    ButtonWidget showSeconds = ButtonWidget.builder(
                    Text.of("Show Seconds: " + (HUD_render.showSeconds ? "On" : "Off")),
                    (btn) -> {
                        HUD_render.showSeconds = !HUD_render.showSeconds;
                        btn.setMessage(Text.of("Show Seconds: " + (HUD_render.showSeconds ? "On" : "Off")));
                        Config.saveConfig();
                    })
            .dimensions(CustomConfigScreen.xPosToggles, 80, CustomConfigScreen.widthToggles, 20)
            .build();

    @Override
    protected void init() {
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
        context.drawText(this.textRenderer, "Clock Settings:", 30, 40 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
        context.drawText(this.textRenderer, HUD_render.getCurrentTime(), CustomConfigScreen.xPosToggles + CustomConfigScreen.widthToggles + 40, 80 - this.textRenderer.fontHeight - 10, 0xFFFFFFFF, true);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }
}
