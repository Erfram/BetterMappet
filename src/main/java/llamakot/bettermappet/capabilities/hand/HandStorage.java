package llamakot.bettermappet.capabilities.hand;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class HandStorage implements Capability.IStorage<IHand>{
    @Override
    public NBTBase writeNBT(Capability<IHand> capability, IHand instance, EnumFacing side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IHand> capability, IHand instance, EnumFacing side, NBTBase nbt) {
        if (nbt instanceof NBTTagCompound)
        {
            instance.deserializeNBT((NBTTagCompound) nbt);
        }
    }
}
