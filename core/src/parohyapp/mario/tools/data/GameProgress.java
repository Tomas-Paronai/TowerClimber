package parohyapp.mario.tools.data;

import com.badlogic.gdx.maps.tiled.TiledMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import parohyapp.mario.TowerClimber;

/**
 * Created by tomas on 4/11/2016.
 */
public class GameProgress {

    private File dataFile;
    private ArrayList<LevelDat> levelData;

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

    public ArrayList<LevelDat> getLevelData() {
        return levelData;
    }

    public void levelPassed(TiledMap map, int pos){
        String name = "uknown";
        if(map.getProperties().containsKey("Name")){
            name = (String) map.getProperties().get("Name");
        }
        lastLevelInfo = new LevelDat(name,pos);
        levelData.add(lastLevelInfo);
        updateData();
    }

    private void updateData(){
        try {

            saveData();
            firstStartup();
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

        dataFile = new File(parentDir,"progress.dat");
        if(!dataFile.exists()){
            dataFile.createNewFile();
        }

        levelData = new ArrayList<LevelDat>();
    }

    public void saveData() throws IOException {
        if(dataFile != null){
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(dataFile);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);

                dataFile.delete();
                dataFile.createNewFile();

                for(LevelDat tmpDat : levelData){
                    objectOutputStream.writeObject(tmpDat);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                objectOutputStream.close();
            }


        }
    }

    public void loadData() throws IOException {

        if(dataFile != null){
            FileInputStream fileInputStream = new FileInputStream(dataFile);
            ObjectInputStream objectInputStream = null;

            try {
                objectInputStream = new ObjectInputStream(fileInputStream);
                while(true){
                    levelData.add((LevelDat) objectInputStream.readObject());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            finally {
                objectInputStream.close();
            }

        }

        if(levelData.size() > 0){
            lastLevel = levelData.get(levelData.size() - 1).getPosition();
        }
        else{
            lastLevel = 1;
        }

    }

    public int getLastLevel() {
        return lastLevel;
    }
}
