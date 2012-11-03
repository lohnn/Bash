package controls;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.objects.PhysicsCharacter;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

public class AI extends PhysicsCharacter implements PhysicsControl, Control
{
    protected Spatial spatial;
    protected boolean enabled;
    protected boolean added;
    protected PhysicsSpace space;
    protected Vector3f viewDirection;
    protected boolean useViewDirection;
    protected boolean applyLocal;

    public AI()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public AI(CollisionShape shape, float stepHeight)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public boolean isApplyPhysicsLocal()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setApplyPhysicsLocal(boolean applyPhysicsLocal)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    private Vector3f getSpatialTranslation()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public Control cloneForSpatial(Spatial spatial)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setSpatial(Spatial spatial)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setEnabled(boolean enabled)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public boolean isEnabled()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setViewDirection(Vector3f vec)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public Vector3f getViewDirection()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public boolean isUseViewDirection()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setUseViewDirection(boolean viewDirectionEnabled)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void update(float tpf)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void render(RenderManager rm, ViewPort vp)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void setPhysicsSpace(PhysicsSpace space)
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public PhysicsSpace getPhysicsSpace()
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void write(JmeExporter ex) throws IOException
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }

    public void read(JmeImporter im) throws IOException
    {
        //compiled code
        throw new RuntimeException("Compiled Code");
    }
}
