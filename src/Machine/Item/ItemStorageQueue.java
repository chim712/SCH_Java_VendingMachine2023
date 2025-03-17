package Machine.Item;

import static Main.MainClass.itemMap;

// 아이템을 저장하는 원형 Queue 를 표현하는 Class
public class ItemStorageQueue {
    /* Queue List ADT Method
     * enqueue - newNode
     * dequeue - remove
     * peek
     * isFull
     * isEmpty
     * size
     */ //LinkedList Based Queue ADT

    //Field
    private static int MAX_QUEUE_SIZE = 15;
    public static int getMaxQueueSize(){return MAX_QUEUE_SIZE;}
    public static void setMaxQueueSize(int value){MAX_QUEUE_SIZE=value;}
    private long itemCode;      //Queue 라인 안에 들어가는 Item
    public long getItemCode(){return itemCode;}
    public void setItemCode(long code){itemCode = code;}
    private QueueNode head;
    private QueueNode tail;
    private int queueSize;

    //LinkedList Method

    public void printLinkedList(){
        int count = 0;
        QueueNode current = head;
        while(current!=null){
            if(current==head) System.out.println("***********Head***********");
            if(current==tail) System.out.println("***********Tail***********");
            System.out.println(++count);

            if(current.data==null) System.out.println("Null");
            else current.data.printItem();
            current = current.next;
        }
    }

    //Queue Method ==========================================================
    public boolean isEmptyQueue(){return queueSize<=0 || head==null;}
    public boolean isFullQueue(){return queueSize>=MAX_QUEUE_SIZE;}
    public int getCurrentSize() {return queueSize;}
    //-----------------------------------------------------------------------
    public boolean enqueue(){
        if(isFullQueue()) return false;     //Queue가 Full이면 false

        if(isEmptyQueue()){
            head = new QueueNode(itemMap.get(itemCode));
            tail = head;
        }
        else{
            tail.next = new QueueNode(itemMap.get(itemCode));
            tail = tail.next;
        }
        queueSize++;
        System.out.println("enqueue - "+tail.data.getName());
        return true;
    }
    //-----------------------------------------------------------------------
    public Item dequeue(){
        if(isEmptyQueue()) return null;     //Queue가 비었으면 null값 리턴

        QueueNode tmp = head;
        head = head.next;
        Item returnData = tmp.data;
        tmp = null;                  //front를 다음 노드로 넘기고 데이터 리턴, 초기화
        System.gc();                        //제외된 레퍼런스의 값 삭제를 요청 (동적할당 해제)
        System.out.println("dequeue - " + returnData.getName() + " " + returnData.getPrice() + "￦");
        queueSize--;
        return returnData;
    }

    //-----------------------------------------------------------------------



    // 생  성  자 ==========================================================
    public ItemStorageQueue(){
        this(0L, 0);
    }
    public ItemStorageQueue(long itemCode){
        this(itemCode, 3);
    }
    public ItemStorageQueue(long itemCode, int amount){
        queueSize = 0;
        this.itemCode = itemCode;
        if(!itemMap.isIn(itemCode)) this.itemCode = 0L;
        if(amount>=1){           // item 입력이 1개 이상이면 해당 값만큼 enqueue
            for(int i=0;i<amount;i++) enqueue();
            System.out.println("enqueue");
        }
        System.out.println("생성자 "+itemCode+", "+amount);
    }
}
//-----------------------------------------------------------------------



// Node 구조체 선언 ==========================================================
class QueueNode{
    Item data;
    QueueNode next;     // Field로 data, nextLink 선언

    QueueNode(){
        data = new Item();
        next = null;
    }   //item 값이 없으면 null로 생성
    QueueNode(Item i){
        data = i;
        next = null;
    }   //item이 넘어오면 해당 item으로 생성
}
