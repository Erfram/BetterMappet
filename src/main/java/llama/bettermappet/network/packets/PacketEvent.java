package llama.bettermappet.network.packets;

import io.netty.buffer.ByteBuf;
import llama.bettermappet.CommonProxy;
import llama.bettermappet.utils.EventType;
import mchorse.mclib.network.ServerMessageHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketEvent implements IMessage {
    NBTTagCompound data;
    EventType accessType;

    public PacketEvent() {
    }

    public PacketEvent(EventType accessType, NBTTagCompound data) {
        this.accessType = accessType;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accessType = EventType.valueOf(ByteBufUtils.readUTF8String(buf));
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.accessType.toString());
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class ServerHandler extends ServerMessageHandler<PacketEvent> {
        @Override
        public void run(EntityPlayerMP entityPlayerMP, PacketEvent packet) {
            NBTTagCompound data = packet.data;
            EventType accessType = packet.accessType;

            switch (accessType) {
                case MOUSE:
                    CommonProxy.eventTriggerHandler.onMouseEvent(data, entityPlayerMP);
                    break;
                case KEYBOARD:
                    CommonProxy.eventTriggerHandler.onKeyboardEvent(data, entityPlayerMP);
                    break;
                case CAMERA:
                    CommonProxy.eventTriggerHandler.onCameraEvent(entityPlayerMP);
                    break;
                case HAND:
                    CommonProxy.eventTriggerHandler.onRenderHandEvent(data, entityPlayerMP);
                    break;
                case HUD:
                    CommonProxy.eventTriggerHandler.onRenderHudEvent(data, entityPlayerMP);
                    break;
            }
        }
    }
}
