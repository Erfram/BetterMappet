package llamakot.bettermappet.network;

import llamakot.bettermappet.BetterMappet;
import llamakot.bettermappet.network.packets.PacketDownloadToClient;
import llamakot.bettermappet.network.packets.PacketEvent;
import mchorse.mclib.network.AbstractDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Iterator;

public class Dispatcher {
    public static final AbstractDispatcher DISPATCHER = new AbstractDispatcher(BetterMappet.MOD_ID) {
        public void register() {
            this.register(PacketEvent.class, PacketEvent.ServerHandler.class, Side.SERVER);
            this.register(PacketDownloadToClient.class, PacketDownloadToClient.ClientHandler.class, Side.CLIENT);
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
