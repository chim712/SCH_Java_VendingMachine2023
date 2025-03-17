package Machine.Money;

import Machine.Item.Item;
import Main.noticePopUp;

import static Machine.Money.Bill.billType;
import static Machine.Money.Coin.coinType;

public class MoneySystem {
    public static InsertBillStack insertBillStack = new InsertBillStack();
    public static InsertCoinStack insertCoinStack = new InsertCoinStack();
    public static int tmpChangeValue=0;
    public static int insertCashTotal=0;
    public static void refreshCashTotal(){
        insertCashTotal = insertBillStack.stackTotal() + insertCoinStack.stackTotal() + tmpChangeValue;
        System.out.println("Total Refresh : " + insertCashTotal + "\n\n");
    }

    public static boolean isSell = false;



    public static void flushToMoneybox(Item item){
        int[] tmp = new int[]{0,0,0,0,0};
        int offsetValue = tmpChangeValue;

        //일단 동전은 다 flush한다.
        Coin bufferC;
        while((bufferC=insertCoinStack.pop())!=null){
            tmp[bufferC.getIndex()]++;
            offsetValue += bufferC.getValue();
        }

        //동전을 flush했음에도 지불액이 모자라면 지폐를 하나씩 pop 한다.
        while(offsetValue<item.getPrice()) {
                offsetValue += insertBillStack.pop().getValue();
                tmp[4]++;
        }
        //돈통에 flush한 동전들과 pop한 지폐를 넣는다.
        moneyBox.addCashAmount(tmp);

        //flush 한 동전과 pop한 지폐를 합친 값에서 item가격을 뺀다 - 동전 거스름돈 금액 임시 저장
        tmpChangeValue = offsetValue - item.getPrice();
        System.out.println("임시 거스름돈 : "+tmpChangeValue);
    }
    public static void restorePartly(int coin, int bill){
        int total = coin + bill;
        int[] restoreCoin = new int[]{0,0,0,0,0};
        for(int i= coinType.length-1;i>=0;i--){
            restoreCoin[i] = -(int)(coin/coinType[i]);
            coin += coinType[i]*restoreCoin[i];
        }
        boolean check = moneyBox.addCashAmount(restoreCoin);
        if(!check) {
            new noticePopUp("거스름돈이 모자랍니다.");
            return;
        }
        for(int i=0;i<bill/1000;i++) insertBillStack.pop();

        Coin bufferC;
        while((bufferC=insertCoinStack.pop())!=null){
            restoreCoin[bufferC.getIndex()]--;
            total += bufferC.getValue();
        }

        // 반환 금액 정산 완료하면 임시 거스름돈 값 초기화와 판매여부 초기화
        tmpChangeValue = 0;
        isSell = false;

        //반환할 금액이 없다면 반환금 없음 표시 후 함수 종료
        if(total<=0){
            new noticePopUp("반환할 금액이 없습니다.");
            return;
        }

        String flushList = "";
        for (int i=0;i<restoreCoin.length;i++){
            if(restoreCoin[i]<0) flushList+=(coinType[i]+"원 "+(-restoreCoin[i])+"개 ");
        }
        if(bill>0) flushList+=(billType[0]+"원 "+bill/1000+"개 ");
        new noticePopUp("투입 금액 "+total+"원 이 모두 반환되었습니다.",flushList);

        int[] tmp = moneyBox.getCashAmount();
        System.out.println("tmpChange : " + tmpChangeValue + "￦ / " + tmp[0]+", "+tmp[1]+", "+tmp[2]+", "+tmp[3]);
    }
    public static void restoreAll(){
        int[] coinAmount = {0,0,0,0};        // 10, 50, 100, 500원 순서
        int[] billAmount = {0};              // 1000원
        int total = insertCashTotal;
        if(total<=0) {
            new noticePopUp("반환할 금액이 없습니다.");
            return;
        }
        int sum = 0;
        Coin bufferC;
        Bill bufferB;
        while((bufferC=insertCoinStack.pop())!=null){
            coinAmount[bufferC.getIndex()]++;
            sum += bufferC.getValue();
        }
        while((bufferB=insertBillStack.pop())!=null){
            billAmount[bufferB.getIndex()]++;
            sum += bufferB.getValue();
        }
        String flushList = "";
        for (int i=0;i<coinAmount.length;i++){
            if(coinAmount[i]>0) flushList+=(coinType[i]+"원 "+coinAmount[i]+"개 ");
        }
        for (int i=0;i<billAmount.length;i++){
            if(billAmount[i]>0) flushList+=(billType[i]+"원 "+billAmount[i]+"개 ");
        }
        new noticePopUp("투입 금액 "+total+"원 이 모두 반횐되었습니다.",flushList);
    }
    public static void giveChange(int value){

    }

    //거스름돈 & 투입금 보관함 Field
    public static MoneyBox moneyBox = new MoneyBox();


}
