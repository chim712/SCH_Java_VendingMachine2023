package AdminSys.system.Sales;

public class SalesDataStruct {
    long itemCode;
    int price;
    int[] time = new int[6];

    public void printData() {
        System.out.printf(
                "%1$d %2$4d￦ %3$04d-%4$02d-%5$02d %6$02d:%7$02d:%8$02d\n",
                itemCode, price, time[0], time[1], time[2], time[3], time[4], time[5]
        );
    }

    public String dataToString(){
        return itemCode + "," + price + "," + time[0] + "," +
                time[1] + "," + time[2] + "," + time[3] + "," + time[4] + "," + time[5];
    }

    public SalesDataStruct() {
        this(0, 0, new int[]{0, 0, 0, 0, 0, 0});
    }

    public SalesDataStruct(long itemCode, int price, int time[]) {
        this.itemCode = itemCode;
        this.price = price;
        this.time = time;
    }
}
