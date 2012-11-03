package custom.camera;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.system.lwjgl.LwjglTimer;
import globals.Triggers;
import java.io.IOException;

/**
 * The camera for the game
 *
 * @author lohnn
 */
public class CharControl implements Control, AnalogListener, ActionListener
{
    private Node camNode, rootNode;
    private Camera cam;
    private boolean rotating;
    private CustomChaseCamera chaseCam;
    private InputManager inputManager;
    private int zoomSpeed = 15, rotationSpeed = 15; //max speeds
    private float acceleration = 10, moveSpeed = 4, toMoveSpeed, kickoffTime = 0.1f; //moveSpeed = km/h; kickoffSpeed = the time to stand still while changing speed (in ms)
    private CharacterControl control;
    private boolean left = false, right = false, forward = false, backward = false, lockCamera = false, isKickingOff;
    private LwjglTimer kickoffTimer = new LwjglTimer();
    private Vector3f spawnPoint;
    private Quaternion spawnRotation;

    /**
     * initalization
     * 
     * @param cam camera to set
     * @param inputManager the input manager
     * @param target rootNode
     * @param rootNode rootNode
     */
    public CharControl(Camera cam, InputManager inputManager, Spatial target, Node rootNode)
    {
        this.cam = cam;
        camNode = new Node();
        camNode.setLocalTranslation(Vector3f.ZERO);
        this.rootNode = rootNode;
        rootNode.attachChild(camNode);
        chaseCam = new CustomChaseCamera(cam, camNode, inputManager);
        chaseCam.setDragToRotate(true);
        chaseCam.setToggleRotationTrigger(Triggers.toggleRotate);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setInvertHorizontalAxis(false);
        chaseCam.setMinVerticalRotation(0.15f);
        chaseCam.setMaxVerticalRotation(FastMath.HALF_PI - 0.1f);
        chaseCam.setSmoothMotion(false);
        chaseCam.setChasingSensitivity(acceleration);
        chaseCam.setZoomInTrigger(Triggers.zoomInTrigger);
        chaseCam.setZoomOutTrigger(Triggers.zoomOutTrigger);
        chaseCam.setMinDistance(5);
        chaseCam.setMaxDistance(15);
        chaseCam.setDefaultDistance(10);
        chaseCam.setTrailingEnabled(false);
        chaseCam.setRotationSensitivity(rotationSpeed);
        target.addControl(this);
        registerInput(inputManager);
    }

    /**
     * Registers all input to the charControl
     * @param inputManager 
     */
    public void registerInput(InputManager inputManager)
    {
        this.inputManager = inputManager;

        String[] mappings = new String[]{"right", "left", "forward", "backward", "run"};
        //Moving
        inputManager.addMapping(mappings[0], Triggers.rightTrigger);
        inputManager.addMapping(mappings[1], Triggers.leftTrigger);
        inputManager.addMapping(mappings[2], Triggers.upTrigger);
        inputManager.addMapping(mappings[3], Triggers.downTrigger);
        inputManager.addMapping(mappings[4], Triggers.run);
        inputManager.addMapping("rotate", Triggers.toggleRotate);
        inputManager.addListener(this, "rotate");
        inputManager.addMapping("Jump", Triggers.jump);
        inputManager.addListener(this, "Jump");
        inputManager.addMapping("Reset", new com.jme3.input.controls.KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(this, "Reset");

        inputManager.addListener(this, mappings);
    }

    /**
     * Called whenever something is pressed (no need to manually call)
     */
    public void onAction(String name, boolean isPressed, float tpf)
    {
        if(name.equals("left")) {
            left = isPressed;
        }
        if(name.equals("right")) {
            right = isPressed;
        }
        if(name.equals("forward")) {
            forward = isPressed;
        }
        if(name.equals("backward")) {
            backward = isPressed;
        }
        if(name.equals("Jump")) {
            control.jump();
        }
        if(name.equals("run")) {
            if(isPressed) {
                setMoveSpeed(16);
            }else {
                setMoveSpeed(4);
            }
        }
        if(name.equals("Reset") && isPressed) {
//            respawn();
            System.out.println(chaseCam.getHorizontalRotation());
        }
        if(name.equals("rotate")) {
            if(isPressed) {
                setIsRotating(true);
            }else {
                setIsRotating(false);
            }
        }
    }
    Vector3f walkdir = new Vector3f();
    Vector3f viewdir = new Vector3f();

    /**
     * Called whenever something is pressed (no need to manually call)
     */
    public void onAnalog(String name, float value, float tpf)
    {
    }

    /**
     * Update code (automaticly called, don't call manually)
     * @param tpf time per frame
     */
    public void update(float tpf)
    {
        if(isKickingOff) {
            if(kickoffTimer.getTimeInSeconds() >= kickoffTime) {
                moveSpeed = toMoveSpeed;
                isKickingOff = false;
            }
        }
        Vector3f camDir = cam.getDirection().clone().multLocal(0.8f);
        camDir = camDir.setY(0.0f); //Ignore up and down when moving forward
        camDir = camDir.normalizeLocal();
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.5f);

        //If camera is moving
        walkdir = new Vector3f(0, 0, 0);
        if(left) {
            walkdir.addLocal(camLeft).normalizeLocal();
        }
        if(right) {
            walkdir.addLocal(camLeft.negate()).normalizeLocal();
        }
        if(forward) {
            walkdir.addLocal(camDir).normalizeLocal();
        }
        if(backward) {
            walkdir.addLocal(camDir.negate()).normalizeLocal().multLocal(0.5f);
        }
        if(walkdir.x != 0 || walkdir.y != 0 || walkdir.z != 0) {
            control.setViewDirection(camDir); //TODO: set smooth turn to look forward, not instantly
        }
        setDirection(walkdir);
    }

