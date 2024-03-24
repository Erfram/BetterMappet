package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.api.ui.components.UIColorPickerComponent;
import llama.bettermappet.api.ui.components.UIScriptEditorComponent;
import mchorse.mappet.api.scripts.code.mappet.MappetUIBuilder;
import mchorse.mappet.api.ui.components.UIComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MappetUIBuilder.class, remap = false)
public abstract class MixinMappetUIBuilder {
    @Shadow public abstract UIComponent getCurrent();

    public UIScriptEditorComponent scriptEditor(String text) {
        UIScriptEditorComponent component = new UIScriptEditorComponent();

        this.getCurrent().getChildComponents().add(component);
        component.label(text);

        return component;
    }

    public UIScriptEditorComponent scriptEditor() {
        UIScriptEditorComponent component = new UIScriptEditorComponent();

        this.getCurrent().getChildComponents().add(component);
        component.label("");

        return component;
    }

    public UIColorPickerComponent colorPicker(int color) {
        UIColorPickerComponent component = new UIColorPickerComponent();

        this.getCurrent().getChildComponents().add(component);
        component.color(color);

        return component;
    }
}