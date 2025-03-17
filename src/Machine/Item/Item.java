package Machine.Item;

public class Item {
    //Field
    private long ItemCode;
    private String Name;
    private int price;
    private String imageFilePath;


    //Method (Setter)
    public void setItemCode(long code){ItemCode = code;}
    public void setName(String name){Name = name;}
    public void setPrice(int price){this.price = price;}
    public void setImage(String path){
        imageFilePath = path;}


    //Method (Getter)
    public long getItemCode(){return ItemCode;}
    public String getName(){return Name;}
    public int getPrice(){return price;}
    public String getImagePath(){return imageFilePath;}


    //Method(Other)
    public void printItem(){
        System.out.println("Code : "+ItemCode);
        System.out.println("Name : "+Name);
        System.out.println("Price : "+price);
    }


    //생성자
    public Item(){
        this(0,"NULL",10,"MenuE.png");
    } //일단 생성하고 나중에 조정할 때 사용
    public Item(long code, String name, int price, String path){
        ItemCode = code;
        Name = name;
        this.price = price;
        imageFilePath = path;
    } //생성 시 매개변수를 모두 지정함
}
