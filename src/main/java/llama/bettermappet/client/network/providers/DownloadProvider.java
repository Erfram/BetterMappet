package llama.bettermappet.client.network.providers;

import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.network.packets.PacketUploadFile;
import llama.bettermappet.utils.DownloadType;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DownloadProvider implements IClientDataProvider{
    @Override
    public void setData(NBTTagCompound data) {
        Path path = Paths.get(data.getString("path"));
        byte[] fileBytes = data.getByteArray("fileBytes");
        DownloadType side = DownloadType.valueOf(data.getString("side"));
        DownloadType type = DownloadType.valueOf(data.getString("type"));

        String url = data.getString("url");

        if(url.contains("https://drive.google.com")) {
            url = url.replace("file/d/", "uc?id=").replace("/view?usp=sharing", "&export=download");
        }

        if(url.contains("https://dropbox.com")) {
            url = url.replace("www.dropbox.com", "dl.dropboxusercontent.com");
        }

        try {
            switch (side) {
                case SERVER_TO_CLIENT:
                    switch (type) {
                        case URL:
                            InputStream is = new URL(url).openStream();

                            Files.copy(is, path);

                            is.close();
                            break;
                        case DOWNLOAD:
                            if (!Files.exists(path.getParent())) {
                                Files.createDirectories(path.getParent());
                            }
                    }
                    Files.write(path, fileBytes);
                case CLIENT_TO_SERVER:
                    try {
                        Path pathFile = Paths.get(data.getString("filePath"));
                        data.setString("url", url);
                        data.setByteArray("fileBytes", Files.readAllBytes(pathFile));

                        if(Files.isDirectory(pathFile)) {
                            List<Path> filesList = Files.list(pathFile).collect(Collectors.toList());

                            filesList.forEach((file) -> {
                                Dispatcher.sendToServer(new PacketUploadFile(data));
                            });
                        }else {
                            Dispatcher.sendToServer(new PacketUploadFile(data));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
