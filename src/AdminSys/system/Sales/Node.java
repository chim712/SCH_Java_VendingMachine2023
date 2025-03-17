package AdminSys.system.Sales;

public class Node {
    public SalesDataStruct data;
    public Node next;

    Node() {
        this(null);
    }   //기본 생성자는 data가 null

    Node(SalesDataStruct data) {
        this.data = data;
        next = null;
    }   //데이터 지정 시 해당 데이터 입력
}
