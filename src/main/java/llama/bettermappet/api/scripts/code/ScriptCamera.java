package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptCamera;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import mchorse.mappet.CommonProxy;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import mchorse.mappet.utils.RunnableExecutionFork;
import mchorse.mclib.utils.Interpolation;
import net.minecraft.entity.player.EntityPlayerMP;

public class ScriptCamera implements IScriptCamera {
    private final EntityPlayerMP player;
    private final Camera camera;
    public ScriptCamera(EntityPlayerMP player) {
        this.player = player;
        this.camera = Camera.get(this.player);
    }

    @Override
    public ScriptVectorAngle getRotate() {
        return camera.getRotate();
    }

    @Override
    public void setRotate(float angle, float x, float y, float z) {
        this.camera.setRotate(angle, x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetRotate() {
        this.setRotate(0, 0, 0, 0);
    }

    @Override
    public ScriptVector getScale() {
        return this.camera.getScale();
    }

    @Override
    public void setScale(float x, float y, float z) {
        this.camera.setScale(x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetScale() {

    }

    @Override
    public ScriptVector getPosition() {
        return this.camera.getPosition();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.camera.setPosition(x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetPosition() {
        this.setPosition(0,0, 0);
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.camera.setCanceled(canceled);

        this.sendToCapability();
    }

    @Override
    public boolean isCanceled() {
        return this.camera.isCanceled();
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
    public void scaledTo(String interpolation, int durationTicks, float x, float y, float z) {
        Interpolation interp = Interpolation.valueOf(interpolation.toUpperCase());
        float startX = (float) this.getScale().x;
        float startY = (float) this.getScale().y;
        float startZ = (float) this.getScale().z;

        for (int i = 0; i < durationTicks; i++) {
            float progress = (float) i / (float) durationTicks;
            float interpX = interp.interpolate(startX, x, progress);
            float interpY = interp.interpolate(startY, y, progress);
            float interpZ = interp.interpolate(startZ, z, progress);

            CommonProxy.eventHandler.addExecutable(new RunnableExecutionFork(i, () -> this.setScale(interpX, interpY, interpZ)));
        }
    }

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.camera.serializeNBT(), CapabilitiesType.CAMERA), this.player);
    }
}
