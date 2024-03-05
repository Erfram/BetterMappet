package llamakot.bettermappet.mixins.scripts.code;

import llamakot.bettermappet.api.scripts.code.ScriptCamera;
import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.mixins.MixinTargetName;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.function.Consumer;

@Mixin(value = ScriptPlayer.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.entities.IScriptPlayer")
public abstract class MixinScriptPlayer<T extends Entity> {
    @Shadow public abstract EntityPlayerMP getMinecraftPlayer();

    public void download(String filePath, String path) throws IOException, BetterException {
        if(path.endsWith(".png") || path.endsWith(".json") || path.endsWith(".cfg") || path.endsWith(".properties") || path.endsWith(".obj") || path.endsWith(".mtl") || path.endsWith(".ogg")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.SERVER_TO_CLIENT, path, Files.readAllBytes(Paths.get(filePath)) ), this.getMinecraftPlayer());
        } else {
            throw new BetterException("Invalid file format");
        }
    }

    public void downloadFromURL(String url, String filePath) throws BetterException {
        if(filePath.endsWith(".png") || filePath.endsWith(".json") || filePath.endsWith(".cfg") || filePath.endsWith(".properties") || filePath.endsWith(".obj") || filePath.endsWith(".mtl") || filePath.endsWith(".ogg")){
            Dispatcher.sendTo(new PacketDownloadToClient(AccessType.URL, filePath, url), this.getMinecraftPlayer());
        } else {
            throw new BetterException("Invalid file format");
        }
    }

    public void reloadModels() throws BetterException {
        if(Loader.isModLoaded("chameleon")) {
            Dispatcher.sendTo(new PacketReloadModels(), this.getMinecraftPlayer());
        } else {
            throw new BetterException("The chameleon mod is not loaded");
        }
    }

    public IllegalSkin.SkinData getElySkin() {
        return IllegalSkin.getSkinlink("http://skinsystem.ely.by/textures/", this.getMinecraftPlayer().getName());
    }

    public void getTime(Consumer<Object> callBack) {
        UUID uniqueId = UUID.randomUUID();
        PacketClientData.сallBack.put(uniqueId, callBack);

        Dispatcher.sendTo(new PacketClientData(PlayerData.TIME, uniqueId), this.getMinecraftPlayer());
    }

    public void setClientGlowing(IScriptEntity entity) {
        EntityDataManager dataManager = entity.getMinecraftEntity().getDataManager();
        dataManager.set(new DataParameter(0, DataSerializers.BYTE), Byte.parseByte(String.valueOf(0x40)));
        getMinecraftPlayer().connection.sendPacket(new SPacketEntityMetadata(entity.getMinecraftEntity().getEntityId(), dataManager, true));
    }

    public ScriptCamera getBetterCamera() {
        return new ScriptCamera(this.getMinecraftPlayer());
    }
}
