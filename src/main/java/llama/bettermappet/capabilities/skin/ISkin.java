package llama.bettermappet.capabilities.skin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;

public interface ISkin {
    void setTexture(ResourceLocation skin);
    ResourceLocation getTexture();

    void setType(String type);
    String getType();

    NBTTagCompound serializeNBT();
    void deserializeNBT(NBTTagCompound tag);
}
