package AdminSys.system.Sales;

import AdminSys.Time;
import Main.MainClass;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static AdminSys.system.Sales.DataAnalysis.getDaySales;
import static AdminSys.system.Sales.DataAnalysis.getMonthSales;
import static AdminSys.system.Sales.SalesMain.*;
import static Main.MainClass.itemMap;
import static Main.MainClass.itemQueue;

public class SalesManageFrame extends JFrame {

    private Container contentPane = getContentPane();
    private JScrollPane dateTableScrollDateA;
    private JScrollPane dateTableScrollDateB;
    private JScrollPane dateTableScrollItemA;
    private JScrollPane dateTableScrollItemB;

    private void writeTitle(){

        JLabel subTitle = new JLabel(MainClass.title);
        subTitle.setFont(new Font("Malgun Gothic",Font.ITALIC,20));
        subTitle.setForeground(Color.LIGHT_GRAY);
        subTitle.setBounds(20,7,700,30);
        contentPane.add(subTitle);

        JLabel mainTitle = new JLabel("매출 관리");
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
        titleTime.setBounds(820,45,400,50);
        contentPane.add(titleTime);

        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0,0,1280,100);
        titlePanel.setBackground(new Color(30,70,100));
        contentPane.add(titlePanel);

        String[] buttonName = new String[]{"전체 목록","기간별","상품별"};
        JButton[] menuButton = new JButton[buttonName.length];
        for(int i=0;i<menuButton.length;i++){
            menuButton[i] = new JButton(buttonName[i]);
            menuButton[i].setBorderPainted(false); //버튼 테두리 삭제
            menuButton[i].setBounds(10,115+i*70,180,45);
            menuButton[i].setFont(new Font("Malgun Gothic",Font.PLAIN,20));
            contentPane.add(menuButton[i]);
        }
        menuButton[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("OverviewMode");
                setOverviewMode();
            }
        });
        menuButton[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDateMode();
                System.out.println("DateMode");
            }
        });
        menuButton[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setItemMode();
                System.out.println("ItemMode");
            }
        });

        JPanel menuBar = new JPanel();
        menuBar.setBounds(0,100,200,620);
        menuBar.setBackground(Color.darkGray);
        contentPane.add(menuBar);
    }       // 상단 타이틀 작성
    private void initFrame(){
        setTitle("Sales Management");
        setSize(1280,720);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setLayout(null);

        setVisible(true);
    }
    private void writeDateModeTitle(){
        int y = 110;

        JTextArea yearInsert = new JTextArea(String.valueOf(Time.getTodayYear()));
        yearInsert.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        yearInsert.setBounds(270,y,100,40);
        contentPane.add(yearInsert);

        JLabel year = new JLabel("년");
        year.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        year.setBounds(380,y,40,40);
        contentPane.add(year);

        JTextArea monthInsert = new JTextArea(String.valueOf(Time.getTodayMonth()));
        monthInsert.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        monthInsert.setBounds(590,y,30,40);
        contentPane.add(monthInsert);

        JLabel month = new JLabel("월");
        month.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        month.setBounds(630,y,40,40);
        contentPane.add(month);


        JButton yPlus = new JButton("+");
        yPlus.setBounds(210,y,50,40);
        yPlus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        yPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearInsert.setText(String.valueOf(Integer.parseInt(yearInsert.getText())+1));
            }
        });
        contentPane.add(yPlus);

        JButton yMinus = new JButton("-");
        yMinus.setBounds(410,y,50,40);
        yMinus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        yMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearInsert.setText(String.valueOf(Integer.parseInt(yearInsert.getText())-1));
            }
        });
        contentPane.add(yMinus);

        JButton mPlus = new JButton("+");
        mPlus.setBounds(520,y,50,40);
        mPlus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        mPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(monthInsert.getText())>=12) {
                    monthInsert.setText("1");
                    yearInsert.setText(
                            String.valueOf(Integer.parseInt(yearInsert.getText())+1)
                    );
                }
                else
                    monthInsert.setText(String.valueOf(Integer.parseInt(monthInsert.getText())+1));
            }
        });
        contentPane.add(mPlus);

        JButton mMinus = new JButton("-");
        mMinus.setBounds(660,y,50,40);
        mMinus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        mMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(monthInsert.getText())<=1) {
                    monthInsert.setText("12");
                    yearInsert.setText(
                            String.valueOf(Integer.parseInt(yearInsert.getText())-1)
                    );
                }
                else
                    monthInsert.setText(String.valueOf(Integer.parseInt(monthInsert.getText())-1));
            }
        });
        contentPane.add(mMinus);

        JLabel todayMonth = new JLabel(monthInsert.getText() + " ");
        todayMonth.setFont(new Font("Malgun Gothic",Font.ITALIC,40));
        todayMonth.setHorizontalAlignment(SwingConstants.RIGHT);
        todayMonth.setBounds(850,y-5,80,40);
        contentPane.add(todayMonth);

        JLabel monthTotal = new JLabel("월 총 매출 : ");
        monthTotal.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        monthTotal.setBounds(930,y,150,40);
        contentPane.add(monthTotal);

        JLabel monthSales = new JLabel(
                getMonthSales(Integer.parseInt(yearInsert.getText()),Integer.parseInt(monthInsert.getText())) + " ");
        monthSales.setFont(new Font("Malgun Gothic",Font.ITALIC,40));
        monthSales.setHorizontalAlignment(SwingConstants.RIGHT);
        monthSales.setBounds(1050,y-5,150,40);
        contentPane.add(monthSales);

        JLabel won = new JLabel("원");
        won.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        won.setBounds(1200,y,50,40);
        contentPane.add(won);

        JButton find = new JButton("조회");
        find.setBounds(770,y,80,40);
        find.setFont(new Font("Malgun Gothic",Font.BOLD,18));
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!yearInsert.getText().matches("\\d+"))
                    yearInsert.setText(String.valueOf(Time.getTodayYear()));
                if (!monthInsert.getText().matches("\\d+"))
                    monthInsert.setText(String.valueOf(Time.getTodayMonth()));
                if(Integer.parseInt(monthInsert.getText())<1) monthInsert.setText("1");
                if(Integer.parseInt(monthInsert.getText())>12) monthInsert.setText("12");
                contentPane.remove(dateTableScrollDateA);
                contentPane.remove(dateTableScrollDateB);
                contentPane.repaint();
                drawDateTableA(Integer.parseInt(yearInsert.getText()),Integer.parseInt(monthInsert.getText()));
                drawDateTableB(Integer.parseInt(yearInsert.getText()),Integer.parseInt(monthInsert.getText()));
                todayMonth.setText(monthInsert.getText() + " ");
                monthSales.setText(
                        getMonthSales(Integer.parseInt(yearInsert.getText()),Integer.parseInt(monthInsert.getText())) + " ");
            }
        });
        contentPane.add(find);

    }
    private void drawOverviewTable(){
        String[] dateTitle = new String[]{"ItemCode","ItemName","Price","SoldDate"};
        String[][] tmp = getListToStringArr();
        String[][] dateTableCell = new String[tmp.length][4];

        for(int i=0;i<getListSize();i++){
            dateTableCell[i][0] = tmp[i][0];
            dateTableCell[i][1] = itemMap.get(Long.parseLong(tmp[i][0])).getName();
            dateTableCell[i][2] = tmp[i][1];
            dateTableCell[i][3] = tmp[i][2]+" - "+
                            String.format("%02d", Integer.parseInt(tmp[i][3]))+" - "+
                            String.format("%02d", Integer.parseInt(tmp[i][4]))+"     "+
                            String.format("%02d", Integer.parseInt(tmp[i][5]))+" : "+
                            String.format("%02d", Integer.parseInt(tmp[i][6]))+" : "+
                            String.format("%02d", Integer.parseInt(tmp[i][7]));
        }

        JTable dateTableAll = new JTable(dateTableCell,dateTitle);
        dateTableAll.setFont(new Font("Malgun Gothic",Font.PLAIN,12));

        TableColumnModel columnModel = dateTableAll.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(300); // 첫 번째 열의 넓이를 300으로 설정
        columnModel.getColumn(1).setPreferredWidth(200); // 두 번째 열의 넓이를 200으로 설정
        columnModel.getColumn(2).setPreferredWidth(150); // 세 번째 열의 넓이를 150으로 설정
        columnModel.getColumn(3).setPreferredWidth(400); // 세 번째 열의 넓이를 400으로 설정

        JScrollPane dateTableScrollAll = new JScrollPane(dateTableAll);
        dateTableScrollAll.setPreferredSize(new Dimension(1050,580));
        dateTableScrollAll.setBounds(200,100,1050,580);
        contentPane.add(dateTableScrollAll);
    }
    private void drawDateTableA(int year, int month){
        String[] dateTitle = new String[]{"ItemCode","ItemName","Price","SoldDate"};
        String[][] tmp = getListFindMonth(year,month);
        String[][] dateTableCell = new String[tmp.length][4];

        for(int i=0;i<tmp.length;i++){
            dateTableCell[i][0] = tmp[i][0];
            dateTableCell[i][1] = itemMap.get(Long.parseLong(tmp[i][0])).getName();
            dateTableCell[i][2] = tmp[i][1];
            dateTableCell[i][3] = tmp[i][2]+" - "+
                    String.format("%02d", Integer.parseInt(tmp[i][3]))+" - "+
                    String.format("%02d", Integer.parseInt(tmp[i][4]))+"     "+
                    String.format("%02d", Integer.parseInt(tmp[i][5]))+" : "+
                    String.format("%02d", Integer.parseInt(tmp[i][6]))+" : "+
                    String.format("%02d", Integer.parseInt(tmp[i][7]));
        }

        JTable dateTable = new JTable(dateTableCell,dateTitle);
        dateTable.setFont(new Font("Malgun Gothic",Font.PLAIN,12));

        TableColumnModel columnModel = dateTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // 첫 번째 열의 넓이를 300으로 설정
        columnModel.getColumn(1).setPreferredWidth(100); // 두 번째 열의 넓이를 200으로 설정
        columnModel.getColumn(2).setPreferredWidth(100); // 세 번째 열의 넓이를 150으로 설정
        columnModel.getColumn(3).setPreferredWidth(300); // 세 번째 열의 넓이를 400으로 설정

        dateTableScrollDateA = new JScrollPane(dateTable);
        dateTableScrollDateA.setPreferredSize(new Dimension(700,520));
        dateTableScrollDateA.setBounds(200,160,700,520);
        contentPane.add(dateTableScrollDateA);
    }
    private void drawDateTableB(int year, int month){
        String[] dateTitle = new String[]{"Year","Month","Day","Sales"};
        int[] tmp = DataAnalysis.analysisMonthToDayList(year,month);

        String[][] dateTableCell = new String[tmp.length][4];
        for(int i=0;i< tmp.length;i++){
            dateTableCell[i][0] = String.valueOf(year);
            dateTableCell[i][1] = String.valueOf(month);
            dateTableCell[i][2] = String.valueOf(i+1);
            dateTableCell[i][3] = String.valueOf(tmp[i]);
        }

        JTable dateTable = new JTable(dateTableCell,dateTitle);
        dateTable.setFont(new Font("Malgun Gothic",Font.PLAIN,12));

        TableColumnModel columnModel = dateTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // 첫 번째 열의 넓이를 300으로 설정
        columnModel.getColumn(1).setPreferredWidth(50); // 두 번째 열의 넓이를 200으로 설정
        columnModel.getColumn(2).setPreferredWidth(50); // 세 번째 열의 넓이를 150으로 설정
        columnModel.getColumn(3).setPreferredWidth(100); // 세 번째 열의 넓이를 400으로 설정

        dateTableScrollDateB = new JScrollPane(dateTable);
        dateTableScrollDateB.setPreferredSize(new Dimension(280,520));
        dateTableScrollDateB.setBounds(950,160,280,520);
        contentPane.add(dateTableScrollDateB);
    }
    private void writeItemModeTitle(){
        int y1 = 110, y2 = 170;

        JComboBox<String> itemComboBox = new JComboBox<>();
        long[] itemList = itemMap.getItemCode();
        for(int i=0;i<itemList.length;i++){
            if(itemList[i]!=0){
                itemComboBox.insertItemAt(itemMap.get(itemList[i]).getName(),i);
            }
        }
        itemComboBox.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        itemComboBox.setBounds(210, y2,500,40);
        contentPane.add(itemComboBox);


        JTextArea yearInsert = new JTextArea(String.valueOf(Time.getTodayYear()));
        yearInsert.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        yearInsert.setBounds(270,y1,100,40);
        contentPane.add(yearInsert);

        JLabel year = new JLabel("년");
        year.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        year.setBounds(380,y1,40,40);
        contentPane.add(year);

        JTextArea monthInsert = new JTextArea(String.valueOf(Time.getTodayMonth()));
        monthInsert.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        monthInsert.setBounds(590,y1,30,40);
        contentPane.add(monthInsert);

        JLabel month = new JLabel("월");
        month.setFont(new Font("Malgun Gothic",Font.PLAIN,25));
        month.setBounds(630,y1,40,40);
        contentPane.add(month);


        JButton yPlus = new JButton("+");
        yPlus.setBounds(210,y1,50,40);
        yPlus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        yPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearInsert.setText(String.valueOf(Integer.parseInt(yearInsert.getText())+1));
            }
        });
        contentPane.add(yPlus);

        JButton yMinus = new JButton("-");
        yMinus.setBounds(410,y1,50,40);
        yMinus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        yMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yearInsert.setText(String.valueOf(Integer.parseInt(yearInsert.getText())-1));
            }
        });
        contentPane.add(yMinus);

        JButton mPlus = new JButton("+");
        mPlus.setBounds(520,y1,50,40);
        mPlus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        mPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(monthInsert.getText())>=12) {
                    monthInsert.setText("1");
                    yearInsert.setText(
                            String.valueOf(Integer.parseInt(yearInsert.getText())+1)
                    );
                }
                else
                    monthInsert.setText(String.valueOf(Integer.parseInt(monthInsert.getText())+1));
            }
        });
        contentPane.add(mPlus);

        JButton mMinus = new JButton("-");
        mMinus.setBounds(660,y1,50,40);
        mMinus.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        mMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(monthInsert.getText())<=1) {
                    monthInsert.setText("12");
                    yearInsert.setText(
                            String.valueOf(Integer.parseInt(yearInsert.getText())-1)
                    );
                }
                else
                    monthInsert.setText(String.valueOf(Integer.parseInt(monthInsert.getText())-1));
            }
        });
        contentPane.add(mMinus);

        JLabel todayMonth = new JLabel(monthInsert.getText() + " ");
        todayMonth.setFont(new Font("Malgun Gothic",Font.ITALIC,40));
        todayMonth.setHorizontalAlignment(SwingConstants.RIGHT);
        todayMonth.setBounds(850,y1-5,80,40);
        contentPane.add(todayMonth);

        JLabel monthTotal = new JLabel("월 총 매출 : ");
        monthTotal.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        monthTotal.setBounds(930,y1,150,40);
        contentPane.add(monthTotal);

        JLabel monthSales = new JLabel(
                getMonthSales(
                        Integer.parseInt(yearInsert.getText()),
                        Integer.parseInt(monthInsert.getText()))

                + " ");
        monthSales.setFont(new Font("Malgun Gothic",Font.ITALIC,40));
        monthSales.setHorizontalAlignment(SwingConstants.RIGHT);
        monthSales.setBounds(1050,y1-5,150,40);
        contentPane.add(monthSales);

        JLabel won = new JLabel("원");
        won.setFont(new Font("Malgun Gothic",Font.BOLD,20));
        won.setBounds(1200,y1,50,40);
        contentPane.add(won);

        JButton find = new JButton("조회");
        find.setBounds(770,y1,80,100);
        find.setFont(new Font("Malgun Gothic",Font.BOLD,18));
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!yearInsert.getText().matches("\\d+"))
                    yearInsert.setText(String.valueOf(Time.getTodayYear()));
                if (!monthInsert.getText().matches("\\d+"))
                    monthInsert.setText(String.valueOf(Time.getTodayMonth()));
                if(Integer.parseInt(monthInsert.getText())>=1&&Integer.parseInt(monthInsert.getText())<=12);
                else if(Integer.parseInt(monthInsert.getText())<1) monthInsert.setText("1");
                else if(Integer.parseInt(monthInsert.getText())>12) monthInsert.setText("12");
                else monthInsert.setText("1");
                contentPane.remove(dateTableScrollItemA);
                contentPane.remove(dateTableScrollItemB);
                contentPane.repaint();
                try{
                    drawItemTableA(
                            Integer.parseInt(yearInsert.getText()),
                            Integer.parseInt(monthInsert.getText()),
                            itemList[itemComboBox.getSelectedIndex()]
                    );
                    drawItemTableB(
                            Integer.parseInt(yearInsert.getText()),
                            Integer.parseInt(monthInsert.getText()),
                            itemList[itemComboBox.getSelectedIndex()]
                    );
                    monthSales.setText(
                            getMonthSales(
                                    Integer.parseInt(yearInsert.getText()),
                                    Integer.parseInt(monthInsert.getText()),
                                    itemList[itemComboBox.getSelectedIndex()]
                            ) + " ");
                }catch (ArrayIndexOutOfBoundsException exception){
                    //아무것도 선택을 안한 경우이므로 표를 그리지 않고 놔둠
                    monthSales.setText("");       //월 매출 부분만 삭제
                }
                todayMonth.setText(monthInsert.getText() + " ");
            }
        });
        contentPane.add(find);

    }
    private void drawItemTableA(int year, int month, long itemCode){
        String[] itemTitle = new String[]{"ItemCode","ItemName","Price","SoldDate"};
        String[][] tmp = getListFindItem(year,month, itemCode);
        System.out.println("tmp length : "+tmp.length);
        String[][] itemTableCell = new String[tmp.length][4];

        for(int i=0;i<tmp.length;i++){
            itemTableCell[i][0] = tmp[i][0];
            itemTableCell[i][1] = itemMap.get(Long.parseLong(tmp[i][0])).getName();
            itemTableCell[i][2] = tmp[i][1];
            itemTableCell[i][3] = tmp[i][2]+" - "+
                    String.format("%02d", Integer.parseInt(tmp[i][3]))+" - "+
                    String.format("%02d", Integer.parseInt(tmp[i][4]))+"     "+
                    String.format("%02d", Integer.parseInt(tmp[i][5]))+" : "+
                    String.format("%02d", Integer.parseInt(tmp[i][6]))+" : "+
                    String.format("%02d", Integer.parseInt(tmp[i][7]));
        }

        JTable itemTable = new JTable(itemTableCell,itemTitle);
        itemTable.setFont(new Font("Malgun Gothic",Font.PLAIN,12));

        TableColumnModel columnModel = itemTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // 첫 번째 열의 넓이를 300으로 설정
        columnModel.getColumn(1).setPreferredWidth(100); // 두 번째 열의 넓이를 200으로 설정
        columnModel.getColumn(2).setPreferredWidth(100); // 세 번째 열의 넓이를 150으로 설정
        columnModel.getColumn(3).setPreferredWidth(300); // 세 번째 열의 넓이를 400으로 설정

        dateTableScrollItemA = new JScrollPane(itemTable);
        dateTableScrollItemA.setPreferredSize(new Dimension(700,460));
        dateTableScrollItemA.setBounds(200,220,700,460);
        contentPane.add(dateTableScrollItemA);
    }
    private void drawItemTableB(int year, int month, long itemCode){
        String[] dateTitle = new String[]{"Year","Month","Day","Sales"};
        int[] tmpB = DataAnalysis.analysisMonthToDayList(year,month,itemCode);

        String[][] dateTableCell = new String[tmpB.length][4];
        for(int i=0;i< tmpB.length;i++){
            dateTableCell[i][0] = String.valueOf(year);
            dateTableCell[i][1] = String.valueOf(month);
            dateTableCell[i][2] = String.valueOf(i+1);
            dateTableCell[i][3] = String.valueOf(tmpB[i]);
        }

        JTable dateTable = new JTable(dateTableCell,dateTitle);
        dateTable.setFont(new Font("Malgun Gothic",Font.PLAIN,12));

        TableColumnModel columnModel = dateTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80); // 첫 번째 열의 넓이를 300으로 설정
        columnModel.getColumn(1).setPreferredWidth(50); // 두 번째 열의 넓이를 200으로 설정
        columnModel.getColumn(2).setPreferredWidth(50); // 세 번째 열의 넓이를 150으로 설정
        columnModel.getColumn(3).setPreferredWidth(100); // 세 번째 열의 넓이를 400으로 설정

        dateTableScrollItemB = new JScrollPane(dateTable);
        dateTableScrollItemB.setPreferredSize(new Dimension(280,520));
        dateTableScrollItemB.setBounds(950,160,280,520);
        contentPane.add(dateTableScrollItemB);
    }


    private void setOverviewMode(){
        contentPane.removeAll();
        contentPane.repaint();
        writeTitle();
        drawOverviewTable();
    }
    private void setDateMode(){
        contentPane.removeAll();
        contentPane.repaint();
        writeTitle();
        writeDateModeTitle();
        drawDateTableA(Time.getTodayYear(), Time.getTodayMonth());
        drawDateTableB(Time.getTodayYear(), Time.getTodayMonth());
    }
    private void setItemMode(){
        contentPane.removeAll();
        contentPane.repaint();
        writeTitle();
        writeItemModeTitle();
        drawItemTableA(Time.getTodayYear(),Time.getTodayMonth(),itemQueue[0].getItemCode());
        drawItemTableB(Time.getTodayYear(),Time.getTodayMonth(),itemQueue[0].getItemCode());
    }
    public SalesManageFrame(){
        initFrame();
        setDateMode();
    }

}
