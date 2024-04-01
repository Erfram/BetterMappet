package llama.bettermappet.client.network.providers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class SkinProvider implements IClientDataProvider{
    @Override
    public void setData(NBTTagCompound data) {
        Minecraft.getMinecraft().getSkinManager().loadSkin(new MinecraftProfileTexture(data.getString("url"), null), MinecraftProfileTexture.Type.SKIN);
    };
}
