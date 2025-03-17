package AdminSys.system.Sales;

import static Main.MainClass.itemMap;

public class DataList {
    Node head;
    Node tail;

    public void printList(){
        Node Current = head;
        while(Current!=null){
            Current.data.printData();
            Current = Current.next;
        }
    }

    public void newNode(){
        newNode(null);
    }
    public void newNode(SalesDataStruct data){
        if(head==null) head = tail = new Node(data);
        else{
            tail.next = new Node(data);
            tail = tail.next;
        }
    }

    public Node getHead(){return head;}


    //================= Sort Method ======================

    public void sortList() {
        if (head == null || head.next == null) {
            // 리스트에 노드가 0개 또는 1개인 경우 정렬할 필요 없음
            return;
        }

        boolean isSorted = false;
        Node lastNode = null;

        while (!isSorted) {
            isSorted = true;
            Node current = head;
            Node previous = null;

            while (current.next != lastNode) {
                Node nextNode = current.next;

                // 현재 노드의 시간과 다음 노드의 시간을 비교하여 정렬
                if (compareSalesDataStruct(current.data, nextNode.data) > 0) {
                    if (previous == null) {
                        // 현재 노드가 head일 경우
                        head = nextNode;
                    } else {
                        previous.next = nextNode;
                    }

                    current.next = nextNode.next;
                    nextNode.next = current;

                    previous = nextNode;
                    nextNode = current.next;
                    isSorted = false;
//                    System.out.println("\nSort \n"+
//                            current.data.time[0]+"-"+current.data.time[1]+"-"+current.data.time[2]+"-"+
//                            current.data.time[3]+":"+current.data.time[4]+":"+current.data.time[5]+"-"
//                            +itemMap.get(current.data.itemCode).getName()+"\n&\n"+
//                            nextNode.data.time[0]+"-"+nextNode.data.time[1]+"-"+nextNode.data.time[2]+"-"+
//                            nextNode.data.time[3]+":"+nextNode.data.time[4]+":"+nextNode.data.time[5]+"-"
//                            +itemMap.get(nextNode.data.itemCode).getName());
                } else {
                    previous = current;
                    current = nextNode;
                    nextNode = nextNode.next;
                }
            }

            lastNode = current;
        }
    }

    private int compareSalesDataStruct(SalesDataStruct data1, SalesDataStruct data2) {
        // SalesDataStruct의 시간(time)을 기준으로 비교하여 정렬
        for (int i = 0; i < 6; i++) {
            if (data1.time[i] < data2.time[i]) {
                return -1;
            } else if (data1.time[i] > data2.time[i]) {
                return 1;
            }
        }

        return 0;
    }

    //====================================================

    DataList(){
        head = tail = null;
    }
}
