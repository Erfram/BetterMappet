package llamakot.bettermappet.network.packets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import llamakot.bettermappet.capabilities.CapabilitiesType;
import llamakot.bettermappet.capabilities.camera.Camera;
import llamakot.bettermappet.capabilities.hand.Hand;
import llamakot.bettermappet.capabilities.hud.Hud;
import mchorse.mclib.network.ClientMessageHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketCapability implements IMessage {
    NBTTagCompound profile;
    CapabilitiesType type;
    public PacketCapability() {
    }

    public PacketCapability(NBTTagCompound profile, CapabilitiesType type) {
        this.profile = profile;
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.profile = ByteBufUtils.readTag(buf);
        this.type = CapabilitiesType.valueOf(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.profile);
        ByteBufUtils.writeUTF8String(buf, this.type.toString());
    }

    public static class ClientHandler extends ClientMessageHandler<PacketCapability> {
        @Override
        @SideOnly(Side.CLIENT)
        public void run(EntityPlayerSP player, PacketCapability message) {
            CapabilitiesType type = message.type;

            switch (type){
                case CAMERA:
                    Camera.get(player).deserializeNBT(message.profile);
                    break;
                case HAND:
                    Hand.get(player).deserializeNBT(message.profile);
                    break;
                case HUD:
                    Hud.get(player).deserializeNBT(message.profile);
                    break;
            }
        }
    }
}
