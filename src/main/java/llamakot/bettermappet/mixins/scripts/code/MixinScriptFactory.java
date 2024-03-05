package llamakot.bettermappet.mixins.scripts.code;

import llamakot.bettermappet.api.scripts.code.ScriptCamera;
import llamakot.bettermappet.mixins.MixinTargetName;
import llamakot.bettermappet.utils.IllegalSkin;
import mchorse.mappet.api.scripts.code.ScriptFactory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScriptFactory.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.IScriptFactory")
public abstract class MixinScriptFactory {
    public String readJSONFromURL(String url) {
        return IllegalSkin.readJsonFromUrl(url);
    }
}
