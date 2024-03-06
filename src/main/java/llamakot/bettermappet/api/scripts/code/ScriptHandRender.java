package llamakot.bettermappet.api.scripts.code;

import llamakot.bettermappet.api.scripts.user.IScriptHandRender;
import llamakot.bettermappet.capabilities.CapabilitiesType;
import llamakot.bettermappet.capabilities.hand.Hand;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketCapability;
import llamakot.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayerMP;

public class ScriptHandRender implements IScriptHandRender {
    private final EntityPlayerMP player;
    private final Hand hand;
    public ScriptHandRender(EntityPlayerMP player, int hand) {
        this.player = player;
        this.hand = Hand.get(player);
        this.hand.setTypeHand(hand);
    }

    @Override
    public void setRotate(double angle, double x, double y, double z) {
        this.hand.setRotate(angle, x, y, z);

        this.sendToCapability();
    }

    @Override
    public ScriptVectorAngle getRotate() {
        return this.hand.getRotate();
    }

    @Override
    public void setPosition(double x, double y, double z) {
        this.hand.setPosition(x, y, z);

        this.sendToCapability();
    }

    @Override
    public void setPosition(ScriptVector pos) {
        this.hand.setPosition(pos.x, pos.y, pos.z);

        this.sendToCapability();
    }

    @Override
    public ScriptVector getPosition() {
        return this.hand.getPosition();
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.hand.setCanceled(canceled);

        this.sendToCapability();
    }

    @Override
    public boolean isCanceled() {
        return this.hand.isCanceled();
    }

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.hand.serializeNBT(), CapabilitiesType.HAND), this.player);
    }
}
