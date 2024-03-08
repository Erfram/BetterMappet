package llama.bettermappet.network.packets;

import io.netty.buffer.ByteBuf;
import mchorse.chameleon.Chameleon;
import mchorse.mclib.network.ClientMessageHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketReloadModels implements IMessage {

    public PacketReloadModels() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ClientHandler extends ClientMessageHandler<PacketReloadModels> {
        @Override
        public void run(EntityPlayerSP entityPlayerSP, PacketReloadModels packetReloadModels) {
            Chameleon.proxy.reloadModels();
        }
    }
}