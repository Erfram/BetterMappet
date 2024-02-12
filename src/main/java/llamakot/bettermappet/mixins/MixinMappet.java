package llamakot.bettermappet.mixins;

import llamakot.bettermappet.BetterMappet;
import mchorse.mappet.Mappet;
import mchorse.mappet.api.scripts.ScriptManager;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(value = Mappet.class, remap = false)
public abstract class MixinMappet {
    @Shadow public static ScriptManager scripts;

    @Inject(method = "serverStarting", at = @At(value = "INVOKE_ASSIGN", target = "Lmchorse/mappet/utils/ScriptUtils;initiateScriptEngines()V"))
    public void onServerStarting(FMLServerStartingEvent event, CallbackInfo ci) {
        scripts = new ScriptManager(new File(BetterMappet.directory.get()));
    }
}
