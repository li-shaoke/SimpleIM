package com.lsk.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Set;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class ChatRoomServerThread {
    private Socket socket;
    private Set<Socket> allSockets;
    private List<String> listUsername;

    public ChatRoomServerThread(Socket socket,Set<Socket> allSockets,List<String> listUsername){
        this.socket=socket;
        this.allSockets=allSockets;
        this.listUsername=listUsername;
    }

    public void run(){
        BufferedReader bufferedReader=null;
        try {
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true){
                if (socket.isClosed())
                    return;

                String message=bufferedReader.readLine();
                if (message!=null && message.length() >0){
                    System.out.println(" system recived :"+message);
                    if (message.startsWith("exit#")==true){
                        String name=message.substring("exit#".length());
                        allSockets.remove(socket);
                        listUsername.remove(name);

                        String str="exit#"+name+"/";
                        for (int i = 0; i < listUsername.size(); i++) {
                            str+=listUsername.get(i)+"/";
                        }
                        if(str.length()!=0){
                            str=str.substring(0, str.length()-1);

                            PrintWriter printWriter;
                            for (Socket s : allSockets) {
                                printWriter=new PrintWriter(s.getOutputStream());
                                printWriter.println(str);
                                printWriter.flush();
                            }
                        }
                        socket.close();
                        return;
                    }

                    PrintWriter printWriter;
                    int i=1;
                    for (Socket s : allSockets) {
                        System.out.println(" sned to client "+i++);
                        printWriter=new PrintWriter(s.getOutputStream());
                        printWriter.println(message);
                        printWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (socket != null){
                allSockets.remove(socket);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
