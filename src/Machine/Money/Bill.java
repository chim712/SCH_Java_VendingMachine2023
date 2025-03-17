package Machine.Money;

public class Bill {
    private static int value;
    public static int[] billType = {1000, 5000, 10000, 50000};
    //Method
    public static int getValue(){return value;}
    public int getIndex(){
        switch (value){
            case 1000:
                return 0;
            case 5000:
                return 1;
            case 10000:
                return 2;
            case 50000:
                return 3;
            default:
                return -1;
        }
    }
    public Bill(){value=1000;}
    public Bill(int value){this.value=value;}
}
