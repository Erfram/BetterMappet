package llama.bettermappet.utils;

import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.skin.Skin;
import llama.bettermappet.client.network.packets.PacketCapability;
import llama.bettermappet.network.Dispatcher;
import mchorse.blockbuster_pack.morphs.ImageMorph;
import mchorse.mclib.utils.resources.FilteredResourceLocation;
import mchorse.mclib.utils.resources.MultiResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class SkinUtils {
    private Skin skin;
    private EntityPlayer player;

    public SkinUtils(EntityPlayer player) {
        this.player = player;
        this.skin = Skin.get(this.player);
    }

    public void setTexture(ImageMorph morph) {
        MultiResourceLocation multiResourceLocation = new MultiResourceLocation();
        ResourceLocation resourceLocation;

        try {
            multiResourceLocation.fromNbt(morph.toNBT().getTag("Texture"));

            BufferedImage combined = new BufferedImage(morph.getWidth(), morph.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = combined.createGraphics();

            for(int i = 0; i < multiResourceLocation.children.size(); i++) {
                FilteredResourceLocation filteredResourceLocation = multiResourceLocation.children.get(0);
                InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(filteredResourceLocation.path).getInputStream();

                g2d.setColor(new Color(filteredResourceLocation.color, true));
                g2d.drawImage(ImageIO.read(inputStream), filteredResourceLocation.shiftX, filteredResourceLocation.shiftY, null);
            }

            g2d.dispose();

            resourceLocation = FMLClientHandler.instance().getClient().getTextureManager()
                    .getDynamicTextureLocation("skin", new DynamicTexture(combined));
        } catch (Exception ignored) {
            resourceLocation = morph.texture;
        }

        this.skin.setTexture(resourceLocation);
        this.sendToCapability();
    }

    public void setType(String type) {
        if(type == null || type.equals("slim") || type.equals("steve")) {
            this.skin.setType(type);
            this.sendToCapability();
        } else {
            throw new IllegalArgumentException("invalid name type");
        }
    }

    private void sendToCapability(){
        Dispatcher.sendToAll(new PacketCapability(this.skin.serializeNBT(), CapabilitiesType.SKIN));
    }
}
