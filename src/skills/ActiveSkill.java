package skills;

/**
 * This is spells directed to other players in a battle, doens't have to be
 * attacking spells (can be healing and stuff)
 *
 * @author lohnn
 */
public abstract class ActiveSkill extends Skill {
    /**
     * This function casts the spell
     */
    public void cast() {
	effect();
    }

    /**
     * The effect of the spell (what effect it has when it's casted)
     * Essentially it lists the properties and returns it
     */
    abstract void effect();
}
