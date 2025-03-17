package AdminSys.system.Sales;

import AdminSys.AdminSysMain;

import javax.swing.*;
import java.util.Calendar;

public class DataAnalysis {
    private static int salesGoal = 2500;
    public static void setSalesGoal(int value){salesGoal = value;}
    private static int[] createDateList(int month){
        int date[];
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                date = new int[31];
                for(int i=0;i<31;i++) date[i] = 0;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                date = new int[30];
                for(int i=0;i<30;i++) date[i] = 0;
                break;
            case 2:
                date = new int[29]; // 윤년에 대한 정보 생략
                for(int i=0;i<29;i++) date[i] = 0;
                break;
            default:
                System.out.println("input error");
                date = new int[0];
                break;
        }
        for(int i=0;i<date.length;i++) date[i] = 0;
        return date;
    }   // 날짜목록(29개, 30개, 31개)를 생성한다.

    //============= 매출 금액 분석 ===================
    public static int[] analysisMonthToDayList(int year, int month){
        int[] date = createDateList(month);
        Node current = SalesMain.dataList.head;
        while(current!=null){
            if(current.data.time[1]==month&&current.data.time[0]==year){
                date[current.data.time[2]-1] += current.data.price;
            }
            current = current.next;
        }
        return date;
    }       // 지정 월의 매출 목록을 배열 형식으로 저장한다.
    public static int[] analysisMonthToDayList(int year, int month, long itemCode){
        int[] date = createDateList(month);
        Node current = SalesMain.dataList.head;
        while(current!=null){
            if((current.data.time[1]==month)
                    &&(current.data.time[0]==year)
                    &&(current.data.itemCode==itemCode)
            ){
                date[current.data.time[2]-1] += current.data.price;
            }
            current = current.next;
        }
        return date;
    }       // 지정 월의 매출 목록을 배열 형식으로 저장한다.
    public static int getDaySales(Calendar calendar){
        return getDaySales(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }       // 특정 날짜의 매출을 구한다. (Calenar 형식 매개변수)
    public static int getDaySales(int year, int month, int day){
        int DaySales = 0;
        //int insertDate[] = new int[] {year,month,day};
        Node current = SalesMain.dataList.head;
        while(current!=null){       //연월일이 일치하면 해당일자 매출합계에 합산
            if(current.data.time[0]==year && current.data.time[1]==month &&
                current.data.time[2]==day)     DaySales+=current.data.price;
            current = current.next;
        }
        return DaySales;
    }       // 특정 날짜의 매출을 구한다.
    public static int getDaySales(int year, int month, int day, long itemCode){
        int DaySales = 0;
        //int insertDate[] = new int[] {year,month,day};
        Node current = SalesMain.dataList.head;
        while(current!=null){       //연월일이 일치하면 해당일자 매출합계에 합산
            if(current.data.time[0]==year && current.data.time[1]==month &&
                    current.data.time[2]==day && current.data.itemCode==itemCode)     DaySales+=current.data.price;
            current = current.next;
        }
        return DaySales;
    }       // 특정 날짜의 매출을 구한다.

    //============== 매출 금액 합계 산출 ===============

    //전월 실적을 구할 때에는 마지막 날짜까지 구함
    //2,4,6,9,11월은 31일이 없으나 값이 무시되므로 동작에는 문제가 없음
    public static int getMonthSales(int year, int month){
        int MonthSales = 0;
        for(int i=1;i<=31;i++){
            MonthSales += getDaySales(year,month,i);
        }
        return MonthSales;
    }   // 특정 월의 매출합계를 구한다.
    public static int getMonthSales(int year, int month, long itemCode){
        int MonthSales = 0;
        for(int i=1;i<=31;i++){
            MonthSales += getDaySales(year,month,i,itemCode);
        }
        return MonthSales;
    }   // 특정 월의 지정 상품 매출합계를 구한다.

    // 당월 실적을 구할 때에는 범위를 오늘까지로 정함
    public static int getMonthSales(Calendar calendar){
        return getMonthSales(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    } // 특정 월의 매출합계(range일까지)를 구한다.(Calendar 매개변수)
    public static int getMonthSales(int year, int month, int range){
        int MonthSales = 0;
        for(int i=1;i<=range;i++){
            int tmp = getDaySales(year,month,i);
            MonthSales += tmp;
        }
        return MonthSales;
    }    // 특정 월의 매출합계(range일까지)를 구한다.

    //=============== 목표 금액 조회 및 달성률 확인 =================
    public static int getSalesGoal(){return salesGoal;}

    public static int getDayGoal(Calendar calendar){
        return getDaySales(calendar) *100 /salesGoal;
    }
    public static int getMonthGoal(int year, int month){
        return getMonthSales(year,month) *100 /(salesGoal*30);
    }
    public static int getMonthGoal(Calendar calendar){
        return getMonthSales(calendar) *100 /(salesGoal*30);
    }
    public static int getMonthGoal(int year, int month, int range){
        return getMonthSales(year,month,range) *100 /(salesGoal*range);
    }
}
