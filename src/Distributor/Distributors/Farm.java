package Distributor.Distributors;
import Collaborations.SaleBuyCollaboration;
import  Distributor.Distributor;
import Restaurant.RestaurantAdministrator;

public class Farm extends Distributor {
    public Farm(String name, String address, float costs, int id) {
        super(name, costs, id);
    }

    @Override
    public boolean CreateCollaboration(RestaurantAdministrator collaborationPartner, SaleBuyCollaboration collaboration) {
        if(collaboration.getMeal().getName() == "Meat")
            return super.CreateCollaboration(collaborationPartner, collaboration);
        else return false;
    }
}
