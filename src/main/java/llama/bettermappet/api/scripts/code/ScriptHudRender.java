package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptHudRender;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import mchorse.mappet.CommonProxy;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import mchorse.mappet.utils.RunnableExecutionFork;
import mchorse.mclib.utils.Interpolation;
import net.minecraft.entity.player.EntityPlayerMP;

public class ScriptHudRender implements IScriptHudRender {
    private final EntityPlayerMP player;
    private final Hud hud;
    private final String name;
    public ScriptHudRender(EntityPlayerMP player, String name) {
        this.player = player;
        this.hud = Hud.get(player);

        this.name = name;
    }

    @Override
    public void setRotate(double angle, double x, double y, double z) {
        this.hud.setName(this.name);
        this.hud.setRotate(angle, x, y, z);

        this.sendToCapability();
    }

    @Override
    public ScriptVectorAngle getRotate() {
        this.hud.setName(this.name);
        return this.hud.getRotate();
    }

    @Override
    public void setPosition(double x, double y) {
        this.hud.setName(this.name);
        this.hud.setPosition(x, y);

        this.sendToCapability();
    }

    @Override
    public ScriptVector getPosition() {
        this.hud.setName(this.name);
        return this.hud.getPosition();
    }

    @Override
    public void setScale(double x, double y) {
        this.hud.setName(this.name);
        this.hud.setScale(x, y);

        this.sendToCapability();
    }

    @Override
    public ScriptVector getScale() {
        this.hud.setName(this.name);
        return this.hud.getScale();
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.hud.setName(this.name);
        this.hud.setCanceled(canceled);

        this.sendToCapability();
    }

    @Override
    public boolean isCanceled() {
        this.hud.setName(this.name);
        return this.hud.isCanceled();
    }

    @Override
    public void resetRotate() {
        this.setRotate(0, 0, 0, 0);
    }

    @Override
    public void resetScale() {
        this.setScale(1 , 1);
    }

    @Override
    public void resetPosition() {
        this.setPosition(0, 0);
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
    public void moveTo(String interpolation, int durationTicks, float x, float y) {
        Interpolation interp = Interpolation.valueOf(interpolation.toUpperCase());
        float startX = (float) this.getPosition().x;
        float startY = (float) this.getPosition().y;

        for (int i = 0; i < durationTicks; i++) {
            float progress = (float) i / (float) durationTicks;
            float interpX = interp.interpolate(startX, x, progress);
            float interpY = interp.interpolate(startY, y, progress);

            CommonProxy.eventHandler.addExecutable(new RunnableExecutionFork(i, () -> this.setPosition(interpX, interpY)));
        }
    }

    @Override
    public void scaledTo(String interpolation, int durationTicks, float x, float y) {
        Interpolation interp = Interpolation.valueOf(interpolation.toUpperCase());
        float startX = (float) this.getScale().x;
        float startY = (float) this.getScale().y;

        for (int i = 0; i < durationTicks; i++) {
            float progress = (float) i / (float) durationTicks;
            float interpX = interp.interpolate(startX, x, progress);
            float interpY = interp.interpolate(startY, y, progress);

            CommonProxy.eventHandler.addExecutable(new RunnableExecutionFork(i, () -> this.setScale(interpX, interpY)));
        }
    }

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.hud.serializeNBT(), CapabilitiesType.HUD), this.player);
    }
}
