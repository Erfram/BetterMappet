package llama.bettermappet.events;

import llama.bettermappet.BetterMappet;
import llama.bettermappet.api.ui.components.UIColorPickerComponent;
import llama.bettermappet.api.ui.components.UIScriptEditorComponent;
import llama.bettermappet.capabilities.CapabilitiesType;
import llama.bettermappet.capabilities.camera.Camera;
import llama.bettermappet.capabilities.camera.CameraProvider;
import llama.bettermappet.capabilities.camera.ICamera;
import llama.bettermappet.capabilities.hand.Hand;
import llama.bettermappet.capabilities.hand.HandProvider;
import llama.bettermappet.capabilities.hand.IHand;
import llama.bettermappet.capabilities.hud.Hud;
import llama.bettermappet.capabilities.hud.HudProvider;
import llama.bettermappet.capabilities.hud.IHud;
import llama.bettermappet.network.Dispatcher;
import llama.bettermappet.client.network.packets.PacketCapability;
import llama.bettermappet.network.packets.PacketEvent;
import llama.bettermappet.utils.EventType;
import mchorse.mappet.CommonProxy;
import mchorse.mappet.api.ui.components.UIComponent;
import mchorse.mappet.api.ui.components.UITextareaComponent;
import mchorse.mappet.api.utils.factory.MapFactory;
import mchorse.mappet.events.RegisterUIComponentEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.lwjgl.input.Keyboard;


public class EventHandler {
    public static final ResourceLocation CAMERA = new ResourceLocation(BetterMappet.MOD_ID, "camera");
    public static final ResourceLocation HAND = new ResourceLocation(BetterMappet.MOD_ID, "hand");
    public static final ResourceLocation HUD = new ResourceLocation(BetterMappet.MOD_ID, "hud");

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(CAMERA, new CameraProvider());
            event.addCapability(HAND, new HandProvider());
            event.addCapability(HUD, new HudProvider());
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;

        final ICamera camera = Camera.get(player);
        final IHand hand = Hand.get(player);
        final IHud hud = Hud.get(player);

        Dispatcher.sendTo(new PacketCapability(camera.serializeNBT(), CapabilitiesType.CAMERA), (EntityPlayerMP) player);
        Dispatcher.sendTo(new PacketCapability(hand.serializeNBT(), CapabilitiesType.HAND), (EntityPlayerMP) player);
        Dispatcher.sendTo(new PacketCapability(hud.serializeNBT(), CapabilitiesType.HUD), (EntityPlayerMP) player);
    }

    @SubscribeEvent
    public void mouseEvent(MouseEvent event) {
        NBTTagCompound data = new NBTTagCompound();

        data.setInteger("button", event.getButton());
        data.setInteger("x", event.getX());
        data.setInteger("y", event.getY());
        data.setInteger("dwheel", event.getDwheel());
        data.setInteger("dx", event.getDx());
        data.setInteger("dy", event.getDy());
        data.setBoolean("buttonState", event.isButtonstate());

        Dispatcher.sendToServer(new PacketEvent(EventType.MOUSE, data));
    }

    @SubscribeEvent
    public void keyInputEvent(InputEvent.KeyInputEvent event) {
        NBTTagCompound data = new NBTTagCompound();

        data.setInteger("keyCode", Keyboard.getEventKey());
        data.setBoolean("keyState", Keyboard.getEventKeyState());

        Dispatcher.sendToServer(new PacketEvent(EventType.KEYBOARD, data));
    }

    @SubscribeEvent
    public void onRegisterUIComponentEvent(RegisterUIComponentEvent event) {
        ((MapFactory<UIComponent>)CommonProxy.getUiComponents())
                .register("scriptEditor", UIScriptEditorComponent.class, 0xffffff)
                .register("colorPicker", UIColorPickerComponent.class, 0xffffff);
    }
}
