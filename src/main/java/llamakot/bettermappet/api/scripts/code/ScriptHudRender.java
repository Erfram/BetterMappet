package llamakot.bettermappet.api.scripts.code;

import llamakot.bettermappet.api.scripts.user.IScriptHudRender;
import llamakot.bettermappet.capabilities.CapabilitiesType;
import llamakot.bettermappet.capabilities.hud.Hud;
import llamakot.bettermappet.network.Dispatcher;
import llamakot.bettermappet.network.packets.PacketCapability;
import llamakot.bettermappet.utils.ScriptVectorAngle;
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

    private void sendToCapability(){
        Dispatcher.sendTo(new PacketCapability(this.hud.serializeNBT(), CapabilitiesType.HUD), this.player);
    }
}
