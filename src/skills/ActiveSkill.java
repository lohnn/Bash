package skills;

import java.util.ArrayList;
import spells.properties.Properties;

/**
 * This is spells directed to other players in a battle, doens't have to be
 * attacking spells (can be healing and stuff)
 *
 * @author lohnn
 */
public abstract class ActiveSkill extends Skill {
    private int caster;
    private String name;
    private ArrayList<Properties> properties;

    @Override
    public int getCaster() {
	return caster;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public ArrayList<Properties> getProperties() {
	return properties;
    }

    /**
     * This function casts the spell
     */
    public void cast() {
	effect();
    }

    /**
     * The effect of the spell TODO: need to find a way for this to not having
     * to be public
     */
    abstract void effect();
}
