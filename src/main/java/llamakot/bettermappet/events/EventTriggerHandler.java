package llamakot.bettermappet.events;

import llamakot.bettermappet.client.AccessType;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketEvent;
import llamakot.bettermappet.triggers.TriggerAccessor;
import mchorse.mappet.Mappet;
import mchorse.mappet.api.triggers.Trigger;
import mchorse.mappet.api.utils.DataContext;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventTriggerHandler {
    @SubscribeEvent
    public void mouseEvent(MouseEvent event) {
        NBTTagCompound data = new NBTTagCompound();

        data.setInteger("button", event.getButton());
        data.setInteger("x", event.getX());
        data.setInteger("y", event.getY());
        data.setInteger("dwheel", event.getDwheel());
        data.setInteger("dx", event.getDx());
        data.setInteger("dy", event.getDy());
        data.setString("buttonState", String.valueOf(event.isButtonstate()));

        Dispatcher.sendToServer(new PacketEvent(AccessType.MOUSE, data));
    }

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
        trigger.trigger(context);
    }

    public boolean shouldCancelTrigger(Trigger trigger) {
        return trigger == null || trigger.isEmpty();
    }
}
