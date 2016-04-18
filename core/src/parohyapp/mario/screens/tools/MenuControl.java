package parohyapp.mario.screens.tools;

/**
 * Created by tomas on 4/18/2016.
 */
public interface MenuControl {

    float SCALE_W = 0.3f;
    float SCALE_H = 0.07f;

    void initSkin();
    void initMenu();
    void initListener();
    void changeScreen(ScreenSet nextScreen);
}
