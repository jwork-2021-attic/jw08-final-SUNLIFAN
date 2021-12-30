package cn.edu.nju.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GameServer {
    private Selector selector;
    private InetSocketAddress listenAddress;
    public static final int PORT = 2012;
    public static byte nextClientID = 0;
    private List<SocketChannel> clientChannels;

    public GameServer(String address, int port){
        listenAddress = new InetSocketAddress(address, port);
        clientChannels = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
            new GameServer("localhost", 2012).startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() throws IOException{
        //open a selector
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        

        //bind server socket channel to port
        serverChannel.socket().bind(listenAddress);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("Server started on port >>> "+ PORT);

        while(true){
            //wait for events
            int readyCount = selector.select();
            if(readyCount == 0)continue;

            //process selection keys
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();

                //remove the key from the set so we don't process it twice
                iterator.remove();

                if(!key.isValid())continue;

                if(key.isAcceptable()){//accept client connection
                    this.accept(key);
                }else if(key.isReadable()){
                    byte[] data = this.read(key);
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    for(SocketChannel client : this.clientChannels){
                        buffer.put(data);
                        buffer.flip();
                        client.write(buffer);
                        buffer.clear();
                    }
                }else if(key.isWritable()){
                    //maybe write data to client
                }
            }
        }
    }


    // accept client connection
	private void accept(SelectionKey key) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		SocketChannel channel = serverChannel.accept();
		channel.configureBlocking(false);
		Socket socket = channel.socket();
		SocketAddress remoteAddr = socket.getRemoteSocketAddress();
		System.out.println("Connected to: " + remoteAddr);

		/*
		 * Register channel with selector for further IO (record it for read/write
		 * operations, here we have used read operation)
		 */
		channel.register(this.selector, SelectionKey.OP_READ);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(nextClientID);
        nextClientID++;
        buffer.flip();
        channel.write(buffer);
        buffer.clear();
        clientChannels.add(channel);
	}

    // read from the socket channel
	private byte[] read(SelectionKey key) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int numRead = -1;
		numRead = channel.read(buffer);

		if (numRead == -1) {
			Socket socket = channel.socket();
			SocketAddress remoteAddr = socket.getRemoteSocketAddress();
			System.out.println("Connection closed by client: " + remoteAddr);
			channel.close();
			key.cancel();
			return null;
		}

		byte[] data = new byte[numRead];
		System.arraycopy(buffer.array(), 0, data, 0, numRead);
		System.out.println("Got: " + new String(data));
        return data;
	}


    
}
