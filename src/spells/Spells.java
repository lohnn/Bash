package spells;

import java.util.ArrayList;
import spells.properties.Properties;

/**
 *
 * @author lohnn
 */
public interface Spells {
    /**
     * Gets the caster
     * @return the caster
     */
    int getCaster();
    /**
     * Gets the name of the spell
     * @return the name
     */
    String getName();
    ArrayList<Properties> getProperties();
}
