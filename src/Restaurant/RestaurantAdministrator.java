package Restaurant;
import Collaborations.SaleBuyCollaboration;
import Distributor.Distributor;
import Meal.Meal;
import Restaurant.Management.*;
import java.io.*;

import java.util.List;

public class RestaurantAdministrator {
    private DistributorsManagement distributorsManagement;
    private MealManagement[] mealManagements;
    private boolean allowAutoRefill;
    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
        if(profit <= 0)
        {
            distributorsManagement.CloseAllCollaborations();
            distributorsManagement = null;
            mealManagements = null;
        }
    }

    private float profit;

    public void setAllowAutoRefill(boolean allowAutoRefill) {
        this.allowAutoRefill = allowAutoRefill;
    }

    public boolean isAllowAutoRefill() {
        return allowAutoRefill;
    }

    public RestaurantAdministrator(boolean allowAutoRefill, float moneyReserve)
    {
        setProfit(moneyReserve);
        setAllowAutoRefill(allowAutoRefill);
        distributorsManagement = new DistributorsManagement(this,null,null);
        mealManagements = new MealManagement[0];
    }
    public void addDistributor(Distributor distributor)
    {
        distributorsManagement.appendDistributor(distributor);
        if(distributor.getSaleBuyCollaboration()!=null)
        {
            createCollaboration(distributor, distributor.getSaleBuyCollaboration());
        }
    }
    public Distributor addDistributor(String name, int costs, int id)
    {
        Distributor distributor = new Distributor(name, costs, id);
        addDistributor(distributor);
        return distributor;
    }
    public void removeDistributor(Distributor distributor)
    {
        distributorsManagement.removeDistributor(distributor);
    }
    public boolean createCollaboration(Distributor distributor, SaleBuyCollaboration saleBuyCollaboration)
    {
        return distributorsManagement.TryCreateCollaboration(distributor, saleBuyCollaboration);
    }
    public SaleBuyCollaboration createCollaboration(Distributor distributor, int maxCollaborationQuantity, int maxQuantityOnRequest, int maxAllowedPaymentDuty, float mealPrice, Meal meal)
    {
        SaleBuyCollaboration saleBuyCollaboration = new SaleBuyCollaboration(maxCollaborationQuantity, maxQuantityOnRequest, maxAllowedPaymentDuty, mealPrice, meal);
        if(createCollaboration(distributor, saleBuyCollaboration))
            return saleBuyCollaboration;
        return null;
    }
    public boolean cancelCollaboration(Distributor distributor)
    {
        return distributorsManagement.TryCancelCollaboration(distributor);
    }
    public void CollaborationIsClosing(Distributor distributor)
    {
        distributorsManagement.removeDistributor(distributor);
    }
    public MealManagement addMeal(String name, float price, int id, int quantity, int lowQuantity)
    {
        MealManagement mealManagement = new MealManagement(this, name, price, id, quantity, lowQuantity);
        addMeal(mealManagement);
        return mealManagement;
    }
    public void addMeal(MealManagement mealManagement)
    {
        //Search if it already exists
        for(int i = 0; i < mealManagements.length; i++)
        {
            if(mealManagements[i].getMeal().getId() == mealManagement.getMeal().getId())
            {
                mealManagements[i].setQuantity(mealManagements[i].getQuantity() + mealManagement.getQuantity());
                mealManagements[i].getMeal().setPrice(mealManagement.getMeal().getPrice());
                mealManagements[i].setLowQuantity(mealManagement.getLowQuantity());
                return;
            }
        }

        MealManagement[] aux = new MealManagement[mealManagements.length + 1];
        for(int i = 0; i < mealManagements.length; i++)
        {
            aux[i] = mealManagements[i];
            if (mealManagements[i].getMealPrice() >= mealManagement.getMealPrice()) {
                aux[i] = mealManagement;
                for (i++; i < mealManagements.length + 1; i++) {
                    aux[i] = mealManagements[i - 1];
                }
            }
        }

        if(mealManagements.length == 0)
        {
            aux[0] = mealManagement;
        }
        else if(mealManagements[mealManagements.length - 1].getMealPrice() < mealManagement.getMealPrice())
        {
            aux[aux.length - 1] = mealManagement;
        }
        mealManagements=aux;
    }
    public void removeMeal(int mealID)
    {
        for(int i = 0; i < mealManagements.length; i++)
        {
            if(mealID == mealManagements[i].getMeal().getId())
            {
                removeMeal(mealManagements[i]);
                return;
            }
        }
    }
    public MealManagement getMeal(int mealID)
    {
        for(int i = 0; i < mealManagements.length; i++)
        {
            if(mealID == mealManagements[i].getMeal().getId())
            {
                return mealManagements[i];
            }
        }
        return  null;
    }
    public Distributor getDistributor(int distributorID)
    {
        for(int i = 0; i < distributorsManagement.getDistributors().length; i++)
        {
            if(distributorID == distributorsManagement.getDistributors()[i].getId())
                return  distributorsManagement.getDistributors()[i];
        }
        return null;
    }
    public void removeMeal(MealManagement mealManagement)
    {
        MealManagement[] aux = new MealManagement[mealManagements.length - 1];
        for(int i = 0; i < mealManagements.length; i++)
        {
            aux[i] = mealManagements[i];
            if (mealManagements[i] == mealManagement) {
                for (; i < mealManagements.length - 1; i++) {
                    aux[i] = mealManagements[i + 1];
                }
            }
        }
        mealManagements = aux;
    }
    public void AutoRequestRefill(Meal meal, int minRequestedQuantity)
    {
        if(isAllowAutoRefill())
        {
            Refill(meal, minRequestedQuantity);
        }
    }
    public void Refill(Meal meal, int minRequestedQuantity)
    {
        if(distributorsManagement.FindByOccupation(meal) != null)
        {
            distributorsManagement.FindByOccupation(meal).DeliverOrder(minRequestedQuantity);
        }
    }
    public void ReceiveOrder(Distributor distributor, Meal meal, int quantity)
    {
        for(int i = 0; i < mealManagements.length; i++)
        {
            if(mealManagements[i].getMeal().getId() == meal.getId())
                mealManagements[i].FillStock(quantity);
        }
        distributor.ReceivePayment(quantity * distributor.getSaleBuyCollaboration().getMealPrice());
        setProfit(getProfit() - quantity * distributor.getSaleBuyCollaboration().getMealPrice());
    }
    public void RefillAll()
    {
        for(int i = 0; i < mealManagements.length; i++)
        {
            int requestedQuantity = 0;
            requestedQuantity =  mealManagements[i].getQuantity() - mealManagements[i].getLowQuantity();
            if(requestedQuantity > 0)
                Refill(mealManagements[i].getMeal(), requestedQuantity);
        }
    }
    public void Sale(Meal meal, int quantity)
    {
        for(int i = 0; i < mealManagements.length; i++)
        {
            if(mealManagements[i].getMeal() == meal)
               setProfit(mealManagements[i].SaleQuantity(quantity,true) + getProfit());
        }
    }
    public MealManagement[] getMealManagements()
    {
        return mealManagements;
    }
    public Distributor[] getDistributors()
    {
        return distributorsManagement.getDistributors();
    }
}
