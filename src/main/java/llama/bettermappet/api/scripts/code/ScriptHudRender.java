package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptHudRender;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayerMP;

public class ScriptHudRender implements IScriptHudRender {
    private final EntityPlayerMP player;
    private final Hud hud;
    private String name;
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

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.hud.serializeNBT(), CapabilitiesType.HUD), this.player);
    }
}
