package skills;

import java.util.ArrayList;
import spells.properties.Properties;

/**
 *
 * @author lohnn
 */
public abstract class Skill implements Cloneable{
    abstract void temp();
    /**
     * Gets the caster
     * @return the caster
     */
    abstract int getCaster();
    /**
     * Gets the name of the spell
     * @return the name
     */
    abstract String getName();
    abstract ArrayList<Properties> getProperties();
}
