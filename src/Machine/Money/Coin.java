package Machine.Money;

public class Coin {
    private int value;
    public static int[] coinType = {10, 50, 100, 500};
    //Method
    public int getValue() {
        return value;
    }
    public int getIndex(){
        switch (value){
            case 10:
                return 0;
            case 50:
                return 1;
            case 100:
                return 2;
            case 500:
                return 3;
            default:
                return -1;
        }
    }
    public static int getIndex(int value){
        switch (value){
            case 10:
                return 0;
            case 50:
                return 1;
            case 100:
                return 2;
            case 500:
                return 3;
            default:
                return -1;
        }
    }
    // 생성자
    public Coin(int value){
        this.value=value;
    }



}
