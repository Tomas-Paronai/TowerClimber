package parohyapp.mario.tools.data;

import com.badlogic.gdx.maps.tiled.TiledMap;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import parohyapp.mario.TowerClimber;
import parohyapp.mario.lib.jsonfile.input.*;
import parohyapp.mario.lib.jsonfile.output.*;

/**
 * Created by tomas on 4/11/2016.
 */
public class GameProgress {

    private File dataFile;
    private ArrayList<LevelDat> levelDataArray;

    private int lastLevel;
    private LevelDat lastLevelInfo;

    public GameProgress() {
        try {
            firstStartup();
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LevelDat> getLevelDataArray() {
        return levelDataArray;
    }

    public void levelPassed(TiledMap map, int pos){
        String name = "uknown";
        if(map.getProperties().containsKey("Name")){
            name = (String) map.getProperties().get("Name");
        }
        lastLevelInfo = new LevelDat(name,pos);
        levelDataArray.add(lastLevelInfo);
        updateData();
    }

    private void updateData(){
        try {

            saveData();
            loadData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void firstStartup() throws IOException {
        File parentDir = new File(TowerClimber.parentDirPath,"TowerClimber");
        if(!parentDir.exists()){
            parentDir.mkdir();
        }

        dataFile = new File(parentDir,"progress.txt");
        if(!dataFile.exists()){
            dataFile.createNewFile();
        }

        levelDataArray = new ArrayList<LevelDat>();
    }

    public void saveData() throws IOException {
        if(dataFile != null){
            dataFile.delete();
            dataFile.createNewFile();
            JSONFileWriter writer = new JSONFileWriter(dataFile);
            for(LevelDat tmpDat : levelDataArray){
                writer.prepareJSONObject(String.valueOf(tmpDat.getPosition()),tmpDat.getName());
            }

            try {
                writer.saveAllClear();
            } catch (JSONFileWriterException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadData() {

        if(dataFile != null){
           JSONFileReader reader = new JSONFileReader(dataFile);
            try {
                for(JSONObject tmpObj : reader.getAll()){
                    String key = tmpObj.keys().next();
                    levelDataArray.add(new LevelDat(tmpObj.getString(key),Integer.parseInt(key)));
                }
            } catch (JSONFileReaderException e) {
                e.printStackTrace();
            }
        }

        if(levelDataArray.size() > 0){
            lastLevel = levelDataArray.get(levelDataArray.size() - 1).getPosition();
        }
        else{
            lastLevel = 1;
        }

    }

    public int getLastLevel() {
        if(lastLevel <= 0){
            return 1;
        }
        return lastLevel;
    }
}
