package llamakot.bettermappet.mixins;

import llamakot.bettermappet.utils.triggers.TriggerAccessor;
import mchorse.mappet.Mappet;
import mchorse.mappet.api.misc.ServerSettings;
import mchorse.mappet.api.triggers.Trigger;
import mchorse.mappet.events.RegisterServerTriggerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(value = ServerSettings.class, remap = false)
public abstract class MixinServerSettings implements TriggerAccessor {
    @Shadow
    public abstract Trigger register(String key, String alias, Trigger trigger);

    public Trigger playerMouse;
    public Trigger command;
    public Trigger playerCamera;
    public Trigger playerRenderHand;
    public Trigger playerKeyboard;
    public Trigger playerRenderHud;

    public Trigger getPlayerMouse(){
        return this.playerMouse;
    }

    public Trigger getCommand(){
        return this.command;
    }

    public Trigger getPlayerCamera(){
        return this.playerCamera;
    }

    public Trigger getPlayerRenderHand(){
        return this.playerRenderHand;
    }

    public Trigger getPlayerKeyboard(){
        return this.playerKeyboard;
    }
    public Trigger getPlayerRenderHud(){
        return this.playerRenderHud;
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    public void constructor(File file, CallbackInfo ci) {
        this.playerMouse = this.register("player_mouse", "player_mouse", new Trigger());
        this.command = this.register("command", "command", new Trigger());
        this.playerCamera = this.register("player_camera", "player_camera", new Trigger());
        this.playerRenderHand = this.register("player_render_hand", "player_render_hand", new Trigger());
        this.playerKeyboard = this.register("player_keyboard", "player_keyboard", new Trigger());
        this.playerRenderHud = this.register("player_render_hud", "player_render_hud", new Trigger());

        Mappet.EVENT_BUS.post(new RegisterServerTriggerEvent((ServerSettings) (Object) this));
    }
}
