package llama.bettermappet.client.gui;

import mchorse.mclib.client.gui.framework.elements.input.color.GuiColorPicker;
import mchorse.mclib.client.gui.framework.elements.utils.GuiContext;
import mchorse.mclib.utils.Color;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.function.Consumer;

public class CustomGuiColorPicker extends GuiColorPicker {
    public CustomGuiColorPicker(Minecraft mc, Consumer<Integer> callback) {
        super(mc, callback);
    }

    @Override
    public boolean mouseClicked(GuiContext context) {
        if (super.mouseClicked(context)) {
            return true;
        } else if (this.red.isInside(context)) {
            this.dragging = 1;
            return true;
        } else if (this.green.isInside(context)) {
            this.dragging = 2;
            return true;
        } else if (this.blue.isInside(context)) {
            this.dragging = 3;
            return true;
        } else if (this.alpha.isInside(context) && this.editAlpha) {
            this.dragging = 4;
            return true;
        } else if (!this.area.isInside(context)) {
            this.dragging = 5;
            System.out.println("DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE, DELETE");
            this.addColor(recentColors, this.color);
            return false;
        } else {
            return true;
        }
    }

    private void addColor(List<Color> colors, Color color) {
        int i = colors.indexOf(color);
        if (i == -1) {
            colors.add(color.copy());
        } else {
            colors.add(colors.remove(i));
        }

    }
}
