package parohyapp.mario.tools;

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

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if(A.getUserData() instanceof Entity && B.getUserData() instanceof Entity){
            //Gdx.app.log(TAG,"A: "+((Entity)A.getUserData()).getEntX()+" "+((Entity)A.getUserData()).getEntY());
            //Gdx.app.log(TAG,"B: "+((Entity)B.getUserData()).getEntX()+" "+((Entity)B.getUserData()).getEntY());

            /*if(A.getUserData() instanceof SignalLight || B.getUserData() instanceof SignalLight){
                Fixture light = A.getUserData() instanceof SignalLight ? A : B;
                Fixture object = A == light ? B : A;
                if(object.getUserData() instanceof Door){
                    Gdx.app.log(TAG,"#####");
                }

            }*/
        }



        if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof InteractiveSpriteEntity && !(object.getUserData() instanceof Creep)){
                ((InteractiveSpriteEntity)object.getUserData()).onColide();
                //Gdx.app.log(TAG, "A: " + A.getUserData() + " B: " + B.getUserData());
            }

            else if(object.getUserData() instanceof Creep){
                ((Climber)player.getUserData()).onColide();
                //((Creep)object.getUserData()).setDying(true);
            }
        }

        else if(A.getUserData() instanceof Creep || B.getUserData() instanceof Creep){
            Fixture creep = A.getUserData() instanceof Creep ? A : B;
            Fixture object = A == creep ? B : A;

            if(object.getUserData() instanceof String && ((String)object.getUserData()).equals("mark")){
                ((Creep)creep.getUserData()).onColide();
            }

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof Ground){
                ((InteractiveSpriteEntity)object.getUserData()).onColideEnd();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
