package cn.edu.nju.net.protocol;

public interface Message {
    public static final int CREATURE_NEW_MSG = 1;
    public static final int CREATURE_MOVE_MSG = 2;
    public static final int CREATURE_DAMAGED_MSG = 3; 
    public static final int BULLET_NEW_MSG = 4;
    public static final int CREATURE_ALREADY_EXISTS_MSG = 5;
    public static final int MONSTER_NEW_MSG = 6;
    //format of message:clientID_MSG_TYPE(An Integer)_XPos_YPos_direction_(damage, if it is damage message\attackVal if it is bullet new message\health if it is creature already exists message)
    public void decode();
}
