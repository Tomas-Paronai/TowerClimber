package parohyapp.mario.tools.data;


import java.io.Serializable;

/**
 * Created by tomas on 4/11/2016.
 */
public class LevelDat implements Serializable{

    private String name;
    private int position;

    public LevelDat(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
