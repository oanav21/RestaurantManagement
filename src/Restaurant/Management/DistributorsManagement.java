package Restaurant.Management;
import Distributor.Distributor;
import Collaborations.SaleBuyCollaboration;
import Meal.Meal;
import Restaurant.RestaurantAdministrator;

public class DistributorsManagement {

    private Distributor[] distributors;
    private SaleBuyCollaboration[] collaborations;
    private RestaurantAdministrator evidenceOwner;

    public DistributorsManagement(RestaurantAdministrator ManagementOwner, Distributor[] distributors, SaleBuyCollaboration[] collaborations)
    {
        this.evidenceOwner = evidenceOwner;
        if(distributors == null)
        {
            this.distributors = new Distributor[0];
            this.collaborations = new SaleBuyCollaboration[0];
            return;
        }
        if(distributors.length <= collaborations.length)
        {
            for(int i = 0; i < distributors.length; i++)
            {
                if(i < collaborations.length - 1)
                {
                    appendDistributor(distributors[i], collaborations[i]);
                }
                else
                {
                    appendDistributor(distributors[i]);
                }
            }
        }
    }
    public boolean TryCreateCollaboration(Distributor distributor, SaleBuyCollaboration collaboration)
    {
        for(int i = 0; i < distributors.length; i++)
        {
            if(distributor.getId() == distributors[i].getId())
            {
                if(distributor.getSaleBuyCollaboration() != null && distributor.getSaleBuyCollaboration().equals(collaboration)
                        || distributor.CreateCollaboration(evidenceOwner, collaboration))
                {
                    collaborations[i] = collaboration;
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public boolean TryCancelCollaboration(Distributor distributor)
    {
        for(int i = 0; i < distributors.length; i++)
        {
            if(distributor.getId() == distributors[i].getId())
            {
                if(distributor.CollaborationIsClosing())
                {
                    collaborations[i] = null;
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    public Distributor removeDistributor(Distributor distributor)
    {
        Distributor[] aux = new Distributor[distributors.length - 1];
        SaleBuyCollaboration[] aux2 = new SaleBuyCollaboration[distributors.length - 1];
        for(int i = 0; i < distributors.length; i++)
        {
            aux[i] = distributors[i];
            aux2[i] = collaborations[i];
            if(distributors[i].getId() == distributor.getId())
            {
                Distributor buf = distributors[i];
                for(; i < distributors.length - 1; i++)
                {
                    aux[i] = distributors[i + 1];
                    aux2[i] = collaborations[i + 1];
                }
                distributors = aux;
                collaborations = aux2;
                return buf;
            }
        }
        return null;
    }
    public void appendDistributor(Distributor distributor)
    {
        Distributor[] aux = new Distributor[distributors.length + 1];
        SaleBuyCollaboration[] aux2 = new SaleBuyCollaboration[distributors.length + 1];
        for(int i = 0; i < distributors.length; i++)
        {
            aux[i] = distributors[i];
            aux2[i] = collaborations[i];
        }
        aux[aux.length - 1] = distributor;
        aux2[aux.length-1] = null;
        distributors = aux;
        collaborations = aux2;
    }
    public void appendDistributor(Distributor distributor, SaleBuyCollaboration collaboration)
    {
        Distributor[] aux = new Distributor[distributors.length + 1];
        SaleBuyCollaboration[] aux2 = new SaleBuyCollaboration[distributors.length + 1];
        for(int i = 0; i < distributors.length; i++)
        {
            aux[i] = distributors[i];
            aux2[i] = collaborations[i];
        }
        aux[aux.length - 1] = distributor;
        distributors = aux;
        if(distributors[distributors.length-1].CreateCollaboration(evidenceOwner, collaboration))
            aux2[aux2.length - 1] = collaboration;
        else aux2[aux2.length - 1] = null;
        collaborations = aux2;
    }
    public Distributor FindByOccupation(Meal meal)
    {
        for(int i = 0; i < distributors.length; i++)
        {
            if(distributors[i].getOccupation().getId() == meal.getId())
                return distributors[i];
        }
        return null;
    }
    public void CloseAllCollaborations()
    {
        for(int i = 0; i < distributors.length; i++)
            distributors[i].CollaborationIsClosing();
    }
    public Distributor[] getDistributors()
    {
        return distributors;
    }
}
