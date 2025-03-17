package Machine.Frame;

import Machine.Money.Bill;
import Machine.Money.Coin;
import Main.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Machine.Money.MoneySystem.*;

public class InsertCashFrame extends JFrame {
    private Container contentPane;
    public static JLabel insertValue;
    private void initFrame(){
        setTitle("금액 투입");
        setSize(240,360);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public void drawLabel(){

        insertValue = new JLabel(insertCashTotal + " 원 ");
        insertValue.setFont(new Font("Malgun Gothic",Font.BOLD,25));
        insertValue.setPreferredSize(new Dimension(240,100));
        insertValue.setHorizontalAlignment(SwingConstants.RIGHT);
        insertValue.setBackground(Color.black);
        insertValue.setForeground(Color.red);
        insertValue.setOpaque(true);
        contentPane.add(insertValue,BorderLayout.CENTER);

        JLabel notice = new JLabel("동전은 최대 30개 or 2000원");
        notice.setFont(new Font("Malgun Gothic",Font.BOLD,15));
        notice.setPreferredSize(new Dimension(240,30));
        notice.setHorizontalAlignment(SwingConstants.CENTER);
        notice.setOpaque(true);
        contentPane.add(notice,BorderLayout.SOUTH);
    }
    public void drawButton(){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JButton[] cashButton = new JButton[5];
        int[] value = new int[]{10,50,100,500,1000};

        Container coinContainer = new Container();
        coinContainer.setLayout(new GridLayout(3,2));
        for(int i=0;i<5;i++){
            int finalI = i;
            cashButton[i] = new JButton(String.valueOf(value[i]));
            cashButton[i].setPreferredSize(new Dimension(120,80));
            cashButton[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            if(i==4){
                cashButton[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        insertBillStack.insertBill(new Bill(value[finalI]));
                        MainClass.refreshCashState();
                        MainClass.refreshItemState();
                    }
                });
            }
            else {
                cashButton[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        insertCoinStack.insertCoin(new Coin(value[finalI]));
                        MainClass.refreshCashState();
                        MainClass.refreshItemState();
                    }
                });
            }

            coinContainer.add(cashButton[i]);
        }

        JButton returnButton = new JButton("반 환");
        returnButton.setFont(new Font("Malgun Gothic",Font.BOLD,15));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isSell){
                    restorePartly(tmpChangeValue,insertBillStack.stackTotal());
                } else {
                    restoreAll();
                }
                //insertBillStack.flush
                MainClass.refreshCashState();
                MainClass.refreshItemState();
            }
        });
        coinContainer.add(returnButton);

        contentPane.add(coinContainer,BorderLayout.NORTH);
    }


    public InsertCashFrame(){
        initFrame();
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        drawButton();
        drawLabel();

        setVisible(false);
    }
}
