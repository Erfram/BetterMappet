package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.BetterMappet;
import llama.bettermappet.api.scripts.code.ScriptCamera;
import llama.bettermappet.api.scripts.code.ScriptHandRender;
import llama.bettermappet.api.scripts.code.ScriptHudRender;
import llama.bettermappet.api.scripts.code.ScriptTeam;
import llama.bettermappet.api.scripts.user.IScriptCamera;
import llama.bettermappet.api.scripts.user.IScriptHandRender;
import llama.bettermappet.api.scripts.user.IScriptHudRender;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketClientData;
import llama.bettermappet.utils.AccessType;
import llama.bettermappet.utils.ClientData;
import llama.bettermappet.utils.DownloadType;
import mchorse.mappet.api.scripts.code.entities.ScriptPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import llama.bettermappet.api.scripts.user.IScriptTeam;

@Mixin(value = ScriptPlayer.class, remap = false)
public abstract class MixinScriptPlayer {
    @Shadow
    public abstract EntityPlayerMP getMinecraftPlayer();
    EntityPlayerMP player = this.getMinecraftPlayer();

    /**Download files from the specified world directory to the player's disk. If you don't specify a disk in the file path, you will start from the game folder. Available extensions:
     * §cjson§r,
     * §ccfg§r,
     * §cproperties§r,
     * §cpng§r,
     * §cobj§r,
     * §cmtl§r,
     * §cogg§r,
     * §czip§r
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
     * @throws IllegalArgumentException if the file format is not valid
     */
    public void download(String filePath, String path) throws IOException {
        Path pathFile = Paths.get(filePath);
        if(Files.isDirectory(pathFile)) {
            List<Path> filesList = Files.list(pathFile).collect(Collectors.toList());

            filesList.forEach((file) -> {
                try {
                    this.download(file.toString(), path +"/"+ file.getFileName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            return;
        }

        if(Arrays.stream(BetterMappet.formats).anyMatch(path::endsWith)){
            NBTTagCompound data = new NBTTagCompound();

            data.setString("path", path);
            data.setByteArray("fileBytes", Files.readAllBytes(pathFile));
            data.setString("side", String.valueOf(DownloadType.SERVER_TO_CLIENT));
            data.setString("type", String.valueOf(DownloadType.DOWNLOAD));

            Dispatcher.sendTo(new PacketClientData(ClientData.DOWNLOAD, AccessType.SET, data), this.player);
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }
    }

    public void upload(String filePath, String path) {
        if(Arrays.stream(BetterMappet.formats).anyMatch(path::endsWith)){
            NBTTagCompound data = new NBTTagCompound();

            data.setString("path", path);
            data.setString("filePath", filePath);
            data.setString("side", String.valueOf(DownloadType.CLIENT_TO_SERVER));
            data.setString("type", String.valueOf(DownloadType.DOWNLOAD));

            Dispatcher.sendTo(new PacketClientData(ClientData.DOWNLOAD, AccessType.SET, data), this.player);
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }
    }

    /**
     * Downloads a file from a URL and saves it to the specified file path on the player's disk.
     *
     * @param url the URL from which to download the file
     * @param path the destination path on the player's disk
     * @throws IllegalArgumentException if the file format is not valid
     */
    public void downloadFromURL(String url, String path) {
        if(Arrays.stream(BetterMappet.formats).anyMatch(path::endsWith)){
            NBTTagCompound data = new NBTTagCompound();

            data.setString("url", url);
            data.setString("path", path);
            data.setString("side", String.valueOf(DownloadType.SERVER_TO_CLIENT));
            data.setString("type", String.valueOf(DownloadType.URL));

            Dispatcher.sendTo(new PacketClientData(ClientData.DOWNLOAD, AccessType.SET, data), this.getMinecraftPlayer());
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }
    }

    public void uploadFromURL(String url, String path) {
        if(Arrays.stream(BetterMappet.formats).anyMatch(path::endsWith)){
            NBTTagCompound data = new NBTTagCompound();

            data.setString("path", path);
            data.setString("url", url);
            data.setString("side", String.valueOf(DownloadType.CLIENT_TO_SERVER));
            data.setString("type", String.valueOf(DownloadType.URL));

            Dispatcher.sendTo(new PacketClientData(ClientData.DOWNLOAD, AccessType.SET, data), this.player);
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }
    }

    /**
     * Reloads chameleon models to the player
     *
     * @throws IllegalArgumentException if the "chameleon" mod is not loaded
     */
    public void reloadModels() {
        if(Loader.isModLoaded("chameleon_morph")) {
            Dispatcher.sendTo(new PacketClientData(ClientData.CHAMELEON_MODELS, AccessType.USE), this.getMinecraftPlayer());
        } else {
            throw new IllegalArgumentException("The chameleon mod is not loaded");
        }
    }

    /**
     * Gets the current time of the client.
     *
     * @param callback
     */
    public void getTime(Consumer<Object> callback) {
        UUID uniqueId = UUID.randomUUID();
        PacketClientData.callback.put(uniqueId, callback);

        Dispatcher.sendTo(new PacketClientData(ClientData.TIME, AccessType.GET, uniqueId), this.getMinecraftPlayer());
    }

    /**
     * Returns a ScriptTeam object representing the player's team.
     * If the player is not on the team, the method returns null.
     *
     * @return a {@link IScriptTeam} object representing the player's team, or null if the player is not on the team
     */
    public ScriptTeam getTeam() {
        if(this.player.isOnScoreboardTeam(this.player.getTeam())) {
            return new ScriptTeam(this.player);
        }

        return null;
    }

    /**
     * Removes a player from his current team.
     */
    public void leaveTeam() {
        this.player.getWorldScoreboard().removePlayerFromTeams(this.player.getName());
    }

    /**
     * Adds a player to the specified team.
     *
     * @param team name of the team to add the player to
     */
    public void joinTeam(String team) {
        this.player.getWorldScoreboard().addPlayerToTeam(this.player.getName(), team);
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