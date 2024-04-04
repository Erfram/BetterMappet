package llama.bettermappet.mixins.early.utils;

import llama.bettermappet.capabilities.skin.ISkin;
import llama.bettermappet.capabilities.skin.Skin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {
    @Inject(method = "getLocationSkin()Lnet/minecraft/util/ResourceLocation;", at = @At("HEAD"), cancellable = true)
    public void onGetLocationSkin(CallbackInfoReturnable<ResourceLocation> cir) {
        ISkin skinCapability = Skin.get(Minecraft.getMinecraft().player);
        ResourceLocation texture = skinCapability.getTexture();
        if(texture != null){
            cir.setReturnValue(texture);
        }
    }

    @Inject(method = "getSkinType", at = @At("HEAD"), cancellable = true)
    public void onGetSkinType(CallbackInfoReturnable<String> cir) {
        ISkin skinCapability = Skin.get(Minecraft.getMinecraft().player);
        String type = skinCapability.getType();
        if(type != null){
            cir.setReturnValue(type);
        }
    }
}