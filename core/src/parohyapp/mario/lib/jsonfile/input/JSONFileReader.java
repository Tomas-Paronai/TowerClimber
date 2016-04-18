package parohyapp.mario.lib.jsonfile.input;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by tomas on 4/16/2016.
 */
public class JSONFileReader {

    private File JSONFile;
    private ArrayList<JSONObject> objectRead;

    /**
     * Create reader from given path
     * @param path - filen name with path
     */
    public JSONFileReader(String path){
        JSONFile = new File(path);
        try {
            if(!JSONFile.exists()){
                JSONFile.createNewFile();
            }

            readAll();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create reader from given file
     * @param file - file to read from
     */
    public JSONFileReader(File file){
        JSONFile = file;
        try {
            if(!JSONFile.exists()){
                JSONFile.createNewFile();
            }

            readAll();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create file reader from file name in the given path
     * @param path - path to file directory
     * @param fileName - file name to read
     * @throws JSONFileReaderException - if there is no directory on given path
     */
    public JSONFileReader(String path, String fileName) throws JSONFileReaderException {
        File dir = new File(path);
        if(!dir.exists() || !dir.isDirectory()){
            throw new JSONFileReaderException("Directory "+path+" does not exist!");
        }

        JSONFile = new File(dir,fileName);
        try {
            if(!JSONFile.exists()){
                JSONFile.createNewFile();
            }

            readAll();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create file reader from file name in the given directory
     * @param dir - directory which contains file to read
     * @param fileName file name to read
     * @throws JSONFileReaderException - if given dir does not exist
     */
    public JSONFileReader(File dir, String fileName) throws JSONFileReaderException {
        if(!dir.exists() || !dir.isDirectory()){
            throw new JSONFileReaderException("Directory "+dir.toString()+" does not exist!");
        }

        JSONFile = new File(dir,fileName);
        try {
            if(!JSONFile.exists()){
                JSONFile.createNewFile();
            }

            readAll();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read all json objects from the file.
     * @throws IOException
     */
    private void readAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(JSONFile));
        String line = reader.readLine();

        if(line != null){
            JSONArray jsonArray = new JSONArray(line);

            if(objectRead == null){
                objectRead = new ArrayList<JSONObject>();
            }

            int max = jsonArray.length();
            for(int i = 0; i < max; i++){
                objectRead.add((JSONObject) jsonArray.get(i));
            }
        }
    }

    /**
     * reloads file reader to update data
     */
    public void reload(){
        objectRead.clear();
        try {
            readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get read object on given position
     * @param index
     * @return
     * @throws JSONFileReaderException - if there is no data read or if the given index is out of bounds
     */
    public JSONObject getReadObject(int index) throws JSONFileReaderException {
        if(objectRead == null || objectRead.size() == 0){
            throw new JSONFileReaderException("No read data.");
        }

        if(objectRead.size() <= index){
            throw new JSONFileReaderException("Index: "+index+" ou of bounds: "+objectRead.size());
        }

        return objectRead.get(index);
    }

    /**
     * Get read object with given key
     * @param key
     * @return
     * @throws JSONFileReaderException - if there is no data read or if there is no json object with the given key
     */
    public JSONObject getReadObject(String key) throws JSONFileReaderException {
        if(objectRead == null || objectRead.size() == 0){
            throw new JSONFileReaderException("No read data.");
        }

        for(JSONObject tmpObj : objectRead){
            if(tmpObj.has(key)){
                return tmpObj;
            }
        }

        throw new JSONFileReaderException("No JSONObject with key: "+key+" found.");
    }

    /**
     * Get a copy of the all read json objects
     * @return
     * @throws JSONFileReaderException - if there is no data read
     */
    public ArrayList<JSONObject> getAll() throws JSONFileReaderException {
        if(objectRead == null || objectRead.size() == 0){
            throw new JSONFileReaderException("No read data.");
        }
        return (ArrayList<JSONObject>) objectRead.clone();
    }

    /**
     * Get count of read objects
     * @return
     */
    public int getObjectCount(){
        if(objectRead == null){
            return 0;
        }
        return objectRead.size();
    }
}
