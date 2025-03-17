package AdminSys.system;

import AdminSys.Time;
import AdminSys.system.Sales.DataAnalysis;
import AdminSys.system.Sales.SalesManageFrame;
import Main.noticePopUp;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

import static Machine.Money.Coin.coinType;
import static Machine.Money.MoneySystem.moneyBox;
import static Main.MainClass.itemMap;
import static Main.MainClass.itemQueue;

public class AdminSysLobby extends JFrame {
    private SalesGraph salesGraph; // 좌측 매출 개요 그래프
    private JLabel[] analysisData;  // 매출액 금액으로 표시
    private JLabel[] achieve;       // 매출 수준 %로 표시
    private CashGraph cashGraph;   // 우측 거스름돈 갯수 그래프
    private int[] cashAmount = moneyBox.getCashAmount();
    private JLabel[][] coinElement; //동전 갯수
    private JLabel[][] itemElement; //재고 갯수

    public SalesManageFrame salesFrame;
    public CashFrame cashFrame;
    public ProductFrame productFrame;
    public AccountSettingFrame accountSettingFrame;
    public InfoFrame infoFrame;


    private void writeTitle(){
        Container contentPane = getContentPane();

        JLabel subTitle = new JLabel("2023 Java Programming - 20204053 임창환");
        subTitle.setFont(new Font("Malgun Gothic",Font.ITALIC,20));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setBounds(20,7,700,30);
        contentPane.add(subTitle);

        JLabel mainTitle = new JLabel("자판기 관리 시스템");
        mainTitle.setFont(new Font("Malgun Gothic",Font.BOLD,50));
        mainTitle.setForeground(Color.white);
        mainTitle.setBounds(20,40,700,50);
        contentPane.add(mainTitle);

        JLabel version = new JLabel("Version : 23H1 (23. 05.)");
        version.setFont(new Font("Malgun Gothic",Font.PLAIN,22));
        version.setForeground(Color.CYAN);
        version.setBounds(480,52,300,50);
        contentPane.add(version);

        JLabel titleTime = new JLabel(Time.getTimeTitle());
        titleTime.setFont(new Font("Malgun Gothic",Font.PLAIN,40));
        titleTime.setForeground(Color.yellow);
        titleTime.setBounds(820,45,500,50);
        contentPane.add(titleTime);
    }       // 상단 타이틀 작성
    private void makeButton(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        JButton[] menuButton = new JButton[6];
        String[] menuName = new String[]{"매출 관리", "화폐 관리", "계정 관리", "상품 관리", "기기 연동", "기기 정보"};
        for(int i=0;i<6;i++){
            menuButton[i] = new JButton(menuName[i]);
            menuButton[i].setBounds(50+(i%3)*250,530+70*((int)(i/3)),200,50);
            menuButton[i].setFont(new Font("Malgun Gothic",Font.BOLD,20));
            contentPane.add(menuButton[i]);
        }

        menuButton[0].addActionListener(e -> {
            salesFrame = new SalesManageFrame();
        });
        menuButton[1].addActionListener(e -> {
            cashFrame = new CashFrame();
        });
        menuButton[2].addActionListener(e -> {
            accountSettingFrame = new AccountSettingFrame();
        });
        menuButton[3].addActionListener(e -> {
            productFrame = new ProductFrame();
        });
        menuButton[4].addActionListener(e -> {
            new noticePopUp("추후 지원 예정인 기능입니다.","(현재 사용 불가)");
        });
        menuButton[5].addActionListener(e -> {
            infoFrame = new InfoFrame();
        });
    }       // 하단 메뉴 부분 구현
    private void salesOverview(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        int[] overviewData = {
                DataAnalysis.getDaySales(Time.getYesterday()),
                DataAnalysis.getDaySales(Time.getToday()),
                DataAnalysis.getMonthSales(Time.getTodayYear(), Time.getTodayMonth()-1),
                DataAnalysis.getMonthSales(Time.getToday())
        };

        //상단 개요 그래프 작성 - 막대그래프
        salesGraph = new SalesGraph(
                overviewData[0],overviewData[1],overviewData[2],overviewData[3]
        );
        salesGraph.setBounds(30,130,520,300);
        contentPane.add(salesGraph);
        //상단 개요 그래프 작성 - 그래프 배경 선
        SalesGraphBackground salesGraphBackground = new SalesGraphBackground();
        salesGraphBackground.setBounds(30,130,750,300);
        contentPane.add(salesGraphBackground);

        //그래프 요소 텍스트 추가 - 제목, 요소, 값
        JLabel salesOverview = new JLabel("판매 실적 (매출) 개요");
        salesOverview.setBounds(35,110,350,50);
        salesOverview.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(salesOverview);


        String[] salesGraphElement = {"전일 실적","당일 실적","전월 실적","당월 실적"};
        JLabel[] elementLabel = new JLabel[salesGraphElement.length];
        analysisData = new JLabel[overviewData.length];
        achieve = new JLabel[overviewData.length];
        JLabel[] rate = new JLabel[4];
        achieve[0] = new JLabel(DataAnalysis.getDayGoal(Time.getYesterday())+"% ");
        achieve[1] = new JLabel(DataAnalysis.getDayGoal(Time.getToday())+"% ");
        achieve[2] = new JLabel(DataAnalysis.getMonthGoal(Time.getLastMonth())+"% ");
        achieve[3] = new JLabel(DataAnalysis.getMonthGoal(Time.getToday())+"% ");
        for(int i=0;i<salesGraphElement.length;i++){
            //좌측 그래프 요소 설명
            elementLabel[i] = new JLabel(salesGraphElement[i]);
            elementLabel[i].setBounds(35,177+i*50,110,30);
            elementLabel[i].setFont(new Font("Malgun Gothic",Font.PLAIN,20));
            contentPane.add(elementLabel[i]);

            //우측 매출액
            analysisData[i] = new JLabel(overviewData[i]+"￦");
            analysisData[i].setBounds(580,177+i*50,90,30);
            analysisData[i].setHorizontalAlignment(SwingConstants.RIGHT);
            analysisData[i].setFont(new Font("Malgun Gothic",Font.PLAIN,20));
            contentPane.add(analysisData[i]);

            //우측 달성률
            achieve[i].setBounds(700,177+i*50,80,30);
            achieve[i].setHorizontalAlignment(SwingConstants.RIGHT);
            achieve[i].setFont(new Font("Malgun Gothic",Font.ITALIC,23));
            contentPane.add(achieve[i]);
        }
        for(int i=0;i<4;i++){
            rate[i] = new JLabel((i*50)+"%");
            rate[i].setBounds((int) (145+i*50*salesGraph.getGraphXrate()),375,70,20);
            rate[i].setHorizontalAlignment(SwingConstants.CENTER);
            rate[i].setFont(new Font("Malgun Gothic",Font.BOLD,20));
            contentPane.add(rate[i]);
        }

        //목표 실적 금액 표시 & 달성 여부
        JLabel goalText = new JLabel("목표 실적 :                     ￦ / 일,                        ￦ / 월");
        goalText.setBounds(35,410,700,50);
        goalText.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        goalText.setForeground(new Color(30,50,120));
        contentPane.add(goalText);

        JLabel goalPrice1 = new JLabel(DataAnalysis.getSalesGoal()+" ");    //일일 실적 목표
        JLabel goalPrice2 = new JLabel(DataAnalysis.getSalesGoal()*30+" ");     //월간 실적 목표
        goalPrice1.setBounds(180,410,150,50);
        goalPrice1.setHorizontalAlignment(SwingConstants.RIGHT);
        goalPrice1.setFont(new Font("Malgun Gothic",Font.ITALIC,30));
        goalPrice1.setForeground(new Color(150,50,0));
        goalPrice2.setBounds(480,410,150,50);
        goalPrice2.setHorizontalAlignment(SwingConstants.RIGHT);
        goalPrice2.setFont(new Font("Malgun Gothic",Font.ITALIC,30));
        goalPrice2.setForeground(new Color(150,50,0));
        contentPane.add(goalPrice1);
        contentPane.add(goalPrice2);

        JLabel messege;
        if(DataAnalysis.getDayGoal(Time.getToday())>=100)           // 실적 달성 여부에 따라 텍스트 표시
            messege = new JLabel("일일 목표 달성! 오늘도 수고하셨습니다.");
        else messege = new JLabel("일일 목표 미달성 - 오늘도 화이팅 !");
        messege.setBounds(170,460,430,30);
        messege.setHorizontalAlignment(SwingConstants.CENTER);
        messege.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        contentPane.add(messege);
    }       // 매출 개요
    public void salesGraphRefresh(){
        achieve[0].setText(DataAnalysis.getDayGoal(Time.getYesterday())+"% ");
        achieve[1].setText(DataAnalysis.getDayGoal(Time.getToday())+"% ");
        achieve[2].setText(DataAnalysis.getMonthGoal(Time.getLastMonth())+"% ");
        achieve[3].setText(DataAnalysis.getMonthGoal(Time.getToday())+"% ");

        int[] overviewData = {
                DataAnalysis.getDaySales(Time.getYesterday()),
                DataAnalysis.getDaySales(Time.getToday()),
                DataAnalysis.getMonthSales(Time.getTodayYear(), Time.getTodayMonth()-1),
                DataAnalysis.getMonthSales(Time.getToday())
        };

        for(int i=0;i<overviewData.length;i++){
            analysisData[i].setText(overviewData[i]+"￦");
        }

        salesGraph.repaint();
    }
    private void cashOverview(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //그래프 요소 텍스트 추가 - 제목, 요소, 값
        JLabel cashOverview = new JLabel("동전 보유 현황");
        cashOverview.setBounds(825,110,250,50);
        cashOverview.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(cashOverview);

        JLabel[] graphLevel = new JLabel[5];
        for(int i=0;i<graphLevel.length;i++){
            graphLevel[i] = new JLabel(i*50 + " 개");
            graphLevel[i].setHorizontalAlignment(SwingConstants.RIGHT);
            graphLevel[i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            graphLevel[i].setBounds(825,390-i*55,55,20);
            contentPane.add(graphLevel[i]);
        }
        //그래프 그리기
        cashGraph = new CashGraph(cashAmount);
        cashGraph.setBounds(800,170,480,600);
        contentPane.add(cashGraph);

        //하단 동전 갯수 표시
        coinElement = new JLabel[2][5];
        for (int i=0;i<4;i++){
            coinElement[0][i] = new JLabel(coinType[i]+"원");
            coinElement[0][i].setHorizontalAlignment(SwingConstants.CENTER);
            coinElement[0][i].setFont(new Font("Malgun Gothic",Font.BOLD,15));
            coinElement[0][i].setBounds(920+i*85,410,60,20);
            contentPane.add(coinElement[0][i]);
        }
        for (int i=0;i<4;i++){
            coinElement[1][i] = new JLabel(moneyBox.getCashAmount()[i]+"개");
            coinElement[1][i].setHorizontalAlignment(SwingConstants.CENTER);
            coinElement[1][i].setFont(new Font("Malgun Gothic",Font.BOLD,20));
            coinElement[1][i].setBounds(920+i*85,430,60,20);
            contentPane.add(coinElement[1][i]);
        }

    }
    public void cashGraphRefresh(){
        cashGraph.repaint();
        for(int i=0;i<4;i++)
            coinElement[1][i].setText(moneyBox.getCashAmount()[i]+"개");
    }
    private void itemOverview(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //그래프 요소 텍스트 추가 - 제목, 요소, 값
        JLabel itemOverview = new JLabel("재고 개수 현황");
        itemOverview.setBounds(825,475,250,50);
        itemOverview.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(itemOverview);

        // 재고 요소
        itemElement = new JLabel[3][5];
        for(int i=0;i<5;i++)
            itemElement[0][i] = new JLabel(String.valueOf(itemQueue[i].getItemCode()));
        for(int i=0;i<5;i++)
            itemElement[1][i] = new JLabel(itemMap.get(itemQueue[i].getItemCode()).getName());
        for(int i=0;i<5;i++){
            itemElement[2][i] = new JLabel(itemQueue[i].getCurrentSize()+"개");
            itemElement[2][i].setForeground(itemQueue[i].getCurrentSize()<=2?Color.red:Color.black);
        }

        for(int j=0;j<3;j++){
            for(int i=0;i<5;i++){
                itemElement[j][i].setFont(new Font("Malgun Gothic",Font.BOLD,10+j*10));
                itemElement[j][i].setHorizontalAlignment(SwingConstants.CENTER);
                itemElement[j][i].setBounds(809+i*92,530+j*j*6+j*20,80,30);
                contentPane.add(itemElement[j][i]);
            }
        }
    }
    public void itemOverviewRefresh(){
        for(int i=0;i<5;i++){
            itemElement[2][i].setText(itemQueue[i].getCurrentSize()+"개");
            itemElement[2][i].setForeground(itemQueue[i].getCurrentSize()<=2?Color.red:Color.black);
        }
    }
    private void setBackground(){
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0,0,1280,100);
        titlePanel.setBackground(new Color(30,70,100));
        contentPane.add(titlePanel);

        JPanel graphPanel = new JPanel();
        graphPanel.setBounds(0,100,800,400);
        graphPanel.setBackground(new Color(250,252,255));
        contentPane.add(graphPanel);

        JPanel menuPanel = new JPanel();
        menuPanel.setBounds(0,500,800,220);
        menuPanel.setBackground(new Color(150,160,180));
        contentPane.add(menuPanel);

        JPanel coinPanel = new JPanel();
        coinPanel.setBounds(800,100,480,370);
        coinPanel.setBackground(new Color(220,225,240));
        contentPane.add(coinPanel);

        JPanel itemPanel = new JPanel();
        itemPanel.setBounds(800,460,490,70);
        itemPanel.setBackground(new Color(200,203,215));
        contentPane.add(itemPanel);

        JPanel[] itemTable = new JPanel[5];
        for(int i=0;i<5;i++){
            itemTable[i] = new JPanel();
            itemTable[i].setBounds(800+92*i,530,96,110);
            itemTable[i].setBackground(i%2==0?new Color(240,245,255):new Color(225,230,240));
            contentPane.add(itemTable[i]);
        }

        JLabel copyrightText = new JLabel("Copyright 2023. Ch.im. All right reserved");
        copyrightText.setFont(new Font("Malgun Gothic",Font.ITALIC,20));
        copyrightText.setHorizontalAlignment(SwingConstants.CENTER);
        copyrightText.setBounds(800,640,490,45);
        copyrightText.setBackground(Color.darkGray);
        copyrightText.setForeground(Color.CYAN);
        copyrightText.setOpaque(true);
        contentPane.add(copyrightText);
    }       // 레이아웃 구분 및 배경 색상 설정
    private void initFrame(){
        setTitle("Admin System");
        setSize(1280,720);
        setResizable(false);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
    }
    public AdminSysLobby(){
        initFrame();

        //타이틀
        writeTitle();
        // 매출 개요
        salesOverview();
        // 현금 개요
        cashOverview();
        // 재고 개요
        itemOverview();
        // 하단 메뉴 버튼 구현
        makeButton();
        //레이아웃 설정 (배경색으로 구분)
        setBackground();


        setVisible(false);
    }
}


