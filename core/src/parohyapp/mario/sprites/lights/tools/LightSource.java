package parohyapp.mario.sprites.lights.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.*;
import box2dLight.Light;
import parohyapp.mario.TowerClimber;
import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.sprites.Switchable;
import parohyapp.mario.sprites.parent.Entity;
import parohyapp.mario.sprites.standing.switches.SwitchableType;
import parohyapp.mario.tools.ResourcesUtil;

/**
 * Created by tomas on 4/3/2016.
 */
public abstract class LightSource extends Entity implements Switchable{

    private box2dLight.Light lightSource;
    protected PlayScreen screen;

    private boolean connectable;

    public LightSource(World world, Rectangle bounds, PlayScreen screen){
        super(world, bounds);
        this.screen = screen;
    }

    public Light getLightSource() {
        return lightSource;
    }

    public void setLightSource(Light lightSource) {
        this.lightSource = lightSource;
    }

    public void setLightMaskFilter(short bits){
        Filter filter = new Filter();
        filter.maskBits = bits;
        if(lightSource != null){
            lightSource.setContactFilter(filter);
        }
    }

    @Override
    public void toggle() {
        if(lightSource.isActive()){
            lightSource.setActive(false);
        }
        else{
            lightSource.setActive(true);
        }
    }

    @Override
    public SwitchableType getTag() {
        return SwitchableType.LIGHT;
    }

    @Override
    public void setConnectable(boolean connectable) {
        this.connectable = connectable;
    }

    @Override
    public boolean isConnectable() {
        return connectable;
    }

    public static class LightUtil {
        public static box2dLight.Light createPointLight(RayHandler rayHandler, int rays, Color color, float radius, Body body){
            PointLight light = new PointLight(rayHandler,rays,color,radius / TowerClimber.PPM, 0, 0);
            light.setSoftnessLength(0f);
            light.attachToBody(body);
            return light;
        }

        public static box2dLight.Light createConeLight(RayHandler rayHandler, int rays, Color color, float dist, float degree, float directionAngle, Body body){
            float angle = ResourcesUtil.degreeToRadiant(degree);
            ConeLight light = new ConeLight(rayHandler,rays,color,dist / TowerClimber.PPM, 0,0,angle,directionAngle);
            light.setSoftnessLength(0f);
            body.setTransform(body.getPosition(), angle);
            light.attachToBody(body);
            return light;
        }
        public static box2dLight.Light createPointLight(RayHandler rayHandler, int rays, Color color, float radius, Body body, float offX, float offY){
            PointLight light = new PointLight(rayHandler,rays,color,radius / TowerClimber.PPM, 0, 0);
            light.setSoftnessLength(0f);
            light.attachToBody(body,offX / TowerClimber.PPM,offY / TowerClimber.PPM);
            return light;
        }

        public static box2dLight.Light createConeLight(RayHandler rayHandler, int rays, Color color, float dist, float degree, float directionAngle, Body body, float offX, float offY){
            float angle = ResourcesUtil.degreeToRadiant(degree);
            ConeLight light = new ConeLight(rayHandler,rays,color,dist / TowerClimber.PPM, 0,0,angle,directionAngle);
            light.setSoftnessLength(0f);
            body.setTransform(body.getPosition(), angle);
            light.attachToBody(body,offX / TowerClimber.PPM,offY / TowerClimber.PPM);
            return light;
        }
    }

}
