package cn.edu.nju.net.protocol;

public class MessageFactory {
    public static Message createMessage(String msg){
        int type = Integer.parseInt(msg.split("_")[1]);
        Message ansMsg = null;
        switch(type){
            case 1:
                ansMsg = new CreatureNewMsg(msg);
                break;
            case 2:
                ansMsg = new CreatureMoveMsg(msg);
                break;
            case 3:
                ansMsg = new CreatureDamagedMsg(msg);
                break;
            case 4:
                ansMsg = new BulletNewMsg(msg);
                break;
            case 5:
                ansMsg = new CreatureAlreadyExists(msg);
                break;
            case 6:
                ansMsg = new MonsterNewMsg(msg);
                break;
        }
        return ansMsg;
    }
}
