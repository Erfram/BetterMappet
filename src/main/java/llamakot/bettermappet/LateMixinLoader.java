package llamakot.bettermappet;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return new ArrayList<String>(Arrays.asList(
        ));
    }
}
