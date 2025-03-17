package Main;

import Machine.Item.Item;
import Machine.Item.ItemStorageQueue;

import java.io.*;

import static AdminSys.Login.LoginSys.getSavedPassword;
import static AdminSys.Login.LoginSys.setSavedPassword;
import static AdminSys.system.Sales.DataAnalysis.getSalesGoal;
import static AdminSys.system.Sales.DataAnalysis.setSalesGoal;
import static Machine.Item.ItemStorageQueue.getMaxQueueSize;
import static Machine.Item.ItemStorageQueue.setMaxQueueSize;
import static Machine.Money.MoneySystem.moneyBox;
import static Main.MainClass.itemMap;
import static Main.MainClass.itemQueue;

public class UserSetting {
    //Field
    private static final String settingFilePath = "C:\\Temp\\Vending Machine Setting.txt";

    //Method
    public static void loadSetting() {
        try (BufferedReader br = new BufferedReader(new FileReader(settingFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && line.contains(":")) {
                    line = line.replaceAll("\\s+", "");
                    System.out.println(line);
                    String[] parts = line.split(":");
                    String variableName = parts[0].trim();
                    String value = parts[1].trim();
                    assignValue(variableName, value);
                }
            }
        } catch (FileNotFoundException e){
            // Setting.txt가 없을 때
            moneyBox.setCashAmount(5,5,5,5,0);  // 거스름돈 초기값 지정

            Item[] defaultItems = new Item[5];  // 상품 기본값  설정
            defaultItems[0] = new Item(8808244100973L, "물", 450, "Menu1.png");
            defaultItems[1] = new Item(8801094523206L, "커피", 500, "Menu2.png");
            defaultItems[2] = new Item(8801056836016L, "이온음료", 550, "Menu3.png");
            defaultItems[3] = new Item(8801037040029L, "고급커피", 700, "Menu4.png");
            defaultItems[4] = new Item(8801056175870L, "탄산음료", 750, "Menu5.png");
            for (Item defaultItem : defaultItems) {
                itemMap.add(defaultItem);
            }   // itemMap (상품 정보 검색 HashMap)에 add

            setSalesGoal(2500); // 일일 매출 목표 기본값 설정

            setSavedPassword("sch20204053!");   // 비밀번호 기본값 설정

            setMaxQueueSize(15);    // 재고 보관라인 (Queue) 최대 보관 가능 개수 설정

            for(int i=0;i<itemQueue.length;i++){        // 재고 보관라인 (Queue) 초기화
                itemQueue[i] = new ItemStorageQueue(defaultItems[i].getItemCode());
            }       // itemCode만 넘겨주는 생성자는 해당 아이템으로 재고를 3개만 보충하여 초기화합니다.
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<5;i++){
            System.out.println("================================\n\n");
            itemQueue[i].printLinkedList();
            System.out.println("================================\n\n\n\n\n\n");
        }
    }

