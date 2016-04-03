package parohyapp.mario.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by tomas on 4/1/2016.
 */
public class ResourcesUtil {
    private static final String TAG = "ResourcesUtil";

    public static Array<TextureRegion> getFramesArray(Texture texture, int spriteWidth){
        TextureRegion spriteSheet = new TextureRegion(texture);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        int max = spriteSheet.getRegionWidth() / spriteWidth;
        for(int i = 0; i < max; i++){
            frames.add(new TextureRegion(spriteSheet.getTexture(),i*spriteWidth,0,spriteWidth,spriteSheet.getRegionHeight()));
        }
        return frames;
    }

    public static AssetManager loadResources(){
        AssetManager assetManager = new AssetManager();
        Resources res[] = Resources.values();

        int max = res.length;
        for(int i =0; i < max; i++){
            switch (res[i].getType()){
                case TEXTURE:
                    assetManager.load(res[i].toString(), Texture.class);
                    break;
                case SOUND:
                    assetManager.load(res[i].toString(), Sound.class);
                    break;
                case MUSIC:
                    assetManager.load(res[i].toString(), Music.class);
                    break;
            }

        }
        assetManager.finishLoading();

        return assetManager;
    }

    public static float radiantToDegree(float radiant){
        return (float) ((radiant * 180) / Math.PI);
    }

    public static float degreeToRadiant(float degree){
        return (float) ((degree * Math.PI) / 180);
    }
}
