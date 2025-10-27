import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Ryan Bookram
 * Software Development 1 (14877)
 * October 10, 2025
 * VehicleManager.java
 * This class allows the storage and manipulation of Vehicle objects in an ArrayList
 */
public class VehicleManager {
    public ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

    /**
     * Method: addVehicle
     * Parameters: info
     * Return: String
     * Purpose: Allows the addition of a single vehicle entry to the ArrayList
     */
    public String addVehicle(String info) {

        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo  = info.split("-");

        try {
            if (splitInfo.length != 8) {
                return "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
            }
            else {

                vinNumber = Integer.parseInt(splitInfo[0]);
                make = splitInfo[1];
                model = splitInfo[2];
                price = Double.parseDouble(splitInfo[3]);
                trans = splitInfo[4];
                miles = Integer.parseInt(splitInfo[5]);
                color = splitInfo[6];
                drivetrain = splitInfo[7];

                vehicleList.add(new Vehicle(vinNumber, make, model, price, trans, miles, color, drivetrain));

                return "Vehicle added successfully!";
            }
        }
        catch(Exception e) {
            return "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
        }
    }

    /**
     * Method: addFromText
     * Parameters: fileName
     * Return: String
     * Purpose: Allows the addition of multiple vehicles to the ArrayList through a text file
     */
    public String addFromText(String fileName) {

        File inputFile = null;
        BufferedReader br = null;
        String vehicle;

        try {

            inputFile = new File(fileName);
            br = new BufferedReader(new FileReader(inputFile));

            while((vehicle = br.readLine()) != null) {
                addVehicle(vehicle);
            }

            return "Vehicles added successfully!";
        }
        catch(Exception e){
            return "An error has occurred. Please ensure your file path is correct and your text file is formatted correctly.";
        }
    }

    /**
     * Method: removeVehicle
     * Parameters: vin
     * Return: String
     * Purpose: Allows the removal of a single vehicle using the VIN number
     */
    public String removeVehicle(int vin) {

        String output = "Vehicle not found. Ensure the VIN number is correct.";

        for(int i = 0; i < vehicleList.size(); i++){

            if(vehicleList.get(i).getVinNumber() == vin){

                vehicleList.remove(i);
                output = "Vehicle removed successfully!";
            }
        }
        return output;
    }

    /**
     * Method: updateVehicle
     * Parameters: info
     * Return: String
     * Purpose: Updates a single vehicle based on the VIN number entered
     */
    public String updateVehicle(String info) {

        boolean exist = false;
        int vinNumber;
        String make;
        String model;
        double price;
        String trans;
        int miles;
        String color;
        String drivetrain;
        String[] splitInfo = info.split("-");
        String output = "";

        try {
            if (splitInfo.length != 8) {
                return "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
            } else {

                vinNumber = Integer.parseInt(splitInfo[0]);
                make = splitInfo[1];
                model = splitInfo[2];
                price = Double.parseDouble(splitInfo[3]);
                trans = splitInfo[4];
                miles = Integer.parseInt(splitInfo[5]);
                color = splitInfo[6];
                drivetrain = splitInfo[7];

                for (int i = 0; i < vehicleList.size(); i++) {

                    if (vehicleList.get(i).getVinNumber() == vinNumber) {

                        exist = true;
                        vehicleList.set(i, new Vehicle(vinNumber, make, model, price, trans, miles, color, drivetrain));

                        output = "Vehicle updated successfully!";
                    }
                }

                if (!exist) {
                    output = "Vehicle not found. Ensure the VIN number is correct.";
                }
            }
        }
        catch (Exception e) {
            output = "Invalid format. Ensure 8 attributes are entered and are separated by dashes with no spaces. Format: VIN-Make-Model-Price-Trans-Miles-Color-Drivetrain";
        }
        return output;
    }

    /**
     * Method: evaluateVehicle
     * Parameters: vin
     * Return: String
     * Purpose: Estimates a vehicle's possible condition using the VIN number as input
     */
    public String evaluateVehicle(int vin) {

        String eval = "Vehicle not found. Ensure the VIN number is correct.";

        for(int i = 0; i < vehicleList.size(); i++){

            if(vehicleList.get(i).getVinNumber() == vin){

                eval = "The condition of the " + vehicleList.get(i).getMake() + " " + vehicleList.get(i).getModel() + " (" + vin + ") is: " + vehicleList.get(i).evaluate();
            }
        }
        return eval;
    }

    /**
     * Method: displayVehicles
     * Parameters: None
     * Return: None
     * Purpose: Prints the full list of all vehicles in the ArrayList to the screen
     */
    public void displayVehicles() {

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.toString());
        }
    }

    /**
     * Method: getList
     * Parameters: None
     * Return: ArrayList<Vehicle>
     * Purpose: Returns the ArrayList containing all vehicle objects
     */
    public ArrayList<Vehicle> getList() {
        return vehicleList;
    }
}
