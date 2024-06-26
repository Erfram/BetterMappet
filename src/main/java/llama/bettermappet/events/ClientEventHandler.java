package llama.bettermappet.events;

import llama.bettermappet.api.scripts.code.ScriptVectorAngle;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.network.packets.PacketEvent;
import llama.bettermappet.utils.EventType;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ClientEventHandler {
    @SubscribeEvent
    public void cameraSetup(EntityViewRenderEvent.CameraSetup event) {
        Camera camera = Camera.get(Minecraft.getMinecraft().player);
        if (!camera.isCanceled()) {
            ScriptVectorAngle rotate = camera.getRotate();
            ScriptVector scale = camera.getScale();
            ScriptVector translate = camera.getPosition();

            GlStateManager.rotate((float)rotate.angle, (float)rotate.x, (float)rotate.y, (float)rotate.z);
            GlStateManager.scale(scale.x, scale.y, scale.z);
            GlStateManager.translate(translate.x, translate.y, translate.z);
        }

        NBTTagCompound data = new NBTTagCompound();

        Dispatcher.sendToServer(new PacketEvent(EventType.CAMERA, data));
    }

    @SubscribeEvent
    public void renderHandEvent(RenderSpecificHandEvent event) {
        Hand hand = Hand.get(Minecraft.getMinecraft().player);

        hand.setTypeHand(event.getHand() == EnumHand.MAIN_HAND ? 0 : 1);

        ScriptVector position = hand.getPosition();
        ScriptVectorAngle rotate = hand.getRotate();
        boolean canceled = hand.isCanceled();

        event.setCanceled(canceled);

        if(canceled && (position.x == 0 && position.y == 0 && position.z == 0 && rotate.angle == 0 && rotate.x == 0 && rotate.y == 0 && rotate.z == 0)){
            return;
        }

        GL11.glRotated(rotate.angle, rotate.x, rotate.y, rotate.z);
        GL11.glTranslated(position.x, position.y, position.z);

        NBTTagCompound data = new NBTTagCompound();

        data.setFloat("partialTicks", event.getPartialTicks());
        data.setFloat("swingProgress", event.getSwingProgress());
        data.setFloat("equipProgress", event.getEquipProgress());
        data.setFloat("interpolatedPitch", event.getInterpolatedPitch());

        Dispatcher.sendToServer(new PacketEvent(EventType.HAND, data));
    }

    @SubscribeEvent
    public void renderHudPre(RenderGameOverlayEvent.Pre event) {
        Minecraft mc = Minecraft.getMinecraft();
        Hud hud = Hud.get(mc.player);

        hud.setName(event.getType().name());
        boolean canceled = hud.isCanceled();
        ScriptVector scale = hud.getScale();
        ScriptVector pos = hud.getPosition();
        ScriptVectorAngle rotate = hud.getRotate();

        event.setCanceled(canceled);

        if (!canceled && event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            if (this.isDefaultHud(hud)) {
                RenderHelper.enableStandardItemLighting();
                GlStateManager.pushMatrix();
                RenderHelper.enableStandardItemLighting();
                GL11.glRotated(rotate.angle, rotate.x, rotate.y, rotate.z);
                GL11.glScaled(scale.x, scale.y, scale.z);
                GL11.glTranslated(pos.x, pos.y, pos.z);
            }
        }

        NBTTagCompound data = new NBTTagCompound();

        data.setString("hudType", event.getType().name());

        Dispatcher.sendToServer(new PacketEvent(EventType.HUD, data));
    }

    @SubscribeEvent
    public void onRenderGuiPost(RenderGameOverlayEvent.Post event) {
        Hud hud = Hud.get(Minecraft.getMinecraft().player);

        hud.setName(event.getType().name());
        if (!event.isCanceled() && event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            if(this.isDefaultHud(hud)) {
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();
                RenderHelper.disableStandardItemLighting();
            }
        }
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

        Dispatcher.sendToServer(new PacketEvent(EventType.MOUSE, data));
    }

    private boolean isDefaultHud(Hud hud) {
        ScriptVector scale = hud.getScale();
        ScriptVector pos = hud.getPosition();
        ScriptVectorAngle rotate = hud.getRotate();
        return !(pos.x == 0 && pos.y == 0 && pos.z == 0 && rotate.angle == 0 && rotate.x == 0 && rotate.y == 0 && rotate.z == 0 && scale.x == 1 && scale.y == 1 && scale.z == 0);
    }
}