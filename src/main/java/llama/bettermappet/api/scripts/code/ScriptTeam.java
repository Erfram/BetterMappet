package llama.bettermappet.api.scripts.code;

import llama.bettermappet.api.scripts.user.IScriptTeam;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextFormatting;

public class ScriptTeam implements IScriptTeam {
    private EntityPlayerMP player;
    private ScorePlayerTeam scorePlayerTeam;

    public ScriptTeam(EntityPlayerMP player) {
        this.player = player;
        this.scorePlayerTeam = player.getWorldScoreboard().getTeam(player.getTeam().getName());
    }

    public ScriptTeam(MinecraftServer server, String team) {
        this.player = null;
        this.scorePlayerTeam = server.getWorld(0).getScoreboard().getTeam(team);
    }

    @Override
    public String getName() {
        return this.scorePlayerTeam.getName();
    }

    @Override
    public String getColor() {
        return this.scorePlayerTeam.getColor().name();
    }

    @Override
    public String getNameTagVisibility() {
        return this.scorePlayerTeam.getNameTagVisibility().name();
    }

    @Override
    public String getDeathMessageVisibility() {
        return this.scorePlayerTeam.getDeathMessageVisibility().name();
    }

    @Override
    public String getCollisionRule() {
        return this.scorePlayerTeam.getCollisionRule().name();
    }

    @Override
    public boolean isAllowFriendlyFire() {
        return this.scorePlayerTeam.getAllowFriendlyFire();
    }

    @Override
    public boolean isSeeFriendlyInvisibles() {
        return this.scorePlayerTeam.getSeeFriendlyInvisiblesEnabled();
    }

    @Override
    public void setColor(String color) {
        this.scorePlayerTeam.setColor(TextFormatting.valueOf(color.toUpperCase()));
    }

    @Override
    public void setNameTagVisibility(String tagVisibility) {
        this.scorePlayerTeam.setNameTagVisibility(Team.EnumVisible.valueOf(tagVisibility.toUpperCase()));
    }

    @Override
    public void setDeathMessageVisibility(String deathMessageVisibility) {
        this.scorePlayerTeam.setDeathMessageVisibility(Team.EnumVisible.valueOf(deathMessageVisibility.toUpperCase()));
    }

    @Override
    public void setCollisionRule(String collisionRule) {
        this.scorePlayerTeam.setCollisionRule(Team.CollisionRule.valueOf(collisionRule.toUpperCase()));
    }

    @Override
    public void setAllowFriendlyFire(boolean allowFriendlyFire) {
        this.scorePlayerTeam.setAllowFriendlyFire(allowFriendlyFire);
    }

    @Override
    public void setSeeFriendlyInvisibles(boolean seeFriendlyInvisibles) {
        this.scorePlayerTeam.setSeeFriendlyInvisiblesEnabled(seeFriendlyInvisibles);
    }
}
