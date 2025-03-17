package AdminSys.system;

import AdminSys.Time;
import Machine.Item.Item;
import Main.MainClass;
import Main.noticePopUp;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Machine.Item.ItemStorageQueue.getMaxQueueSize;
import static Main.MainClass.*;

public class ProductFrame extends JFrame {
    private Container contentPane = getContentPane();
    private final int x=25, y=180, intervel=100;



    //좌측 판넬
    private JLabel[] itemCodeLabel = new JLabel[5];
    private JLabel[] itemNameLabel = new JLabel[5];
    private JLabel[] itemPriceLabel = new JLabel[5];
    private JLabel[] itemAmount = new JLabel[5];

    //우측 판넬
    private DefaultListModel<String> listModel;
    private JList<String> itemList;
    private String[] itemInfoElement = new String[]{"상품코드 : ","상품이름 : ","상품가격 : ","사진파일 : "};
    private int editStorageNum;         // 정보 수정일 경우 진열라인 index 저장
    private boolean imageLoadCheck = false;

    //Method
    public void refreshItemAmount(){
        for(int i=0;i<5;i++){
            itemAmount[i].setText(itemQueue[i].getCurrentSize()+" 개");
        }
    }
    public void refreshStorageTable(){
        for(int i=0;i<5;i++){
            itemCodeLabel[i].setText("Code : " + itemQueue[i].getItemCode());
            itemNameLabel[i].setText(itemMap.get(itemQueue[i].getItemCode()).getName());
            itemPriceLabel[i].setText(itemMap.get(itemQueue[i].getItemCode()).getPrice()+" 원");
        }
    }
    private void refreshJList(){
        listModel.removeAllElements();
        for(int i=0;i<10;i++){
            if(itemMap.getByIndex(i)!=null)
                listModel.add(i,itemMap.getByIndex(i).getName());
        }
        itemList.repaint();
    }

