package llamakot.bettermappet;

import llamakot.bettermappet.network.Dispatcher;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
    public CommonProxy() {
    }
    public void preInit(FMLPreInitializationEvent event) {
        Dispatcher.register();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
