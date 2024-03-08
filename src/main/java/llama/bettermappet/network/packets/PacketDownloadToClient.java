package llama.bettermappet.network.packets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import llama.bettermappet.client.AccessType;
import mchorse.mclib.network.ClientMessageHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PacketDownloadToClient implements IMessage {
    String path;
    byte[] file;
    AccessType type;
    String url;

    public PacketDownloadToClient() {
    }

    public PacketDownloadToClient(AccessType type, String path, byte[] file) {
        this.type = type;
        this.path = path;
        this.file = file;
        this.url = "";
    }

    public PacketDownloadToClient(AccessType type, String path, String url) {
        this.type = type;
        this.path = path;
        this.file = new byte[]{};
        this.url = url;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = AccessType.valueOf(ByteBufUtils.readUTF8String(buf));
        this.path = ByteBufUtils.readUTF8String(buf);
        this.file = mchorse.mclib.utils.ByteBufUtils.readByteArray(buf);
        this.url = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.type.toString());
        ByteBufUtils.writeUTF8String(buf, this.path);
        mchorse.mclib.utils.ByteBufUtils.writeByteArray(buf, this.file);
        ByteBufUtils.writeUTF8String(buf, this.url);
    }

    public static class ClientHandler extends ClientMessageHandler<PacketDownloadToClient> {
        @Override
        @SideOnly(Side.CLIENT)
        public void run(EntityPlayerSP player, PacketDownloadToClient message) {
            Path path = Paths.get(message.path);
            byte[] fileBytes = message.file;
            AccessType type = message.type;
            String url = message.url;

            if(url.contains("drive.google.com")) {
                url = url.replace("file/d/", "uc?id=").replace("/view?usp=sharing", "&export=download");
            }

            if(url.contains("dropbox.com")) {
                url = url.replace("www.dropbox.com", "dl.dropboxusercontent.com");
            }

            GuiScreen.setClipboardString(url);

            try {
                switch (type) {
                    case URL:
                        InputStream is = new URL(url).openStream();

                        Files.copy(is, path);

                        is.close();
                        break;
                    case SERVER_TO_CLIENT:
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