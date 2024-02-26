package llamakot.bettermappet.mixins;

import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.client.ClientData;
import llamakot.bettermappet.client.network.PacketClientData;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketDownloadToClient;
import llamakot.bettermappet.utils.NotSuitableExtensionException;
import mchorse.mappet.api.scripts.code.entities.ScriptPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Consumer;

@Mixin(value = ScriptPlayer.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.entities.IScriptPlayer")
public abstract class MixinScriptPlayer<T extends Entity> {
    @Shadow public abstract EntityPlayerMP getMinecraftPlayer();

    /**Uploads files from the specified world directory to the player's disk. If you don't specify a disk in the file path, you will start from the game folder. Available extensions:
     * .json,
     * .cfg,
     * .properties,
     * .png
     *
     * <pre>{@code
     *    function main(c) {
     *        var fileDir = mappet.getWorldDir().resolve('icon.png').getPath()
     *
     *        // Loads the icon.png picture from the world folder, to the player in the config folder
     *        c.player.download(fileDir, 'config\\icon.png')
     *    }
     * }</pre>
     */
    public void download(String filePath, String path) throws IOException, NotSuitableExtensionException {
        if(path.endsWith(".png") || path.endsWith(".json") || path.endsWith(".cfg") || path.endsWith(".properties")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.SERVER_TO_CLIENT, path, Files.readAllBytes(Paths.get(filePath)) ), this.getMinecraftPlayer());
        } else {
            throw new NotSuitableExtensionException("Invalid file format");
        }
    }

    public void downloadFromURL(String url, String filePath) throws NotSuitableExtensionException {
        if(filePath.endsWith(".png") || filePath.endsWith(".json") || filePath.endsWith(".cfg") || filePath.endsWith(".properties")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.URL, filePath, url), this.getMinecraftPlayer());
        } else {
            throw new NotSuitableExtensionException("Invalid file format");
        }
    }
}
