package Meal;

public class Meal {
    private String name;
    private float price;
    private int id;
    public Meal(String name, float price, int id)
    {
        setId(id);
        setName(name);
        setPrice(price);
    }
    public float getPrice()
    {
        return price;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}
