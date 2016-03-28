package parohyapp.mario.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import parohyapp.mario.sprites.Climber;
import parohyapp.mario.sprites.Diamond;
import parohyapp.mario.sprites.parent.InteractiveSpriteEntity;

/**
 * Created by tomas on 3/25/2016.
 */
public class WorldContactListener implements ContactListener{
    private static final String TAG = "WorldContactListener";

    @Override
    public void beginContact(Contact contact) {
        Fixture A = contact.getFixtureA();
        Fixture B = contact.getFixtureB();
        Gdx.app.log(TAG,"A: "+A.getUserData()+" B: "+B.getUserData());
        if(A.getUserData() instanceof Climber || B.getUserData() instanceof Climber){
            Fixture player = A.getUserData() instanceof Climber ? A : B;
            Fixture object = A == player ? B : A;

            if(object.getUserData() instanceof Diamond){
                ((InteractiveSpriteEntity)object.getUserData()).onColide();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
