package cn.edu.nju.GameLogic;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.edu.nju.entity.Bullet;
import cn.edu.nju.entity.BulletAI;
import cn.edu.nju.entity.Player;
import cn.edu.nju.entity.PlayerControl;
import cn.edu.nju.net.Client;
import cn.edu.nju.resources.MapData;
import cn.edu.nju.resources.Textures;
import cn.edu.nju.scene.Map;
import cn.edu.nju.entity.Monster;
import cn.edu.nju.entity.MonsterAI;
import cn.edu.nju.entity.MonsterGenerator;

public class GameControl {
    private static ExecutorService cachedPool = Executors.newCachedThreadPool();
    private static Player myPlayer;
    private static HashMap<Integer, Player> otherPlayers;
    private static Map map;
    private static Vector<Bullet> bullets;
    public static boolean isOnTileScreen;
    public static boolean gameState;
    public static boolean playerWin;
    public static int[][] initPosition = {{1,1},{1,6},{1,38},{1,49}};
    public static int[][] monsterInitPosition = {{18, 10}, {18, 26}, {18, 51}, {2, 43}};
    public static List<Monster> monsters;
    
    private static void init(){
        //create entities and prepare map
        String[] mapData = MapData.MAP_DATA;
        map = new Map(mapData);
        bullets = new Vector<>();
        myPlayer = new Player(initPosition[Client.getInstance().clientID][0], initPosition[Client.getInstance().clientID][1], map, bullets, Client.getInstance().clientID); 
        otherPlayers = new HashMap<>();
        monsters = new Vector<>();
        isOnTileScreen = true;
    }

    public static void initGame(){
        Textures.init();
        init();
    }

    public static void startGame(){
        isOnTileScreen = false;
        gameState = true;
        playerWin = true;
        cachedPool.execute(new BulletAI(bullets));
        cachedPool.execute(new PlayerControl(myPlayer));
        cachedPool.execute(new MonsterAI());
        cachedPool.execute(new MonsterGenerator(monsterInitPosition[Client.getInstance().clientID][0], monsterInitPosition[Client.getInstance().clientID][1]));
    }

    public static Map getMap(){return map;}

    public static Player getPlayer(){return myPlayer;}

    public static Vector<Bullet> getBullets(){return bullets;}

    public static HashMap<Integer, Player> getOtherPlayers(){return otherPlayers;}

    
}
