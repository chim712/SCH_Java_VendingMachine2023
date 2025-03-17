package AdminSys.system;

import javax.swing.*;
import java.awt.*;

public class CashGraph extends JPanel {
    private static double graphYrate = 1.1;//막대그래프 길이 보정치

    public static double getGraphYrate() {
        return graphYrate;
    }

    private int[] data; // 데이터 값
    // 그래프 갱신 method

    // 생성자
    public CashGraph(int data[]) {
        this.data = data;
        setOpaque(false);
        setPreferredSize(new Dimension(480, 330));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 100; // 그래프 시작 위치
        int y = 230;
        int barWidth = 35; // 막대그래프 두께

        // 그래프 배경 선
        g.setColor(new Color(200, 200, 200)); // 그래프 색상 설정
        for (int i = 0; i < 4; i++) {
            g.drawLine(x, 10 + i * 55, 450, 10 + i * 55);
        }
        g.setColor(Color.black); // 그래프 색상 설정
        g.drawLine(x, 0, x, y);
        g.drawLine(x, y, 450, y);

        // 그래프 그리기 - 전일, 당일 실적
        g.setColor(new Color(30, 70, 120)); // 그래프 색상 설정
        for (int i = 0; i < 4; i++)
            g.fillRect(x + i * 85 + 35, y - (int) (data[i] * graphYrate), barWidth, (int) (data[i] * graphYrate));

    }
}
