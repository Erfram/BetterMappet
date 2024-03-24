package llama.bettermappet.api.ui.components;

import mchorse.mappet.api.ui.UIContext;
import mchorse.mappet.api.ui.components.UIComponent;
import mchorse.mappet.api.ui.components.UILabelBaseComponent;
import mchorse.mappet.api.ui.utils.DiscardMethod;
import mchorse.mappet.client.gui.panels.GuiScriptPanel;
import mchorse.mappet.client.gui.scripts.GuiTextEditor;
import mchorse.mclib.client.gui.framework.elements.GuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UIScriptEditorComponent extends UILabelBaseComponent {
    public UIScriptEditorComponent noBackground() {
        this.hasBackground = false;
        return this;
    }

    @Override
    @DiscardMethod
    public int getDefaultUpdateDelay() {
        return UIComponent.DELAY;
    }

    @Override
    @DiscardMethod
    @SideOnly(Side.CLIENT)
    public void applyProperty(UIContext context, String key, GuiElement element) {
        super.applyProperty(context, key, element);
        if (key.equals("Label")) {
            ((GuiTextEditor)element).setText(this.label);
        }
    }

    @Override
    @DiscardMethod
    @SideOnly(Side.CLIENT)
    public GuiElement create(Minecraft minecraft, UIContext context) {
        GuiTextEditor textEditor = new GuiTextEditor(minecraft, (text) -> {
            if(!this.id.isEmpty()) {
                context.data.setString(this.id, text);
                context.dirty(this.id, (long)this.updateDelay);
            }
        });
        textEditor.context(() -> GuiScriptPanel.createScriptContextMenu(minecraft, textEditor));
        textEditor.wrap().setText(this.label);
        textEditor.background(this.hasBackground);
        return this.apply(textEditor, context);
    }

    @Override
    @DiscardMethod
    public void populateData(NBTTagCompound tag) {
        super.populateData(tag);
        if (!this.id.isEmpty()) {
            tag.setString(this.id, this.label);
        }
    }

    @Override
    @DiscardMethod
    public void serializeNBT(NBTTagCompound tag)
    {
        super.serializeNBT(tag);
    }

    @Override
    @DiscardMethod
    public void deserializeNBT(NBTTagCompound tag)
    {
        super.deserializeNBT(tag);
    }
}