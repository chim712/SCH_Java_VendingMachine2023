//package Machine.Item;
//
//public class Queue_BackUp {
//    //-----------------------------------------------------------------------
//    public boolean enqueue(){
//        if(isEmptyList()) return false;     //LinkedList 선언이 안되면 false
//        if(isFullQueue()) return false;     //Queue가 Full이면 false
//        rear = rear.next;                   //rear 를 다음 노드를 넘김
//        rear.data = itemMap.get(itemCode);                   //넘기고 나서 data 삽입
//        queueSize++;
//        System.out.println("enqueue ");
//        return true;
//    }
//    //-----------------------------------------------------------------------
//    public Item dequeue(){
//        if(isEmptyList()) return null;
//        if(isEmptyQueue()) return null;     //없는 리스트거나 Queue가 비었으면 null값 리턴
//        front = front.next;
//        Item returnData = front.data;
//        front.data = null;                  //front를 다음 노드로 넘기고 데이터 리턴, 초기화
//        System.gc();                        //제외된 레퍼런스의 값 삭제를 요청 (동적할당 해제)
//        System.out.println("dequeue - " + returnData.getName() + " " + returnData.getPrice() + "￦");
//        queueSize--;
//        return returnData;
//    }
//
//    // Index 삭제 폐기 - 사유 : linkedlist 기반이므로 정렬을 돌려야 함 - 프로그램 안정성 저하
////    public Machine.Item.Item dequeueIndex(int index){
////        Machine.Item.QueueNode current = front;
////        if(index==0) return dequeue();      //0번 인덱스(front) 삭제면 dequeue() 호출
////        if(isEmptyList()) return null;
////        if(isEmptyQueue()) return null;     //없는 리스트거나 Queue가 비었으면 null값 리턴
////        for(int i=0;i<index-1;i++) {
////            current = current.next;
////        }
////        Machine.Item.Item returnData = current.next.data;
////        current.next = current.next.next;           //현 노드의 다음 링크를 다다음 노드로 설정
////        System.gc();                        //제외된 레퍼런스의 값 삭제를 요청 (동적할당 해제)
////        System.out.println("dequeue" + index + "index");
////        return returnData;
////    }
//    //-----------------------------------------------------------------------
//
//    // 생  성  자 ==========================================================
//    public ItemStorageQueue(){
//        this(0, 0, 0);
//    }
//    public ItemStorageQueue(long itemCode){
//        this(itemCode, 3, 0);
//    }
//    public ItemStorageQueue(long itemCode, int amount){
//        this(itemCode,amount,0);
//        System.out.println("생성자 "+itemCode+", "+amount);
//    }
//
//    public ItemStorageQueue(long itemCode, int amount, int max){
//        //max는 프로그램 안정화 단계에서 삭제처리하였습니다. (무관변수)
//        queueSize = 0;
//        this.itemCode = itemCode;
//        if(!itemMap.isIn(itemCode)) this.itemCode = 0L;
//        for(int i=0;i<MAX_QUEUE_SIZE+1;i++){
//            newNode();
//        }
//        tail.next = head;       // 원형 큐이므로 꼬리 - 헤드 연결
//        front = tail;           // front와 rear 모두 tail (-1번 인덱스)로 설정
//        rear = tail;
//        if(amount>=1){           // item 입력이 1개 이상이면 해당 값만큼 enqueue
//            for(int i=0;i<amount;i++) enqueue();
//            System.out.println("enqueue");
//        }
//    }
//}
////-----------------------------------------------------------------------
//}
