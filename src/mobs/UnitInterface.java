package mobs;

import com.jme3.scene.Spatial;

/**
 *
 * @author lohnn
 */
//TODO: see what I can do to make this an interface? or implement one?
public interface UnitInterface
{
    /**
     * @return the geometry
     */
    public Spatial getGeometry();

    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(Spatial geometry);
}
