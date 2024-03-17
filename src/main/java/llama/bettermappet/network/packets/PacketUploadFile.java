package llama.bettermappet.network.packets;

import io.netty.buffer.ByteBuf;
import llama.bettermappet.CommonProxy;
import llama.bettermappet.utils.DownloadType;
import llama.bettermappet.utils.EventType;
import mchorse.mclib.network.ServerMessageHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PacketUploadFile implements IMessage {
    NBTTagCompound data;

    public PacketUploadFile() {
    }

    public PacketUploadFile(NBTTagCompound data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.data);
    }

    public static class ServerHandler extends ServerMessageHandler<PacketEvent> {
        @Override
        public void run(EntityPlayerMP entityPlayerMP, PacketEvent packet) {
            NBTTagCompound data = packet.data;

            Path path = Paths.get(data.getString("path"));
            byte[] fileBytes = data.getByteArray("fileBytes");
            DownloadType type = DownloadType.valueOf(data.getString("type"));
            try {
                URL url = new URL(data.getString("url"));

                switch (type) {
                    case URL:
                        InputStream is = url.openStream();

                        Files.copy(is, path);

                        is.close();
                        break;
                    case DOWNLOAD:
                        if (!Files.exists(path.getParent())) {
                            Files.createDirectories(path.getParent());
                        }
                        Files.write(path, fileBytes);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
