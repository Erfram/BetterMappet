package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.api.ui.components.UIScriptEditorComponent;
import mchorse.mappet.api.scripts.code.mappet.MappetUIBuilder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = MappetUIBuilder.class, remap = false)
public abstract class MixinMappetUIBuilder {
    public UIScriptEditorComponent scriptEditor(String text) {
        MappetUIBuilder instance = ((MappetUIBuilder) (Object) this);
        UIScriptEditorComponent component = new UIScriptEditorComponent();

        instance.getCurrent().getChildComponents().add(component);
        component.label(text);

        return component;
    }
}
