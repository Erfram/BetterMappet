package llama.bettermappet.capabilities.skin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class Skin implements ISkin {
    private EntityPlayer player;

    public ResourceLocation texture = null;
    public String type = null;

    public static Skin get(EntityPlayer player)
    {
        ISkin skinCapability = player == null ? null : player.getCapability(SkinProvider.SKIN, null);

        if (skinCapability instanceof Skin)
        {
            Skin profile = (Skin) skinCapability;
            profile.player = player;

            return profile;
        }
        return null;
    }

    @Override
    public void setTexture(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTexture() {
        return this.texture;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        tag.setString("texture", this.texture == null ? "null" : this.texture.toString());
        tag.setString("type", this.type == null ? "null" : this.type);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        if (tag.hasKey("texture")) {
            ResourceLocation texture = null;
            if(!tag.getString("texture").equals("null")) {
                texture = new ResourceLocation(tag.getString("texture"));
            }

            this.texture = texture;
        }

        if(tag.hasKey("type")) {
            this.type = tag.getString("type").equals("null") ? null : tag.getString("type");
        }
    }
}