package DateCalculation.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.Asserts;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.Assert;


public class weathershopper {

	public static void main(String[] args) throws InterruptedException {
	
		/* 
		* We are using Chromedriver of version 95 or above and Selenium 3.1 or above
		* We can download selenium and add it to class path or we can Maven POM file to download from repository
		*/
		System.setProperty("webdriver.chrome.driver", "C://Users//sudheer_majji//Desktop//chromedriver_win32//chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		/* 
		* Launching weathershopper site and checking current temperature
		*/
		driver.get("https://weathershopper.pythonanywhere.com/");
		String temp=driver.findElement(By.xpath("//*[@id='temperature']")).getText();
		int temperature;
		if(temp.length()==3){temperature=Integer.parseInt(temp.substring(0,1));}
		else{temperature=Integer.parseInt(temp.substring(0,2));}
		
		/* 
		* Creating Hashmaps and storing all the Moisturizers and Sunscreens
		* Creating Hashmaps and storing all the Moisturizers,Sunscreens and corresponding cart buttons webElements
		*/
		
		HashMap<Integer, String> aloevera= new HashMap<Integer, String>();
		HashMap<Integer, String> almond= new HashMap<Integer, String>();
		HashMap<Integer, String> spf50= new HashMap<Integer, String>();
		HashMap<Integer, String> spf30= new HashMap<Integer, String>();
		HashMap<String, WebElement> moisEle= new HashMap<String, WebElement>();
		HashMap<String, WebElement> sunEle= new HashMap<String, WebElement>();
		int price1;
		int price2;
		/* 
		* Validating whether current temperature is <19 or not
		* Based on current temperature, select Moisturizers or Sunscreens
		*/
		if(temperature<19){
			driver.findElement(By.partialLinkText("moisturizers")).click();
			
			
			for(int i =2;i<=3;i++)
				{
				
						for(int j=1;j<=3;j++)
						{
							/* 
							* Identifying name and price of each item and adding them in Hashmap
							*/
							String name=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/p[1]")).getText();
							String price=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/p[2]")).getText();
							WebElement cart=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/button"));
							moisEle.put(name, cart);
							int finalPrice;
							
							if(price.length()==14){finalPrice=Integer.parseInt(price.substring(11,14));}
							else {finalPrice=Integer.parseInt(price.substring(7,10));}
							
							if(name.contains("Aloe")){aloevera.put(finalPrice, name);}
							else{almond.put(finalPrice, name);}
							
							
						}
					
				}
			/* 
			* Selecting least priced Aloevera and Least priced Almond gel
			* Adding them to cart
			*/
				if(aloevera.size()==1)
				{
				for (Object val : aloevera.values()) { moisEle.get(val).click();}
				price1=Collections.min(aloevera.keySet());
				}
				else{moisEle.get(aloevera.get(Collections.min(aloevera.keySet()))).click(); price1=Collections.min(aloevera.keySet());}
				
				if(almond.size()==1)
				{
				for (Object val : almond.values()) { moisEle.get(val).click();}
				price2=Collections.min(almond.keySet());
			
				}
				else{moisEle.get(almond.get(Collections.min(almond.keySet()))).click();price2=Collections.min(almond.keySet());}
				int tot=price1+price2;
				/* 
				* Clicking on CART button
				*/
				driver.findElement(By.xpath("/html/body/nav/ul/button")).click();
				/* 
				* Validating the total value in Cart page
				*/
				String totalValue=driver.findElement(By.xpath("//*[@id='total']")).getText();
				int total=Integer.parseInt(totalValue.substring(14,17));
				if(tot==total){
					System.out.println("total is matching");
				}
				/* 
				* Click on payment
				* Adding CC details for payment and submit
				*/
				driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/button/span")).click();
				driver.switchTo().frame(driver.findElement(By.xpath("/html/body/iframe")));
				driver.findElement(By.id("email")).sendKeys("ss@g.com");
				JavascriptExecutor js= (JavascriptExecutor) driver;
				js.executeScript("arguments[1].value = arguments[0]; ", "4242424242424242", driver.findElement(By.xpath("//*[@id='card_number']")));
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='cc-exp']")).sendKeys("12");
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='cc-exp']")).sendKeys("21");
				driver.findElement(By.xpath("//*[@id='cc-csc']")).sendKeys("123");
				driver.findElement(By.xpath("//*[@id='billing-zip']")).sendKeys("530051");
				driver.findElement(By.xpath("//*[@id='submitButton']")).click();
				Thread.sleep(5000);
				/* 
				* Validation payment status(failed or successful
				*/
				String successMessage=driver.findElement(By.xpath("/html/body/div/div[1]/h2")).getText();
				if(successMessage.equalsIgnoreCase("PAYMENT SUCCESS")){
					System.out.println("Payment succesful");
				}
				else{System.out.println("Payment failed");}
				

		}
		
