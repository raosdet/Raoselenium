package MavenProject.CarDetailsChecking;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class CarCheck2
{
  
	public void carDetailsCompare() throws Exception {
    //Read the data from input text file
    List<String> carsList = UserDefinedFunctions.ExtractCarDataFromInputFile("src\\resources\\car_input.txt");
   
    //Open the web site
    UserDefinedFunctions.OpenWebSite("https://cartaxcheck.co.uk/");
   
    //A variable to hold the extracted data for all cars
    Map<String, String> carDataMap = new HashMap<String, String>();
   
    //Extract data for each car
    for(String car : carsList)
    {
    System.out.println("Extracting the data for: " + car);
   
    Map<String, String> carData = UserDefinedFunctions.ExctractCarInfoFromWebSite(car);
   
    String carDataAsCommaSeperatedString = "";
   
    carDataAsCommaSeperatedString = carData.get("Registration");
    carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Make");
    carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Model");
    carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Colour");
    carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Year");
   
            carDataMap.put(car, carDataAsCommaSeperatedString);
            System.out.println("car: " + carDataAsCommaSeperatedString);
            System.out.println("=============");
    }
   
    System.out.println("");
    System.out.println("");
   
    //Close website
    //UserDefinedFunctions.CloseWebsite();
   
    //Extract the data from output file
    List<String> DataFromOutputFile = UserDefinedFunctions.ExtractDataOutputFile("src\\resources\\car_input.txt");
    System.out.println("Content from output file");
    for(String line : DataFromOutputFile)
    {
    System.out.println(line);
    }
   
    System.out.println("");
    System.out.println("");
   
    //Compare extracted car data for each car with data in output text file
    Iterator<Entry<String, String>> it = carDataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
           
            Boolean mapFound = false;
           
           
            System.out.println("Comparing the car " + pair.getKey() + " in output file content");
            //System.out.println(pair.getValue());
            for(String line : DataFromOutputFile)
            {
            //System.out.println(line);
            if (line.trim().contains(pair.getValue().toString().trim()))
        {
            System.out.println("MATCH FOUND");
        mapFound = true;
        break;
        }
            }
           
            if(mapFound) System.out.println("PASS");
            else System.out.println("FAIL");
           
            System.out.println("=============");
        }
    }
}

