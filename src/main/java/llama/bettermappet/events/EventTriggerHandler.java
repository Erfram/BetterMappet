package llama.bettermappet.events;

import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.utils.triggers.TriggerAccessor;
import mchorse.mappet.CommonProxy;
import mchorse.mappet.Mappet;
import mchorse.mappet.api.triggers.Trigger;
import mchorse.mappet.api.utils.DataContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventTriggerHandler {
    public void onMouseEvent(NBTTagCompound data, EntityPlayerMP player) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getPlayerMouse();

        if (shouldCancelTrigger(trigger) || player.world.isRemote) {
            return;
        }

        DataContext context = new DataContext(player);
        context.set("button", data.getInteger("button"));
        context.set("x", data.getInteger("x"));
        context.set("y", data.getInteger("y"));
        context.set("dwheel", data.getInteger("dwheel"));
        context.set("dx", data.getInteger("dx"));
        context.set("dy", data.getInteger("dy"));
        context.getValues().put("buttonState", data.getBoolean("buttonState"));

        CommonProxy.eventHandler.trigger(new MouseEvent() , trigger, context);
    }

    public void onCameraEvent(EntityPlayerMP player) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getPlayerCamera();

        if (shouldCancelTrigger(trigger) || player.world.isRemote) {
            return;
        }

        Camera camera = Camera.get(player);

        DataContext context = new DataContext(player);
        context.getValues().put("rotate", camera.getRotate());
        context.getValues().put("scale", camera.getScale());
        context.getValues().put("canceled", camera.isCanceled());

        trigger.trigger(context);
    }

    @SubscribeEvent
    public void onCommandEvent(CommandEvent event) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getCommand();

        if (shouldCancelTrigger(trigger)) {
            return;
        }

        DataContext context = new DataContext(event.getSender().getServer());
        context.set("command", event.getCommand().getName());
        context.set("sender", event.getSender().getName());
        context.getValues().put("parameters", event.getParameters());

        CommonProxy.eventHandler.trigger(event, trigger, context);
    }

    public void onRenderHandEvent(NBTTagCompound data, EntityPlayerMP player) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getPlayerRenderHand();

        if (shouldCancelTrigger(trigger) || player.world.isRemote) {
            return;
        }

        DataContext context = new DataContext(player);
        context.getValues().put("partialTicks", data.getFloat("partialTicks"));
        context.getValues().put("swingProgress", data.getFloat("swingProgress"));
        context.getValues().put("equipProgress", data.getFloat("equipProgress"));
        context.getValues().put("interpolatedPitch", data.getFloat("interpolatedPitch"));

        trigger.trigger(context);
    }

    public void onKeyboardEvent(NBTTagCompound data, EntityPlayerMP player) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getPlayerKeyboard();

        if (shouldCancelTrigger(trigger) || player.world.isRemote) {
            return;
        }

        DataContext context = new DataContext(player);
        context.set("keyCode", data.getInteger("keyCode"));
        context.getValues().put("keyState", data.getBoolean("keyState"));

        trigger.trigger(context);
    }

    public void onRenderHudEvent(NBTTagCompound data, EntityPlayerMP player) {
        if (Mappet.settings == null) {
            return;
        }

        Trigger trigger = ((TriggerAccessor) Mappet.settings).getPlayerRenderHud();

        if (shouldCancelTrigger(trigger) || player.world.isRemote) {
            return;
        }

        DataContext context = new DataContext(player);
        context.set("hudType", data.getString("hudType"));

        trigger.trigger(context);
    }

    public boolean shouldCancelTrigger(Trigger trigger) {
        return trigger == null || trigger.isEmpty();
    }
}
