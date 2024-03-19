package llama.bettermappet.mixins.client;

import llama.bettermappet.BetterMappet;
import mchorse.mappet.client.gui.GuiTriggerBlockScreen;
import mchorse.mappet.client.gui.utils.GuiVecPosElement;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiTriggerBlockScreen.class, remap = false)
public abstract class MixinGuiTriggerBlockScreen {
    @Inject(method = "sanitizeTrackpads", at = @At(value = "INVOKE_ASSIGN", target = "Lmchorse/mappet/client/gui/utils/GuiVecPosElement;clamp(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lmchorse/mappet/client/gui/utils/GuiVecPosElement;"))
    public void onSanitizeTrackpads(GuiVecPosElement guiVecPosElement, CallbackInfo ci) {
        if(BetterMappet.removeRestrictionsScriptBlock.get()) {
            guiVecPosElement.clamp(new Vec3d(-3, -3, -3), new Vec3d(3, 3, 3));
        }
    }
}
