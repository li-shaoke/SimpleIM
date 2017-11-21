package com.lsk.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class RegisterAndLoginService {
    private ServerSocket serverSocket=null;
    private Set<Socket> allSockets=null;
    public RegisterAndLoginService(){
        try {
            serverSocket=new ServerSocket(9797);

        }catch (IOException e){
            e.printStackTrace();
        }
        allSockets=new HashSet<Socket>();
    }

    public void startService(){
        while (true){
            Socket socket=null;
            try {
                socket=serverSocket.accept();
            }catch (IOException e){
                e.printStackTrace();
            }

            if (socket.isConnected()){
                System.out.printf("socket is connected ");
            }
            allSockets.add(socket);
            new RegisterAndLoginServiceThread(socket,allSockets).start();
        }
    }
}
