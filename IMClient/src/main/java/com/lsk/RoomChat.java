package com.lsk;

/**
 * Created by lsk10238 on 2017/11/20.
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RoomChat {
    private Socket socket;
    private String name;

    private JFrame jf;
    private JPanel jpDown;

    private JTextArea jtaShowMessage;
    private JList list;
    private JLabel jlNameArea;
    private JTextField jtfMessage;

    private JButton jbSend;
    private JButton jbShake;

    private SimpleDateFormat format;

    public RoomChat(String name){
        this.name=name;

        jf=new JFrame();
        jpDown=new JPanel();

        try {
            socket=new Socket("127.0.0.1",8822);
        } catch (Exception e) {
            e.printStackTrace();
        }

        send("login#"+name);


        jf.setTitle("Chating room system");

        jtaShowMessage=new JTextArea(25,38);
        list=new JList();

        jlNameArea=new JLabel(name+" say:");
        jtfMessage=new JTextField(32);
        jtfMessage.setText("");
        jbSend=new JButton("Send");
        jbShake=new JButton("Shake");
        format=new SimpleDateFormat("[yyyy.MM.dd HH:mm:ss]");
    }

    private void init(){

        JScrollPane jspMessage=new JScrollPane(jtaShowMessage);

        jf.add(jspMessage,new BorderLayout().CENTER);
        jf.add(list,new BorderLayout().EAST);


        jf.getRootPane().setDefaultButton(jbSend);

        jpDown.add(jlNameArea);
        jpDown.add(jtfMessage);
        jpDown.add(jbSend);
        jpDown.add(jbShake);

        jf.add(jpDown,BorderLayout.SOUTH);
    }

    private void setStyle(){

    }

    private void addEventHandler(){
        new Thread(){
            public void run(){
                BufferedReader br=null;

                try {
                    br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    System.out.println("dsp");
                    while(true){
                        String cmd;
                        cmd= br.readLine();
                        System.out.println(cmd);

                        if(cmd.startsWith("login#")){
                            System.out.println("receive ok");
                            System.out.println(cmd);
                            String data=cmd.substring("login#".length());
                            String []listname=data.split("/");
                            list.setListData(listname);
                            jtaShowMessage.append(listname[listname.length-1]+" was  just login! "+format.format(new Date())+"\n");

                        }
                        if(cmd.startsWith("exit#")){
                            System.out.println("receive ok");
                            String[] data=cmd.substring("exit#".length()).split("/");
                            jtaShowMessage.append(data[0]+" has left the chat room"+format.format(new Date())+"\n");
                            String []listname=cmd.substring((data[0]+"exit#").length()).split("/");

                            list.setListData(listname);
                        }
                        if(cmd.startsWith("message#")){
                            System.out.println("receive ok");
                            String[] data=cmd.split("#");
                            jtaShowMessage.append(data[1]+" "+format.format(new Date())+"\n"+data[2]+"\n");
                        }
                        if(cmd.startsWith("shake#")){
                            System.out.println("receive ok shake");
                            String[] data=cmd.split("#");
                            jtaShowMessage.append(data[1]+" send a shake! "+format.format(new Date())+"\n");
                            shake();

                        }
                        locate();
                    }
                } catch (Exception e) {
                    System.out.println("dd");
                }finally{
                }
            }
        }.start();
        jf.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            public void windowClosed(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            public void windowClosing(WindowEvent e) {


                int op=JOptionPane.showConfirmDialog(jf, "Do you want to exit to the chatroom?","exit",JOptionPane.YES_NO_OPTION);
                if(JOptionPane.YES_OPTION==op){
                    send("exit#"+name);

                    if(socket!=null){
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }

                }
                System.exit(1);
            }

            public void windowDeactivated(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            public void windowDeiconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            public void windowIconified(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            public void windowOpened(WindowEvent e) {
                // TODO Auto-generated method stub


            }

        });
        jbSend.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                if("".equals(jtfMessage.getText())){
                    JOptionPane.showMessageDialog(jf, "The message is empty!");
                    return;
                }
                send("message#"+name+"#"+jtfMessage.getText());
                jtfMessage.setText("");
            }

        });
        jbShake.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                send("shake#"+name);
            }

        });
    }
    public void show(){
        init();
        setStyle();
        addEventHandler();

        jtaShowMessage.setEditable(false);

        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.setVisible(true);
        jf.setResizable(false);
    }
    public void send(String cmd){
        PrintWriter pw=null;
        try {
            pw =new PrintWriter(socket.getOutputStream());
            pw.println(cmd);
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }finally{

        }
    }
    private void shake(){
        int x=jf.getX();
        int y=jf.getY();

        for (int i = 0; i <20; i++) {
            if(i%2==1){
                x+=10;
                y+=10;
            }else {
                x-=10;
                y-=10;
            }
            jf.setLocation(x,y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            jf.setAlwaysOnTop(true);
        }
    }
    private void locate(){
        jtaShowMessage.select(jtaShowMessage.getText().length(), jtaShowMessage.getText().length());
    }
    public static void main(String[] args) {
        new RoomChat("test").show();
    }
}
