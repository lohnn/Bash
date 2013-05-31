package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import custom.camera.EditorCam;
import globals.Triggers;
import java.util.ArrayList;
import unit.MobSpawner;
import unit.Unit;

/**
 * Main
 * @author Lohnn
 */
public class Editor extends SimpleApplication implements ActionListener
{
    EditorCam characterControl;
//    AmbientLight selectionLight;
    PointLight pl;
    DirectionalLight dl;
    Node characterNode, sceneNode, stuffNode, pointerNode;
    CharacterControl player;
    RigidBodyControl landscape;
    BulletAppState bulletAppState;
    Vector3f spawnPoint;
    Quaternion spawnRotation;
    Geometry pointer;
    ArrayList<Unit> mobTypes = new ArrayList<>();
    Spatial currentCursor;

    @Override
    public void simpleInitApp()
    {
        initModels();
        initPhysics();
        initNodes();
        initMap();
        initLights();
        initCam();
        initPointer();
        registerInput();
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        getMousePos();
        dl.setDirection(cam.getDirection());
    }

    @Override
    public void simpleRender(RenderManager rm)
    {
    }

    private void initPhysics()
    {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
    }

    private void initNodes()
    {
        stuffNode = new Node();
        rootNode.attachChild(stuffNode);
        characterNode = new Node();
        stuffNode.attachChild(characterNode);
        sceneNode = new Node("SceneNode");
        rootNode.attachChild(sceneNode);
        pointerNode = new Node("PointerNode");
        rootNode.attachChild(pointerNode);
    }

    /**
     * Loads map geometry and adds spawners
     */
    private void initMap()
    {
        Spatial map = assetManager.loadModel("Scenes/Fast-Travel Map_3.j3o");
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) map);
        landscape = new RigidBodyControl(sceneShape, 0);
        map.addControl(landscape);
        if(((Node) map).getChild("Spawn") != null) {
            spawnPoint = ((Node) map).getChild("Spawn").getWorldTranslation();
            spawnRotation = ((Node) map).getChild("Spawn").getWorldRotation();
        }else {
            //TODO: Use ray to be on top of the map (we wouldn't want the spawn to be inside a mountain do we?)
            spawnPoint = new Vector3f(0, 2, 0);
            spawnRotation = Quaternion.DIRECTION_Z;
        }
        sceneNode.attachChild(map);

//        mobSpawner = new MobSpawner(((Node)map).getChild("Mobs"), speed, null);
    }
    MobSpawner mobSpawner;

    private void initLights()
    {
//        selectionLight = new AmbientLight();
//        selectionLight.setColor(ColorRGBA.White);

        pl = new PointLight();
        pl.setColor(ColorRGBA.White.multLocal(0.5f));
        rootNode.addLight(pl);

        dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White.multLocal(0.2f));
        rootNode.addLight(dl);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
        fpp.addFilter(bloom);
        viewPort.addProcessor(fpp);
    }

    private void initModels()
    {
        mobTypes.add(0, new Unit(assetManager.loadModel("Blender Models/Monsters/Drop/Drop.j3o")));
//        mobTypes.add(0, assetManager.loadModel("Blender Models/Monsters/Drop/Drop.j3o"));
    }

    private void initCam()
    {
        flyCam.setEnabled(false);

        characterControl = new EditorCam(cam, inputManager, rootNode, rootNode);
        Spatial geom = assetManager.loadModel("Models/Characters/human-male-01.j3o");
        player = geom.getControl(CharacterControl.class);

        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(player);
        bulletAppState.getPhysicsSpace().addAll(geom);

        player.setPhysicsLocation(spawnPoint);
        player.setViewDirection(spawnRotation.mult(player.getViewDirection()));

        characterNode.attachChild(geom);

//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }

    private void initPointer()
    {
        Sphere s = new Sphere(5, 5, 0.5f);
        pointer = new Geometry("Pointer", s);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("Color", ColorRGBA.Red);
        pointer.setMaterial(mat);
//        setCursor(pointer);
        setCursor(mobTypes.get(0).getGeometry());
    }
    Geometry selectedModel;

    /**
     * Returns the mouse position as a Vector3f
     * @return The position of the mouse
     */
    private Vector3f getMousePos()
    {
        Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        stuffNode.collideWith(ray, results);

        
        if(results.size() > 0) { //If it hits a character, spawner or control of some kind
            if(pointerNode.getQuantity() != 0) {
                pointerNode.detachAllChildren(); //"Hides" the cursor when outside map
            }
            CollisionResult closest = results.getClosestCollision();
            updateCursorPosition(closest.getContactPoint());
            if(selectedModel != closest.getGeometry() && selectedModel != null) {
                selectedModel.getMaterial().setColor("GlowColor", ColorRGBA.Black);
            }
            selectedModel = closest.getGeometry();
            selectedModel.getMaterial().setColor("GlowColor", ColorRGBA.Blue);
            return null;
        }else { //If it hits the map
            if(selectedModel != null) {
                selectedModel.getMaterial().setColor("GlowColor", ColorRGBA.Black);
            }
            sceneNode.collideWith(ray, results);
            if(results.size() > 0) {
                if(pointerNode.getQuantity() == 0) {
                    pointerNode.attachChild(currentCursor); //"Reveals" back the cursor when back from outside map
                }
                CollisionResult closest = results.getClosestCollision();
                updateCursorPosition(closest.getContactPoint());
//                System.out.println("Cursor position: "+ closest.getContactPoint());
                return closest.getContactPoint();
            }
        }
        return null;
    }

    public void registerInput()
    {
        String[] mappings = new String[]{"Action"};
        //Moving
        inputManager.addMapping(mappings[0], Triggers.actionButton);
        inputManager.addListener(this, mappings);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
        if(name.equals("Action") && isPressed) {
            if(getMousePos() != null) {
                createSpawner(new MobSpawner(getMousePos(), 3f, mobTypes.get(0)), stuffNode);
//                createObject(mobTypes.get(0).clone(), getMousePos(), stuffNode);
            }
        }
        if(name.equals("Action") && !isPressed) {
        }
    }

    /**
     * Adds an object to the world in the specified node at the specified location
     * @param geom 
     * @param pos 
     * @param node 
     */
    public void createObject(Spatial geom, Vector3f pos, Node node)
    {
        //Add all controls
//        for(int i = 0; i < geom.getNumControls(); i++) {
//            bulletAppState.getPhysicsSpace().add(geom.getControl(i));
//            System.out.println("Added a control");
//        }

        geom.setLocalTranslation(pos);
        node.attachChild(geom);
    }

    public void createSpawner(MobSpawner spawner, Node node)
    {
        Spatial temp;
        temp = spawner.getType().getGeometry().clone();
        temp.setLocalTranslation(spawner.getPos());
        node.attachChild(temp);
    }

    /**
     * Updates the position of the 3D-cursor
     * @param pos 
     */
    public void updateCursorPosition(Vector3f pos)
    {
        if(pos != null && !characterControl.isRotating()) {
            currentCursor.setLocalTranslation(pos);
            pl.setPosition(pos.clone().addLocal(0, 2, 0).clone());
        }
    }

    /**
     * Sets the 3D-cursor
     * @param geom 
     */
    public void setCursor(Spatial geom)
    {
        currentCursor = geom;
        pointerNode.detachAllChildren();
        pointerNode.attachChild(geom);
    }
}
