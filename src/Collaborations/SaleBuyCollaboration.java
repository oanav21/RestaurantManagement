package Collaborations;
import Meal.Meal;

public class SaleBuyCollaboration {
    private Meal meal;
    private int maxCollaborationQuantity, maxQuantityOnRequest, maxAllowedPaymentDuty;
    private float mealPrice;

    public int getMaxCollaborationQuantity() {
        return maxCollaborationQuantity;
    }

    public void setMaxCollaborationQuantity(int maxCollaborationQuantity) {
        this.maxCollaborationQuantity = maxCollaborationQuantity;
    }

    public int getMaxQuantityOnRequest() {
        return maxQuantityOnRequest;
    }

    public void setMaxQuantityOnRequest(int maxQuantityOnRequest) {
        this.maxQuantityOnRequest = maxQuantityOnRequest;
    }

    public int getMaxAllowedPaymentDuty() {
        return maxAllowedPaymentDuty;
    }

    public void setMaxAllowedPaymentDuty(int maxAllowedPaymentDuty) {
        this.maxAllowedPaymentDuty = maxAllowedPaymentDuty;
    }

    public float getMealPrice() {
        return mealPrice;
    }

    public void setMealPrice(float mealPrice) {
        this.mealPrice = mealPrice;
    }

    public SaleBuyCollaboration(int maxCollaborationQuantity, int maxQuantityOnRequest, int maxAllowedPaymentDuty, float mealPrice, Meal meal) {
        setMaxCollaborationQuantity(maxCollaborationQuantity);
        setMaxQuantityOnRequest(maxQuantityOnRequest);
        setMaxAllowedPaymentDuty(maxAllowedPaymentDuty);
        setMealPrice(mealPrice);
        this.meal = meal;
    }

    public Meal getMeal() {
        return meal;
    }
}
