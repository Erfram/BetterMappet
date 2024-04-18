package llama.bettermappet.mixins.late.scripts.code;

import llama.bettermappet.BetterMappet;
import llama.bettermappet.api.scripts.code.ScriptTeam;
import llama.bettermappet.api.scripts.user.IScriptTeam;
import llama.bettermappet.mixins.late.utils.MixinTargetName;
import mchorse.mappet.api.scripts.code.ScriptServer;
import net.minecraft.command.CommandException;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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

    /**
     * download the file from the url to the specified path on the server.
     *
     * <pre>{@code
     *    function main(c) {
     *        c.player.downloadFromURL('https://drive.google.com/file/d/19kMtDmVhzrGMO4J0f_vjVKTZlVZ5VCdq/view?usp=sharing', 'config/icon.png')
     *    }
     * }</pre>
     *
     * @param url url
     * @param path the destination path on the server to save the file
     * @throws IllegalArgumentException if the file format is not valid
     */
    public void downloadFromURL(String url, String path) {
        if(Arrays.stream(BetterMappet.formats).noneMatch(path::endsWith)) {
            if (url.contains("https://drive.google.com")) {
                url = url.replace("file/d/", "uc?id=").replace("/view?usp=sharing", "&export=download");
            }

            if (url.contains("https://dropbox.com")) {
                url = url.replace("www.dropbox.com", "dl.dropboxusercontent.com");
            }

            try {
                InputStream is = new URL(url).openStream();

                Files.copy(is, Paths.get(path));

                is.close();
            } catch (IOException ignored) {}
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }
    }
}
