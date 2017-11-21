package com.lsk;

/**
 * Created by lsk10238 on 2017/11/20.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class RegisterForm {

    private JPanel jpjf;

    private JLabel jlTitle;

    private JLabel jlUsername;
    private JLabel jlPassword;
    private JLabel jlSex;
    private JLabel jlEDUbackground;
    private JLabel jlHobby;
    private JLabel jlRemark;

    private JTextField jtfUsername;
    private JPasswordField jpf;
    private JTextArea jtaRemark;
    private JCheckBox jcbHoby1;
    private JCheckBox jcbHoby2;
    private JCheckBox jcbHoby3;
    private JRadioButton jrbSex1;
    private JRadioButton jrbSex2;
    private JComboBox jcbEDU;

    private JButton jbSave;
    private JButton jbReset;

    private JLabel jlAlert;

    private JPanel jp;

    private Socket socket;

    public RegisterForm(){

        jpjf=new JPanel();

        jlTitle=new JLabel("User Registration Page");

        jlUsername=new JLabel("User Name :");
        jlPassword=new JLabel("Password   :");
        jlSex=new JLabel("Sex :");
        jlEDUbackground=new JLabel("Educational Background :");
        jlHobby=new JLabel("Hobbies :");
        jlRemark=new JLabel("Remark :");

        jtfUsername=new JTextField(10);
        jpf=new JPasswordField (10);

        jrbSex1=new JRadioButton("man");
        jrbSex2=new JRadioButton("woman");

        jcbEDU=new JComboBox();
        String[] items={"---COPTION---","master","bachelor","junior"};
        for (int i = 0; i < items.length; i++) {
            jcbEDU.addItem(items[i]);
        }

        jcbHoby1=new JCheckBox("Reading");
        jcbHoby2=new JCheckBox("Music");
        jcbHoby3=new JCheckBox("Sport");

        jtaRemark=new JTextArea(2,20);

        jbSave=new JButton("Save");
        jbReset=new JButton("Reset");
        jlAlert=new JLabel("");

        jp=new JPanel(new GridLayout(7,1));
    }

    private void init(){
        stytle();

        JPanel jp1=new JPanel();
        jp1.add(jlTitle);
        jpjf.add(jp1,BorderLayout.NORTH);

        JPanel jp2=new JPanel();
        jp2.add(jlUsername);
        jp2.add(jtfUsername);
        jp.add(jp2);

        JPanel jp3=new JPanel();
        jp3.add(jlPassword);
        jp3.add(jpf);
        jp.add(jp3);

        JPanel jp4=new JPanel();
        jp4.add(jlSex);
        ButtonGroup bgSex=new ButtonGroup();
        bgSex.add(jrbSex1);
        bgSex.add(jrbSex2);
        jp4.add(jrbSex1);
        jp4.add(jrbSex2);
        jp.add(jp4);

        JPanel jp5=new JPanel();
        jp5.add(jlEDUbackground);
        jp5.add(jcbEDU);
        jp.add(jp5);

        JPanel jp6=new JPanel();
        jp6.add(jlHobby);
        jp6.add(jcbHoby1);
        jp6.add(jcbHoby2);
        jp6.add(jcbHoby3);
        jp.add(jp6);

        JPanel jp7=new JPanel();
        JScrollPane jsp=new JScrollPane(jtaRemark);
        jp7.add(jlRemark);
        jp7.add(jsp);
        jp.add(jp7);


        JPanel jp9=new JPanel();
        jp9.add(jlAlert);
        jp.add(jp9);

        jpjf.add(jp,BorderLayout.CENTER);

        JPanel jp8=new JPanel();
        jp8.add(jbSave);
        jp8.add(jbReset);


        jpjf.add(jp8,BorderLayout.SOUTH);

    }
    private void stytle(){
        Font font1=new Font("Courier New",Font.BOLD,26);
        jlTitle.setFont(font1);
        jlTitle.setForeground(Color.orange);
        Font font3=new Font("Courier New",Font.ITALIC,14);
        jp.setFont(font3);
        jp.setForeground(Color.blue);
        Font font2=new Font("Courier New",Font.BOLD,16);
        jlAlert.setFont(font2);
        jlAlert.setForeground(Color.red);
    }

    public void show(){
        init();
        addEvnentHandler();
    }

    public JPanel getPanel() {
        return jpjf;
    }

    private void addEvnentHandler(){
        jbSave.addActionListener(new ActionListener() {
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

                if("".equals(jtfUsername.getText())){
                    JOptionPane.showMessageDialog(jpjf, "The \"username\" is empty!");
                    return;
                }
                String username=jtfUsername.getText();

                if("".equals(new String(jpf.getPassword()))){
                    JOptionPane.showMessageDialog(jpjf, "The \"password\" is empty!");
                    return;
                }
                String password=new String(jpf.getPassword());


                if(!(jrbSex1.isSelected()||jrbSex2.isSelected())){
                    JOptionPane.showMessageDialog(jpjf, "The \"Sex\" is empty!");
                    return;
                }
                String sex=jrbSex1.isSelected()?jrbSex1.getText():jrbSex2.getText();

                if(!(jcbHoby1.isSelected()||jcbHoby2.isSelected()||jcbHoby3.isSelected())){
                    JOptionPane.showMessageDialog(jpjf, "The \"hobbies\" is empty!");
                    return;
                }
                String hobbies = "";
                if(jcbHoby1.isSelected()){
                    hobbies+=jcbHoby1.getText()+" ";
                }if(jcbHoby2.isSelected()){
                    hobbies+=jcbHoby2.getText()+" ";
                }if(jcbHoby3.isSelected()){
                    hobbies+=jcbHoby3.getText()+" ";
                }


                if("---COPTION---".equals((String)jcbEDU.getSelectedItem())){
                    JOptionPane.showMessageDialog(jpjf, "The \"Educational background\" is empty!");
                    return;
                }
                String educational = (String) jcbEDU.getSelectedItem();


                if("".equals(jtaRemark.getText())){
                    JOptionPane.showMessageDialog(jpjf, "The \"remark\" is empty!");
                    return;
                }
                String remark = jtaRemark.getText();


                try {
                    pw=new PrintWriter(socket.getOutputStream());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                String userReg = "register/"+username+"/"+password+"/"+sex+"/"+hobbies+"/"+educational+"/"+remark;

                try {
                    pw.println(userReg);
                    pw.flush();

                    br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    String mess = br.readLine();

                    if(mess != null && mess.toLowerCase().indexOf("existing")!=-1){
                        jlAlert.setText("Username aleradly exist!");
                    }

                    if(mess != null && mess.toLowerCase().indexOf("success")!=-1){
                        JOptionPane.showMessageDialog(jp, "Register success");
                        JOptionPane.getFrameForComponent(jp)
                                .dispose();
                        new RoomChat(jtfUsername.getText()).show();
                        return;
                    }

                    JOptionPane.showMessageDialog(jpjf, "Sorry,Registered failed...");
                    return;

                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(pw!=null){
                        pw.close();
                    }
                    if(br!=null){
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        jbReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                jtfUsername.setText("");
                jpf.setText("");
                jrbSex1.setSelected(false);
                jrbSex2.setSelected(false);
                jcbEDU.setSelectedIndex(0);
                jcbHoby1.setSelected(false);
                jcbHoby2.setSelected(false);
                jcbHoby3.setSelected(false);
                jtaRemark.setText("");
                jlAlert.setText("");
            }

        });

    }

}
