package llamakot.bettermappet.client;

import net.minecraft.nbt.NBTTagCompound;

public enum ClientData {
    CONFIG_DIR {
        @Override
        public Object process(NBTTagCompound data) {
            return data.getString(this.name());
        }
    };

    ClientData() {

    }

    public abstract Object process(NBTTagCompound data);
}
