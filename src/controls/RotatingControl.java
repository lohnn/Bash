package controls;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author lohnn
 */
public class RotatingControl extends AbstractControl
{
    private Vector3f speed = new Vector3f(0, 0, 0);

    @Override
    protected void controlUpdate(float tpf)
    {
        spatial.rotate(tpf * speed.x, tpf * speed.y, tpf * speed.z);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp)
    {
    }

    public Control cloneForSpatial(Spatial spatial)
    {
        RotatingControl control = new RotatingControl();
        control.setSpeed(speed);
        control.setSpatial(spatial);
        return control;
    }

    /**
     * @return the speed
     */
    public Vector3f getSpeed()
    {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(Vector3f speed)
    {
        this.speed = speed;
    }
}
