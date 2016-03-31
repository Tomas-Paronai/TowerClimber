package parohyapp.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.Update;

/**
 * Created by tomas on 3/25/2016.
 */
public class GameMaster implements Update,Disposable{
    private static final String TAG = "GameMaster";

    private int score;
    private int time;
    private int lives;
    private float timeCount;
    private int numberOfDiamonds;

    private TmxMapLoader mapLoader;
    private PlayScreen screen;
    private TextureAtlas textureAtlas32;
    private TextureAtlas textureAtlas64;
    private AssetManager assetManager;

    private Music music;

    private String[] levels = {"level1.tmx","level2.tmx","test_level1.tmx","test_level2.tmx"};
    private int numberOfLevels;
    private int currentLevel;

    public GameMaster(PlayScreen screen){
        this.screen = screen;
        score = 0;
        time = 180;
        lives = 3;

        numberOfLevels = levels.length;
        currentLevel = 2;

        mapLoader = new TmxMapLoader();
        textureAtlas32 = new TextureAtlas("climber.pack");
        textureAtlas64 = new TextureAtlas("climber64.pack");
        loadAssets();

    }

    private void loadAssets() {
        assetManager = new AssetManager();
        assetManager.load("audio/music.wav", Music.class);
        assetManager.load("audio/jump.wav", Sound.class);
        assetManager.load("audio/levelup.wav", Sound.class);
        assetManager.load("audio/score.mp3", Sound.class);
        assetManager.load("audio/timer.wav", Sound.class);
        assetManager.finishLoading();

        music = assetManager.get("audio/music.wav",Music.class);
        music.setLooping(true);
        //music.play();
    }

    @Override
    public void update(float delta) {
        timeCount += delta;
        if(timeCount >= 1){
            timeCount = 0;
            time--;
            if(time <= 10){
                screen.getGameMaster().getAssetManager().get("audio/timer.wav", Sound.class).play();
            }
        }

        if(time == 0){
            //TODO no more time
            Gdx.app.log(TAG,"Out of time. Restart!");
        }


        if(numberOfDiamonds == 0){
            if(currentLevel + 1 <= numberOfLevels){
                currentLevel++;
                time = 180;
                screen.loadGame();
                screen.getGameMaster().getAssetManager().get("audio/levelup.wav", Sound.class).play();
            }
            else{
                //TODO no more levels
                Gdx.app.log(TAG,"No more levels");
            }
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives += lives;
    }

    public TiledMap getLevelMap(){
        return mapLoader.load(levels[currentLevel-1]);
    }

    public void setNumberOfDiamonds(int numberOfDiamonds) {
        this.numberOfDiamonds += numberOfDiamonds;
    }

    public TextureRegion getTextureByRegion32(String region){
        return textureAtlas32.findRegion(region);
    }

    public TextureRegion getTextureByRegion64(String region){
        return textureAtlas64.findRegion(region);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }


    @Override
    public void dispose() {
        assetManager.dispose();
        textureAtlas32.dispose();
    }
}
