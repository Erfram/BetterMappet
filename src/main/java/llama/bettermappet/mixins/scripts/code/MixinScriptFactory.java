package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.utils.IllegalSkin;
import llama.bettermappet.mixins.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.ScriptFactory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScriptFactory.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.IScriptFactory")
public abstract class MixinScriptFactory {
    public String readJSONFromURL(String url) {
        return IllegalSkin.readJsonFromUrl(url);
    }
}