    /**
     * set the node and control for the camera to follow
     * @param follow
     * @param charControl 
     */
    public void setFollow(Node follow, CharacterControl charControl)
    {
        camNode.detachAllChildren();
        camNode.setLocalTranslation(follow.getLocalTranslation());
        camNode.attachChild(follow);
        follow.setLocalTranslation(Vector3f.ZERO);
        control = charControl;
    }

    /**
     * set the node for the camera to follow
     * @param follow node to follow
     */
    public void setFollow(Node follow)
    {
        camNode.detachAllChildren();
        camNode.setLocalTranslation(follow.getLocalTranslation());
        camNode.attachChild(follow);
        follow.setLocalTranslation(Vector3f.ZERO);
    }

    /**
     * set the control for the camera to follow
     * @param charControl control
     */
    public void setCharacterControl(CharacterControl charControl)
    {
        control = charControl;
    }

    /**
     * set direction to move to (called from update)
     * @param direction direction to move towards
     */
    private void setDirection(Vector3f direction)
    {
        control.setWalkDirection(direction.divideLocal(60).multLocal(getMoveSpeed()));
        camNode.setLocalTranslation(control.getPhysicsLocation());
    }

    /**
     * sets the Y locaition
     * @param Y 
     */
    public void setYTranslation(float Y)
    {
        control.setPhysicsLocation(new Vector3f(control.getPhysicsLocation().x, Y, control.getPhysicsLocation().z));
    }

    public void setTranslation(Vector3f pos)
    {
        control.setPhysicsLocation(pos);
    }

    public void setRotation(Quaternion rot)
    {
        control.setViewDirection(rot.mult(control.getViewDirection()));
        chaseCam.setDefaultHorizontalRotation(rot.getY());
        //TODO: Rotate camera
    }

    public void setSpawnPoint(Vector3f pos, Quaternion rot)
    {
        spawnPoint = pos;
        spawnRotation = rot;
    }

    public void respawn()
    {
        setTranslation(spawnPoint);
        setRotation(spawnRotation);
    }

    public Vector3f getTargetTranslation()
    {
        return camNode.getLocalTranslation();
    }

    public Vector3f getPhysicslLocation()
    {
        return control.getPhysicsLocation();
    }

    public Control cloneForSpatial(Spatial spatial)
    {
        CharControl other = new CharControl(cam, inputManager, spatial, rootNode);
        return other;
    }

    public boolean isEnabled()
    {
        return true;
    }

    public void setSpatial(Spatial spatial)
    {
    }

    public void setEnabled(boolean enabled)
    {
    }

    public void render(RenderManager rm, ViewPort vp)
    {
    }

    public void write(JmeExporter ex) throws IOException
    {
    }

    public void read(JmeImporter im) throws IOException
    {
    }

    public void setInvertScrolling(boolean b)
    {
        if(b) {
            chaseCam.setZoomInTrigger(Triggers.zoomOutTrigger);
            chaseCam.setZoomOutTrigger(Triggers.zoomInTrigger);
        }else {
            chaseCam.setZoomInTrigger(Triggers.zoomInTrigger);
            chaseCam.setZoomOutTrigger(Triggers.zoomOutTrigger);
        }
    }

    /**
     * @return the rotating
     */
    public boolean isRotating()
    {
        return rotating;
    }

    /**
     * @param rotating the rotating to set
     */
    public void setIsRotating(boolean rotating)
    {
        this.rotating = rotating;
    }

    /**
     * @return the moveSpeed
     */
    public float getMoveSpeed()
    {
        return moveSpeed;
    }

    /**
     * Sets the movement speed and multiplies it with potential speed boosts
     * @param moveSpeed the moveSpeed to set
     */
    public void setMoveSpeed(float moveSpeed)
    {
        //TODO: make failsafe, please help with that Considerate
        isKickingOff = true;
        kickoffTimer.reset();
        toMoveSpeed = moveSpeed;
    }

    public void resetMoveSpeed()
    {
        moveSpeed = 4;
    }
}
