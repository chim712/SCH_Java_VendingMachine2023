package Main;

import AdminSys.AdminSysMain;
import AdminSys.system.Sales.*;
import Machine.Frame.mainFrame;
import Machine.Item.ItemKeyMap;
import Machine.Item.ItemStorageQueue;

import java.io.*;

import static Machine.Frame.InsertCashFrame.insertValue;
import static Machine.Money.MoneySystem.insertCashTotal;
import static Machine.Money.MoneySystem.refreshCashTotal;
import static Main.UserSetting.loadSetting;
import static Main.UserSetting.rewriteSetting;

public class MainClass {
    public static final String title = "2023 Java Programming - 20204053 임창환";
    public static final String version = "Version : 23H1 (23. 05.)";
    public static AdminSysMain adminSysMain;

    public static ItemKeyMap itemMap = new ItemKeyMap();
    public static ItemStorageQueue itemQueue[] = new ItemStorageQueue[5];


    //===================== 갱신 함수 ==================================

    public static void refreshCashState(){
        refreshCashTotal();
        mainFrame.refreshCashDisplay();
        if(insertValue!=null)
            insertValue.setText(insertCashTotal + " 원 ");
        if(adminSysMain!=null) {
            if (adminSysMain.adminSysLobby != null)
                adminSysMain.adminSysLobby.cashGraphRefresh();
            if(adminSysMain.adminSysLobby.cashFrame!=null)
                adminSysMain.adminSysLobby.cashFrame.cashGraphRefresh();
        }
    }
    public static void refreshItemState(){
        mainFrame.refreshItemState();
        if(adminSysMain!=null){
            if(adminSysMain.adminSysLobby!=null){
                adminSysMain.adminSysLobby.itemOverviewRefresh();
                if(adminSysMain.adminSysLobby.productFrame!=null)
                    adminSysMain.adminSysLobby.productFrame.refreshItemAmount();
            }
        }

    }
    public static void refreshStorageState(){
        if(adminSysMain.adminSysLobby.productFrame!=null)
            adminSysMain.adminSysLobby.productFrame.refreshStorageTable();
        
        mainFrame.refreshStorageState();
        /*if(adminSysMain!=null){
            if(adminSysMain.adminSysLobby!=null){
                if(adminSysMain.adminSysLobby.productFrame!=null)
                    adminSysMain.adminSysLobby.productFrame.refreshStorageTable();
            }
        }*/
    }
    public static void refreshSalesState(){
        if(adminSysMain.adminSysLobby!=null)
            adminSysMain.adminSysLobby.salesGraphRefresh();
    }
    public static void refreshQueue(int index){
        int loop = itemQueue[index].getCurrentSize();
        for(int i=0;i<loop;i++){
            itemQueue[index].dequeue();
        }
        for(int i=0;i<loop;i++){
            itemQueue[index].enqueue();
        }
    }




    //=======================
    public static void off(){
        rewriteSalesData();
        rewriteSetting();
    }

    private static void rewriteSalesData(){
        String filePath = "sales data.csv";

        try {
            // 파일 쓰기
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Node current = SalesMain.dataList.getHead();
            // 새로운 내용 출력
            for (int i=0;i< SalesMain.getListSize();i++) {
                SalesDataStruct currentData = current.data;
                String buffer = currentData.dataToString();
                bufferedWriter.write(buffer);
                bufferedWriter.newLine();
                System.out.println(buffer);
                current = current.next;
            }

            // 버퍼 비우고 닫기
            bufferedWriter.flush();
            bufferedWriter.close();

            System.out.println("\nsales data.csv 파일 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //========================== Main ==================================
    public static void main(String[] args) {
        loadSetting();

        new mainFrame();
        adminSysMain = new AdminSysMain();

        refreshCashState();
        refreshStorageState();
        refreshItemState();

    }
}



/* 5월 19일 할 일
 *
 * 매출 분석 일별, 월별 콘솔출력으로 일단 만들어놓ㄱ
 * 자판기 본체 디자인 완성해두기
 * => 현금투입구
 * => 상품배출구
 * => 버튼 종류
 *
 */