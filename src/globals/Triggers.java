package globals;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author lohnn
 */
public class Triggers
{
    public static TriggerStringThingie number1 = new TriggerStringThingie("leftKey", new KeyTrigger(KeyInput.KEY_A));
    public static Trigger leftTrigger = new KeyTrigger(KeyInput.KEY_A),
            rightTrigger = new KeyTrigger(KeyInput.KEY_D),
            downTrigger = new KeyTrigger(KeyInput.KEY_S),
            upTrigger = new KeyTrigger(KeyInput.KEY_W),
            rotLeftTrigger = new MouseAxisTrigger(MouseInput.AXIS_X, true),
            rotRightTrigger = new MouseAxisTrigger(MouseInput.AXIS_X, false),
            tiltUpTrigger = new MouseAxisTrigger(MouseInput.AXIS_Y, true),
            tiltDownTrigger = new MouseAxisTrigger(MouseInput.AXIS_Y, false),
            zoomOutTrigger = new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true),
            zoomInTrigger = new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false),
            toggleRotate = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT),
            secondButton = new MouseButtonTrigger(MouseInput.BUTTON_RIGHT),
            actionButton = new MouseButtonTrigger(MouseInput.BUTTON_LEFT),
            jump = new KeyTrigger(KeyInput.KEY_SPACE),
            run = new KeyTrigger(KeyInput.KEY_LSHIFT);
}
class TriggerStringThingie
{
    private String name;
    private Trigger trigger;

    public TriggerStringThingie(String name, Trigger trigger)
    {
        this.name = name;
        this.trigger = trigger;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the trigger
     */
    public Trigger getTrigger()
    {
        return trigger;
    }

    /**
     * @param trigger the trigger to set
     */
    public void setTrigger(Trigger trigger)
    {
        this.trigger = trigger;
    }
}