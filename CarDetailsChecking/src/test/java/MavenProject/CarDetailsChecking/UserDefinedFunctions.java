package MavenProject.CarDetailsChecking;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserDefinedFunctions {

public static WebDriver driver;


/**
* Extracts the car details and returns a string of Array of cars.
* inputTextFileName: src\\resources\\car_input.txt
* @param inputTextFileName
* @return
*/
public static List<String> ExtractCarDataFromInputFile(String inputTextFileName)
{
List<String> carsList = new ArrayList();

String CAR_NUMBER_REGEX = "[A-Za-z]{2}[0-9]{2}[ ]{0,1}[a-zA-Z]{3}";
Pattern p = Pattern.compile(CAR_NUMBER_REGEX);
BufferedReader reader;

try {
reader = new BufferedReader(new FileReader(inputTextFileName));
String line;  

while((line=reader.readLine())!=null)   {
Matcher m = p.matcher(line);
   
while(m.find()) {
       //System.out.println(m.group());
       carsList.add(m.group());
       }
}
reader.close();
}
catch (IOException e) {
e.printStackTrace();
}

return carsList;
}

public static List<String> ExtractDataOutputFile(String OutputTextFileName)
{
List<String> OutputFileData = new ArrayList();

BufferedReader reader;

try {
reader = new BufferedReader(new FileReader(OutputTextFileName));
String line;  

while((line=reader.readLine())!=null)   {
OutputFileData.add(line);
}
reader.close();
}
catch (IOException e) {
e.printStackTrace();
}

return OutputFileData;
}

public static void OpenWebSite(String Url)
{
WebDriverManager.firefoxdriver().setup();
driver = new FirefoxDriver();
    driver.get(Url);
    //System.out.println( driver.getTitle() );
}


/**
* Extracts Registration, Make, Model, Colour and Year info from the website as a Map<String, String>
* @param carRegNumber
* @return
* @throws InterruptedException
*/
public static Map<String, String> ExctractCarInfoFromWebSite(String carRegNumber) throws InterruptedException
{
	 driver.navigate().refresh();
WebElement searchBox= driver.findElement(By.id("vrm-input"));

        searchBox.sendKeys(carRegNumber);
       
        WebElement getFullCheckButton = driver.findElement(By.xpath("//button[contains(@class,'jsx-3655351943')]"));
        getFullCheckButton.click();
       
        Thread.sleep(5000);
       
        Map<String, String> carData = new HashMap<String, String>();
       
        carData.put("Registration", ExtractItem("Registration"));
        carData.put("Make", ExtractItem("Make"));
        carData.put("Model", ExtractItem("Model"));
        carData.put("Colour", ExtractItem("Colour"));
        carData.put("Year", ExtractItem("Year"));    
       
        driver.navigate().back();
       
        return carData;
}

/**
* Extract the DD based on given DT
* @param ItemName
* @return
*/
public static String ExtractItem(String ItemName)
{
WebElement dataTerm = driver.findElement(By.xpath("//*[text()='"+ItemName+"']"));
        //System.out.println(dataTerm.getText());
       
        WebElement dataList = dataTerm.findElement(By.xpath("./.."));
       
        WebElement dataDescirption = dataList.findElement(By.tagName("dd"));
        //System.out.println(dataDescirption.getText());
       
        return dataDescirption.getText();
}

public static void CloseWebsite()
{
driver.close();
        driver.quit();
}
}

