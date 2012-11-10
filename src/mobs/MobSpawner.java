package mobs;

import com.jme3.math.Vector3f;

/**
 *
 * @author lohnn
 */
public class MobSpawner
{
    private Vector3f pos;
    private Float radius;
    private Unit type;

    public MobSpawner()
    {
    }

    /**
     * MobSpawner with all variables set
     * @param position Position of spawner
     * @param radius Radius of spawner
     * @param type Unit
     */
    public MobSpawner(Vector3f position, Float radius, Unit type)
    {
        pos = position;
        this.radius = radius;
        this.type = type;
    }

    /**
     * @return the pos
     */
    public Vector3f getPos()
    {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(Vector3f pos)
    {
        this.pos = pos;
    }

    /**
     * @return the radius
     */
    public Float getRadius()
    {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Float radius)
    {
        this.radius = radius;
    }

    /**
     * @return the type
     */
    public Unit getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Unit type)
    {
        this.type = type;
    }
}