    // DrawFrame
    private void initFrame(){
        setTitle("Sales Management");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setBackground(new Color(250,252,255));
        contentPane.setLayout(null);

        contentPane.setLayout(null);
        contentPane.setBounds(0,0,1280,720);

        setVisible(true);
    }
    private void writeTitle() {

        JLabel subTitle = new JLabel(MainClass.title);
        subTitle.setFont(new Font("Malgun Gothic", Font.ITALIC, 20));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setBounds(20, 7, 700, 30);
        contentPane.add(subTitle);

        JLabel mainTitle = new JLabel("재고 관리");
        mainTitle.setFont(new Font("Malgun Gothic", Font.BOLD, 50));
        mainTitle.setForeground(Color.white);
        mainTitle.setBounds(20, 40, 700, 50);
        contentPane.add(mainTitle);

        JLabel version = new JLabel(MainClass.version);
        version.setFont(new Font("Malgun Gothic", Font.PLAIN, 22));
        version.setForeground(Color.CYAN);
        version.setBounds(260, 52, 300, 50);
        contentPane.add(version);

        JLabel titleTime = new JLabel(Time.getTimeTitle());
        titleTime.setFont(new Font("Malgun Gothic", Font.PLAIN, 40));
        titleTime.setForeground(Color.yellow);
        titleTime.setBounds(820, 45, 400, 50);
        contentPane.add(titleTime);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 0, 1280, 100);
        titlePanel.setBackground(new Color(30, 70, 100));
        contentPane.add(titlePanel);
    }
    private void storageTable(){
        JLabel storageOverview = new JLabel("잔여 재고 현황");
        storageOverview.setBounds(25,110,250,50);
        storageOverview.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(storageOverview);

        int x=25, y=180, intervel=100;
        JButton[][] fillItem = new JButton[5][3];

        for(int i=0;i<5;i++){
            itemCodeLabel[i] = new JLabel("Code : " + itemQueue[i].getItemCode());
            itemCodeLabel[i].setFont(new Font("Malgun Gothic",Font.BOLD,14));
            itemCodeLabel[i].setBounds(x,y+i*intervel,220,15);
            contentPane.add(itemCodeLabel[i]);

            itemNameLabel[i] = new JLabel(itemMap.get(itemQueue[i].getItemCode()).getName());
            itemNameLabel[i].setFont(new Font("Malgun Gothic",Font.BOLD,30));
            itemNameLabel[i].setBounds(x,y+18+i*intervel,300,40);
            contentPane.add(itemNameLabel[i]);

            itemPriceLabel[i] = new JLabel(itemMap.get(itemQueue[i].getItemCode()).getPrice()+" 원");
            itemPriceLabel[i].setFont(new Font("Malgun Gothic",Font.BOLD,20));
            itemPriceLabel[i].setBounds(x,y+60+i*intervel,100,25);
            contentPane.add(itemPriceLabel[i]);

            itemAmount[i] = new JLabel(itemQueue[i].getCurrentSize() + " 개");
            itemAmount[i].setFont(new Font("Malgun Gothic",Font.PLAIN,50));
            itemAmount[i].setHorizontalAlignment(SwingConstants.RIGHT);
            itemAmount[i].setBounds(x+280,y+10+i*intervel,140,50);
            contentPane.add(itemAmount[i]);

            int finalI = i;
            fillItem[i][0] = new JButton("1개 폐기");
            fillItem[i][0].setForeground(Color.red);
            fillItem[i][0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(itemQueue[finalI].isEmptyQueue()){
                        new noticePopUp("폐기할 재고가 없습니다.");
                        return;
                    }
                    itemQueue[finalI].dequeue();
                    refreshItemState();
                }
            });
            fillItem[i][1] = new JButton("1개 보충");
            fillItem[i][1].setForeground(Color.black);
            fillItem[i][1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(itemQueue[finalI].isFullQueue()){
                        new noticePopUp("재고 보관함이 꽉 찼습니다.");
                        return;
                    }
                    itemQueue[finalI].enqueue();
                    refreshItemState();
                }
            });
            fillItem[i][2] = new JButton("전체 보충");
            fillItem[i][2].setForeground(Color.blue);
            fillItem[i][2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(itemQueue[finalI].isFullQueue()){
                        new noticePopUp("재고 보관함이 꽉 찼습니다.");
                        return;
                    }
                    for(int j=itemQueue[finalI].getCurrentSize();j<=getMaxQueueSize();j++)
                        itemQueue[finalI].enqueue();
                    refreshItemState();
                    itemQueue[finalI].printLinkedList();
                }
            });
            for(int j=0;j<3;j++){
                fillItem[i][j].setFont(new Font("Malgun Gothic",Font.BOLD,15));
                fillItem[i][j].setBounds(x+460,y+5+i*intervel+j*25,110,20);
                contentPane.add(fillItem[i][j]);
            }
        }
    }
    private void ItemListTable(){
        JLabel productManage = new JLabel("상품 정보 등록 · 변경");
        productManage.setBounds(630,110,350,50);
        productManage.setFont(new Font("Malgun Gothic",Font.BOLD,30));
        contentPane.add(productManage);

        listModel = new DefaultListModel<>();
        for(int i=0;i<10;i++){
            if(itemMap.getByIndex(i)!=null)
                listModel.add(i,itemMap.getByIndex(i).getName());
        }
        itemList = new JList<>(listModel);
        itemList.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        itemList.setBounds(630,y,130,350);
        contentPane.add(itemList);

        JButton productManageButton = new JButton("수정");
        productManageButton.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        productManageButton.setBounds(630,640,130,30);
        contentPane.add(productManageButton);


        //==================== 우측 상품 정보 패널 ================

        JLabel[] infoTableElement = new JLabel[5];
        JTextArea[] infoTableInsert = new JTextArea[4];
        for(int i=0;i<4;i++){
            infoTableElement[i] = new JLabel(itemInfoElement[i]);
            infoTableElement[i].setFont(new Font("Malgun Gothic",Font.PLAIN,20));
            infoTableElement[i].setBounds(790,180+i*55+(i>0?10:0),125,35);
            contentPane.add(infoTableElement[i]);

            infoTableInsert[i] = new JTextArea();
            infoTableInsert[i].setFont(new Font("Malgun Gothic",Font.PLAIN,20));
            infoTableInsert[i].setBounds(890,180+i*55+(i>0?10:0),300,35);
            Border lineBorder = BorderFactory.createLineBorder(Color.gray, 1);
            Border emptyBorder = BorderFactory.createEmptyBorder(1, 4, 1, 3);
            infoTableInsert[i].setBorder(BorderFactory.createCompoundBorder(lineBorder, emptyBorder));
            contentPane.add(infoTableInsert[i]);
        }
        JLabel itemCodeNotice = new JLabel("전면에 진열된 상품은 상품 코드 변경이 불가능합니다.");
        itemCodeNotice.setBounds(890,215,450,20);
        itemCodeNotice.setFont(new Font("Malgun Gothic",Font.ITALIC,12));
        contentPane.add(itemCodeNotice);

        JLabel imageNotice = new JLabel("경로 설정 후 불러오기를 누르세요.");
        imageNotice.setBounds(890,390,450,20);
        imageNotice.setFont(new Font("Malgun Gothic",Font.ITALIC,12));
        contentPane.add(imageNotice);

        infoTableElement[4] = new JLabel();
        infoTableElement[4].setBounds(790,400,80,200);
        contentPane.add(infoTableElement[4]);

        JButton imageLoadButton = new JButton("불러오기");
        imageLoadButton.setBounds(1100,390,90,20);
        imageLoadButton.setFont(new Font("Malgun Gothic",Font.BOLD,12));
        imageLoadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLoadCheck = true;
                ImageIcon loadedImage = new ImageIcon(infoTableInsert[3].getText());
                if(loadedImage.getImageLoadStatus()!=MediaTracker.COMPLETE) {
                    loadedImage = new ImageIcon("MenuE.png");
                    infoTableInsert[3].setText("MenuE.png");
                    new noticePopUp("없는 파일입니다.");
                }
                infoTableElement[4].setIcon(loadedImage);
            }
        });
        contentPane.add(imageLoadButton);

        JButton[] editButton = new JButton[2];
        editButton[0] = new JButton("내용 저장");
        editButton[1] = new JButton("작성 취소");
        for(int i=0;i<2;i++){
            editButton[i].setFont(new Font("Malgun Gothic",Font.BOLD,13));
            editButton[i].setBounds(990+i*110,620,100,30);
            contentPane.add(editButton[i]);
            editButton[i].setVisible(false);
        }

        //================== Button ActionListener =================
        editButton[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //형식 검사 (ItemCode, 가격, 이미지Path 검사)
                if(!infoTableInsert[2].getText().matches("\\d+")){
                    new noticePopUp("물품 가액 형식이 잘못되었습니다.");
                    return;
                }       // 물품 가액 형식 오류
                if(Integer.parseInt(infoTableInsert[2].getText())>5000){
                    new noticePopUp("물품 가액은 5000원을 넘길 수 없습니다.");
                    return;
                }       // 물품 가액 범위 오류 (>5000)
                if(Integer.parseInt(infoTableInsert[2].getText())<10){
                    new noticePopUp("물품 가액은 10원 이상으로 설정하여야 합니다.");
                    return;
                }       // 물품 가액 범위 오류 (<10)
                if(Integer.parseInt(infoTableInsert[2].getText())%10!=0){
                    new noticePopUp("물품 가액은 10원 단위로 설정하여야 합니다.");
                    return;
                }       // 물품 가액 범위 오류
                if(infoTableInsert[3].getText()
                        .equals(itemMap.getByIndex(itemList.getSelectedIndex()).getImagePath())){
                    imageLoadCheck = true;
                }       // 파일 경로 수정이 안되었다면 불러오기를 누르지 않아도 됨

                if(!imageLoadCheck){
                    new noticePopUp("이미지 파일 불러오기를 해주십시오.");
                    return;
                }
                Item buffer = new Item();
                buffer.setItemCode(Long.parseLong(infoTableInsert[0].getText()));
                buffer.setName(infoTableInsert[1].getText());
                buffer.setPrice(Integer.parseInt(infoTableInsert[2].getText()));
                buffer.setImage(infoTableInsert[3].getText());

                //editMode에 따라 신규추가 or 기존수정
                itemMap.set(itemList.getSelectedIndex(),buffer);


                for(int i=0;i<infoTableInsert.length;i++){
                    infoTableInsert[i].setText("");
                }
                infoTableInsert[0].setEditable(true);
                infoTableElement[4].setVisible(false);
                editButton[0].setVisible(false);
                editButton[1].setVisible(false);
                imageLoadCheck = false;

                refreshJList();

                MainClass.refreshStorageState();
                MainClass.refreshItemState();

                new noticePopUp("저장되었습니다.");
                if(editStorageNum!=-1){
                    refreshQueue(editStorageNum);
                }
                editStorageNum = -1;
            }
        });     // 내용 저장
        editButton[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<infoTableInsert.length;i++){
                    infoTableInsert[i].setText("");
                }
                infoTableElement[4].setVisible(false);
                editButton[0].setVisible(false);
                editButton[1].setVisible(false);
            }
        });     // 작성 취소

        productManageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                infoTableInsert[0].setText(
                        String.valueOf(itemMap.getByIndex(itemList.getSelectedIndex()).getItemCode()));
                for(int i=0;i<5;i++){
                    if(Long.parseLong(infoTableInsert[0].getText())==itemQueue[i].getItemCode()){
                        infoTableInsert[0].setEditable(false);
                        editStorageNum = i;
                    }
                }
                infoTableInsert[1].setText(
                        itemMap.getByIndex(itemList.getSelectedIndex()).getName());
                infoTableInsert[2].setText(
                        String.valueOf(itemMap.getByIndex(itemList.getSelectedIndex()).getPrice()));
                infoTableInsert[3].setText(
                        itemMap.getByIndex(itemList.getSelectedIndex()).getImagePath());
                infoTableElement[4].setIcon(new ImageIcon(infoTableInsert[3].getText()));
                infoTableElement[4].setVisible(true);
                editButton[0].setVisible(true);
                editButton[1].setVisible(true);

            }
        });
    }
    private void setBackground(){
        JPanel[] tableBackground = new JPanel[6];
        for(int i=0;i<6;i++){
            tableBackground[i] = new JPanel();
            tableBackground[i].setBounds(0, y-10+i*intervel, 620, 100);
            if(i%2==1)
                tableBackground[i].setBackground(new Color(255,255,255));
            else
                tableBackground[i].setBackground(new Color(230,230,230));
            contentPane.add(tableBackground[i]);
        }
        JPanel storagePanel = new JPanel();
        storagePanel.setBounds(0,100,1280,70);
        storagePanel.setBackground(new Color(200,203,215));
        contentPane.add(storagePanel);

        JPanel itemListPanel = new JPanel();
        itemListPanel.setBounds(620,y-10,150,550);
        itemListPanel.setBackground(Color.darkGray);
        contentPane.add(itemListPanel);
    }
    public ProductFrame(){
        initFrame();
        writeTitle();
        storageTable();
        ItemListTable();
        setBackground();
    }
}
