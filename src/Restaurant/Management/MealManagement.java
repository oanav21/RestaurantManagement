package Restaurant.Management;
import Meal.Meal;
import Restaurant.RestaurantAdministrator;


public class MealManagement{
    private Meal meal;
    transient private RestaurantAdministrator parent;

    public int getQuantity() {
        return quantity;
    }

    private int quantity;

    public int getLowQuantity() {
        return lowQuantity;
    }

    private int lowQuantity;
    public void setLowQuantity(int lowQuantity) {
        this.lowQuantity = lowQuantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if(this.getQuantity() <= lowQuantity)
            parent.AutoRequestRefill(meal, lowQuantity - quantity + 1);
    }

    public MealManagement(RestaurantAdministrator parent, String name, float price, int id, int quantity, int lowQuantity)
    {
        this.parent = parent;
        meal = new Meal(name, price, id);
        setLowQuantity(lowQuantity);
        setQuantity(quantity);

    }
    public MealManagement(RestaurantAdministrator parent, Meal meal, int quantity, int lowQuantity)
    {
        this.parent = parent;
        this.meal = meal;
        setLowQuantity(lowQuantity);
        setQuantity(quantity);
    }
    public void FillStock(int addedQuantity)
    {
        setQuantity(quantity + addedQuantity);
    }
    public float SaleQuantity(int requestedQuantity, boolean asMuchAsPossible)
    {
        if(quantity >= requestedQuantity)
        {
            setQuantity(quantity - requestedQuantity);
            return requestedQuantity * meal.getPrice();
        }
        else if(asMuchAsPossible)
        {
            int sealedQuantity = quantity;
            setQuantity(0);
            return sealedQuantity * meal.getPrice();
        }
        else return 0;
    }
    public float getMealPrice()
    {
        return meal.getPrice();
    }
    public Meal getMeal()
    {
        return meal;
    }
}
