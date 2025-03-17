package Machine.Item;

import AdminSys.system.Sales.SalesDataStruct;
import AdminSys.system.Sales.SalesMain;
import Main.noticePopUp;

import java.util.Calendar;

import static AdminSys.Time.getToday;
import static Machine.Money.MoneySystem.*;
import static Main.MainClass.*;

public class SellingSystem {
    public static void sell(int storageNum){
        if (insertCashTotal< itemMap.get(itemQueue[storageNum].getItemCode()).getPrice()){
            System.out.println("금액 부족 (무반응)");
            return;
        }
        if(itemQueue[storageNum].getCurrentSize()<=0){
            System.out.println("재고 부족 (무반응)");
            return;
        }
        isSell = true;
        Item tmp = itemQueue[storageNum].dequeue();
        new noticePopUp(tmp.getName() + " (" + tmp.getPrice() + "원) 을 구매하였습니다.");
        record(tmp);
        flushToMoneybox(tmp);
        refreshCashState();
        refreshItemState();
        refreshSalesState();

    }
    public static void record(Item item){
        Calendar now = getToday();
        int[] calendarTmp = new int[6];
        calendarTmp[0] = now.get(Calendar.YEAR);
        calendarTmp[1] = now.get(Calendar.MONTH);
        calendarTmp[2] = now.get(Calendar.DAY_OF_MONTH);
        calendarTmp[3] = now.get(Calendar.HOUR_OF_DAY);
        calendarTmp[4] = now.get(Calendar.MINUTE);
        calendarTmp[5] = now.get(Calendar.SECOND);
        for(int i:calendarTmp){
            System.out.print(i+"  ");
        }
        System.out.println();
        SalesDataStruct buffer = new SalesDataStruct(item.getItemCode(),item.getPrice(),calendarTmp);
        SalesMain.dataList.newNode(buffer);
        SalesMain.increaseListSize();
    }
}
