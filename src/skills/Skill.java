package skills;

import java.util.ArrayList;
import skills.properties.Properties;
import unit.Unit;

/**
 *
 * @author lohnn
 */
public abstract class Skill implements Cloneable {
    protected ArrayList<Unit> targets;
    protected Unit caster;
    protected ArrayList<Properties> properties = new ArrayList<>();
    protected String name;

    /**
     * Gets the caster of the skill
     *
     * @return the caster
     */
    public Unit getCaster() {
	return caster;
    }

    /**
     * Gets the name of the skill
     */
    public String getName() {
	return name;
    }

    /**
     * Gets the properties of the skill
     *
     * @return the properties
     */
    public ArrayList<Properties> getProperties() {
	return properties;
    }

    /**
     * Sets everything up, like name and properties
     */
    public abstract void setup();
}
