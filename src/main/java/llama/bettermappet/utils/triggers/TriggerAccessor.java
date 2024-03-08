package llama.bettermappet.utils.triggers;

import mchorse.mappet.api.triggers.Trigger;

public interface TriggerAccessor {
    Trigger getPlayerMouse();
    Trigger getCommand();
    Trigger getPlayerCamera();
    Trigger getPlayerRenderHand();
    Trigger getPlayerKeyboard();
    Trigger getPlayerRenderHud();
}
