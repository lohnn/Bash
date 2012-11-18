package unit;

import com.jme3.scene.Spatial;

/**
 *
 * @author lohnn
 */
//TODO: see what I can do to make this an interface? or implement one?
public class Unit
{
    private Spatial geometry;

    public Unit(Spatial geo)
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
