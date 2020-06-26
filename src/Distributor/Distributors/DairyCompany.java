package Distributor.Distributors;
import Collaborations.SaleBuyCollaboration;
import  Distributor.Distributor;
import Restaurant.RestaurantAdministrator;

public class DairyCompany extends Distributor {
    public DairyCompany(String name, String address, float costs, int id) {
        super(name, costs, id);
    }

    @Override
    public boolean CreateCollaboration(RestaurantAdministrator collaborationPartner, SaleBuyCollaboration collaboration) {
        if(collaboration.getMeal().getName() == "Yogurt")
            return super.CreateCollaboration(collaborationPartner, collaboration);
        else return false;
    }
}
