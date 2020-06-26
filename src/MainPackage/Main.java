package MainPackage;
import Serialization.Serialize;
import Collaborations.SaleBuyCollaboration;
import Restaurant.RestaurantAdministrator;

import java.io.*;
public class Main {

    public static void main(String args[]) throws IOException, ClassNotFoundException
    {
        boolean allowAutoRefill = true;
        RestaurantAdministrator restaurantAdministrator = new RestaurantAdministrator(allowAutoRefill,1000);
        Serialize.SetOwner(restaurantAdministrator).fromCSV();
        Service service = new Service();
        service.Run(restaurantAdministrator);
        Serialize.SetOwner(restaurantAdministrator).toCSV();
    }
}
