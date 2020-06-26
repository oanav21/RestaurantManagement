package MainPackage;

import Collaborations.SaleBuyCollaboration;
import Distributor.Distributor;
import Restaurant.Management.MealManagement;
import Restaurant.RestaurantAdministrator;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Service {
    Scanner scanner;
    RestaurantAdministrator restaurantAdministrator;
    FileOutputStream fileOutputStream;
    DataOutputStream dataOutputStream;
    public void Run(RestaurantAdministrator restaurantAdministrator) throws IOException
    {
        scanner = new Scanner(System.in);
        fileOutputStream = new FileOutputStream("auditService.csv");
        dataOutputStream = new DataOutputStream(fileOutputStream);
        this.restaurantAdministrator = restaurantAdministrator;
        while (true)
        {
            System.out.println("Type a command: ");
            System.out.println("1-Add meal");
            System.out.println("2-Create collaboration");
            System.out.println("3-Add distributor");
            System.out.println("4-Request refill");
            System.out.println("5-Sale");
            System.out.println("6-Cancel Collaboration");
            System.out.println("7-Remove distributor");
            System.out.println("8-Remove meal");
            System.out.println("9-Show all meals");
            System.out.println("10-Show all distributors");
            System.out.println("11-Show all collaborations");
            int choose = scanner.nextInt();
            switch (choose)
            {
                case 1:
                    dataOutputStream.writeBytes("Add meal\n");
                    AddMeal();
                    break;
                case 2:
                    dataOutputStream.writeBytes("Create collaboration\n");
                    CreateCollaboration();
                    break;
                case 3:
                    dataOutputStream.writeBytes("Add distributor\n");
                    AddDistributor();
                    break;
                case 4:
                    dataOutputStream.writeBytes("Requeste refill\n");
                    RequestRefill();
                    break;
                case 5:
                    dataOutputStream.writeBytes("Sale\n");
                    Sale();
                    break;
                case 6:
                    dataOutputStream.writeBytes("Cancel collaboration\n");
                    CancelCollaboration();
                    break;
                case 7:
                    dataOutputStream.writeBytes("Remove distributor\n");
                    RemoveDistributor();
                    break;
                case 8:
                    dataOutputStream.writeBytes("Remove meal\n");
                    RemoveMeal();
                    break;
                case 9:
                    dataOutputStream.writeBytes("Show all meals\n");
                    ShowAllMeals();
                    break;
                case 10:
                    dataOutputStream.writeBytes("Show all distributors\n");
                    ShowAllDistributors();
                    break;
                case 11:
                    dataOutputStream.writeBytes("Show all collaborations\n");
                    ShowAllCollaborations();
                    break;
                default:
                    dataOutputStream.close();
                    fileOutputStream.close();
                    return;

            }
        }
    }
    void AddMeal()
    {
        System.out.println("Type the meal name: ");
        String mealName = scanner.next();
        System.out.println("Type an ID for the meal: ");
        int mealId = scanner.nextInt();
        System.out.println("Type the meal price: ");
        float price = scanner.nextFloat();
        System.out.println("Type the quantity considered as low: ");
        int lowQuantity = scanner.nextInt();
        System.out.println("Type the startup quantity for this meal: ");
        int quantity = scanner.nextInt();
        MealManagement mealManagement = new MealManagement(restaurantAdministrator, mealName, price, mealId, quantity, lowQuantity);
        restaurantAdministrator.addMeal(mealManagement);
    }
    void CreateCollaboration()
    {

        System.out.println("Type the ID of the meal:");
        MealManagement mealManagement = restaurantAdministrator.getMeal(scanner.nextInt());
        System.out.println("Type the maximum quantity of the collaboration: ");
        int maxCQ = scanner.nextInt();
        System.out.println("Type the maximum quantity allowed to be requested once: ");
        int maxQOR = scanner.nextInt();
        System.out.println("Type the maximum duty that the distributor will allow to the restaurant: ");
        int maxAPD =scanner.nextInt();
        System.out.println("Type the meal price: ");
        float mealPrice = scanner.nextFloat();
        System.out.println("Type the ID of the distributor you want to collaborate with: ");
        Distributor distributor = restaurantAdministrator.getDistributor(scanner.nextInt());

        SaleBuyCollaboration collaboration = new SaleBuyCollaboration(maxCQ, maxQOR, maxAPD, mealPrice, mealManagement.getMeal());
        restaurantAdministrator.createCollaboration(distributor,collaboration);
    }
    void AddDistributor()
    {
        System.out.println("Type the name of the distributor: ");
        String name = scanner.next();
        System.out.println("Type the ID of th distributor: ");
        int id = scanner.nextInt();
        System.out.println("Type the mealion costs of the distributor: ");
        float costs = scanner.nextFloat();
        Distributor distributor = new Distributor(name, costs, id);
        restaurantAdministrator.addDistributor(distributor);
    }
    void RequestRefill()
    {
        System.out.println("Type the ID of the meal you want to try refill:");
        MealManagement mealManagement = restaurantAdministrator.getMeal(scanner.nextInt());
        System.out.println("Type the quantity you want to refill with: ");
        int quantity = scanner.nextInt();
        restaurantAdministrator.Refill(mealManagement.getMeal(), quantity);
    }
    void Sale()
    {
        System.out.println("Type the ID of the meal you want to sale:");
        MealManagement mealManagement = restaurantAdministrator.getMeal(scanner.nextInt());
        System.out.println("Type the quantity you want to sale: ");
        int quantity = scanner.nextInt();
        restaurantAdministrator.Sale(mealManagement.getMeal(),quantity);
    }
    void CancelCollaboration()
    {
        System.out.println("Type the ID of the distributor you want to cancel collaboration with: ");
        Distributor distributor = restaurantAdministrator.getDistributor(scanner.nextInt());
        restaurantAdministrator.cancelCollaboration(distributor);
    }
    void RemoveDistributor()
    {
        System.out.println("Type the ID of the distributor you want to  remove: ");
        Distributor distributor = restaurantAdministrator.getDistributor(scanner.nextInt());
        restaurantAdministrator.removeDistributor(distributor);
    }
    void RemoveMeal()
    {
        System.out.println("Type the ID of the meal you want to remove:");
        restaurantAdministrator.removeMeal(scanner.nextInt());
    }
    void ShowAllMeals() {
        MealManagement[] mealManagements = restaurantAdministrator.getMealManagements();
        System.out.println("  Name, ID, Price, Quantity, LowQuantity");
        for (int i = 0; i < mealManagements.length; i++)
        {
            System.out.print((i + 1) + ": ");
            System.out.print(mealManagements[i].getMeal().getName());
            System.out.print(",");
            System.out.print(mealManagements[i].getMeal().getId());
            System.out.print(",");
            System.out.print(mealManagements[i].getMeal().getPrice());
            System.out.print(",");
            System.out.print(mealManagements[i].getQuantity());
            System.out.print(",");
            System.out.println(mealManagements[i].getLowQuantity());
        }
    }
    void ShowAllDistributors()
    {
        Distributor[] distributors = restaurantAdministrator.getDistributors();
        System.out.println("Name, ID, Meal costs");
        for(int i = 0; i < distributors.length; i++)
        {
            System.out.print((i + 1) + ": ");
            System.out.print(distributors[i].getName());
            System.out.print(",");
            System.out.print(distributors[i].getId());
            System.out.print(",");
            System.out.println(distributors[i].getCosts());
        }
    }
    void ShowAllCollaborations()
    {
        Distributor[] distributors = restaurantAdministrator.getDistributors();
        System.out.println("Meal ID, Max Quantity, Max Quantity on Request, Max Duty, Price");
        for(int i = 0; i < distributors.length; i++)
        {
            System.out.print((i+1)+": ");
            System.out.print(distributors[i].getSaleBuyCollaboration().getMeal().getId());
            System.out.print(",");
            System.out.print(distributors[i].getSaleBuyCollaboration().getMaxCollaborationQuantity());
            System.out.print(",");
            System.out.println(distributors[i].getSaleBuyCollaboration().getMaxQuantityOnRequest());
            System.out.print(",");
            System.out.println(distributors[i].getSaleBuyCollaboration().getMaxAllowedPaymentDuty());
            System.out.print(",");
            System.out.println(distributors[i].getSaleBuyCollaboration().getMealPrice());
        }
    }
}
