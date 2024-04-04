package llama.bettermappet.mixins.late.scripts.code;

import llama.bettermappet.mixins.late.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.entities.ScriptEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ScriptEntity.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.entities.IScriptEntity")
public abstract class MixinScriptEntity {
}
