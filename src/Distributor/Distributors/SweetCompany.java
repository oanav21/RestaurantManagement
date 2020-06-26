package Distributor.Distributors;
import Collaborations.SaleBuyCollaboration;
import  Distributor.Distributor;
import Restaurant.RestaurantAdministrator;

public class SweetCompany extends Distributor {
    public SweetCompany(String name, float costs, int id) {
        super(name, costs, id);
    }

    @Override
    public boolean CreateCollaboration(RestaurantAdministrator collaborationPartner, SaleBuyCollaboration collaboration) {
        if(collaboration.getMeal().getName() == "Desert")
            return super.CreateCollaboration(collaborationPartner, collaboration);
        else return false;
    }
}
