package llama.bettermappet.utils;

import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.skin.Skin;
import llama.bettermappet.client.network.packets.PacketCapability;
import llama.bettermappet.network.Dispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkinUtils {
    private Skin skin;
    private EntityPlayer player;

    public SkinUtils(EntityPlayer player) {
        this.player = player;
        this.skin = Skin.get(this.player);
    }

    public void setTexture(ResourceLocation texture) {
        this.skin.setTexture(texture);
        this.sendToCapability();
    }

    public void setType(String type) {
        if(type.equals("slim") || type.equals("steve")) {
            this.skin.setType(type);
            this.sendToCapability();
        } else {
            throw new IllegalArgumentException("invalid name type");
        }
    }

    private void sendToCapability(){
        Dispatcher.sendToAll(new PacketCapability(this.skin.serializeNBT(), CapabilitiesType.SKIN));
    }
}
