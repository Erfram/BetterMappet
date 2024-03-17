package llama.bettermappet.client.network.packets;

import io.netty.buffer.ByteBuf;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.capabilities.hud.Hud;
import mchorse.mclib.network.ClientMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        public void run(EntityPlayerSP entityPlayerSP, PacketCapability message) {
            CapabilitiesType type = message.type;

            switch (type){
                case CAMERA:
                    Camera.get(Minecraft.getMinecraft().player).deserializeNBT(message.profile);
                    break;
                case HAND:
                    Hand.get(Minecraft.getMinecraft().player).deserializeNBT(message.profile);
                    break;
                case HUD:
                    Hud.get(Minecraft.getMinecraft().player).deserializeNBT(message.profile);
                    break;
            }
        }
    }
}