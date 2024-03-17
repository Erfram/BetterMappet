package llama.bettermappet.client.network.providers;

import llama.bettermappet.utils.ClientData;
import net.minecraft.nbt.NBTTagCompound;

import java.time.LocalTime;

public class TimeProvider implements IClientDataProvider {
    public NBTTagCompound getData() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        String time = LocalTime.now().toString();
        nbtTagCompound.setString(ClientData.TIME.toString(), time);

        return nbtTagCompound;
    }
}
