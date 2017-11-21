package com.lsk.Service;

import com.lsk.BasicCore.DbUnit;
import com.lsk.DataAccess.UserDataAccess;
import com.lsk.Entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

/**
 * Created by lsk10238 on 2017/11/20.
 */
public class RegisterAndLoginServiceThread extends Thread {

    private Socket socket=null;
    public RegisterAndLoginServiceThread(Socket socket, Set<Socket> allSockets){
        this.socket=socket;
    }

    public  void run(){
        BufferedReader bufferedReader=null;
        PrintWriter printWriter=null;

        try{
            if (socket != null && !socket.isClosed()){
                bufferedReader=new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String userStr=bufferedReader.readLine();

                if (userStr != null && userStr.startsWith("register")){
                    String[] info = userStr.split("/");

                    String username = info[1];
                    if (UserDataAccess.selectIsExist(username)) {
                        printWriter = new PrintWriter(socket.getOutputStream());
                        printWriter.print("the name '" + username + "' existing ");
                        printWriter.flush();
                        return;
                    }
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(info[2]);
                    user.setSex(info[3]);
                    user.setHobbies(info[4]);
                    user.setEducational(info[5]);
                    user.setRemark(info[6]);

                    System.out.println("To prepare new users :"+username);

                    UserDataAccess.insertUser(user);

                    printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println("Add user '"+username +"' success ");
                    printWriter.flush();
                }

                if(userStr!=null && userStr.startsWith("login")) {
                    String info[] = userStr.split("/");
                    String username = info[1];
                    String password = info[2];
                    System.out.println(username+password);

                    if(!UserDataAccess.ValidateUserByUsernameAndPwd(username, password)){
                        printWriter = new PrintWriter(socket.getOutputStream());
                        printWriter.println("wrong");
                        printWriter.flush();
                        return;
                    }
                    printWriter = new PrintWriter(socket.getOutputStream());
                    printWriter.println(username +" login success");
                    printWriter.flush();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
