package llamakot.bettermappet.events;

import llamakot.bettermappet.BetterMappet;
import llamakot.bettermappet.capabilities.CapabilitiesType;
import llamakot.bettermappet.capabilities.camera.Camera;
import llamakot.bettermappet.capabilities.camera.CameraProvider;
import llamakot.bettermappet.capabilities.camera.ICamera;
import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketCapability;
import llamakot.bettermappet.network.packets.PacketEvent;
import llamakot.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class EventHandler {
    public static final ResourceLocation CAMERA = new ResourceLocation(BetterMappet.MOD_ID, "betterCamera");

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CAMERA, new CameraProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        final ICamera camera = Camera.get(player);
        Dispatcher.sendTo(new PacketCapability(camera.serializeNBT(), CapabilitiesType.CAMERA), (EntityPlayerMP) player);
    }

    @SubscribeEvent
    public void mouseEvent(MouseEvent event) {
        NBTTagCompound data = new NBTTagCompound();

        data.setInteger("button", event.getButton());
        data.setInteger("x", event.getX());
        data.setInteger("y", event.getY());
        data.setInteger("dwheel", event.getDwheel());
        data.setInteger("dx", event.getDx());
        data.setInteger("dy", event.getDy());
        data.setBoolean("buttonState", event.isButtonstate());

        Dispatcher.sendToServer(new PacketEvent(AccessType.MOUSE, data));
    }

    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup event) {
        //pitch, yaw, roll, cancel

        Camera camera = Camera.get((EntityPlayerSP)event.getEntity());
        if (!camera.isCanceled()) {
            ScriptVectorAngle rotate = camera.getRotate();
            ScriptVector scale = camera.getScale();

            GlStateManager.rotate((float)rotate.angle, (float)rotate.x, (float)rotate.y, (float)rotate.z);
            GlStateManager.scale(scale.x, scale.y, scale.z);
        }

        NBTTagCompound data = new NBTTagCompound();

        Dispatcher.sendToServer(new PacketEvent(AccessType.CAMERA, data));
    }
}