class SalesGraph extends JPanel {
    private static double graphXrate = 2.3;//막대그래프 길이 보정치
    public static double getGraphXrate(){return graphXrate;}
    private int[] data; // 데이터 값

    // 생성자
    public SalesGraph(int a, int b, int c, int d) {
        data = new int[]{a, b, c, d};
        setOpaque(false);
        setPreferredSize(new Dimension(550,300));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 150; // 그래프 시작 위치
        int y = 50;
        int barHeight = 30; // 막대그래프 높이

        // 그래프 그리기 - 전일, 당일 실적
        g.setColor(new Color(91, 155, 213)); // 그래프 색상 설정
        g.fillRect(x, y, (int)(graphXrate*DataAnalysis.getDayGoal(Time.getYesterday())), barHeight);
        g.fillRect(x, y + (barHeight + 20), (int)(graphXrate*DataAnalysis.getDayGoal(Time.getToday())), barHeight);
        // 그래프 그리기 - 전월, 당월 실적
        g.setColor(new Color(30, 75, 116)); // 그래프 색상 설정
        g.fillRect(x, y + (barHeight + 20) * 2, (int)(graphXrate*DataAnalysis.getMonthGoal(Time.getLastMonth())), barHeight);
        g.fillRect(x, y + (barHeight + 20) * 3, (int)(graphXrate*DataAnalysis.getMonthGoal(Time.getToday())), barHeight);
    }   // Chat GPT가 만들어준 그래프 그리기 Class - 일부 수정하여 사용
}

class SalesGraphBackground extends JPanel {
    private double graphXrate = SalesGraph.getGraphXrate();//막대그래프 길이 보정치
    public SalesGraphBackground() {
        setOpaque(false);
        setPreferredSize(new Dimension(550,300));
    }       //생성자

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);    // 가로줄 테두리 색상
        for(int i=0;i<5;i++){
            g.drawLine(0,i*50+40,800,i*50+40);
        }

        g.setColor(new Color(200,200,200)); // 세로줄 색상 설정
        for(int i=0;i<=3;i++) {
            g.drawLine((int) (150+i*graphXrate*50),40, (int) (150+i*graphXrate*50),240);
        }
    }   // Chat GPT가 만들어준 그래프 그리기 Class - 일부 수정하여 사용
}

