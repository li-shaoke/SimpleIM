package com.lsk;

import com.lsk.Service.ChatRoomServer;
import com.lsk.Service.RegisterAndLoginService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "start program ... " );
        Thread threadRegisterAndLogin=new Thread(new Runnable() {
            public void run() {
                new RegisterAndLoginService().startService();
            }
        });
        Thread threadChat=new Thread(new Runnable() {
            public void run() {
                new ChatRoomServer().startService();
            }
        });
        System.out.println( "program is running ... " );
        threadChat.start();
        threadRegisterAndLogin.start();
    }
}
