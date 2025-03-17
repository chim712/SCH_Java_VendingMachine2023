package Machine.Money;

import Main.noticePopUp;

import static Machine.Money.MoneySystem.insertCashTotal;

public class InsertCoinStack{
    //Field
    private  final int MAX_COIN_AMOUNT = 30;
    private  final int MAX_COIN_SUM = 2000;
    private  int top = -1;
    private  Coin[] coinStack = new Coin[MAX_COIN_AMOUNT];

    //Method
    public int stackTotal(){
        if(isEmpty()) return 0;
        int sum=0;
        for(int i=0;i<=top;i++) sum+=coinStack[i].getValue();
        System.out.println("Coin Stack : "+sum+"￦");
        return sum;
    }       // 스택 내 금액 합계 계산
    public boolean isFull(){
        if(top>=MAX_COIN_AMOUNT-1) return true;
        if(stackTotal()>=MAX_COIN_SUM) return true;
        return false;
    }
    public boolean isEmpty(){ return top<=-1; }
    public int getSize(){ return top+1; }
    public  void insertCoin(Coin coin){
        if(isFull()||(coin.getValue()+stackTotal())>MAX_COIN_SUM) {
            new noticePopUp("동전 입력 한도를 초과하였습니다.");
            return;
        }
        if(insertCashTotal+coin.getValue()>5000) {
            new noticePopUp("현금 최대 입력 한도를 초과합니다.");
            return;
        }
        coinStack[++top] = coin;
        System.out.print(top+"에 "+coin.getValue()+" 투입");
    }   // push와 동일한 역할
    public  Coin pop(){
        if(isEmpty()) return null;
        Coin tmp = coinStack[top];
        System.out.println((top)+"에 있던 "+peek().getValue()+" 반환");
        coinStack[top--] = null;    //동적 할당 해제
        System.gc();
        return tmp;
    }
    public  Coin peek(){
        if(isEmpty()) return null;
        return coinStack[top];
    }

}