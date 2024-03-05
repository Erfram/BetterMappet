package llamakot.bettermappet.api.scripts.code;

import llamakot.bettermappet.api.scripts.user.IScriptCamera;
import llamakot.bettermappet.capabilities.CapabilitiesType;
import llamakot.bettermappet.capabilities.camera.Camera;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketCapability;
import llamakot.bettermappet.utils.ScriptVectorAngle;
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
    public ScriptVector getScale() {
        return camera.getScale();
    }

    @Override
    public void setScale(float x, float y, float z) {
        camera.setScale(x, y, z);

        this.sendToCapability();
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
