package Main;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class noticePopUp extends JDialog {
    public noticePopUp(){
        this("안내 창 호출");
    }
    public noticePopUp(String message){
        setTitle("Notice");
        setSize(360,120);
        setLocationRelativeTo(null);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        JLabel textArea = new JLabel(message);
        textArea.setPreferredSize(new Dimension(340,30));
        textArea.setOpaque(false); // 배경을 투명하게 설정
        textArea.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        contentPane.add(textArea);

        JButton ok = new JButton("확인");
        ok.setPreferredSize(new Dimension(70,25));
        ok.setFont(new Font("Malgun Gothic",Font.PLAIN,14));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(ok);

        setVisible(true);
    }
    public noticePopUp(String message1, String message2){
        setTitle("Notice");
        setSize(360,120);
        setLocationRelativeTo(null);
        setResizable(false);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        JLabel textArea1 = new JLabel(message1);
        textArea1.setPreferredSize(new Dimension(340,16));
        textArea1.setHorizontalAlignment(SwingConstants.CENTER);
        textArea1.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        contentPane.add(textArea1);

        JLabel textArea2 = new JLabel(message2);
        textArea2.setPreferredSize(new Dimension(340,16));
        textArea2.setHorizontalAlignment(SwingConstants.CENTER);
        textArea2.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        contentPane.add(textArea2);

        JButton ok = new JButton("확인");
        ok.setPreferredSize(new Dimension(70,25));
        ok.setFont(new Font("Malgun Gothic",Font.PLAIN,14));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(ok);

        setVisible(true);
    }
}
