package AdminSys.Login;

import AdminSys.AdminSysMain;
import AdminSys.system.AdminSysLobby;
import Main.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class LoginSys extends JFrame {
    private int count = 0;
    private static String savedPassword;
    public static void setSavedPassword(String password){
        savedPassword = password;
    }
    public static String getSavedPassword(){
        return savedPassword;
    }
    private JPasswordField passwordField;
    private JLabel wrongPw;

    ActionListener login = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pw = passwordField.getText();
            System.out.println(pw);
            System.out.println(savedPassword);
            if(Objects.equals(pw, savedPassword)) {
                System.out.println("Login");
                MainClass.adminSysMain.adminSysLobby.setVisible(true);
                passwordField.setText("");
                dispose();
            }
            else {
                wrongPw.setVisible(true);
                System.out.println("Fail");
                wrongPw.setText("Wrong Password "+ count++);
            }
        }
    };


    private void initFrame(){
        setTitle("Admin System Login");
        setSize(360,120);
        setLocationRelativeTo(null);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200,30));
        JButton loginButton = new JButton("Login");
        wrongPw = new JLabel("Wrong PassWord  "+ ++count);
        wrongPw.setPreferredSize(new Dimension(200,30));
        wrongPw.setForeground(Color.red);
        wrongPw.setVisible(false);
        contentPane.add(passwordField);
        contentPane.add(loginButton);
        contentPane.add(wrongPw);

        passwordField.addActionListener(login); //패스워드 입력 박스
        loginButton.addActionListener(login);   //로그인 버튼 (같은 ActionListener)
        //로그인 버튼 누르면 텍스트 받아서 잠금해제
        /*
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pw = passwordField.getText();
                System.out.println(pw);
                System.out.println(savedPassword);
                if(Objects.equals(pw, savedPassword)) {
                    System.out.println("Login");
                    MainClass.adminSysMain.adminSysLobby.setVisible(true);
                    dispose();
                }
                else {
                    wrongPw.setVisible(true);
                    System.out.println("Fail");
                    wrongPw.setText("Wrong Password "+ count++);
                }
            }
        });

         */
    }


    public LoginSys(){
        initFrame();

        setVisible(false);
    }
}

