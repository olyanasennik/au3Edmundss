import org.openqa.selenium.*;
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
            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys(Keys.CONTROL, "a", Keys.BACK_SPACE);

            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys("22031", Keys.ENTER);
            //Click the checkbox:
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.xpath("//input[@class = 'input m-0 no-focus form-check-input']"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);


//Choose Tesla for a make model
            Select dropdownbox = new Select(driver.findElement(By.xpath("//select[@name = 'make']")));
            dropdownbox.selectByVisibleText("Tesla");
//Verify that the default option in Models dropdown is "Any Model"  and default years are 2012 and 2023 in year field:
            Select modeldropdown = new Select(driver.findElement(By.id("usurp-model-select")));
            Assert.assertEquals(modeldropdown.getFirstSelectedOption().getText(), "Add Model");

            Select yeardropdownMin = new Select(driver.findElement(By.xpath("//input[@value='2012']")));
            Assert.assertEquals(yeardropdownMin.getFirstSelectedOption().getText(), "2012");

            Select yeardropdownMax = new Select(driver.findElement(By.id("//input[@value='2023']")));
            Assert.assertEquals(yeardropdownMax.getFirstSelectedOption().getText(), "2023");
//Verify that Model dropdown options are [Any Model, Model 3, Model S, Model X, Model Y, Cybertruck, Roadster]:
            Select selectModel = new Select(driver.findElement(By.id("usurp-model-select")));
        List<WebElement> models = selectModel.getOptions();


            List<String> actualModels = new ArrayList<>();
            for (WebElement model : models) {
                actualModels.add(model.getText());
            }
        List<String> expectedModels = List.of("Add Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");
        Assert.assertEquals(actualModels, expectedModels);
            //In Models dropdown choose Model 3:
            selectModel.selectByVisibleText("Model 3");
            //Enter 2020 for year min field and hit enter (clear the existing year first):
        Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).click();
        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).sendKeys("2020", Keys.ENTER);
            //driver.findElement(By.className("inventory-button p-0_5 see-results-btn btn-primary-b text-capitalize ml-1 w-100 btn btn-secondary")).click();
/*In the results page, verify that there are 21 search results, excluding the sponsored
 result/s. And verify that each search result title contains 'Tesla Model 3'
To isolate the 21 results, excluding the sponsored one/s, use a custom xpath which uses
the common class for the search results that you need.*/

        }
    }



