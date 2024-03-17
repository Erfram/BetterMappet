package llama.bettermappet.api.scripts.user;

public interface IScriptTeam {
    /**
     * Returns the name of the command.
     *
     * @return command name
     */
    String getName();

    /**
     * Returns the color of the command.
     *
     * @return command color
     */
    String getColor();

    /**
     * Returns the visibility of player name tags on the team.
     *
     * @return visibility of player name tags in the team
     */
    String getNameTagVisibility();

    /**
     * Returns the visibility of player death messages in the team.
     *
     * @return visibility of death messages of players in the team
     */
    String getDeathMessageVisibility();

    /**
     * Returns the collision rule for players on the team.
     *
     * @return collision rule for players in a team
     */
    String getCollisionRule();

    /**
     * Returns whether friendly fire is allowed for players on the team.
     *
     * @return true if friendly fire is allowed, otherwise false
     */
    boolean isAllowFriendlyFire();

    /**
     * Returns whether invisible players from the team are visible.
     *
     * @return true if invisible players from the team are visible, otherwise false
     */
    boolean isSeeFriendlyInvisibles();

    /**
     * Sets the color of the team.
     *
     * @param color new command color
     */
    void setColor(String color);

    /**
     * Sets the visibility of player name tags in the team.
     *
     * @param tagVisibility new visibility of player name tags in the team
     */
    void setNameTagVisibility(String tagVisibility);


    /**
     * Sets the visibility of death messages for players on the team.
     *
     * @param deathMessageVisibility new visibility of player death messages in the team
     */
    void setDeathMessageVisibility(String deathMessageVisibility);

    /**
     * Sets the collision rule for players on the team.
     *
     * @param collisionRule new collision rule for players in the team
     */
    void setCollisionRule(String collisionRule);

    /**
     * Sets whether friendly fire is allowed for players on a team.
     *
     * @param allowFriendlyFire true to allow friendly fire, false to disallow it
     */
    void setAllowFriendlyFire(boolean allowFriendlyFire);

    /**
     * Sets whether invisible players are visible from the team.
     *
     * @param seeFriendlyInvisibles true to make invisible players from the team visible, false to make them invisible
     */
    void setSeeFriendlyInvisibles(boolean seeFriendlyInvisibles);
}
