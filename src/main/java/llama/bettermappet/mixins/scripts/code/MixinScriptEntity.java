package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.mixins.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.entities.ScriptEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScriptEntity.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.entities.IScriptEntity")
public abstract class MixinScriptEntity {
}
