package com.lsk;

/**
 * Created by lsk10238 on 2017/11/20.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginForm {
    private JPanel jp;

    private JLabel title;
    private JLabel jlUsername;
    private JLabel jlPassword;
    private JTextField jtfUsername;
    private JPasswordField jpf;
    private JButton jbLogin;
    private JButton jbReset;

    private JPanel jp1;
    private JPanel jp2;
    private JPanel jp3;

    private JLabel jlAlert;

    private Socket socket;

    public LoginForm(){


        jp=new JPanel();

        title=new JLabel("The User Login Page");
        jp.add(title);

        jp1=new JPanel();
        jlUsername=new JLabel("UserName :");
        jtfUsername=new JTextField(18);

        jp2=new JPanel();
        jlPassword=new JLabel("Password :");
        jpf=new JPasswordField(18);

        jp3=new JPanel();
        jbLogin=new JButton("Login");
        jbReset=new JButton("Reset");

        jlAlert=new JLabel("");
    }

    private void init(){
        style();
        adddActionEventHandler();
        jp1.add(jlUsername);
        jp1.add(jtfUsername);

        jp2.add(jlPassword);
        jp2.add(jpf);

        jp3.add(jbLogin);
        jp3.add(jbReset);

        jp.add(jp1);
        jp.add(jp2);
        jp.add(jp3);

        jp.add(jlAlert);

    }
    private void style(){
        Font font=new Font("Courier New",Font.BOLD,20);
        title.setFont(font);
        title.setForeground(Color.orange);

        Font font1=new Font("Courier New",Font.BOLD,16);
        jlAlert.setFont(font1);
        jlAlert.setForeground(Color.red);
    }

    public void show(){
        init();

    }
    private  void adddActionEventHandler(){
        jbLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    socket=new Socket("127.0.0.1",9797);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PrintWriter pw = null;
                BufferedReader br = null;
                String username=jtfUsername.getText();
                String password=new String(jpf.getPassword());
                if("".equals(username) || "".equals(password) ){
                    jlAlert.setText("The username or password is empty!");
                    return;
                }
                String userLog = "login/"+username+"/"+password;
                try {
                    pw=new PrintWriter(socket.getOutputStream());

                    pw.println(userLog);
                    pw.flush();

                    br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream(),"utf-8"));
                    String mess = br.readLine();

                    if("wrong".equals(mess)){
                        jlAlert.setText("username or password is wrong!");
                    }
                    if(mess.toLowerCase().indexOf("login success") !=-1){
                        JOptionPane.getFrameForComponent(jp)//获得jp所在的JFrame
                                .dispose();
                        new RoomChat(jtfUsername.getText()).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        jbReset.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                jtfUsername.setText("");
                jpf.setText("");
                jlAlert.setText("");
            }

        });
    }
    public JPanel getJPanel() {
        return jp;
    }
}
