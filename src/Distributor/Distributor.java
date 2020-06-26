package Distributor;
import Collaborations.SaleBuyCollaboration;
import Meal.Meal;
import Restaurant.RestaurantAdministrator;

import java.io.Serializable;

public class Distributor {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public SaleBuyCollaboration getSaleBuyCollaboration() {
        return saleBuyCollaboration;
    }

    SaleBuyCollaboration saleBuyCollaboration;
    RestaurantAdministrator collaborationPartner;
    float partnerDuty;

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
        if(deliveredQuantity >= saleBuyCollaboration.getMaxCollaborationQuantity())
            CancelCollaboration();
    }

    private int deliveredQuantity;

    public float getPartnerDuty() {
        return partnerDuty;
    }

    public void setPartnerDuty(float partnerDuty) {
        this.partnerDuty = partnerDuty;
        if(saleBuyCollaboration != null && partnerDuty >= saleBuyCollaboration.getMaxAllowedPaymentDuty())
        {
            CancelCollaboration();
        }
    }

    private float profit;

    public float getCosts() {
        return costs;
    }

    private float costs;
    public void setProfit(float profit) {
        this.profit = profit;
        if(profit <= 0)
        {
            CancelCollaboration();
        }
    }
    public float getProfit()
    {
        return this.profit;
    }
    public Distributor(String name, float costs, int id)
    {
        setName(name);
        setId(id);
        profit = 0;
        this.costs = costs;
        setPartnerDuty(0);
        setProfit(100);
    }
    public Distributor(String name, float costs, int id, float partnerDuty, int deliveredQuantity, float profit)
    {
        this(name, costs, id);
        this.partnerDuty = partnerDuty;
        this.deliveredQuantity = deliveredQuantity;
        this.profit = profit;
    }
    public Meal getOccupation()
    {
        return saleBuyCollaboration.getMeal();
    }
    public boolean CreateCollaboration(RestaurantAdministrator collaborationPartner, SaleBuyCollaboration saleBuyCollaboration)
    {
        this.collaborationPartner = collaborationPartner;
        this.saleBuyCollaboration = saleBuyCollaboration;
        return true;
    }
    void CancelCollaboration()
    {
        this.collaborationPartner.CollaborationIsClosing(this);
        this.collaborationPartner = null;
        this.saleBuyCollaboration = null;
    }
    public boolean CollaborationIsClosing()
    {
        if(partnerDuty <= 0)
        {
            this.collaborationPartner = null;
            this.saleBuyCollaboration = null;
            return true;
        }
        else return false;
    }
    public void DeliverOrder(int quantity)
    {
        if(saleBuyCollaboration != null && collaborationPartner != null)
        {
            if(partnerDuty < saleBuyCollaboration.getMaxAllowedPaymentDuty())
            {
                if(quantity > saleBuyCollaboration.getMaxQuantityOnRequest())
                    quantity = saleBuyCollaboration.getMaxQuantityOnRequest();
                setProfit(getProfit() - costs);
                setPartnerDuty(getPartnerDuty() + quantity * saleBuyCollaboration.getMealPrice());
                setDeliveredQuantity(getDeliveredQuantity() + quantity);
                collaborationPartner.ReceiveOrder(this, getOccupation(), quantity);
            }
        }
    }
    public void ReceivePayment(float sum)
    {
        setPartnerDuty(partnerDuty - sum);
        setProfit(getProfit() + sum);
    }
}
