package llama.bettermappet.capabilities.skin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class SkinProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(ISkin.class)
    public static final Capability<ISkin> SKIN = null;

    private ISkin instance = SKIN.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == SKIN;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? SKIN.<T>cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return SKIN.getStorage().writeNBT(SKIN, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        SKIN.getStorage().readNBT(SKIN, instance, null, nbt);
    }

    public static ISkin getHandler(Entity entity) {
        if (entity.hasCapability(SKIN, EnumFacing.DOWN))
            return entity.getCapability(SKIN, EnumFacing.DOWN);
        return null;
    }
}
