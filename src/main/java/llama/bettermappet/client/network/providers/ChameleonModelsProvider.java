package llama.bettermappet.client.network.providers;

import mchorse.chameleon.Chameleon;

public class ChameleonModelsProvider implements IClientDataProvider{
    @Override
    public void setData() {
        Chameleon.proxy.reloadModels();
    }
}