package Machine.Item;

import Main.noticePopUp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ItemKeyMap {
    //Field
    private long itemCode[];    //Key
    private Item itemValue[];        //Value

    public long[] getItemCode(){
        return itemCode;
    }


    //Method
    private boolean isFull(){
        boolean result = true;
        for(int i=0;i<itemCode.length;i++)
            if(itemCode[i]==0) result=false;
        return result;
    }
    public boolean isIn(long code){
        for(int i=0;i<itemCode.length;i++)
            if(itemCode[i]==code) return true;
        return false;
    }
    private int find(long code){
        for(int i=0;i<itemCode.length;i++)
            if(itemCode[i]==code) return i;
        return -1;
    }

    public boolean add(Item newItem){
        if(isIn(newItem.getItemCode())) {
            System.out.println("이미 해당 상품 코드가 등록되어 있습니다.");
            return false;
        }
        if(isFull()){
            System.out.println("상품 등록 가능 개수를 초과하였습니다.");
            return false;
        }
        int index = find(0);
        System.out.println(index);
        itemCode[index] = newItem.getItemCode();
        itemValue[index] = newItem;
        return true;
    }
    public boolean delete(long code){
        if(!isIn(code)){
            new noticePopUp("등록되지 않은 상품입니다.");
            return false;
        }
        int index = find(code);
        itemCode[index] = 0;
        itemValue[index] = null;
        new noticePopUp("삭제되었습니다.");
        return true;
    }
    public Item get(long code){
        if(!isIn(code)) {
            System.out.println("없는 상품 - null반환");
            return null;
        }
        return itemValue[find(code)];
    }
    public Item getByIndex(int index){
        if(index<0||index> itemValue.length) return null;
        return itemValue[index];
    }
    public void set(int index, Item item){
        itemCode[index] = item.getItemCode();
        itemValue[index] = item;
    }


    private void loadItem(){
        try{
            String filePath = "itemList.csv";
            File itemList = new File(filePath);
            Scanner scanner = new Scanner(itemList);                 // file을 로드하고 Scanner 선언

            //////////// 읽기 반복 //////////////////
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();                // line String buffer에 저장
                String[] elements = line.split(",");      // element 배열에 컴마 단위로 잘라 저장
                Item buffer = new Item();

                buffer.setItemCode(Long.parseLong(elements[0].trim()));
                buffer.setPrice(Integer.parseInt(elements[2].trim()));
                buffer.setName(elements[1]);
                buffer.setImage(elements[3]);

                add(buffer);
            }//end of for
            scanner.close();
            System.out.println("아이템 목록 로드 성공");

        } //end of try
        catch (FileNotFoundException e){
            System.out.println("Error : 아이템 리스트 파일 정보 없음");     // 파일이 없을 경우 파일정보 없음
        }
    }

    //Constructor
    public ItemKeyMap(){
        this(10);
    }
    public ItemKeyMap(int size) {
        itemCode = new long[size];
        itemValue = new Item[size];
        for (int i = 0; i < size; i++) {
            itemCode[i] = 0;
            itemValue[i] = null;
        }
        //loadItem();
    }
}