		else{
			driver.findElement(By.partialLinkText("sunscreens")).click();
		
				for(int i =2;i<=3;i++)
					{
				
						for(int j=1;j<=3;j++)
						{
							/* 
							* Identifying name and price of each item and adding them in Hashmap
							*/
							String name=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/p[1]")).getText();
							String price=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/p[2]")).getText();
							WebElement cart=driver.findElement(By.xpath("/html/body/div[1]/div["+i+"]/div["+j+"]/button"));
							sunEle.put(name, cart);
							int finalPrice;
								if(price.length()==14){finalPrice=Integer.parseInt(price.substring(11,14));}
								else{finalPrice=Integer.parseInt(price.substring(7,10));}
								
								if(name.toLowerCase().contains("spf-50")){spf50.put(finalPrice, name);}
								if(name.toLowerCase().contains("spf-30")){spf30.put(finalPrice, name);}
						}
		
					}

				/* 
				* Selecting least priced SPF-50 and Least priced SPF-30
				* Adding them to cart
				*/
				if(spf50.size()==1)
				{
				for (Object val : spf50.values()) { sunEle.get(val).click();}
				price1=Collections.min(spf50.keySet());
				}
				else{sunEle.get(spf50.get(Collections.min(spf50.keySet()))).click();price1=Collections.min(spf50.keySet());}
				
				if(spf30.size()==1)
				{
				for (Object val : spf30.values()) { sunEle.get(val).click();}
				price2=Collections.min(spf30.keySet());
				}
				else{sunEle.get(spf30.get(Collections.min(spf30.keySet()))).click();price2=Collections.min(spf30.keySet());}
				int tot=price1+price2;
				/* 
				* Clicking on CART button
				*/
				driver.findElement(By.xpath("/html/body/nav/ul/button")).click();
				/* 
				* Validating the total value in Cart page
				*/
				
				String totalValue=driver.findElement(By.xpath("//*[@id='total']")).getText();
				int total=Integer.parseInt(totalValue.substring(14,17));
				if(tot==total){
					System.out.println("Total amount is matching");
				}
				/* 
				* Click on payment
				* Adding CC details for payment and submit
				*/
				driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/button/span")).click();
				driver.switchTo().frame(driver.findElement(By.xpath("/html/body/iframe")));
				driver.findElement(By.id("email")).sendKeys("ss@g.com");
				JavascriptExecutor js= (JavascriptExecutor) driver;
				js.executeScript("arguments[1].value = arguments[0]; ", "4242424242424242", driver.findElement(By.xpath("//*[@id='card_number']"))); 
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='cc-exp']")).sendKeys("12");
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='cc-exp']")).sendKeys("21");
				driver.findElement(By.xpath("//*[@id='cc-csc']")).sendKeys("123");
				driver.findElement(By.xpath("//*[@id='billing-zip']")).sendKeys("530051");
				driver.findElement(By.xpath("//*[@id='submitButton']")).click();
				Thread.sleep(5000);
				/* 
				* Validation payment status(failed or successful
				*/
				String successMessage=driver.findElement(By.xpath("/html/body/div/div[1]/h2")).getText();
				if(successMessage.equalsIgnoreCase("PAYMENT SUCCESS")){System.out.println("Payment is succesful");}
				else{System.out.println("Payment is failed");}
				
			}
		

}
}
