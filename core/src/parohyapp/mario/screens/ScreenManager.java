package parohyapp.mario.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import parohyapp.mario.TowerClimber;

/**
 * Created by tomas on 3/31/2016.
 */
public class ScreenManager {

    public final static int MENU_SCREEN = 0;
    public final static int LEVEL_SELECT = 1;
    public final static int PLAY_SCREEN = 2;

    private int currentScreen;
    private TowerClimber game;

    public ScreenManager(TowerClimber game) {
        currentScreen = PLAY_SCREEN;
        this.game = game;
    }

    public int getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(int currentScreen) {
        this.currentScreen = currentScreen;
    }

    public Screen getScreen(){
        switch(currentScreen){
            case MENU_SCREEN: return new MenuScreen();
            case LEVEL_SELECT:
            case PLAY_SCREEN:
            default: return new PlayScreen(game);
        }
    }
}
