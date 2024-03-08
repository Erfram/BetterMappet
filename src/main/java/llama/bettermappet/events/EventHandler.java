package llama.bettermappet.events;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import llama.bettermappet.BetterMappet;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.capabilities.camera.CameraProvider;
import llama.bettermappet.capabilities.camera.ICamera;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.capabilities.hand.HandProvider;
import llama.bettermappet.capabilities.hand.IHand;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.capabilities.hud.HudProvider;
import llama.bettermappet.capabilities.hud.IHud;
import llama.bettermappet.client.AccessType;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.network.packets.PacketCapability;
import llama.bettermappet.network.packets.PacketEvent;
import llama.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class EventHandler {
    public static final ResourceLocation CAMERA = new ResourceLocation(BetterMappet.MOD_ID, "camera");
    public static final ResourceLocation HAND = new ResourceLocation(BetterMappet.MOD_ID, "hand");
    public static final ResourceLocation HUD = new ResourceLocation(BetterMappet.MOD_ID, "hud");

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CAMERA, new CameraProvider());
            event.addCapability(HAND, new HandProvider());
            event.addCapability(HUD, new HudProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        final ICamera camera = Camera.get(player);
        final IHand hand = Hand.get(player);
        final IHud hud = Hud.get(player);

        Dispatcher.sendTo(new PacketCapability(camera.serializeNBT(), CapabilitiesType.CAMERA), (EntityPlayerMP) player);
        Dispatcher.sendTo(new PacketCapability(hand.serializeNBT(), CapabilitiesType.HAND), (EntityPlayerMP) player);
        Dispatcher.sendTo(new PacketCapability(hud.serializeNBT(), CapabilitiesType.HUD), (EntityPlayerMP) player);
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
    public void keyInputEvent(InputEvent.KeyInputEvent event) {
        NBTTagCompound data = new NBTTagCompound();

        data.setInteger("keyCode", Keyboard.getEventKey());
        data.setBoolean("keyState", Keyboard.getEventKeyState());

        Dispatcher.sendToServer(new PacketEvent(AccessType.KEYBOARD, data));
    }

    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup event) {
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

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderHandEvent(RenderSpecificHandEvent event) {
        Hand hand = Hand.get(Minecraft.getMinecraft().player);

        if(event.getHand() == EnumHand.MAIN_HAND) {
            hand.setTypeHand(0);
        } else {
            hand.setTypeHand(1);
        }

        ScriptVector position = hand.getPosition();
        ScriptVectorAngle rotate = hand.getRotate();
        boolean canceled = hand.isCanceled();

        if(canceled) {
            event.setCanceled(true);
            return;
        }

        if(position.x == 0 && position.y == 0 && position.z == 0 && rotate.angle == 0 && rotate.x == 0 && rotate.y == 0 && rotate.z == 0){
            return;
        }

        GL11.glRotated(rotate.angle, rotate.x, rotate.y, rotate.z);
        GL11.glTranslated(position.x, position.y, position.z);

        NBTTagCompound data = new NBTTagCompound();

        data.setFloat("partialTicks", event.getPartialTicks());
        data.setFloat("swingProgress", event.getSwingProgress());
        data.setFloat("equipProgress", event.getEquipProgress());
        data.setFloat("interpolatedPitch", event.getInterpolatedPitch());

        Dispatcher.sendToServer(new PacketEvent(AccessType.HAND, data));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderGuiPre(RenderGameOverlayEvent.Pre event) {
        Hud hud = Hud.get(Minecraft.getMinecraft().player);

        hud.setName(event.getType().name());
        boolean canceled = hud.isCanceled();
        ScriptVector scale = hud.getScale();
        ScriptVector pos = hud.getPosition();
        ScriptVectorAngle rotate = hud.getRotate();

        event.setCanceled(canceled);

        if (!canceled && (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR && event.getType() != RenderGameOverlayEvent.ElementType.ALL)) {
            GL11.glPushMatrix();
            GL11.glRotated(rotate.angle, rotate.x, rotate.y, rotate.z);
            GL11.glScaled(scale.x, scale.y, scale.z);
            GL11.glTranslated(pos.x, pos.y, pos.z);
        }

        NBTTagCompound data = new NBTTagCompound();

        data.setString("hudType", event.getType().name());

        Dispatcher.sendToServer(new PacketEvent(AccessType.HUD, data));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onRenderGuiPost(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR && event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            GL11.glPopMatrix();
    }
}
