package llama.bettermappet.api.ui.components;

import llama.bettermappet.client.gui.CustomGuiColorPicker;
import mchorse.mappet.api.ui.UIContext;
import mchorse.mappet.api.ui.components.UIComponent;
import mchorse.mappet.api.ui.utils.DiscardMethod;
import mchorse.mclib.client.gui.framework.elements.GuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UIColorPickerComponent extends UIComponent {
    public int color;
    public boolean alpha = false;

    public UIColorPickerComponent() {
    }

    public UIColorPickerComponent alpha(boolean alpha) {
        this.change("Alpha");
        this.alpha = alpha;
        return this;
    }

    public UIColorPickerComponent color(int color) {
        this.change("Color");
        this.color = color;
        return this;
    }

    @Override
    @DiscardMethod
    protected int getDefaultUpdateDelay()
    {
        return UIComponent.DELAY;
    }

    @Override
    @DiscardMethod
    @SideOnly(Side.CLIENT)
    protected void applyProperty(UIContext context, String key, GuiElement element)
    {
        super.applyProperty(context, key, element);

        CustomGuiColorPicker colorPicker = (CustomGuiColorPicker) element;

        if (key.equals("Color"))
        {
            colorPicker.setColor(this.color);
        }
        else if (key.equals("Alpha") && this.alpha)
        {
            colorPicker.editAlpha();
        }
    }

    @Override
    @DiscardMethod
    @SideOnly(Side.CLIENT)
    public GuiElement create(Minecraft mc, UIContext context)
    {
        CustomGuiColorPicker element = new CustomGuiColorPicker(mc, (color) -> {
            if(!this.id.isEmpty()) {
                context.data.setInteger(this.id, color);
                context.data.setBoolean(this.id + ".alpha", this.alpha);
                context.dirty(this.id, (long)this.updateDelay);
            }
        });

        if(this.alpha) {
            element.editAlpha();
        }

        element.setColor(this.color);
        return this.apply(element, context);
    }

    @Override
    @DiscardMethod
    public void populateData(NBTTagCompound tag)
    {
        super.populateData(tag);

        if (!this.id.isEmpty()) {
            tag.setInteger(this.id, this.color);
            tag.setBoolean(this.id, this.alpha);
        }
    }

    @Override
    @DiscardMethod
    public void serializeNBT(NBTTagCompound tag)
    {
        super.serializeNBT(tag);

        tag.setInteger("Color", this.color);
        tag.setBoolean("Alpha", this.alpha);
    }

    @Override
    @DiscardMethod
    public void deserializeNBT(NBTTagCompound tag)
    {
        super.deserializeNBT(tag);

        if (tag.hasKey("Color"))
        {
            this.color = tag.getInteger("Color");
        }

        if (tag.hasKey("Alpha"))
        {
            this.alpha = tag.getBoolean("Alpha");
        }
    }
}