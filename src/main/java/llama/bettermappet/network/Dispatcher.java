package llama.bettermappet.network;

import llama.bettermappet.BetterMappet;
import llama.bettermappet.client.network.packets.PacketCapability;
import llama.bettermappet.client.network.packets.PacketClientData;
import llama.bettermappet.network.packets.PacketEvent;
import mchorse.mclib.network.AbstractDispatcher;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

public class Dispatcher {
    public static final AbstractDispatcher DISPATCHER = new AbstractDispatcher(BetterMappet.MOD_ID) {
        public void register() {
            this.register(PacketClientData.class, PacketClientData.ServerHandler.class, Side.SERVER);
            this.register(PacketEvent.class, PacketEvent.ServerHandler.class, Side.SERVER);
            this.register(PacketClientData.class, PacketClientData.ClientHandler.class, Side.CLIENT);
            this.register(PacketCapability.class, PacketCapability.ClientHandler.class, Side.CLIENT);
        }
    };

    public static void sendTo(IMessage message, EntityPlayerMP player) {
        DISPATCHER.sendTo(message, player);
    }

    public static void sendToServer(IMessage message) {
        DISPATCHER.sendToServer(message);
    }

    public static void register() {
        DISPATCHER.register();
    }
}
