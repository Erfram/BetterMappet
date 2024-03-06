package llamakot.bettermappet.mixins.scripts.code;

import llamakot.bettermappet.api.scripts.code.ScriptHandRender;
import llamakot.bettermappet.api.scripts.code.ScriptCamera;
import llamakot.bettermappet.api.scripts.code.ScriptHudRender;
import llamakot.bettermappet.api.scripts.user.IScriptCamera;
import llamakot.bettermappet.api.scripts.user.IScriptHudRender;
import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.mixins.utils.MixinTargetName;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketClientData;
import llamakot.bettermappet.network.packets.PacketDownloadToClient;
import llamakot.bettermappet.network.packets.PacketReloadModels;
import llamakot.bettermappet.utils.BetterException;
import llamakot.bettermappet.utils.IllegalSkin;
import llamakot.bettermappet.utils.PlayerData;
import mchorse.mappet.api.scripts.code.entities.ScriptPlayer;
import mchorse.mappet.api.scripts.user.entities.IScriptEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import llamakot.bettermappet.api.scripts.user.IScriptHandRender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Consumer;

@Mixin(value = ScriptPlayer.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.entities.IScriptPlayer")
public abstract class MixinScriptPlayer<T extends Entity> {
    @Shadow public abstract EntityPlayerMP getMinecraftPlayer();

    /**Uploads files from the specified world directory to the player's disk. If you don't specify a disk in the file path, you will start from the game folder. Available extensions:
     * §cjson§r,
     * §ccfg§r,
     * §cproperties§r,
     * §cpng§r,
     * §cobj§r,
     * §cmtl§r,
     * §cogg§r
     *
     * <pre>{@code
     *    function main(c) {
     *        var filePath = mappet.getWorldDir().resolve('icon.png').getPath()
     *
     *        // Loads the icon.png picture from the world folder, to the player in the config folder
     *        c.player.download(filePath, 'config\\icon.png')
     *    }
     * }</pre>
     *
     * @param filePath
     * @param path
     * @throws IOException if an I/O error occurs while reading the file
     * @throws BetterException if the file format is not valid
     */
    public void download(String filePath, String path) throws IOException, BetterException {
        if(path.endsWith(".png") || path.endsWith(".json") || path.endsWith(".cfg") || path.endsWith(".properties") || path.endsWith(".obj") || path.endsWith(".mtl") || path.endsWith(".ogg")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.SERVER_TO_CLIENT, path, Files.readAllBytes(Paths.get(filePath)) ), this.getMinecraftPlayer());
        } else {
            throw new BetterException("Invalid file format");
        }
    }

    /**
     * Downloads a file from a URL and saves it to the specified file path on the player's disk.
     *
     * @param url the URL from which to download the file
     * @param filePath the destination path on the player's disk
     * @throws BetterException if the file format is not valid
     */
    public void downloadFromURL(String url, String filePath) throws BetterException {
        if(filePath.endsWith(".png") || filePath.endsWith(".json") || filePath.endsWith(".cfg") || filePath.endsWith(".properties") || filePath.endsWith(".obj") || filePath.endsWith(".mtl") || filePath.endsWith(".ogg")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.URL, filePath, url), this.getMinecraftPlayer());
        } else {
            throw new BetterException("Invalid file format");
        }
    }

    /**
     * Reloads chameleon models to the player
     *
     * @throws BetterException if the "chameleon" mod is not loaded
     */
    public void reloadModels() throws BetterException {
        if(Loader.isModLoaded("chameleon")) {
            Dispatcher.sendTo(new PacketReloadModels(), this.getMinecraftPlayer());
        } else {
            throw new BetterException("The chameleon mod is not loaded");
        }
    }

    /**
     * Gets the skin data for the current player from the specified URL. §8getURL§r(), §8getType§r()
     *
     * @return the skin data for the current player
     */
    public IllegalSkin.SkinData getElySkin() {
        return IllegalSkin.getSkinlink("http://skinsystem.ely.by/textures/", this.getMinecraftPlayer().getName());
    }

    /**
     * Gets the current time of the client.
     *
     * @param callBack
     */
    public void getTime(Consumer<Object> callBack) {
        UUID uniqueId = UUID.randomUUID();
        PacketClientData.сallBack.put(uniqueId, callBack);

        Dispatcher.sendTo(new PacketClientData(PlayerData.TIME, uniqueId), this.getMinecraftPlayer());
    }

    /**
     * Gets a {@link IScriptCamera} for the current player.
     *
     * @return the camera instance
     */
    public IScriptCamera getCamera() {
        return new ScriptCamera(this.getMinecraftPlayer());
    }

    /**
     * Gets a {@link IScriptHandRender} main or off - hand; 0 - main; 1 - off;.
     */
    public IScriptHandRender getHand(int hand) {
        return new ScriptHandRender(this.getMinecraftPlayer(), hand);
    }

    /**
     * Gets the {@link IScriptHudRender} with the specified name for the current player.
     *
     * @param name the name of the HUD render
     */
    public IScriptHudRender getHud(String name) {
        return new ScriptHudRender(this.getMinecraftPlayer(), name);
    }
}
