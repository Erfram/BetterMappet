package llamakot.bettermappet;

import llamakot.bettermappet.capabilities.camera.Camera;
import llamakot.bettermappet.capabilities.camera.CameraStorage;
import llamakot.bettermappet.capabilities.camera.ICamera;
import llamakot.bettermappet.capabilities.hand.Hand;
import llamakot.bettermappet.capabilities.hand.HandStorage;
import llamakot.bettermappet.capabilities.hand.IHand;
import llamakot.bettermappet.capabilities.hud.Hud;
import llamakot.bettermappet.capabilities.hud.HudStorage;
import llamakot.bettermappet.capabilities.hud.IHud;
import llamakot.bettermappet.events.EventHandler;
import llamakot.bettermappet.events.EventTriggerHandler;
import llamakot.bettermappet.network.Dispatcher;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public static EventTriggerHandler eventTriggerHandler;
    public static EventHandler eventHandler;
    public CommonProxy() {
    }
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(eventTriggerHandler = new EventTriggerHandler());
        MinecraftForge.EVENT_BUS.register(eventHandler = new EventHandler());
        Dispatcher.register();

        CapabilityManager.INSTANCE.register(ICamera.class, new CameraStorage(), Camera::new);
        CapabilityManager.INSTANCE.register(IHand.class, new HandStorage(), Hand::new);
        CapabilityManager.INSTANCE.register(IHud.class, new HudStorage(), Hud::new);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
