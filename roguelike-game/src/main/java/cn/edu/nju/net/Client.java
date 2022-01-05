package cn.edu.nju.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

import cn.edu.nju.GameLogic.GameControl;
import cn.edu.nju.gui.KeyBoard;
import cn.edu.nju.gui.Window;
import cn.edu.nju.net.protocol.Message;
import cn.edu.nju.net.protocol.MessageFactory;

public class Client {
    public int clientID;
    private SocketChannel clientChannel;
    private static Client clientInstance = new Client(0);
    public static int gold = 0;
    public static int kill = 0;
    
    public Client(int ID){
        this.clientID = ID;
    }

    public static Client getInstance(){
        return clientInstance;
    }

    public void startClient() {
        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 2012);
        try {
            ByteBuffer tmp = ByteBuffer.allocate(10);
            clientChannel = SocketChannel.open(hostAddress);
            int numRead = clientChannel.read(tmp);
            if(numRead != 0 && numRead != -1)clientID = (int)tmp.array()[0];
        } catch (IOException e1) {
            System.out.println("[Client]:IO Exception occurs when connecting.....");
            e1.printStackTrace();
            System.exit(-1);
        }
        System.out.println("[Client]: Client starting.....");

        Window.create();
        GameControl.initGame();
        Window.setVisible();
        Window.screen.setFocusable(true);
        Window.screen.requestFocus();
        KeyBoard kb = new KeyBoard();
		Window.screen.addKeyListener(kb);
		while(!kb.enter_be_pressed){};
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("[Client]:Exception occurs when initializing....");
            System.exit(-1);
        }
        GameControl.startGame();
        Client.getInstance().sendMsg(clientID + "_" + Message.CREATURE_NEW_MSG + "_" + GameControl.getPlayer().getXPos() + "_"
        + GameControl.getPlayer().getYPos() + "_" + GameControl.getPlayer().dir.ordinal());
        System.out.println("[Client]:Game starts !!!");
        while(GameControl.gameState){
            try {
                read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String msg){
        if(msg == null || msg.length() == 0)return;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(msg.getBytes());
        buffer.flip();
        try {
            clientChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.clear();
    }

    public void read() throws IOException{
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int numRead = clientChannel.read(buffer);
        
        if(numRead == -1 || numRead == 0){
            return;
        }

        String msg = new String(buffer.array());
        Message myMsg = MessageFactory.createMessage(msg);
        System.out.println("Got:" + msg);
        myMsg.decode();
    }
    public static void main(String[] args) {
        Client.getInstance().startClient();
    }
}

