package parohyapp.mario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import parohyapp.mario.screens.PlayScreen;
import parohyapp.mario.tools.data.GameProgress;
import parohyapp.mario.tools.data.resources.Resources;
import parohyapp.mario.tools.data.resources.ResourcesUtil;
import parohyapp.mario.tools.Update;

/**
 * Created by tomas on 3/25/2016.
 */
public class GameMaster implements Update,Disposable{
    private static final String TAG = "GameMaster";

    private static int levelNumberFromMenu;

    private int score;
    private int time;
    private float timeCount;

    private int numberOfGemstones;
    private boolean finished;

    private int lives;
    private int lastLives;
    private boolean imunity;
    private int imunityTime;

    private PlayScreen screen;
    private TextureAtlas textureAtlas;
    private AssetManager assetManager;

    private Music music;

    private String[] levels = {"level00.tmx","level01.tmx","level02.tmx","level03.tmx","level04.tmx","level1.tmx","level2.tmx","test_level1.tmx","test_level2.tmx"};
    private int numberOfLevels;
    private int currentLevel;

    private GameProgress gameProgressTracker;

    public GameMaster(int levelNumber){
        levelNumberFromMenu = levelNumber;
    }

    public GameMaster(PlayScreen screen){
        this.screen = screen;
        score = 0;
        time = 180;
        lastLives = lives = 3;
        imunityTime = 3;

        gameProgressTracker = new GameProgress();
        numberOfLevels = levels.length;
        if(levelNumberFromMenu != 0){
            Gdx.app.log(TAG,"Starting with SELECTED level!");
            currentLevel = levelNumberFromMenu;
        }
        else{
            Gdx.app.log(TAG,"Starting with LAST level!");
            currentLevel = gameProgressTracker.getLastLevel();
        }

        loadAssets();

        gameProgressTracker.levelPassed(getLevelMap(), currentLevel);
    }

    private void loadAssets() {
        assetManager = ResourcesUtil.loadResources();

        music = assetManager.get(Resources.M_GAME_MUSIC.toString(), Music.class);
        music.setLooping(true);
        music.setPosition(0.7f);
        //music.play();
    }

    @Override
    public void update(float delta) {
        timeCount += delta;
        if(timeCount >= 1){
            timeCount = 0;
            time--;
            if(time <= 10){
                screen.getGameMaster().getAssetManager().get(Resources.A_TIMER.toString(), Sound.class).play();
            }

            if(imunity){
                imunityTime--;
                if(imunityTime <= 0){
                    imunity = false;
                    imunityTime = 3;
                }
            }
        }

        if(time == 0){
            //TODO no more time
            Gdx.app.log(TAG,"Out of time. Restart!");
        }

        if(lives == 0){
            //TODO no more lives
            Gdx.app.log(TAG,"Out of lives. Restart!");
        }


        if(numberOfGemstones == 0){
            finished = true;
        }


    }

    public void nextLevel(){
        if(currentLevel + 1 <= numberOfLevels){
            finished = false;
            currentLevel++;
            time = 180;
            screen.loadGame();
            screen.getGameMaster().getAssetManager().get(Resources.A_LEVELUP.toString(), Sound.class).play();
            gameProgressTracker.levelPassed(getLevelMap(),currentLevel);
        }
        else{
            //TODO no more levels
            Gdx.app.log(TAG,"No more levels");
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
        if(!imunity && lives < 0){
            this.lives += lives;
            lastLives += lives;
            setImunity(true);
        }
        else if(this.lives + lives == lastLives){
            this.lives += lives;
        }
    }

    public void setImunity(boolean imunity) {
        this.imunity = imunity;
    }

    public TiledMap getLevelMap(){
        TmxMapLoader mapLoader = new TmxMapLoader();
        return mapLoader.load(levels[currentLevel-1]);
    }

    public void setNumberOfGemstones(int numberOfDiamonds) {
        this.numberOfGemstones += numberOfDiamonds;
        Gdx.app.log(TAG,"Left: "+numberOfGemstones);
    }

    public int getNumberOfGemstones() {
        return numberOfGemstones;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public boolean isFinished() {
        return finished;
        //return true;
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        textureAtlas.dispose();
    }
}
