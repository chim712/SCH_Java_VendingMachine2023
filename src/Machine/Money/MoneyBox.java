package Machine.Money;

import Main.MainClass;
import Main.noticePopUp;

import static Machine.Money.Coin.coinType;

public class MoneyBox {
    private int[] moneyBox;    //10, 50, 100, 500, 1000 순
    private int coinMinimun = 15;   //15개 이상일 때 부터 수금 가능

    //    private int coin10;
//    private int coin50;
//    private int coin100;
//    private int coin500;
//    private int bill1000;
    public void setCashAmount(int c10, int c50, int c100, int c500, int b1000) {
        moneyBox = new int[]{c10, c50, c100, c500, b1000};
    }
    public void addCashAmount(int c10, int c50, int c100, int c500, int b1000) {
        int[] insert = new int[]{c10, c50, c100, c500, b1000};
        int[] tmp = moneyBox;
        for(int i=0;i<tmp.length;i++){
            if(tmp[i]+insert[i]>150){
                new noticePopUp("동전은 최대 150개까지만 보충 가능합니다.");
                return;
            }
        }
        addCashAmount(insert);
    }
    public boolean addCashAmount(int[] insert) {
        for (int i = 0; i < moneyBox.length; i++) {            //잔돈 갯수 검증
            if (moneyBox[i] + insert[i] < 0) {
                new noticePopUp("잔돈 개수가 모자랍니다.");
                System.out.println("잔돈 갯수가 모자랍니다.");
                return false;
            }
        }
        for (int i = 0; i < moneyBox.length; i++) moneyBox[i] += insert[i];
        System.out.println("10 : " + moneyBox[0]
                + "/ 50 : " + moneyBox[1]
                + "/ 100 : " + moneyBox[2]
                + "/ 500 : " + moneyBox[3]);
        return true;
    }
    public boolean checkEnough(){
        boolean check = true;
        for(int i=0;i<4;i++){
            if(moneyBox[i]<5) check = false;
        }
        return check;
    }
    public void collectBill(){
        if(moneyBox[4]>0){
            new noticePopUp("1000원권 지폐"+moneyBox[4]+"장","(총 "+moneyBox[4]*1000+"원) 을 회수하였습니다.");
            moneyBox[4]=0;
            MainClass.refreshCashState();
        }
        else{
            new noticePopUp("회수할 지폐가 없습니다.");
        }
    }
    public void collectCoin(){
        int[] tmp = {0, 0, 0, 0};
        String returnAmount = "";
        for(int i=0;i<4;i++){
            if(moneyBox[i]>coinMinimun){
                tmp[i] = moneyBox[i]-coinMinimun;   //임시 배열에 최소 잔량을 제외한 개수 삽입
                moneyBox[i] = coinMinimun;          //거스름돈 동전 보관함에는 최소 수량만 남김
                returnAmount+=(coinType[i]+"원 "+tmp[i]+"개  ");
            }
        }

        if(returnAmount==""){
            new noticePopUp("회수할 동전이 없습니다.","동전 최소 잔여 수량 : "+coinMinimun+" 개");
        } else {
            new noticePopUp("최소 잔여수량 ("+coinMinimun+" 개) 을 제외하고 회수하였습니다.",returnAmount);
        }
        MainClass.refreshCashState();
    }

    public int[] getCashAmount() {
        return moneyBox;
    }

    MoneyBox() {
        setCashAmount(5, 5, 5, 5, 0);
    }

    MoneyBox(int c10, int c50, int c100, int c500, int b1000) {
        setCashAmount(c10, c50, c100, c500, b1000);
    }
}       //  거스름돈통 & 1000원권 보관함
