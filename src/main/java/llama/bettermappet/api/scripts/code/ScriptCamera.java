package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptCamera;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayerMP;

public class ScriptCamera implements IScriptCamera {
    private EntityPlayerMP player;
    private Camera camera;
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
        camera.setRotate(angle, x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetRotate() {
        this.setRotate(0, 0, 0, 0);
    }

    @Override
    public ScriptVector getScale() {
        return camera.getScale();
    }

    @Override
    public void setScale(float x, float y, float z) {
        camera.setScale(x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetScale() {

    }

    @Override
    public ScriptVector getPosition() {
        return camera.getPosition();
    }

    @Override
    public void setPosition(float x, float y, float z) {
        camera.setPosition(x, y, z);

        this.sendToCapability();
    }

    @Override
    public void resetPosition() {
        this.setPosition(0,0, 0);
    }

    @Override
    public void setCanceled(boolean canceled) {
        camera.setCanceled(canceled);

        this.sendToCapability();
    }

    @Override
    public boolean isCanceled() {
        return camera.isCanceled();
    }

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.camera.serializeNBT(), CapabilitiesType.CAMERA), this.player);
    }
}
