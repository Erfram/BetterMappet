package llamakot.bettermappet.triggers;

import mchorse.mappet.api.triggers.Trigger;

public interface TriggerAccessor {
    Trigger getPlayerMouse();
    Trigger getCommand();
    Trigger getPlayerCamera();
}
