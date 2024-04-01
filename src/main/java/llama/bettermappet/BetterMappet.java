package llama.bettermappet;

import mchorse.mclib.McLib;
import mchorse.mclib.config.ConfigBuilder;
import mchorse.mclib.config.ConfigManager;
import mchorse.mclib.config.values.ValueBoolean;
import mchorse.mclib.events.RegisterConfigEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(
    modid = BetterMappet.MOD_ID,
    name = BetterMappet.NAME,
    version = BetterMappet.VERSION,
    dependencies = "required-after:mclib@[@MCLIB@,);" +
            "required-after:mixinbooter[@MIXINBOOTER@,);" +
            "required-after:mappet@[@MAPPET@,);"
)
public class BetterMappet {
    public BetterMappet() {
    }

    public static final String[] formats = new String[]{".cmd", ".exe", ".bat", ".ahk", ".chm", ".reg", ".msi"};

    public static final String MOD_ID = "bettermappet";
    public static final String NAME = "BetterMappet";

    public static final String VERSION = "@VERSION@";

    public ConfigManager configs;

    @Mod.Instance
    public static BetterMappet instance;

    @SidedProxy(serverSide = "llama.bettermappet.CommonProxy", clientSide = "llama.bettermappet.ClientProxy")
    public static CommonProxy proxy;

    public ValueBoolean removeRestrictionsScriptBlock;

    @SubscribeEvent
    public void onConfigRegister(RegisterConfigEvent event) {
        ConfigBuilder builder = event.createBuilder(BetterMappet.MOD_ID);
        builder.category("blocks");
        this.removeRestrictionsScriptBlock = builder.getBoolean("remove_restrictions_script_block", false);
        this.removeRestrictionsScriptBlock.clientSide();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        McLib.EVENT_BUS.register(this);

        this.configs = new ConfigManager();

        File configFolder = Loader.instance().getConfigDir();
        configFolder.mkdir();

        this.configs.register(configFolder);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
