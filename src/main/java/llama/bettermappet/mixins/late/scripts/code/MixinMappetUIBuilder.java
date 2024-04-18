package llama.bettermappet.mixins.late.scripts.code;

import llama.bettermappet.api.ui.components.UIColorPickerComponent;
import llama.bettermappet.api.ui.components.UIScriptEditorComponent;
import llama.bettermappet.mixins.late.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.mappet.MappetUIBuilder;
import mchorse.mappet.api.ui.components.UIComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MappetUIBuilder.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.mappet.IMappetUIBuilder")
public abstract class MixinMappetUIBuilder {
    @Shadow public abstract UIComponent getCurrent();

    /**
     * Creates a script editor ui component that supports code highlighting, etc.
     *
     * <pre>{@code
     *     function main(c) {
     *         const ui = mappet.createUI(c, "handler")
     *
     *         ui.scriptEditor('function main(c) {\n    c.send("Hi!")\n}').rwh(0.25, 0.25).rxy(0.5, 0.5).anchor(0.5)
     *
     *         c.player.openUI(ui)
     *     }
     * }</pre>
     */
    public UIScriptEditorComponent scriptEditor(String text) {
        UIScriptEditorComponent component = new UIScriptEditorComponent();

        this.getCurrent().getChildComponents().add(component);
        component.label(text);

        return component;
    }

    /**
     * Creates a script editor ui component that supports code highlighting, etc.
     *
     * <pre>{@code
     *     function main(c) {
     *         const ui = mappet.createUI(c, "handler")
     *
     *         ui.scriptEditor().rwh(0.25, 0.25).rxy(0.5, 0.5).anchor(0.5)
     *
     *         c.player.openUI(ui)
     *     }
     * }</pre>
     */
    public UIScriptEditorComponent scriptEditor() {
        UIScriptEditorComponent component = new UIScriptEditorComponent();

        this.getCurrent().getChildComponents().add(component);
        component.label("");

        return component;
    }

    /**
     * Creates a color picker ui component.
     *
     * <pre>{@code
     *     function main(c) {
     *         const ui = mappet.createUI(c, "handler")
     *
     *         ui.colorPicker(0xffffff).rwh(0.25, 0.25).rxy(0.5, 0.5).anchor(0.5)
     *
     *         c.player.openUI(ui)
     *     }
     * }</pre>
     */
    public UIColorPickerComponent colorPicker(int color) {
        UIColorPickerComponent component = new UIColorPickerComponent();

        this.getCurrent().getChildComponents().add(component);
        component.color(color);

        return component;
    }

    /**
     * Creates a color picker ui component.
     *
     * <pre>{@code
     *     function main(c) {
     *         const ui = mappet.createUI(c, "handler")
     *
     *         ui.colorPicker().rwh(0.25, 0.25).rxy(0.5, 0.5).anchor(0.5)
     *
     *         c.player.openUI(ui)
     *     }
     * }</pre>
     */
    public UIColorPickerComponent colorPicker() {
        UIColorPickerComponent component = new UIColorPickerComponent();

        this.getCurrent().getChildComponents().add(component);
        component.color(0x000000);

        return component;
    }
}