    public static void assignValue(String variableName, String value) {
        switch (variableName) {
            case "Cash":
                try{
                    String[] elements = value.split(",");
                    moneyBox.setCashAmount(
                            Integer.parseInt(elements[0]),
                            Integer.parseInt(elements[1]),
                            Integer.parseInt(elements[2]),
                            Integer.parseInt(elements[3]),
                            Integer.parseInt(elements[4])
                    );
                }
                catch (NumberFormatException exception){
                    System.out.println("Cash State Load Failed");
                    moneyBox.setCashAmount(5,5,5,5,0);
                }
                break;
            case "Item":
                try{
                    System.out.println(value);
                    String[] elements = value.split(",");      // element 배열에 컴마 단위로 잘라 저장
                    Item buffer = new Item();
                    /*for(String s:elements)
                        System.out.println(elements);*/

                    buffer.setItemCode(Long.parseLong(elements[0]));
                    buffer.setName(elements[1]);
                    buffer.setPrice(Integer.parseInt(elements[2]));
                    buffer.setImage(elements[3]);
                    buffer.printItem();

                    itemMap.add(buffer);
                }
                catch (NumberFormatException e){
                    Item buffer = new Item();
                    /*for(String s:elements)
                        System.out.println(elements);*/

                    buffer.setItemCode(0L);
                    buffer.setName("None");
                    buffer.setPrice(10);
                    buffer.setImage("MenuE.png");
                    buffer.printItem();

                    itemMap.add(buffer);
                    System.out.println("Item Load Failed");
                }
                break;
            case "SalesGoal":
                try {setSalesGoal(Integer.parseInt(value));}
                catch (NumberFormatException exception){
                    setSalesGoal(2500);
                    System.out.println("Sales Goal Load Failed");
                }
                break;
            case "Password":
                setSavedPassword(value);
                break;
            case "MaxQueueSize":
                try{setMaxQueueSize(Integer.parseInt(value));}
                catch (NumberFormatException exception){
                    setMaxQueueSize(15);
                    System.out.println("Max Queue Size Load Failed");
                }
                break;
            case "Queue":
                try{
                    String[] elements = value.split(",");
                    int index = Integer.parseInt(elements[0]);
                    System.out.println("index : " + index);
                    System.out.println("ele1 : " + Long.parseLong(elements[1]));
                    System.out.println("ele2 : " + elements[2]);
                    itemQueue[index]
                            = new ItemStorageQueue(Long.parseLong(elements[1]),Integer.parseInt(elements[2]));
                }
                catch (NumberFormatException ignore){
                    System.out.println("Queue Line Load Failed");
                }
                break;
            default:
                System.out.println("Unknown variable: " + variableName);
        }
    }

    protected static void rewriteSetting(){
        String filePath = "C:\\Temp\\Vending Machine Setting.txt";
        try {
            // 파일 쓰기
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("# 동전 지폐 현황(5), 재고 라인 현황(5), 매출목표, Password");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("# 동전 및 지폐 현황");
            bufferedWriter.newLine();
            bufferedWriter.write("#	10￦	50￦	100￦	500￦	1000￦");
            bufferedWriter.newLine();
            bufferedWriter.write("#------------------------------------------------");
            bufferedWriter.newLine();
            int[] buffer = moneyBox.getCashAmount();
            bufferedWriter.write(
                    "Cash : \t"+buffer[0]+",\t"+buffer[1]+",\t"
                            +buffer[2]+",\t"+buffer[3]+",\t"+buffer[4]+",\t");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("# 라인 당 최대 투입 가능 상품 갯수");
            bufferedWriter.newLine();
            bufferedWriter.write("MaxQueueSize : "+getMaxQueueSize());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("# 아이템 종류");
            bufferedWriter.newLine();
            bufferedWriter.write("#\tItemCode\tName\t\tPrice\tImagePath");
            bufferedWriter.newLine();
            bufferedWriter.write("#----------------------------------------------------------");
            bufferedWriter.newLine();
            for(int i=0;i<itemQueue.length;i++){
                bufferedWriter.write("Item : "+itemQueue[i].getItemCode()+",\t"+
                        itemMap.get(itemQueue[i].getItemCode()).getName()+",\t"+
                        itemMap.get(itemQueue[i].getItemCode()).getPrice()+",\t"+
                        itemMap.get(itemQueue[i].getItemCode()).getImagePath());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write("# 재고 라인 현황");
            bufferedWriter.newLine();
            bufferedWriter.write("#\tindex\tItemCode\tQuantity");
            bufferedWriter.newLine();
            bufferedWriter.write("#------------------------------------------");
            bufferedWriter.newLine();
            for(int i=0;i<itemQueue.length;i++){
                bufferedWriter.write("Queue : "+i+",\t"+
                        itemQueue[i].getItemCode()+",\t"+
                        itemQueue[i].getCurrentSize());
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.write("# 주의사항 : 아이템 종류에 없는 itemCode 사용 시 오류납니다.");
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("# 매출 목표 (일일 목표)");
            bufferedWriter.newLine();
            bufferedWriter.write("SalesGoal : "+getSalesGoal());
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("#Password");
            bufferedWriter.newLine();
            bufferedWriter.write("Password : "+getSavedPassword());
            bufferedWriter.newLine();

            // 버퍼 비우고 닫기
            bufferedWriter.flush();
            bufferedWriter.close();

            System.out.println("\nVending Machine Setting.txt 파일 내용이 갱신되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
