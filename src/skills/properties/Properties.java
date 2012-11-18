package skills.properties;

import unit.Unit;

/**
 *
 * @author lohnn
 */
public interface Properties {
    /**
     * Gets the effect of the property
     *
     * @return the effect
     */
    int getEffect(); //TODO: add effects

    /**
     * Gets the reciever of the property
     *
     * @return the recieving unit
     */
    Unit getReciever();
}
