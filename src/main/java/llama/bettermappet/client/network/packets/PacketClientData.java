package llama.bettermappet.client.network.packets;

import io.netty.buffer.ByteBuf;
import llama.bettermappet.client.network.providers.SkinProvider;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.utils.AccessType;
import llama.bettermappet.utils.ClientData;
import llama.bettermappet.client.network.providers.DownloadProvider;
import llama.bettermappet.client.network.providers.IClientDataProvider;
import llama.bettermappet.client.network.providers.ChameleonModelsProvider;
import mchorse.mclib.network.ClientMessageHandler;
import mchorse.mclib.network.ServerMessageHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PacketClientData implements IMessage {
    public static final Map<UUID, Consumer<Object>> callback = new HashMap<>();
    ClientData type;
    AccessType access;
    NBTTagCompound nbtTagCompound;
    NBTTagCompound nbtData;
    String uniqueId;

    public PacketClientData() {
    }

    public PacketClientData(ClientData type, AccessType access, UUID uniqueId) {
        this.type = type;
        this.access = access;
        this.nbtTagCompound = new NBTTagCompound();
        this.nbtData = new NBTTagCompound();
        this.uniqueId = uniqueId.toString();
    }

    public PacketClientData(ClientData type, AccessType access) {
        this.type = type;
        this.access = access;
        this.nbtTagCompound = new NBTTagCompound();
        this.nbtData = new NBTTagCompound();
        this.uniqueId = "";
    }

    public PacketClientData(NBTTagCompound nbtData, ClientData type, AccessType access, UUID uniqueId) {
        this.type = type;
        this.access = access;
        this.nbtTagCompound = new NBTTagCompound();
        this.nbtData = nbtData;
        this.uniqueId = uniqueId.toString();
    }

    public PacketClientData(ClientData type, AccessType access, NBTTagCompound nbtTagCompound) {
        this.type = type;
        this.access = access;
        this.nbtTagCompound = nbtTagCompound;
        this.nbtData = new NBTTagCompound();
        this.uniqueId = "";
    }

    public PacketClientData(ClientData type, AccessType access, NBTTagCompound nbtTagCompound, UUID uniqueId) {
        this.type = type;
        this.access = access;
        this.nbtTagCompound = nbtTagCompound;
        this.nbtData = new NBTTagCompound();
        this.uniqueId = uniqueId.toString();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = ClientData.valueOf(ByteBufUtils.readUTF8String(buf)); // Enum type
        this.access = AccessType.valueOf(ByteBufUtils.readUTF8String(buf)); // Enum access
        this.nbtTagCompound = ByteBufUtils.readTag(buf); // nbtTagCompound
        this.nbtData = ByteBufUtils.readTag(buf); // nbtData
        this.uniqueId = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.type.toString());
        ByteBufUtils.writeUTF8String(buf, this.access.toString());
        ByteBufUtils.writeTag(buf, this.nbtTagCompound);
        ByteBufUtils.writeTag(buf, this.nbtData);
        ByteBufUtils.writeUTF8String(buf, this.uniqueId);
    }

    public static class ClientHandler extends ClientMessageHandler<PacketClientData> {
        @Override
        public void run(EntityPlayerSP entityPlayerSP, PacketClientData packetClientData) {
            ClientData typeEnum = packetClientData.type;
            AccessType typeAccess = packetClientData.access;
            NBTTagCompound value = packetClientData.nbtTagCompound;
            NBTTagCompound nbtData = packetClientData.nbtData;
            String uniqueId = packetClientData.uniqueId;
            IClientDataProvider provider = createProvider(typeEnum);

            switch (typeAccess) {
                case GET:
                    NBTTagCompound data = provider.getData();

                    Dispatcher.sendToServer(new PacketClientData(typeEnum, typeAccess, data, UUID.fromString(uniqueId)));
                    break;
                case SET:
                    provider.setData(value);
                    break;
                case USE:
                    provider.setData();
                    break;
                case GET_WITH_DATA:
                    NBTTagCompound dataWithResponse = provider.getData(nbtData);

                    Dispatcher.sendToServer(new PacketClientData(typeEnum, typeAccess, dataWithResponse, UUID.fromString(uniqueId)));
            }
        }

        public static IClientDataProvider createProvider(ClientData typeEnum) {

            switch(typeEnum) {
                case DOWNLOAD:
                    return new DownloadProvider();
                case CHAMELEON_MODELS:
                    return new ChameleonModelsProvider();
                case SKIN:
                    return new SkinProvider();
            }
            throw new IllegalArgumentException("Invalid typeEnum");
        }
    }

    public static class ServerHandler extends ServerMessageHandler<PacketClientData> {
        @Override
        public void run(EntityPlayerMP entityPlayerMP, PacketClientData packet) {
            NBTTagCompound value = packet.nbtTagCompound;
            UUID uniqueId = UUID.fromString(packet.uniqueId);

            callback.get(uniqueId).accept(packet.type.process(value));
            callback.remove(uniqueId);
        }
    }
}