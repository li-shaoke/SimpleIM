package com.lsk;

/**
 * Created by lsk10238 on 2017/11/20.
 */

import javax.swing.JFrame;
import javax.swing.JTabbedPane;


public class Index {
    private JFrame frame;
    private RegisterForm registerForm;
    private LoginForm loginForm;
    private JTabbedPane jtp;

    public Index() {
        frame=new JFrame("");
        registerForm=new RegisterForm();
        loginForm=new LoginForm();
        jtp=new JTabbedPane();
    }

    private void init(){

        loginForm.show();

        jtp.add("Login",loginForm.getJPanel());

        registerForm.show();

        jtp.add("Register",registerForm.getPanel());
        frame.add(jtp);

    }

    public void show(){
        init();
        frame.setSize(400,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}

