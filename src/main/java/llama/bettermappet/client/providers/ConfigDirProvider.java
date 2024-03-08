package llama.bettermappet.client.providers;

import llama.bettermappet.client.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class ConfigDirProvider implements IClientDataProvider {
    @Override
    public NBTTagCompound getData() {
        String configDir = Minecraft.getMinecraft().toString();

        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        nbtTagCompound.setString(ClientData.CONFIG_DIR.toString(), configDir);

        return nbtTagCompound;
    }

    @Override
    public void setData(NBTTagCompound data) {
    }
}
