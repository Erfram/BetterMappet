package llama.bettermappet.capabilities.hand;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HandProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IHand.class)
    public static final Capability<IHand> HAND = null;

    private IHand instance = HAND.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == HAND;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? HAND.<T>cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return HAND.getStorage().writeNBT(HAND, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        HAND.getStorage().readNBT(HAND, instance, null, nbt);
    }

    public static IHand getHandler(Entity entity) {
        if (entity.hasCapability(HAND, EnumFacing.DOWN))
            return entity.getCapability(HAND, EnumFacing.DOWN);
        return null;
    }
}
