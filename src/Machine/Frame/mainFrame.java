package Machine.Frame;

import AdminSys.Login.LoginSys;
import Machine.Item.SellingSystem;
import Main.MainClass;
import Main.noticePopUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static Machine.Money.MoneySystem.insertCashTotal;
import static Machine.Money.MoneySystem.moneyBox;
import static Main.MainClass.itemQueue;
import static Main.MainClass.itemMap;

public class mainFrame extends JFrame {
    private Container contentPane = getContentPane();
    private LoginSys loginSys = new LoginSys();
    private InsertCashFrame cashFrame = new InsertCashFrame();
    private int adminButtonCount;
    private static JLabel[] itemImage = new JLabel[5];         //상품 그림
    private static JLabel[] itemPrice = new JLabel[5];         //상품 가격
    private static JButton[] itemPurchaseButton = new JButton[5];  //구매 버튼
    private static JLabel[] itemState = new JLabel[5];         //상품 상태(판매/품절)
    private static ImageIcon[] buttonImage = new ImageIcon[]{
            new ImageIcon("ButtonB.png"),
            new ImageIcon("ButtonG.png"),
            new ImageIcon("ButtonR.png")};
    public static JLabel cashDisplay;
    public static JLabel isEnoughCoin;
    public static void refreshCashDisplay(){
        int value = insertCashTotal;
        cashDisplay.setText(value+" 원 ");
        if(moneyBox.checkEnough()) isEnoughCoin.setText("");
        else isEnoughCoin.setText("잔 돈 없 음");

    }
    public static void refreshItemState(){
        for(int i=0;i<5;i++){
            itemState[i].setText("판매중");
            itemState[i].setForeground(Color.green);
            itemPurchaseButton[i].setIcon(buttonImage[0]);
            //품절 제품 text 표시 (버튼과는 별도 동작)
            if (itemQueue[i].getCurrentSize() <= 0) {
                itemState[i].setText("품  절");
                itemState[i].setForeground(Color.red);
            }
            //구입 가능 금액 이상 투입 시 초록버튼 - 품절 제품은 빨간버튼
            if(insertCashTotal >= itemMap.get(itemQueue[i].getItemCode()).getPrice()) {
                if (itemQueue[i].getCurrentSize() <= 0){
                    itemPurchaseButton[i].setIcon(buttonImage[2]);
                } else
                    itemPurchaseButton[i].setIcon(buttonImage[1]);
            }
        }
    }
    public static void refreshStorageState(){
        for(int i=0;i<5;i++){
            String imagePath = itemMap.get(itemQueue[i].getItemCode()).getImagePath();
            itemImage[i].setIcon(new ImageIcon(imagePath));

            int price = itemMap.get(itemQueue[i].getItemCode()).getPrice();
            itemPrice[i].setText(price + " 원");
        }
    }
    private void displayItem(){
        for(int i=0;i<5;i++){
            int finalI = i;
            String imagePath = itemMap.get(itemQueue[i].getItemCode()).getImagePath();
            int price = itemMap.get(itemQueue[i].getItemCode()).getPrice();
            // 상품 이미지 표시
            itemImage[i] = new JLabel(new ImageIcon(imagePath));
            itemImage[i].setBounds(40+i*80,35,80,200);
            contentPane.add(itemImage[i]);
            // 상품 가격표 부착
            itemPrice[i] = new JLabel(price + " 원");
            itemPrice[i].setFont(new Font("Malgun Gothic",Font.BOLD,14));
            itemPrice[i].setHorizontalAlignment(SwingConstants.CENTER);
            itemPrice[i].setBackground(new Color(240,235,220));
            itemPrice[i].setOpaque(true);
            itemPrice[i].setBounds(45+i*80,235,70,20);
            contentPane.add(itemPrice[i]);
            // 상품 구매 버튼 장착
            itemPurchaseButton[i] = new JButton(buttonImage[0]);
            itemPurchaseButton[i].setBorderPainted(false); //버튼 테두리 삭제
            itemPurchaseButton[i].setContentAreaFilled(false); //버튼 채우기 해제
            itemPurchaseButton[i].setFocusPainted(false); //클릭시 강조 칠하기 해제
            itemPurchaseButton[i].setOpaque(false); //투명하게
            itemPurchaseButton[i].setBounds(55+i*80,260,50,30);
            itemPurchaseButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SellingSystem.sell(finalI);
                }
            });
            contentPane.add(itemPurchaseButton[i]);
            //판매중 or 품절 표시
            itemState[i] = new JLabel("판매중");
            itemState[i].setForeground(Color.green);
            itemState[i].setBackground(Color.black);
            itemState[i].setFont(new Font("Malgun Gothic",Font.BOLD,14));
            itemState[i].setHorizontalAlignment(SwingConstants.CENTER);
            itemState[i].setOpaque(true);
            itemState[i].setBounds(50+i*80,297,60,20);
            contentPane.add(itemState[i]);


        }
    }
    private void displayInsertCash(){

        int value = insertCashTotal;
        cashDisplay = new JLabel(value+" 원 ");
        cashDisplay.setFont(new Font("Malgun Gothic",Font.BOLD,15));
        cashDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        cashDisplay.setBackground(Color.black);
        cashDisplay.setForeground(Color.red);
        cashDisplay.setOpaque(true);
        cashDisplay.setBounds(282,337,66,17);
        contentPane.add(cashDisplay);

        isEnoughCoin = new JLabel("");
        isEnoughCoin.setFont(new Font("Malgun Gothic",Font.BOLD,12));
        isEnoughCoin.setHorizontalAlignment(SwingConstants.CENTER);
        isEnoughCoin.setBackground(Color.black);
        isEnoughCoin.setForeground(Color.red);
        isEnoughCoin.setOpaque(true);
        isEnoughCoin.setBounds(282,354,66,13);
        contentPane.add(isEnoughCoin);

        setVisible(true);
    }
    private void initFrame(){
        setTitle(MainClass.title);
        setSize(480,720);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel main = new JLabel(new ImageIcon("Machine.png"));
        main.setBounds(0,0,480,720);
        contentPane.add(main);

    }
    private void notice(){
        new noticePopUp("관리자메뉴는 칠성사이다를 세 번 누르십시오.","현금 투입은 현금 투입구 부분을 클릭하십시오.");
    }
    private void addAdminSysButton(){
        contentPane.setLayout(null);

        JButton adminSys = new JButton();
        adminSys.setBorderPainted(false); //버튼 테두리 삭제
        adminSys.setContentAreaFilled(false); //버튼 채우기 해제
        adminSys.setFocusPainted(false); //클릭시 강조 칠하기 해제
        adminSys.setOpaque(false); //투명하게
        adminSys.setBounds(25,345,230,175);
        adminSys.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("AdminButton Pressed");
                adminButtonCount++;
                if(adminButtonCount>=3) {
                    loginSys.setVisible(true);
                    adminButtonCount = 0;
                }
            }
        });
        contentPane.add(adminSys);
    }
    private void insertCashButton(){
        contentPane.setLayout(null);

        JButton cashInsert = new JButton();
        cashInsert.setBorderPainted(false); //버튼 테두리 삭제
        cashInsert.setContentAreaFilled(false); //버튼 채우기 해제
        cashInsert.setFocusPainted(false); //클릭시 강조 칠하기 해제
        cashInsert.setOpaque(false); //투명하게
        cashInsert.setBounds(275,335,185,85);
        cashInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("coinInsert Pressed");
                cashFrame.setVisible(true);
            }
        });
        contentPane.add(cashInsert);
    }
    public mainFrame(){
        adminButtonCount = 0;
        contentPane.setLayout(null);

        displayItem();
        displayInsertCash();
        initFrame();
        addAdminSysButton();
        insertCashButton();
        setVisible(true);

        // 닫기 버튼을 누르면 off를 수행하고 닫도록 함.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // MainClass의 off() 메서드 호출
                MainClass.off();
                System.exit(0); // 프로그램 종료
            }
        });

        notice();
    }
}
