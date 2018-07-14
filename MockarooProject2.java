package Mocaroo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.io.SocketInputBuffer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooProject2 {

	WebDriver driver;
	ArrayList<String>addCities=new ArrayList<>();
	ArrayList<String>addCountries=new ArrayList<>();
	@BeforeClass
		public void setUp() {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().fullscreen();
			driver.get("https://mockaroo.com/"); // Step 2
		}
//	@AfterClass
//	public void tearDown() {
//		driver.close();
//	}

	
	@Test (priority=1)
	public void checkTitle() {
		assertTrue(driver.getTitle().contains("Mockaroo"));
	}
	@Test(priority=2)
	public void assertMockarooReal(){
		WebElement mockaroo=driver.findElement(By.xpath("//div[@class='brand']"));
		assertTrue(mockaroo.isDisplayed());
		WebElement realisticDataGenerator=driver.findElement(By.xpath("//div[@class='tagline']"));
		assertTrue(realisticDataGenerator.isDisplayed());
	}
	
	@Test(priority=3)
	public void remoteAllExisting() {
		List<WebElement> all=driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));
		for(WebElement all2:all) {
			all2.click();
		}
		
	}
	@Test(priority=4)
	
	
	public void assertFTOptionsDisplayed() {

		List<WebElement> FieldName=driver.findElements(By.xpath("//div[starts-with(@class,'column column-header')]"));
		
		for(WebElement each: FieldName) {
			
			assertTrue(each.isDisplayed());
		}
		
		
	}
	@Test(priority=5) 
	public void AddAnotherField() {
		assertTrue(driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isDisplayed());	
	}
	@Test(priority=6)
	public void defaultNumber() {

		assertEquals(driver.findElement(By.xpath("//input[@id='num_rows']")).getAttribute("value"), "1000");
		
	}
	
	@Test(priority=7)
	public void csv() {
		String expected ="CSV";
		String actual=driver.findElement(By.xpath("//option[@value='csv']")).getText();
		assertEquals(actual, expected);
	}
	
	@Test(priority=8)
	public void Unix() {
		String expected ="Unix (LF)";
		String actual=driver.findElement(By.xpath("//option[@value='unix']")).getText();
		assertEquals(actual, expected);
				
	}
	@Test(priority=9)
	public void BOM() {
		assertTrue(driver.findElement(By.xpath("(//input[@type='checkbox'])[34]")).isSelected());
		assertTrue(!driver.findElement(By.xpath("(//input[@type='checkbox'])[35]")).isSelected());
	}
	@Test (priority=10)
	public void AddAnotherFieldCity() throws InterruptedException {
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("(//input[@class='column-name form-control'])[7]")).sendKeys("City");
		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[7]")).click();
		
		String expected="Choose a Type";
		Thread.sleep(1000);
		String actual=driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals(actual, expected);
	}
	@Test(priority=11)
	public void cityCity() {
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("City");
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
	}
	@Test (priority=12)
	public void AddAnotherFieldCountry() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[8]")).clear();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[8]")).sendKeys("Country");
		driver.findElement(By.xpath("(//input[@class='btn btn-default'])[8]")).click();
		Thread.sleep(1000);
		
		String expected="Choose a Type";
		Thread.sleep(1000);
		String actual=driver.findElement(By.xpath("(//h3[@class='modal-title'])[1]")).getText();
		assertEquals(actual, expected);
	}
	@Test(priority=13)
	public void Country() {
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("Country");
		driver.findElement(By.xpath("//div[@class='type-name']")).click();
	}
	//16
	@Test(priority=14)
	public void download() throws InterruptedException {
		Thread.sleep(1000);
		driver.findElement(By.id("download")).click();
	}
	//17-20
	@Test(priority=15)
	public void Buffer() throws IOException, InterruptedException {
		FileReader reader= new FileReader("/Users/mivekulmedova/Desktop/MOCK_DATA.csv");
		BufferedReader br=new BufferedReader(reader);
		String fLine=br.readLine();
		String allLine=br.readLine();
		assertEquals(fLine, "City,Country");
		
		ArrayList<String>allCities=new ArrayList<>();
		
		while(allLine!=null) {
			allCities.add(allLine);
			allLine=br.readLine();
		}
		System.out.println(allCities.size());
		Thread.sleep(1000);
		assertEquals(allCities.size(), 1000);

		//22
		int max=allCities.get(0).length();
		for (int i = 1; i < allCities.size(); i++) {
			if(allCities.get(i).length()>max) {
				max=allCities.get(i).length();
				
			}
		}
		for (int i = 0; i <allCities.size(); i++) {
			if(allCities.get(i).length()==max) {
				System.out.println("max length: "+allCities.get(i)+",");
			}
			
		}
		
		//22
		int min=allCities.get(0).length();
		for(int i=1; i<allCities.size();i++) {
			if(allCities.get(i).length()<min) {
				min=allCities.get(i).length();
			}
		}
	
		for(int i=0; i<allCities.size(); i++) {
			if(allCities.get(i).length()==min) {
				System.out.println("min length "+ allCities.get(i)+",");
			}
		}
		
					
				}
	//23
			@Test (priority=16)
			public void countryMentioned() {
			int count=0;
			for (int i = 0; i < addCountries.size(); i++) {
				for (int j = 0; j < addCountries.size(); j++) {
					if(addCountries.get(i).equals(addCountries.get(j))) {
				
			}
				
			}
				System.out.println("CountryName: "+addCountries.get(i)+" count: "+ count);
			
		}
	
			
		}
}
