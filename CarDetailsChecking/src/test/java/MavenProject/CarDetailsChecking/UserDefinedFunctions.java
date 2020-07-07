package MavenProject.CarDetailsChecking;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
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

/**
* Returns only text files in the given folder
* @param folderName
* @return
*/
public static File[] findAllTextFiles( String folderName){
        File folder = new File(folderName);

        return folder.listFiles(new FilenameFilter() {
                 public boolean accept(File dir, String filename)
                      { return filename.endsWith(".txt"); }
        } );

    }

/**
* Extracts car data from all text files in the given fodler
* @param inputFolderName
* @return
*/
public static List<String> ExtractCarDataFromInputFolder(String inputFolderName)
{
List<String> carsList = new ArrayList();

String CAR_NUMBER_REGEX = "[A-Za-z]{2}[0-9]{2}[ ]{0,1}[a-zA-Z]{3}";
Pattern p = Pattern.compile(CAR_NUMBER_REGEX);
BufferedReader reader;

for(File textFile : findAllTextFiles(inputFolderName))
{
try {
reader = new BufferedReader(new FileReader(textFile));
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
}

return carsList;
}

/**
* Extracts the data from given text file as array of content from each row
* @param OutputTextFileName
* @return
*/
public static List<String> ExtractDataFromOutputFile(String OutputTextFileName)
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

/**
* Extracts the data from all text files in the given folder as array of each row
* @param OutputFolderName
* @return
*/
public static List<String> ExtractDataFromOutputFolder(String OutputFolderName)
{
List<String> OutputFileData = new ArrayList();

BufferedReader reader;

for(File textFile : findAllTextFiles(OutputFolderName))
{
try {
reader = new BufferedReader(new FileReader(textFile));
String line;  

while((line=reader.readLine())!=null)   {
OutputFileData.add(line);
}
reader.close();
}
catch (IOException e) {
e.printStackTrace();
}
}

return OutputFileData;
}

/**
* Open the given website
* @param Url
*/
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
WebElement searchBox= driver.findElement(By.id("vrm-input"));
searchBox.clear();
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

/**
* Close the website
*/
public static void CloseWebsite()
{
driver.close();
        driver.quit();
}
}

