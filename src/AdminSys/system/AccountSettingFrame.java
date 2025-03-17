package AdminSys.system;

import AdminSys.Time;
import Main.MainClass;
import Main.noticePopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static AdminSys.Login.LoginSys.getSavedPassword;
import static AdminSys.Login.LoginSys.setSavedPassword;

public class AccountSettingFrame extends JFrame {
    private JPasswordField[] passwordField = new JPasswordField[3];
    private ActionListener callChange = e -> passwordChange();
    private Container contentPane = getContentPane();
    private void initFrame(){
        setTitle("Account Setting");
        setSize(480,360);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setLayout(null);

        setVisible(true);
    }
    private void writeTitle(){
        Container contentPane = getContentPane();

        JLabel subTitle = new JLabel(MainClass.title);
        subTitle.setFont(new Font("Malgun Gothic",Font.ITALIC,15));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setBounds(20,7,400,20);
        contentPane.add(subTitle);

        JLabel mainTitle = new JLabel("계정 관리");
        mainTitle.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        mainTitle.setForeground(Color.white);
        mainTitle.setBounds(20,27,400,33);
        contentPane.add(mainTitle);

        JLabel version = new JLabel(MainClass.version);
        version.setFont(new Font("Malgun Gothic",Font.PLAIN,15));
        version.setForeground(Color.CYAN);
        version.setBounds(20,60,300,20);
        contentPane.add(version);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0,0,640,90);
        titlePanel.setBackground(new Color(30,70,100));
        contentPane.add(titlePanel);
    }       // 상단 타이틀 작성
    private void drawPasswordField(){
        String[] boxName = new String[]{"현재 비밀번호 : ","변경 비밀번호 : ", "한번 더 입력   : "};
        JLabel[] boxTitle = new JLabel[3];
        for(int i=0;i<3;i++){
            boxTitle[i] = new JLabel(boxName[i]);
            boxTitle[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            boxTitle[i].setBounds(20,100+i*40,130,30);
            contentPane.add(boxTitle[i]);

            passwordField[i] = new JPasswordField();
            passwordField[i].setBounds(130,100+i*40,310,30);
            passwordField[i].addActionListener(callChange);
            contentPane.add(passwordField[i]);
        }

        JLabel[] changeNotice = new JLabel[2];
        changeNotice[0] = new JLabel("변경할 비밀번호는 8자 이상으로, 영문, 숫자,");
        changeNotice[1] = new JLabel("특수문자를 한 글자 이상 포함하십시오.");
        for (int i=0;i<2;i++){
            changeNotice[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            changeNotice[i].setBounds(20,220+i*20,440,20);
            changeNotice[i].setHorizontalAlignment(SwingConstants.CENTER);
            contentPane.add(changeNotice[i]);
        }

        JButton apply = new JButton("비밀번호 변경");
        apply.setFont(new Font("Malgun Gothic",Font.BOLD,15));
        apply.setBounds(160,270,160,30);
        contentPane.add(apply);

        apply.addActionListener(callChange);
    }
    private void passwordChange() {
        // Check if the password meets the required conditions
        if(!passwordField[0].getText().equals(getSavedPassword())){
            new noticePopUp("현재 비밀번호 입력이 틀렸습니다.");
            return;
        }
        if ((passwordField[1].getText()).length() < 8) {
            new noticePopUp("변경할 비밀번호는 8자 이상으로 하십시오.");
            return;
        }
        if(!passwordField[1].getText().equals(passwordField[2].getText())){
            new noticePopUp("비밀번호 확인이 일치하지 않습니다.");
            return;
        }

        boolean hasDigit = false;
        boolean hasLetter = false;
        boolean hasSpecialChar = false;

        for (char c : (passwordField[1].getText()).toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (Character.isLetter(c)) {
                hasLetter = true;
            } else {
                hasSpecialChar = true;
            }
        }
        if (hasDigit && hasLetter && hasSpecialChar) {
            setSavedPassword(passwordField[1].getText());
            new noticePopUp("비밀번호가 변경되었습니다.");
            dispose();
        }else{
            new noticePopUp("비밀번호는 영문, 숫자, 특수문자를","한 글자 이상 포함하여야 합니다.");
        }
    }

    public AccountSettingFrame(){
        initFrame();
        writeTitle();
        drawPasswordField();
    }
}
