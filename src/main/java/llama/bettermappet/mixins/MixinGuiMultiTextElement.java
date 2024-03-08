package llama.bettermappet.mixins;

import mchorse.mappet.client.gui.scripts.utils.HighlightedTextLine;
import mchorse.mappet.client.gui.utils.text.GuiMultiTextElement;
import mchorse.mappet.client.gui.utils.text.undo.TextEditUndo;
import mchorse.mappet.client.gui.utils.text.utils.Cursor;
import mchorse.mclib.client.gui.framework.elements.utils.GuiContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = GuiMultiTextElement.class, remap = false)
public abstract class MixinGuiMultiTextElement {
    @Shadow public abstract void deselect();

    @Shadow public abstract List<HighlightedTextLine> getLines();

    @Shadow @Final public Cursor cursor;

    @Shadow public abstract void writeString(String string);

    @Shadow @Final public Cursor selection;

    @Shadow public abstract void setText(String text);

    @Inject(method = "handleKeys", at = @At(value = "INVOKE", target = "Lmchorse/mappet/client/gui/utils/text/utils/Cursor;<init>()V"), cancellable = true)
    public void onHandleKeys(GuiContext context, TextEditUndo undo, boolean ctrl, boolean shift, CallbackInfoReturnable<Boolean> cir) {
        HighlightedTextLine line = this.getLines().get(this.cursor.line);
        String text = line.text;

        boolean isCommented = text.trim().startsWith("//");

        if(!isCommented) {
            line.set("//"+text);
        }

        undo.ready().post("// " + text, this.cursor, this.selection);

        cir.setReturnValue(true);
    }
}
