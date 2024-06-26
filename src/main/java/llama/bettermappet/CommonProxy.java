package llama.bettermappet;

import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.capabilities.camera.CameraStorage;
import llama.bettermappet.capabilities.camera.ICamera;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.capabilities.hand.HandStorage;
import llama.bettermappet.capabilities.hand.IHand;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.capabilities.hud.HudStorage;
import llama.bettermappet.capabilities.hud.IHud;
import llama.bettermappet.capabilities.skin.ISkin;
import llama.bettermappet.capabilities.skin.Skin;
import llama.bettermappet.capabilities.skin.SkinStorage;
import llama.bettermappet.events.ClientEventHandler;
import llama.bettermappet.events.EventHandler;
import llama.bettermappet.events.EventTriggerHandler;
import llama.bettermappet.network.Dispatcher;
import mchorse.mappet.Mappet;
import mchorse.mappet.api.ui.components.*;
import mchorse.mappet.api.utils.factory.MapFactory;
import mchorse.mappet.events.RegisterUIComponentEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public static EventTriggerHandler eventTriggerHandler;
    public static EventHandler eventHandler;
    public static ClientEventHandler clientEventHandler;
    public CommonProxy() {
    }
    public void preInit(FMLPreInitializationEvent event) {

        if(FMLCommonHandler.instance().getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(clientEventHandler = new ClientEventHandler());
        }

        MinecraftForge.EVENT_BUS.register(eventTriggerHandler = new EventTriggerHandler());
        MinecraftForge.EVENT_BUS.register(eventHandler = new EventHandler());
        Mappet.EVENT_BUS.register(eventHandler);

        Dispatcher.register();

        CapabilityManager.INSTANCE.register(ICamera.class, new CameraStorage(), Camera::new);
        CapabilityManager.INSTANCE.register(IHand.class, new HandStorage(), Hand::new);
        CapabilityManager.INSTANCE.register(IHud.class, new HudStorage(), Hud::new);
        CapabilityManager.INSTANCE.register(ISkin.class, new SkinStorage(), Skin::new);
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
