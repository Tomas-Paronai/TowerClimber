package parohyapp.mario.tools.data;

import java.util.ArrayList;

/**
 * Created by tomas on 4/11/2016.
 */
public class GameProgress {

    private ArrayList<LevelDat> levelData;

    public GameProgress() {
        levelData = new ArrayList<LevelDat>();
    }

    public ArrayList<LevelDat> getLevelData() {
        return levelData;
    }
}
