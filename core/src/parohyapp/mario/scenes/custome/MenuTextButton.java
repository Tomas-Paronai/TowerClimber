package parohyapp.mario.scenes.custome;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import parohyapp.mario.screens.tools.MenuControl;

/**
 * Created by tomas on 4/18/2016.
 */
public class MenuTextButton {


    public static TextButton createMenuButton(String text){
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap((int) (Gdx.app.getGraphics().getWidth() * MenuControl.SCALE_W), (int) (Gdx.app.getGraphics().getHeight() * MenuControl.SCALE_H), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        bfont.getData().setScale(1.5f);
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white",Color.CYAN);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        return new TextButton(text,textButtonStyle);
    }

    public static TextButton createMenuButton(String text,int width, int height){
        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white",Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white",Color.CYAN);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        return new TextButton(text,textButtonStyle);
    }
}
