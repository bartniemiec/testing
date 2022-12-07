package Magazyn;

public class Product {
    
    private String name;
    private int quantity=0;
    private double tax=0;
    private double price=0;
    private int ID=0;

    public Product(String name, int quantity, double tax, double price, int ID)
    {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.tax = tax;
        this.price = price;
    }

    public Product(String name)
    {
        this.name = name;
    }

    public Product(String name, int quantity)
    {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTax() {
        return tax;
    }
    public int getID() {
        return ID;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if(quantity < 1)
            quantity=0;

        this.quantity = quantity;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    double getFullPrice(int quantity)
    {
        return quantity * price;
    }
}
