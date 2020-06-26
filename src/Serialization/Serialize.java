package Serialization;
import Distributor.Distributor;
import Meal.Meal;
import Restaurant.Management.MealManagement;
import Restaurant.RestaurantAdministrator;
import Collaborations.SaleBuyCollaboration;

import java.io.*;
import java.util.Locale;
import java.util.Scanner;

import java.sql.*;

public class Serialize {
    static Serialize Singleton = null;
    static RestaurantAdministrator owner;
    File fileInputStream;
    Scanner scanner;
    FileOutputStream fileOutputStream;
    DataOutputStream dataOutputStream;

    Connection sqlConnection;
    public static Serialize SetOwner(RestaurantAdministrator Owner)
    {
        owner = Owner;
        return getInstance();
    }

    public Serialize() {
        try{
            sqlConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=oana","oana","1234");
        }
        catch (SQLException e)
        {
            System.out.println("Nu am putut gasi baza de date");
        }
    }

    public static Serialize getInstance()
    {
        if(Singleton == null)
        {
            Singleton = new Serialize();
        }
        return Singleton;
    }
    public void toCSV() throws IOException
    {
        //meals
        fileOutputStream = new FileOutputStream("mealManagements.csv");
        dataOutputStream = new DataOutputStream(fileOutputStream);
        MealManagement[] mealManagements = owner.getMealManagements();
        String nl = System.getProperty("line.separator");
        for(int i = 0; i < mealManagements.length; i++)
        {
            mealManagementToCSV(mealManagements[i]);
            dataOutputStream.writeBytes(nl);
        }
        dataOutputStream.close();
        fileOutputStream.close();
        //distributors
        fileOutputStream = new FileOutputStream("distributorsManagement.csv");
        dataOutputStream = new DataOutputStream(fileOutputStream);
        Distributor[] distributors = owner.getDistributors();
        for(int i = 0; i < distributors.length; i++)
        {
            distributorToCSV(distributors[i]);
            dataOutputStream.writeBytes(nl);
        }
        dataOutputStream.close();
        fileOutputStream.close();
    }
    public void fromCSV() throws IOException
    {
        //meals
        String nl = System.getProperty("line.separator");
        fileInputStream = new File("mealManagement.csv");
        scanner = new Scanner(fileInputStream).useDelimiter(",|".concat(nl));
        while(scanner.hasNext())
        {
            MealManagement pd = mealManagementFromCSV();
            owner.addMeal(pd);
        }
        scanner.close();
        fileInputStream = new File("distributorsManagement.csv");
        scanner = new Scanner(fileInputStream).useDelimiter(",|".concat(nl));
        while(scanner.hasNext())
        {
            Distributor distributor = distributorFromCSV();
            owner.addDistributor(distributor);
        }
        scanner.close();
    }
    void collaborationToCSV(SaleBuyCollaboration collaboration) throws IOException
    {
        dataOutputStream.writeBytes(collaborationToString(collaboration));
    }
    String collaborationToString(SaleBuyCollaboration collaboration)
    {
        return new String(
                Float.toString(collaboration.getMealPrice()).concat(
                        ",".concat(
                                Integer.toString(collaboration.getMaxCollaborationQuantity()).concat(
                                        ",".concat(
                                                Integer.toString(collaboration.getMaxQuantityOnRequest()).concat(
                                                        ",".concat(
                                                                Integer.toString(collaboration.getMaxAllowedPaymentDuty()).concat(
                                                                        ",".concat(
                                                                                mealToString(collaboration.getMeal())
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }
    SaleBuyCollaboration collaborationFromCSV() throws IOException
    {
        String buf = scanner.next();
        if(buf.equals("null"))
            return null;
        float mealPrice = Float.parseFloat(buf);
        int maxCollaborationQuantity = scanner.nextInt();
        int maxQuantityOnRequest = scanner.nextInt();
        int maxAllowedPaymentDuty = scanner.nextInt();
        Meal meal = mealFromCSV();
        return new SaleBuyCollaboration(maxCollaborationQuantity, maxQuantityOnRequest, maxAllowedPaymentDuty, mealPrice, meal);
    }
    void mealToCSV(Meal meal) throws IOException
    {
        dataOutputStream.writeBytes(Integer.toString(meal.getId()));
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(meal.getPrice() + "");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(meal.getName());
    }
    Meal mealFromCSV() throws IOException
    {
        int id =scanner.nextInt();
        float price = scanner.nextFloat();
        String name = scanner.next();
        return new Meal(name, price, id);
    }
    Meal mealFromString(String s)
    {
        Scanner scanner = new Scanner(s);
        int id = scanner.nextInt();
        float price = scanner.nextFloat();
        String name = scanner.next();
        return new Meal(name, price, id);
    }
    String mealToString(Meal meal)
    {
        return new String(
                Integer.toString(meal.getId()).concat(
                        ",".concat(
                                Float.toString(meal.getPrice()).concat(
                                        ",".concat(
                                                meal.getName()
                                        )
                                )
                        )
                )
        );
    }
    void mealManagementToCSV(MealManagement mealManagement) throws IOException
    {
        dataOutputStream.writeBytes(mealManagement.getQuantity() + "");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(mealManagement.getLowQuantity() + "");
        dataOutputStream.writeByte(',');
        mealToCSV(mealManagement.getMeal());
    }
    MealManagement mealManagementFromCSV() throws IOException
    {
        int quantity = scanner.nextInt();
        int lowQuantity = scanner.nextInt();
        Meal meal = mealFromCSV();
        return new MealManagement(owner, meal, quantity, lowQuantity);
    }
    Distributor distributorFromCSV() throws IOException
    {
        int id = scanner.nextInt();
        String name = scanner.next();
        float partnerDuty = scanner.nextFloat();
        SaleBuyCollaboration collaboration;
        collaboration = collaborationFromCSV();
        int deliveredQuantity = scanner.nextInt();
        float costs = scanner.nextFloat();
        float profit = scanner.nextFloat();
        Distributor distributor = new Distributor(name, costs, id, partnerDuty, deliveredQuantity, profit);
        if(collaboration != null)
            distributor.CreateCollaboration(owner, collaboration);
        return distributor;
    }
    void distributorToCSV(Distributor distributor) throws IOException
    {
        dataOutputStream.writeBytes(distributor.getId() + "");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(distributor.getName());
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(distributor.getPartnerDuty() + "");
        dataOutputStream.writeByte(',');
        if(distributor.getSaleBuyCollaboration() != null)
            dataOutputStream.writeBytes(collaborationToString(distributor.getSaleBuyCollaboration()));
        else
            dataOutputStream.writeBytes("null");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(distributor.getDeliveredQuantity() + "");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(distributor.getCosts() + "");
        dataOutputStream.writeByte(',');
        dataOutputStream.writeBytes(distributor.getProfit() + "");
    }
}
