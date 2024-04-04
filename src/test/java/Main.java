import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class Main {
    public static void main(String[] args) {
        System.out.println(Loader.instance().getConfigDir());
    }
}
