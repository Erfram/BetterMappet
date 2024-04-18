package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptHandRender;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import mchorse.mappet.CommonProxy;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import mchorse.mappet.utils.RunnableExecutionFork;
import mchorse.mclib.utils.Interpolation;
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
    public void resetRotate() {
        this.setRotate(0, 0, 0, 0);
    }

    @Override
    public void resetPosition() {
        this.setPosition(0, 0, 0);
    }

    @Override
    public void rotateTo(String interpolation, int durationTicks, float angle, float x, float y, float z) {
        Interpolation interp = Interpolation.valueOf(interpolation.toUpperCase());
        float startAngle = (float) this.getRotate().angle;
        float startX = (float) this.getRotate().x;
        float startY = (float) this.getRotate().y;
        float startZ = (float) this.getRotate().z;

        for (int i = 0; i < durationTicks; i++) {
            float progress = (float) i / (float) durationTicks;
            float interpAngle = interp.interpolate(startAngle, angle, progress);
            float interpX = interp.interpolate(startX, x, progress);
            float interpY = interp.interpolate(startY, y, progress);
            float interpZ = interp.interpolate(startZ, z, progress);

            CommonProxy.eventHandler.addExecutable(new RunnableExecutionFork(i, () -> this.setRotate(interpAngle, interpX, interpY, interpZ)));
        }
    }

    @Override
    public void moveTo(String interpolation, int durationTicks, float x, float y, float z) {
        Interpolation interp = Interpolation.valueOf(interpolation.toUpperCase());
        float startX = (float) this.getPosition().x;
        float startY = (float) this.getPosition().y;
        float startZ = (float) this.getPosition().z;

        for (int i = 0; i < durationTicks; i++) {
            float progress = (float) i / (float) durationTicks;
            float interpX = interp.interpolate(startX, x, progress);
            float interpY = interp.interpolate(startY, y, progress);
            float interpZ = interp.interpolate(startZ, z, progress);

            CommonProxy.eventHandler.addExecutable(new RunnableExecutionFork(i, () -> this.setPosition(interpX, interpY, interpZ)));
        }
    }

    @Override
    public boolean isCanceled() {
        return this.hand.isCanceled();
    }

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.hand.serializeNBT(), CapabilitiesType.HAND), this.player);
    }
}
