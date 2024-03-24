package llama.bettermappet.mixins.scripts.code;

import llama.bettermappet.api.scripts.code.ScriptTeam;
import llama.bettermappet.api.scripts.user.IScriptTeam;
import llama.bettermappet.mixins.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.ScriptServer;
import mchorse.mappet.api.scripts.user.IScriptFactory;
import net.minecraft.command.CommandException;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ScriptServer.class, remap = false)
@MixinTargetName("mchorse.mappet.api.scripts.user.IScriptServer")
public abstract class MixinScriptServer {
    @Shadow private MinecraftServer server;

    /**
     * Removes the team with the specified name from the game world.
     *
     * @param team name of the team to delete
     * @throws CommandException if a command with the specified name is not found
     */
    public void deleteTeam(String team) throws CommandException {
        Scoreboard scoreboard = this.server.getWorld(0).getScoreboard();
        ScorePlayerTeam playerTeam = scoreboard.getTeam(team);

        if (playerTeam == null) {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] {team});
        } else {
            this.server.worlds[0].getScoreboard().removeTeam(playerTeam);
        }
    }

    /**
     * Creates a new team with the specified name in the game world.
     *
     * @param team name of the new team
     */
    public void createTeam(String team) {
        this.server.worlds[0].getScoreboard().createTeam(team);
    }

    /**
     * Returns an IScriptTeam object representing the command with the specified name.
     *
     * @param team team name
     * @return {@link IScriptTeam} object representing the command
     */
    public IScriptTeam getTeam(String team) {
        return new ScriptTeam(this.server, team);
    }
}
