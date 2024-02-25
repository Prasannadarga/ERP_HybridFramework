package commonFuntions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary 
{
public static WebDriver driver ;
public static Properties pro ;
public static WebDriver startBrowser() throws Throwable
{
	pro = new Properties();
	pro.load(new FileInputStream("./PropertyFiles//Environment.properties"));
	if(pro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
		else if(pro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
		driver = new FirefoxDriver();	
		}
		else
		{
			Reporter.log("Browser value is Not Matching",true);
		}
	return driver;
}
	public static void OpenUrl(WebDriver driver)
	{
		driver.get(pro.getProperty("Url"));
	}
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String TestData)
	
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));	
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));	
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));

			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));	
		}
	}
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
	}


	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_value)
	{
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_value)).click();
			
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
		}
	}

public static void validateTitle(WebDriver driver,String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Expected_Title, Actual_Title, "Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
	
}
public static void mouseClick(WebDriver driver)throws Throwable
{
	Actions ac = new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//a[start-with(text(),'stockItems')]"))).perform();
	Thread.sleep(2000);
	ac.moveToElement(driver.findElement(By.xpath("//a[.='Stock Categories'](2)")));
	ac.pause(3000).click().perform();
}
public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable{
	if(!driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).isDisplayed())
	driver.findElement(By.xpath(pro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(pro.getProperty("Search-button"))).click();
	Thread.sleep(3000);
	String Act_data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	Reporter.log(Exp_Data+" "+Act_data,true);
	try {
		Assert.assertEquals(Exp_Data , Act_data,"Category name is not found in table");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
		
	}
}
	public static void dropDownAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.xpath(Locator_Value));
			Select xt =new Select(element);
			
			xt.selectByIndex(value);
		}
		
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.id(Locator_Value));
			Select xt =new Select(element);
			
			xt.selectByIndex(value);
		}

		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			WebElement element = driver.findElement(By.name(Locator_Value));
			Select xt =new Select(element);
			
			xt.selectByIndex(value);
		}
	}
	public static void captureStock(WebDriver driver,String Locator_Type,String Locator_value) throws Throwable
	{
		String StockNumber = " " ;
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			StockNumber = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			StockNumber = driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			StockNumber = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./captureData/stockNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNumber);
		bw.flush();
		bw.close();
	}
	public static void StockTable(WebDriver driver) throws Throwable
	{
		FileReader fr = new FileReader("./captureData/stockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(pro.getProperty("Search-panel"))).click();
		driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(pro.getProperty("Search-button"))).click();
		Thread.sleep(3000);
			String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(Exp_Data+" "+Act_data,true);
			Assert.assertEquals(Exp_Data,Act_data,"stockNumber not found in table");
			}	
	public static void captureSupplier(WebDriver driver,String Locator_Type,String Locator_value) throws Throwable
	{
		String SupplierNum = " ";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			SupplierNum= driver.findElement(By.id(Locator_value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			SupplierNum = driver.findElement(By.name(Locator_value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			SupplierNum= driver.findElement(By.xpath(Locator_value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./captureData/supplierNumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();	
	}
	public static void suppliertable(WebDriver driver, String locator_Type, String locator_value) throws Throwable
	{
		FileReader fr = new FileReader("./captureData/supplierNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(pro.getProperty("Search-panel"))).click();
		driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(pro.getProperty("Search-button"))).click();
		Thread.sleep(3000);
			String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+" "+Act_data,true);
			Assert.assertEquals(Exp_Data,Act_data,"supplierNumber is not found in table");
	}		
	
public static void capturecustomer(WebDriver driver,String Locator_value,String Locator_Type) throws Throwable
{
	String customerNum = " ";
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		customerNum = driver.findElement(By.id(Locator_value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		customerNum = driver.findElement(By.name(Locator_value)).getAttribute("value");
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		customerNum = driver.findElement(By.xpath(Locator_value)).getAttribute("value");
	}
	FileWriter fw = new FileWriter("./captureData/CustomerNumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(customerNum);
	bw.flush();
	bw.close();	
}

public static void customerTable(WebDriver driver) throws Throwable
{
	FileReader fr = new FileReader("./captureData/CustomerNumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_Data = br.readLine();
	if(!driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(pro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(pro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
	driver.findElement(By.xpath(pro.getProperty("Search-button"))).click();
	Thread.sleep(3000);
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+" "+Act_data,true);
		try {
		Assert.assertEquals(Exp_Data,Act_data,"CustomerNumber is not found in table");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
}		
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("2014-02-08");
			return df.format(date);
}

public static void closeBrowser(WebDriver driver)
{
	driver.quit();
		}
	}

	



