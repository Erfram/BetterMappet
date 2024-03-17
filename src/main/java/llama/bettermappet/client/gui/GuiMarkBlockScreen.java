package llama.bettermappet.client.gui;

import mchorse.mclib.client.gui.framework.GuiBase;
import mchorse.mclib.client.gui.framework.elements.GuiElement;
import mchorse.mclib.client.gui.framework.elements.IGuiElement;
import mchorse.mclib.client.gui.framework.elements.input.GuiTextElement;
import mchorse.mclib.client.gui.framework.elements.input.color.GuiColorPicker;
import mchorse.mclib.client.gui.utils.Elements;
import mchorse.mclib.client.gui.utils.keys.IKey;
import mchorse.mclib.config.values.ValueString;
import net.minecraft.client.Minecraft;

public class GuiMarkBlockScreen extends GuiBase {
    public GuiTextElement name;
    public GuiColorPicker colorPicker;

    public GuiMarkBlockScreen(String name, int color) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiElement element = new GuiElement(mc);
        element.flex().relative(this.viewport).xy(0.5F, 0.5F).w(0.5F).anchor(0.5F, 0.5F).column(5).vertical().stretch();
        this.name = new GuiTextElement(mc, new ValueString(name));
        this.colorPicker = new GuiColorPicker(mc, (c) -> this.colorPicker.setColor(c));

        element.add(Elements.label(IKey.lang("tile.bettermappet.mark.name")).background().marginBottom(5), this.name);
        element.add(Elements.label(IKey.lang("tile.bettermappet.mark.tooltip")).background().marginTop(12).marginBottom(5), this.colorPicker);
        this.root.add(element);
    }
}