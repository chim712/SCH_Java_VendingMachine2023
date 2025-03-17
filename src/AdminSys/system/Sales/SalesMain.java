package AdminSys.system.Sales;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SalesMain {
    private static int listSize=0;
    public static DataList dataList = new DataList();
    public static String[][] getListToStringArr(){
        Node current = dataList.getHead();
        String[][] list = new String[getListSize()][8];
        for(int i=0;i<getListSize();i++){
            list[i][0] = String.valueOf(current.data.itemCode);
            list[i][1] = String.valueOf(current.data.price);
            for(int j=0;j<6;j++){
                list[i][j+2] = String.valueOf(current.data.time[j]);
            }
            current = current.next;
        }
        return list;
    }
    public static String[][] getListFindMonth(int year, int month){    // 탐색 희망 월 List 리턴
        int count=0, last=0, i;
        String[][] load = getListToStringArr();
        for(i=0;i<load.length;i++){
            if(Integer.parseInt(load[i][2])==year&&Integer.parseInt(load[i][3])==month)
                count++;
        }
        String[][] returnList = new String[count][8];

        for(i=0;i<load.length;i++){
            if(Integer.parseInt(load[i][2])==year&&Integer.parseInt(load[i][3])==month){
                for(int j=0;j<8;j++) returnList[last][j] = load[i][j];
                last++;
            }

        }
        System.out.println("Last : "+last);
        return returnList;
    }
    public static String[][] getListFindItem(int year, int month, Long itemCode){    // 탐색 희망 월 List 리턴
        int count=0, last=0, i;
        String[][] load = getListToStringArr();
        for(i=0;i<load.length;i++){
            if(Integer.parseInt(load[i][2])==year&&Integer.parseInt(load[i][3])==month&&Long.parseLong(load[i][0])==itemCode) {
                count++;
            }
        }
        String[][] returnList = new String[count][8];
    //&&Long.parseLong(load[i][0])==itemCode
        for(i=0;i<load.length;i++){
            if(Integer.parseInt(load[i][2])==year&&Integer.parseInt(load[i][3])==month&&Long.parseLong(load[i][0])==itemCode){
                for(int j=0;j<8;j++) returnList[last][j] = load[i][j];
                last++;
            }

        }
        System.out.println("Last : "+last);
        return returnList;
    }

    public static int getListSize(){return listSize;}
    public static void increaseListSize(){listSize++;}


    //====================== 파일 로딩 =================================
    public void fileLoad(){
        try{
            String filePath = "sales data.csv";
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);                 // file을 로드하고 Scanner 선언

            //////////// 읽기 반복 //////////////////
            while(scanner.hasNextLine()) {
                SalesDataStruct buffer = new SalesDataStruct();              // buffer 클래스 선언
                String line = scanner.nextLine();                // line String buffer에 저장

                // 한 줄에서 각 요소를 분리하여 출력하기
                String[] elements = line.split(",");      // element 배열에 컴마 단위로 잘라 저장
                for (int j = 0; j < elements.length; j++) {
                    if (j == 0) {
                        // 아이템 코드를 long 자료형으로 처리
                        buffer.itemCode = Long.parseLong(elements[j].trim());   //첫 요소는 바코드 번호 (10자리)
                    } else if (j == 1) {
                        buffer.price = Integer.parseInt(elements[j].trim());    // 두번째 요소는 제품가격
                    } else {
                        // 가격과 날짜/시간 정보는 int로 처리
                        int num = Integer.parseInt(elements[j].trim());         // 3~8번째 연월일 시분초
                        buffer.time[j-2] = num;
                    }
                }//end of for
                dataList.newNode(buffer);                   //버퍼 노드를 DataList에 삽입
                listSize++;
            }//end of while
            scanner.close();

            dataList.sortList();            // File Load가 끝났다면 Sort
        } //end of try
        catch (FileNotFoundException e){
            System.out.println("Error : 파일 정보 없음");     // 파일이 없을 경우 파일정보 없음
        }
    }//end of method - fileLoad()

    //====================== end of fileLoad =================================




    //====================== 생성자 =================================
    public SalesMain(){
        fileLoad();

    }
}
