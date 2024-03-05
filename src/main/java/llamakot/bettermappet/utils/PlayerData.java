package llamakot.bettermappet.utils;

import net.minecraft.nbt.NBTTagCompound;

public enum PlayerData {
    TIME{
        @Override
        public Object process(NBTTagCompound data) {
            return data.getString(this.name());
        }
    };

    public abstract Object process(NBTTagCompound data);
}
