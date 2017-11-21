package com.lsk.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class ChatRoomServer {
    private ServerSocket serverSocket;
    private Set<Socket> allSockets;
    List<String> listName=new ArrayList<String>();
    public ChatRoomServer() {
        try {
            serverSocket=new ServerSocket(8822);

            allSockets=new HashSet<Socket>();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startService(){
        while(true){
            Socket socket=null;
            try {
                socket=serverSocket.accept();
                if(socket.isConnected()){
                    System.out.println("connected");
                }
                allSockets.add(socket);
                BufferedReader br=new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String username=br.readLine().substring("login#".length());
                System.out.println(username);
                listName.add(username);
                String str="login#";
                for (int i = 0; i < listName.size(); i++) {
                    str+=listName.get(i)+"/";
                }
                str=str.substring(0, str.length()-1);
                for (Socket item : allSockets) {
                    PrintWriter pw=new PrintWriter(item.getOutputStream());
                    pw.println(str);
                    pw.flush();
                }
                new ChatRoomServerThread(socket, allSockets,listName).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
