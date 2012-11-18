package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import custom.camera.CharControl;
import unit.MobSpawner;

/**
 * Main
 * @author Lohnn
 */
public class Game extends SimpleApplication
{
    CharControl characterControl;
//    AmbientLight al;
    PointLight pl;
//    DirectionalLight dl;
    Node characterNode;
    CharacterControl player;
    RigidBodyControl landscape;
    BulletAppState bulletAppState;
    Vector3f spawnPoint;
    Quaternion spawnRotation;

    @Override
    public void simpleInitApp()
    {
        initPhysics();
        initNodes();
        initMap();
        initLights();
        initCharacter();
    }

    @Override
    public void simpleUpdate(float tpf)
    {
//        dl.setDirection(cam.getDirection());
        pl.setPosition(characterControl.getPhysicslLocation().addLocal(0, 2, 0));
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
        characterNode = new Node();
        rootNode.attachChild(characterNode);
    }

    /**
     * Loads map geometry and adds spawners
     */
    private void initMap()
    {
        Spatial map = assetManager.loadModel("Scenes/Map2.j3o");
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) map);
        landscape = new RigidBodyControl(sceneShape, 0);
        map.addControl(landscape);
        if(((Node) map).getChild("Spawn") != null) {
            spawnPoint = ((Node) map).getChild("Spawn").getWorldTranslation();
            spawnRotation = ((Node) map).getChild("Spawn").getWorldRotation();
        }else {
            spawnPoint = new Vector3f(0, 2, 0);
            spawnRotation = Quaternion.DIRECTION_Z;
        }
        rootNode.attachChild(map);
        
//        mobSpawner = new MobSpawner(((Node)map).getChild("Mobs"), speed, null);
    }
    MobSpawner mobSpawner;

    private void initLights()
    {
//        al = new AmbientLight();
//        al.setColor(ColorRGBA.White.multLocal(0.8f));
//        rootNode.addLight(al);

        pl = new PointLight();
        pl.setColor(ColorRGBA.White.multLocal(0.5f));
        rootNode.addLight(pl);

//        dl = new DirectionalLight();
//        dl.setColor(ColorRGBA.White.multLocal(0.2f));
//        rootNode.addLight(dl);
    }

    private void initCharacter()
    {
        flyCam.setEnabled(false);

        characterControl = new CharControl(cam, inputManager, rootNode, rootNode);
        Spatial geom = assetManager.loadModel("Models/Characters/human-male-01.j3o");
        player = geom.getControl(CharacterControl.class);

        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(player);
        bulletAppState.getPhysicsSpace().addAll(geom);

        characterNode.attachChild(geom);
        characterControl.setFollow(characterNode, player);
        characterControl.setSpawnPoint(spawnPoint, spawnRotation);
        characterControl.respawn();

//        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
    }
}
