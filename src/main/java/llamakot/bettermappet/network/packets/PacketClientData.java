package llamakot.bettermappet.network.packets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.utils.PlayerData;
import mchorse.mclib.network.ClientMessageHandler;
import mchorse.mclib.network.ServerMessageHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PacketClientData implements IMessage {
    public static final Map<UUID, Consumer<Object>> сallBack = new HashMap<>();
    PlayerData type;
    AccessType accessType;
    NBTTagCompound data;
    String uniqueId;

    public PacketClientData() {
    }

    public PacketClientData(PlayerData type, NBTTagCompound data, UUID uniqueId) {
        this.type = type;
        this.data = data;
        this.uniqueId = uniqueId.toString();
    }

    public PacketClientData(PlayerData type, NBTTagCompound data, String uniqueId) {
        this.type = type;
        this.data = data;
        this.uniqueId = uniqueId;
    }

    public PacketClientData(PlayerData type, NBTTagCompound data) {
        this.type = type;
        this.data = data;
        this.uniqueId = "null";
    }

    public PacketClientData(PlayerData type, UUID uniqueId) {
        this.type = type;
        this.data = new NBTTagCompound();
        this.uniqueId = uniqueId.toString();
    }

    public PacketClientData(PlayerData type, String uniqueId) {
        this.type = type;
        this.data = new NBTTagCompound();
        this.uniqueId = uniqueId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = PlayerData.valueOf(ByteBufUtils.readUTF8String(buf)); // Enum type
        this.data = ByteBufUtils.readTag(buf); // nbtData
        this.uniqueId = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.type.toString());
        ByteBufUtils.writeTag(buf, this.data);
        ByteBufUtils.writeUTF8String(buf, this.uniqueId);
    }

    public static class ClientHandler extends ClientMessageHandler<PacketClientData> {
        @Override
        @SideOnly(Side.CLIENT)
        public void run(EntityPlayerSP player, PacketClientData message) {
            switch (message.type) {
                case TIME:
                    NBTTagCompound tag = new NBTTagCompound();

                    tag.setString(PlayerData.TIME.toString(), LocalTime.now().toString());
                    Dispatcher.sendToServer(new PacketClientData(PlayerData.TIME, tag, message.uniqueId));
                    break;
            }
        }
    }

    public static class ServerHandler extends ServerMessageHandler<PacketClientData> {
        @Override
        public void run(EntityPlayerMP entityPlayerMP, PacketClientData packet) {
            NBTTagCompound data = packet.data;
            UUID uniqueId = UUID.fromString(packet.uniqueId);

            сallBack.get(uniqueId).accept(packet.type.process(data));
            сallBack.remove(uniqueId);
        }
    }
}
