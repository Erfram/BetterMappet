package llama.bettermappet.capabilities.hud;

import llama.bettermappet.utils.ScriptVectorAngle;
import mchorse.mappet.api.scripts.user.data.ScriptVector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class Hud implements IHud{
    private EntityPlayer player;
    public Map<String, NBTTagCompound> HUDs = this.createHUDs();

    public String name;

    public static Hud get(EntityPlayer player)
    {
        IHud hudCapability = player == null ? null : player.getCapability(HudProvider.HUD, null);

        if (hudCapability instanceof Hud)
        {
            Hud profile = (Hud) hudCapability;
            profile.player = player;

            return profile;
        }

        return null;
    }

    private Map<String, NBTTagCompound> createHUDs(){
        Map<String, NBTTagCompound> map = new HashMap<>();

        String[] huds = new String[]{"ALL", "HELMET", "PORTAL", "CROSSHAIRS", "BOSSHEALTH", "BOSSINFO", "ARMOR", "HEALTH", "FOOD", "AIR", "HOTBAR", "EXPERIENCE", "TEXT", "HEALTHMOUNT", "JUMPBAR", "CHAT", "PLAYER_LIST", "DEBUG", "POTION_ICONS", "SUBTITLES", "FPS_GRAPH", "VIGNETTE"};

        for(String hud : huds) {
            map.put(hud, this.createDefaultNBT());
        }

        return map;
    }

    private NBTTagCompound createDefaultNBT(){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        NBTTagCompound scale = new NBTTagCompound();
        scale.setDouble("x", 1);
        scale.setDouble("y", 1);

        NBTTagCompound position = new NBTTagCompound();
        position.setDouble("x", 0);
        position.setDouble("y", 0);

        NBTTagCompound rotate = new NBTTagCompound();
        rotate.setDouble("angle", 0);
        rotate.setDouble("x", 0);
        rotate.setDouble("y", 0);
        rotate.setDouble("z", 0);

        nbtTagCompound.setTag("scale", scale);
        nbtTagCompound.setTag("position", position);
        nbtTagCompound.setTag("rotate", rotate);

        nbtTagCompound.setBoolean("canceled", false);

        return nbtTagCompound;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public ScriptVector getScale(){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound scale = hud.getCompoundTag("scale");
        return new ScriptVector(
                scale.getDouble("x"),
                scale.getDouble("y"),
                0);
    }

    @Override
    public void setScale(double x, double y){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound scale = hud.getCompoundTag("scale");

        scale.setDouble("x", x);
        scale.setDouble("y", y);
    }

    @Override
    public ScriptVector getPosition(){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound pos = hud.getCompoundTag("position");

        return new ScriptVector(
                pos.getDouble("x"),
                pos.getDouble("y"),
                0);
    }

    @Override
    public void setPosition(double x, double y){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound pos = hud.getCompoundTag("position");

        pos.setDouble("x", x);
        pos.setDouble("y", y);
    }

    @Override
    public ScriptVectorAngle getRotate(){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound rotate = hud.getCompoundTag("rotate");

        return new ScriptVectorAngle(
                rotate.getDouble("angle"),
                rotate.getDouble("x"),
                rotate.getDouble("y"),
                rotate.getDouble("z")
        );
    }

    @Override
    public void setRotate(double angle, double x, double y, double z){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        NBTTagCompound rotate = hud.getCompoundTag("rotate");

        rotate.setDouble("angle", angle);
        rotate.setDouble("x", x);
        rotate.setDouble("y", y);
        rotate.setDouble("z", z);
    }

    @Override
    public boolean isCanceled(){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        return hud.getBoolean("canceled");
    }

    @Override
    public void setCanceled(boolean canceled){
        NBTTagCompound hud = this.HUDs.get(this.getName());

        hud.setBoolean("canceled", canceled);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        this.HUDs.keySet().forEach((key) -> tag.setTag(key, this.HUDs.get(key)));
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        this.HUDs.keySet().forEach((key) -> {
            if(tag.hasKey(key)) {
                this.HUDs.put(key, tag.getCompoundTag(key));
            }
        });
    }
}