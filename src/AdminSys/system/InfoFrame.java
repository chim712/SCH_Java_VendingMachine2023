package AdminSys.system;

import Main.noticePopUp;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class InfoFrame extends JDialog {
    Container contentPane = getContentPane();
    JTextArea textField;
    private void initFrame(){
        setTitle("Program Infomation");
        setSize(480,360);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setLayout(null);

        textField = new JTextArea();
        textField.setEditable(false);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setFont(new Font("Malgun Gothic",Font.BOLD,15));
        textField.setBounds(0,0,460,330);

        displayInfoFromFile();
        getContentPane().add(textField);

        setVisible(true);
    }

    private void displayInfoFromFile() {
        textField.append(
                "\n 기기 이름 : 20204053 임창환 과제물\n"+
                        " 기기 유형 : 자판기\n\n"+
                        " 제작 착수 : 2023. 05. 20.\n"+
                        " 최종 완성 : 2023. 05. 29.\n"+
                        " 제작 기간 : 10일\n"+
                        " 사용 도구 : IntelliJ Community Ver.\n\n"+
                        " 제  작  자 : 20204053 임창환\n"+
                        " 연  락  처 : chim712@sch.ac.kr / kdlove5500@gmail.com\n\n"+
                        " 희망 사항 : A+를 받고 싶습니다.\n"
        );
//        try {
// programInfo.txt에 내용을 써서 출력하려 하였으나,
// JAR export 후 실행 시 textField에 출력이 안되는 문제 발생하여 하드코딩하였습니다.
//            File file = new File("programInfo.txt");
//            Scanner scanner = new Scanner(file);
//
//            while (scanner.hasNextLine()) {
//
//            }
//
//
//            scanner.close();
//        } catch (FileNotFoundException e) {
//            new noticePopUp("Info 파일 로드 실패");
//            e.printStackTrace();
//        }
    }

    InfoFrame(){
        initFrame();
    }
}
