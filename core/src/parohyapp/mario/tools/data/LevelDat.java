package parohyapp.mario.tools.data;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by tomas on 4/11/2016.
 */
public class LevelDat {

    private Image thumbnail;
    private String name;
    private int position;

    public LevelDat(Image thumbnail, String name, int position) {
        this.thumbnail = thumbnail;
        this.name = name;
        this.position = position;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}
