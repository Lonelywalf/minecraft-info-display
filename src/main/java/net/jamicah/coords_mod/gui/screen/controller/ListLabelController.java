package net.jamicah.coords_mod.gui.screen.controller;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.ListOptionEntry;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ControllerWidget;
import dev.isxander.yacl3.gui.controllers.string.IStringController;
import dev.isxander.yacl3.gui.controllers.string.StringControllerElement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class ListLabelController<T extends Controller<?>> extends ControllerWidget<T> {


    public ListLabelController(T control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
    }

    @Override
    protected int getHoveredControlWidth() {
        return 0;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return false;
    }
}
