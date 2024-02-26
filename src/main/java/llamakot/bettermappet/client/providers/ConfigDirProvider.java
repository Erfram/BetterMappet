package llamakot.bettermappet.client.providers;

import llamakot.bettermappet.client.ClientData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

import java.nio.file.Path;

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
