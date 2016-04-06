package parohyapp.mario.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import parohyapp.mario.sprites.animated.Climber;
import parohyapp.mario.sprites.animated.Creep;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;
import parohyapp.mario.sprites.standing.Ground;

/**
 * Created by tomas on 3/25/2016.
 */
public class WorldContactListener implements ContactListener{
    private static final String TAG = "WorldContactListener";

    private WorldManager worldManager;

    public WorldContactListener(WorldManager manager){
        worldManager = manager;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();

        if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof InteractiveSpriteEntity && !(object.getUserData() instanceof Creep)){
                ((InteractiveSpriteEntity)object.getUserData()).onColide();
            }
        }

        if((A.getUserData() instanceof String && A.getUserData().equals("foot")) || (B.getUserData() instanceof String && B.getUserData().equals("foot"))){
            Fixture foot = A.getUserData() instanceof String ? A : B;
            Fixture object = A == foot ? B : A;

            if(object.getUserData() instanceof Ground || object.getUserData() instanceof Creep){
                Gdx.app.log(TAG,"Touch with foot");
                worldManager.getClimber().setOnGround(true);
            }
        }
        else if(A.getUserData() instanceof Fixture || B.getUserData() instanceof Fixture){
            Fixture headSensor = A.getUserData() instanceof Fixture ? A : B;
            Fixture object = A == headSensor ? B : A;

            if(object.getUserData() instanceof Climber){
                Fixture creepFix = (Fixture) headSensor.getUserData();
                ((Creep)creepFix.getUserData()).setDying(true);
                worldManager.getGameMaster().setLives(1); //TODO takes life on hit
                worldManager.getGameMaster().setScore(7);
            }
        }

        else if(A.getUserData() instanceof Creep || B.getUserData() instanceof Creep){
            Fixture creep = A.getUserData() instanceof Creep ? A : B;
            Fixture object = A == creep ? B : A;

            if(!((Creep)creep.getUserData()).isDying()){
                if(object.getUserData() instanceof String && ((String)object.getUserData()).equals("mark")){
                    ((Creep)creep.getUserData()).onColide();
                }

                else if(object.getUserData() instanceof Climber){
                    Gdx.app.log(TAG,"taking life");
                    worldManager.getClimber().onColide();
                }
            }
        }

        /*if((A.getUserData() instanceof String && A.getUserData().equals("foot")) || (B.getUserData() instanceof String && B.getUserData().equals("foot"))){
            //Gdx.app.log(TAG,"A: "+((Entity)A.getUserData()).getEntX()+" "+((Entity)A.getUserData()).getEntY());
            //Gdx.app.log(TAG,"B: "+((Entity)B.getUserData()).getEntX()+" "+((Entity)B.getUserData()).getEntY());

            Fixture playerFoot = A.getUserData() instanceof String ? A : B;
            Fixture object = A == playerFoot ? B : A;

            if(object.getUserData() instanceof Ground){
                worldManager.getClimber().setOnGround(true);
            }
            else if(object.getUserData() instanceof Creep){
                worldManager.getClimber().jump();
                ((Creep)object.getUserData()).setDying(true);

                Gdx.app.log(TAG, "AT FOOT-> A: " + A.getUserData() + " B: " + B.getUserData());
            }
        }



        else if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof InteractiveSpriteEntity && !(object.getUserData() instanceof Creep)){
                ((InteractiveSpriteEntity)object.getUserData()).onColide();
                //Gdx.app.log(TAG, "A: " + A.getUserData() + " B: " + B.getUserData());
            }

        }

        else if(A.getUserData() instanceof Creep || B.getUserData() instanceof Creep){
            Fixture creep = A.getUserData() instanceof Creep ? A : B;
            Fixture object = A == creep ? B : A;

            if(object.getUserData() instanceof String && ((String)object.getUserData()).equals("mark")){
                ((Creep)creep.getUserData()).onColide();
            }
            else if(object.getUserData() instanceof Climber){
                ((Climber)object.getUserData()).onColide();

                Gdx.app.log(TAG, "AT SIDE-> A: " + A.getUserData() + " B: " + B.getUserData());
            }

        }*/
    }

    @Override
    public void endContact(Contact contact) {
        /*Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof Ground){
                ((InteractiveSpriteEntity)object.getUserData()).onColideEnd();
            }
        }*/
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
