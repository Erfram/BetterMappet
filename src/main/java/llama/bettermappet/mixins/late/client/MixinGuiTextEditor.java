package llama.bettermappet.mixins.late.client;

import mchorse.mappet.client.gui.scripts.GuiTextEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GuiTextEditor.class, remap = false)
public abstract class MixinGuiTextEditor {
    @Inject(method = "getFromChar", at = @At("HEAD"), cancellable = true)
    public void onGetFromChar(char typedChar, CallbackInfoReturnable<String> cir) {
        GuiTextEditor instance = ((GuiTextEditor)(Object)this);

        String line = instance.getLines().get(instance.cursor.line).text;
        if (typedChar == '*' && line.charAt(instance.cursor.offset - 1) == '/') {
            cir.setReturnValue("**/");
        }
    }
}