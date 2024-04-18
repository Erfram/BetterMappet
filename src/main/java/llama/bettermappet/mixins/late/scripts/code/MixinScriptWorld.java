package llama.bettermappet.mixins.late.scripts.code;

import llama.bettermappet.mixins.late.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.ScriptWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ScriptWorld.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.IScriptWorld")
public abstract class MixinScriptWorld {
    @Shadow private World world;

    @Shadow private BlockPos.MutableBlockPos pos;

    public void chunkLoad(int x, int z) {
        this.world.getChunkFromBlockCoords(this.pos.setPos(x, 0, z)).onLoad();
    }
}
