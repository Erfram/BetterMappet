package llamakot.bettermappet.capabilities.hud;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class HudProvider implements ICapabilitySerializable<NBTBase> {
    @CapabilityInject(IHud.class)
    public static final Capability<IHud> HUD = null;

    private IHud instance = HUD.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == HUD;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? HUD.<T>cast(instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return HUD.getStorage().writeNBT(HUD, instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        HUD.getStorage().readNBT(HUD, instance, null, nbt);
    }

    public static IHud getHandler(Entity entity) {
        if (entity.hasCapability(HUD, EnumFacing.DOWN))
            return entity.getCapability(HUD, EnumFacing.DOWN);
        return null;
    }
}
