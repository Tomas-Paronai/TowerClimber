package parohyapp.mario.tools;

/**
 * Created by tomas on 4/1/2016.
 */
public enum Resources {
    //texture
    CLIMBER_IDLE("sprites/idle.png",ResType.TEXTURE),
    CLIMBER_RUNNING("sprites/running.png",ResType.TEXTURE),
    CLIMBER_JUMP("sprites/jump.png",ResType.TEXTURE),
    ROBOT_GO("sprites/robot_go.png",ResType.TEXTURE),
    ROBOT_DIE("sprites/robot_die.png",ResType.TEXTURE),
    ROBOT_IDLE("sprites/robot_idle.png",ResType.TEXTURE),
    DIAMOND("sprites/diamond.png",ResType.TEXTURE),
    RUBY("sprites/ruby.png",ResType.TEXTURE),
    FIREBALL("sprites/fireball.png",ResType.TEXTURE),
    FIREBALL_DIE("sprites/fireball_die.png",ResType.TEXTURE),
    MINE("sprites/mine.png",ResType.TEXTURE),
    MINE_DIE("sprites/mine_die.png",ResType.TEXTURE),
    DOOR_FRONT("sprites/door_front.png",ResType.TEXTURE),
    DOOR_BACK("sprites/door_back.png",ResType.TEXTURE),
    DOOR_BG("sprites/door_bg.png",ResType.TEXTURE),

    //audio
    A_JUMP("audio/jump.wav",ResType.SOUND),
    A_LEVELUP("audio/levelup.wav",ResType.SOUND),
    A_SCORE("audio/score.mp3",ResType.SOUND),
    A_TIMER("audio/timer.wav",ResType.SOUND),

    //music
    M_GAME_MUSIC ("audio/a_new_day.ogg",ResType.MUSIC);

    private final String stringValue;
    private final ResType stringType;
    Resources(final String path, final ResType type){
        stringValue = path;
        stringType = type;
    }

    public String toString() {
        return stringValue;
    }

    public ResType getType(){
        return stringType;
    }
}
