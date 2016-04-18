package parohyapp.mario.lib.jsonfile.output;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by tomas on 4/16/2016.
 */
public class JSONFileWriter {

    private File JSONFile;
    private ArrayList<JSONObject> objectsToWrite;

    /**
     * Create writer from given path
     * @param path - filen name with path
     */
    public JSONFileWriter(String path){
        JSONFile = new File(path);

        if(!JSONFile.exists()){
            try {
                JSONFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create reader from given file
     * @param file - file to read from
     */
    public JSONFileWriter(File file){
        JSONFile = file;

        if(!JSONFile.exists()){
            try {
                JSONFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create file writer from file name in the given path
     * @param path - path to file directory
     * @param fileName - file name to read
     */
    public JSONFileWriter(String path, String fileName){
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }

        JSONFile = new File(path,fileName);

        if(!JSONFile.exists()){
            try {
                JSONFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create file writer from file name in the given directory
     * @param dir - directory which contains file to read
     * @param fileName file name to read
     */
    public JSONFileWriter(File dir, String fileName){
        if(!dir.isDirectory()){
            dir.mkdir();
        }

        JSONFile = new File(dir,fileName);

        if(!JSONFile.exists()){
            try {
                JSONFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Insert json object for writing
     * @param object - json object to write
     */
    public void prepareJSONObject(JSONObject object){
        if(objectsToWrite == null){
            objectsToWrite = new ArrayList<JSONObject>();
        }

        objectsToWrite.add(object);
    }

    /**
     * Insert json object for writing
     * @param key
     * @param value
     */
    public void prepareJSONObject(String key, String value){
        if(objectsToWrite == null){
            objectsToWrite = new ArrayList<JSONObject>();
        }

        objectsToWrite.add(new JSONObject().put(key,value));
    }

    /**
     * Insert json object for writing next to parent
     * @param key
     * @param value
     * @param parent - key of the json object parent
     * @throws JSONFileWriterException - if no parent found
     */
    public void prepareJSONObject(String key, String value, String parent) throws JSONFileWriterException {
        JSONObject parentObject = null;

        if(objectsToWrite == null){
            objectsToWrite = new ArrayList<JSONObject>();
        }

        for(JSONObject tmpObj : objectsToWrite){
            if(tmpObj.has(parent)){
                tmpObj.put(key,value);
                return;
            }
        }

        throw new JSONFileWriterException("Parent JSONObject with key "+parent+" not found!");
    }

    /**
     * Write all inserted json objects to file
     * @throws JSONFileWriterException - if there is no json object inserted to write
     */
    public void saveAll() throws JSONFileWriterException {
        JSONArray jsonArray = new JSONArray();
        if(objectsToWrite == null || objectsToWrite.size() == 0){
            throw new JSONFileWriterException("No JSONObject to write. Use prepareJSONObject first!");
        }

        int index = 0;
        for(JSONObject tmpObj : objectsToWrite){
            jsonArray.put(index++,tmpObj);
        }

        try {
            FileWriter writer = new FileWriter(JSONFile);
            writer.write(jsonArray.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    /**
     * Write all inserted json objects to file and clear
     * @throws JSONFileWriterException - if there is no json object inserted to write
     */
    public void saveAllClear() throws JSONFileWriterException {
        saveAll();
        clear();
    }

    /**
     * Clear all inserted json objects
     */
    public void clear(){
        objectsToWrite.clear();
    }

    /**
     * Clear specific json object with given key
     * @param key
     * @throws JSONFileWriterException - if there is no json object inserted or if there is no object with given key
     */
    public void remove(String key) throws JSONFileWriterException {
        if(objectsToWrite == null || objectsToWrite.size() == 0){
            throw new JSONFileWriterException("No JSONObject to remove. Use prepareJSONObject first!");
        }

        for(JSONObject tmpObj : objectsToWrite){
            if(tmpObj.has(key)){
                tmpObj.remove(key);
                if(!tmpObj.keys().hasNext()){
                    objectsToWrite.remove(tmpObj);
                }
                return;
            }
        }

        throw new JSONFileWriterException("No JSONObject with "+key+" found.");
    }

    /**
     * Clear specific json object on the given index
     * @param index
     * @throws JSONFileWriterException - if there is no json object inserted or if the given index is out of bounds
     */
    public void remove(int index) throws JSONFileWriterException {
        if(objectsToWrite == null || objectsToWrite.size() == 0){
            throw new JSONFileWriterException("No JSONObject to remove. Use prepareJSONObject first!");
        }

        if(index >= objectsToWrite.size()){
            throw new JSONFileWriterException("Index "+index+" is out of bounds: "+objectsToWrite.size());
        }
        objectsToWrite.remove(index);
    }

    /**
     * Get inserted json object on the given index
     * @param index
     * @return
     * @throws JSONFileWriterException - if there is no json object inserted or if the given index is out of bounds
     */
    public JSONObject getPreparedObj(int index) throws JSONFileWriterException {
        JSONObject result = null;

        if(objectsToWrite == null){
            throw new JSONFileWriterException("Queue is empty. Use prepareJSONObject first!");
        }

        if(index >= objectsToWrite.size()){
            throw new JSONFileWriterException("Index "+index+" is out of bounds: "+objectsToWrite.size());
        }

        return objectsToWrite.get(index);
    }

    /**
     * Get inserted json object with the given key
     * @param key
     * @return
     * @throws JSONFileWriterException - if there is no json object inserted or if there is no object with the given key
     */
    public JSONObject getPreparedObj(String key) throws JSONFileWriterException {
        if(objectsToWrite == null){
            throw new JSONFileWriterException("Queue is empty. Use prepareJSONObject first!");
        }

        for(JSONObject tmpObj : objectsToWrite){
            if(tmpObj.has(key)){
                return tmpObj;
            }
        }

        throw new JSONFileWriterException("No JSONObject with key "+key+" found.");
    }

    /**
     * Get count of all inserted objects
     * @return
     */
    public int getObjectCount(){
        if(objectsToWrite == null){
            return 0;
        }
        return objectsToWrite.size();
    }
}
