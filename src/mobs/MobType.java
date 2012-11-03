package mobs;

import com.jme3.scene.Spatial;

/**
 *
 * @author lohnn
 */
public class MobType
{
    private Spatial geometry;

    public MobType(Spatial geo)
    {
        geometry = geo;
    }

    /**
     * @return the geometry
     */
    public Spatial getGeometry()
    {
        return geometry;
    }

    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(Spatial geometry)
    {
        this.geometry = geometry;
    }
}
