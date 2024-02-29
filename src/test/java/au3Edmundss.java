import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class au3Edmundss {
    public static void main (String[]args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //try {


            driver.get("https://www.edmunds.com/");
            //click on shop used:
            driver.findElement(By.xpath("//a[@data-tracking-id = 'home_page_inventory_select_tab']")).click();
            //In the next page, clear the zipcode field and enter 22031:
            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys(Keys.CONTROL, "a");

            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys("22031", Keys.ENTER);
            //Click the checkbox:
        Thread.sleep(2000);
            driver.findElement(By.xpath("//input[@class = 'input m-0 no-focus form-check-input']")).click();

//Choose Tesla for a make model
            Select dropdownbox = new Select(driver.findElement(By.xpath("//select[@name = 'make']")));
            dropdownbox.selectByVisibleText("Tesla");
//Verify that the default option in Models dropdown is "Any Model"  and default years are 2012 and 2023 in year field:
            Select modeldropdown = new Select(driver.findElement(By.id("usurp-model-select")));
            Assert.assertEquals(modeldropdown.getFirstSelectedOption().getText(), "Any Model ");

            Select yeardropdownMin = new Select(driver.findElement(By.id("min-value-input-Year")));
            Assert.assertEquals(yeardropdownMin.getFirstSelectedOption().getText(), "2012");

            Select yeardropdownMax = new Select(driver.findElement(By.id("max-value-input-Year")));
            Assert.assertEquals(yeardropdownMax.getFirstSelectedOption().getText(), "2023");
//Verify that Model dropdown options are [Any Model, Model 3, Model S, Model X, Model Y, Cybertruck, Roadster]:
            List<String> expectedModels = List.of("Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");
            List<WebElement> options = new Select(driver.findElement(By.id("usurp-model-select"))).getOptions();

            List<String> actualModels = new ArrayList<>();
            for (WebElement option : options) {
                actualModels.add(option.getText());
            }
            Assert.assertEquals(actualModels, expectedModels);
            modeldropdown.selectByVisibleText("Model 3");
            driver.findElement(By.id("min-value-input-Year")).sendKeys("2020");
            driver.findElement(By.className("inventory-button p-0_5 see-results-btn btn-primary-b text-capitalize ml-1 w-100 btn btn-secondary")).click();

        }
    }



