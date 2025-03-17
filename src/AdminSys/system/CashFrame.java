package AdminSys.system;

import AdminSys.Time;
import AdminSys.system.CashGraph;
import Main.MainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Machine.Money.Coin.coinType;
import static Machine.Money.MoneySystem.moneyBox;

public class CashFrame extends JFrame {
    private Container contentPane = getContentPane();

    // 좌측 개요 Panel
    private CashGraph cashGraph;
    private int[] cashAmount = moneyBox.getCashAmount();
    private JLabel[][] coinElement;
    private JLabel billValue;
    private JLabel billAmount;

    // 우측 동전 관리 Panel
    private JRadioButton[] coinRadioButtons;
    private JRadioButton[] actionRadioButtons;
    private void initFrame(){
        setTitle("Cash Management");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setLayout(null);

        setVisible(true);
    }
    private void writeTitle(){
        Container contentPane = getContentPane();

        JLabel subTitle = new JLabel(MainClass.title);
        subTitle.setFont(new Font("Malgun Gothic",Font.ITALIC,20));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setBounds(20,7,700,30);
        contentPane.add(subTitle);

        JLabel mainTitle = new JLabel("화폐 관리");
        mainTitle.setFont(new Font("Malgun Gothic",Font.BOLD,50));
        mainTitle.setForeground(Color.white);
        mainTitle.setBounds(20,40,700,50);
        contentPane.add(mainTitle);

        JLabel version = new JLabel(MainClass.version);
        version.setFont(new Font("Malgun Gothic",Font.PLAIN,22));
        version.setForeground(Color.CYAN);
        version.setBounds(260,52,300,50);
        contentPane.add(version);

        JLabel titleTime = new JLabel(Time.getTimeTitle());
        titleTime.setFont(new Font("Malgun Gothic",Font.PLAIN,40));
        titleTime.setForeground(Color.yellow);
        titleTime.setBounds(820,45,500,50);
        contentPane.add(titleTime);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0,0,1280,100);
        titlePanel.setBackground(new Color(30,70,100));
        contentPane.add(titlePanel);
    }       // 상단 타이틀 작성
    private void cashOverview(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);


        //그래프 요소 텍스트 추가 - 제목, 요소, 값
        JLabel cashOverview = new JLabel("동전 보유 현황");
        cashOverview.setBounds(25,110,250,50);
        cashOverview.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(cashOverview);

        JLabel[] graphLevel = new JLabel[5];
        for(int i=0;i<graphLevel.length;i++){
            graphLevel[i] = new JLabel(i*50 + " 개");
            graphLevel[i].setHorizontalAlignment(SwingConstants.RIGHT);
            graphLevel[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            graphLevel[i].setBounds(25,390-i*55,55,20);
            contentPane.add(graphLevel[i]);
        }
        //그래프 그리기
        cashGraph = new CashGraph(cashAmount);
        cashGraph.setBounds(0,170,480,600);
        contentPane.add(cashGraph);

        //하단 동전 갯수 표시
        coinElement = new JLabel[2][5];
        for (int i=0;i<4;i++){
            coinElement[0][i] = new JLabel(coinType[i]+"원");
            coinElement[0][i].setHorizontalAlignment(SwingConstants.CENTER);
            coinElement[0][i].setFont(new Font("Malgun Gothic",Font.BOLD,20));
            coinElement[0][i].setBounds(120+i*85,410,60,22);
            contentPane.add(coinElement[0][i]);
        }
        for (int i=0;i<4;i++){
            coinElement[1][i] = new JLabel(moneyBox.getCashAmount()[i]+"개");
            coinElement[1][i].setHorizontalAlignment(SwingConstants.CENTER);
            coinElement[1][i].setFont(new Font("Malgun Gothic",Font.PLAIN,25));
            coinElement[1][i].setBounds(115+i*85,435,70,33);
            contentPane.add(coinElement[1][i]);
        }


        /// 하단 1000원권 수량
        JLabel billOverview = new JLabel("1000원권 보유 현황");
        billOverview.setBounds(25,500,250,30);
        billOverview.setFont(new Font("Malgun Gothic",Font.BOLD,25));
        contentPane.add(billOverview);

        billAmount = new JLabel(moneyBox.getCashAmount()[4] + " 장");
        billAmount.setBounds(200,540,250,40);
        billAmount.setFont(new Font("Malgun Gothic",Font.PLAIN,35));
        billAmount.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(billAmount);

        billValue = new JLabel(moneyBox.getCashAmount()[4]*1000 + " ￦");
        billValue.setBounds(200,580,250,40);
        billValue.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        billValue.setHorizontalAlignment(SwingConstants.RIGHT);
        contentPane.add(billValue);

    }
    public void cashGraphRefresh(){
        cashGraph.repaint();
        for(int i=0;i<4;i++)
            coinElement[1][i].setText(moneyBox.getCashAmount()[i]+"개");
        billValue.setText(moneyBox.getCashAmount()[4]*1000 + " ￦");
        billAmount.setText(moneyBox.getCashAmount()[4] + " 장");
    }
    private void fillCoinPanel(){
        JLabel fillCoin = new JLabel("동전 보충 및 회수");
        fillCoin.setBounds(550,110,250,50);
        fillCoin.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(fillCoin);

        coinRadioButtons = new JRadioButton[4];
        coinRadioButtons[0] = new JRadioButton("10원");
        coinRadioButtons[1] = new JRadioButton("50원");
        coinRadioButtons[2] = new JRadioButton("100원");
        coinRadioButtons[3] = new JRadioButton("500원");

        actionRadioButtons = new JRadioButton[4];
        actionRadioButtons[0] = new JRadioButton("1개 보충");
        actionRadioButtons[1] = new JRadioButton("5개 보충");
        actionRadioButtons[2] = new JRadioButton("40개 보충");
        actionRadioButtons[3] = new JRadioButton("10개 회수");

        ButtonGroup coinButtonGroup = new ButtonGroup();
        for (JRadioButton radioButton : coinRadioButtons) {
            radioButton.setFont(new Font("Malgun Gothic",Font.BOLD,25));
            coinButtonGroup.add(radioButton);
        }

        ButtonGroup actionButtonGroup = new ButtonGroup();
        for (JRadioButton radioButton : actionRadioButtons) {
            radioButton.setFont(new Font("Malgun Gothic",Font.ITALIC,20));
            actionButtonGroup.add(radioButton);
        }
        actionRadioButtons[3].setForeground(Color.red);

        JButton applyButton = new JButton("적용");
        applyButton.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        applyButton.setBounds(1120,400,80,30);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int coinIndex = -1;
                for (int i = 0; i < coinRadioButtons.length; i++) {
                    if (coinRadioButtons[i].isSelected()) {
                        coinIndex = i;
                        break;
                    }
                }

                int actionIndex = -1;
                for (int i = 0; i < actionRadioButtons.length; i++) {
                    if (actionRadioButtons[i].isSelected()) {
                        actionIndex = i;
                        break;
                    }
                }

                int coinAmount = getCoinAmount(coinIndex);
                int quantity = getQuantity(actionIndex);

                // 동전의 갯수 변경 로직 구현
                switch (coinIndex) {
                    case 0:
                        moneyBox.addCashAmount(quantity, 0, 0, 0, 0);
                        break;
                    case 1:
                        moneyBox.addCashAmount(0, quantity, 0, 0, 0);
                        break;
                    case 2:
                        moneyBox.addCashAmount(0, 0, quantity, 0, 0);
                        break;
                    case 3:
                        moneyBox.addCashAmount(0, 0, 0, quantity, 0);
                        break;
                    default:
                        break;
                }
                MainClass.refreshCashState();
            }
        });
        contentPane.add(applyButton);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 4));
        for (JRadioButton radioButton : coinRadioButtons) {
            panel.add(radioButton);
        }
        for (JRadioButton radioButton : actionRadioButtons) {
            panel.add(radioButton);
        }
        panel.setBounds(550,170,700,200);
        contentPane.add(panel);
        //pack();

        // 동전 회수

        JButton[] collectButton = new JButton[2];
        collectButton[0] = new JButton("지폐 전체 수금");
        collectButton[1] = new JButton("동전 자동 수금");
        for(int i=0;i<collectButton.length;i++){
            collectButton[i].setBounds(550+i*250,500,200,35);
            collectButton[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            contentPane.add(collectButton[i]);
        }
        collectButton[0].addActionListener(e->{moneyBox.collectBill();});
        collectButton[1].addActionListener(e->{moneyBox.collectCoin();});

    }
    private int getCoinAmount(int index) {
        switch (index) {
            case 0:
                return 10;
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 500;
            default:
                return 0;
        }
    }   //fillCoin sub method
    private int getQuantity(int index) {
        switch (index) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 40;
            case 3:
                return -10;
            default:
                return 0;
        }
    }     //fillCoin sub method
    private void setBackground(){
        JPanel graphPanel = new JPanel();
        graphPanel.setBounds(0,0,500,720);
        graphPanel.setBackground(new Color(250,252,255));
        contentPane.add(graphPanel);
    }
    public CashFrame(){
        initFrame();
        writeTitle();
        cashOverview();
        fillCoinPanel();
        setBackground();
    }
}
