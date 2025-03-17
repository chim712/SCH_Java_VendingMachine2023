package Machine.Money;

import Main.noticePopUp;

import static Machine.Money.MoneySystem.insertCashTotal;

public class InsertBillStack {
    //Field
    private final int MAX_BILL_AMOUNT = 3;
    private  final int MAX_BILL_SUM = 3000;
    private int top = -1;
    private Bill[] billStack = new Bill[MAX_BILL_AMOUNT];

    //Method
    public int stackTotal(){
        if(isEmpty()) return 0;
        int sum=0;
        for(int i=0;i<=top;i++) sum+=billStack[i].getValue();
        System.out.println("Bill Stack : "+sum+"￦");
        return sum;
    }       // 스택 내 금액 합계 계산

    public boolean isFull() {
        if (top == MAX_BILL_AMOUNT-1) return true;
        if (stackTotal()>=MAX_BILL_SUM) return true;
        return false;
    }

    public boolean isEmpty() {
        return top <= -1;
    }

    public void insertBill(Bill bill) {
        if (isFull()) {
            new noticePopUp("지폐 입력 한도를 초과하였습니다.");
            return;
        }
        if(insertCashTotal+bill.getValue()>5000) {
            new noticePopUp("현금 최대 입력 한도를 초과합니다.");
            return;
        }
        billStack[++top] = bill;
        System.out.println(top + "에 " + bill.getValue() + "push");
    }   // push와 동일한 역할

    public Bill pop() {
        if (isEmpty()) return null;
        Bill tmp = billStack[top];
        billStack[top--] = null;    //동적 할당 해제
        System.gc();
        return tmp;
    }

    public Bill peek() {
        if (isEmpty()) return null;
        return billStack[top];
    }
}
