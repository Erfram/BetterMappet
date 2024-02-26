package llamakot.bettermappet;

import llamakot.bettermappet.events.EventTriggerHandler;
import llamakot.bettermappet.network.Dispatcher;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public static EventTriggerHandler eventTriggerHandler;
    public CommonProxy() {
    }
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(eventTriggerHandler = new EventTriggerHandler());
        Dispatcher.register();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
