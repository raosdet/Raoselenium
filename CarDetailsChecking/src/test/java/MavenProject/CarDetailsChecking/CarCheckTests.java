package MavenProject.CarDetailsChecking;


import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CarCheckTests {

private static String inputFolder = "src\\resources\\inputfiles";
private static String outputFolder = "src\\resources\\outputfiles";

private static List<String> carsList;
private static List<String> DataFromOutputFile;
private static Map<String, String> carDataMap;

@BeforeTest
public void setUp() throws InterruptedException {
carsList = UserDefinedFunctions.ExtractCarDataFromInputFolder(inputFolder);
DataFromOutputFile = UserDefinedFunctions.ExtractDataFromOutputFolder(outputFolder);

//Open the web site
    UserDefinedFunctions.OpenWebSite("https://cartaxcheck.co.uk/");
}

@AfterTest
public void tearDown() throws InterruptedException {
//Close the web site
    UserDefinedFunctions.CloseWebsite();
   
}


@DataProvider
public Iterator<String> testData() throws IOException {
   List<String> list = new ArrayList<>();
   list = carsList;
   
   return list.iterator();
}

@Test(dataProvider = "testData")
public void CarCheckTest(String carReg) throws InterruptedException {
System.out.println("Running the test for the car: " + carReg);

Map<String, String> carData = UserDefinedFunctions.ExctractCarInfoFromWebSite(carReg);

String carDataAsCommaSeperatedString = "";

carDataAsCommaSeperatedString = carData.get("Registration");
carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Make");
carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Model");
carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Colour");
carDataAsCommaSeperatedString = carDataAsCommaSeperatedString + "," + carData.get("Year");

        System.out.println("car: " + carDataAsCommaSeperatedString);
        System.out.println("=============");
       
        boolean matchFound = false;
       
        for(String line : DataFromOutputFile)
        {
        //System.out.println(line);
        if (line.trim().contains(carDataAsCommaSeperatedString.trim()))
    {
        System.out.println("MATCH FOUND");
    matchFound = true;
    break;
    }
        }
       
        Assert.assertEquals(matchFound, true);
}

}
