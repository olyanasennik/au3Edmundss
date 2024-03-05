import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys(Keys.chord(Keys.COMMAND, "A"), Keys.BACK_SPACE);

            driver.findElement(By.xpath("//input[@name= 'zip']")).sendKeys("22031", Keys.ENTER);
            //Click the checkbox:
        Thread.sleep(5000);
        WebElement element = driver.findElement(By.xpath("//label[@class= 'checkbox-facet d-flex align-items-center mb-0 justify-content-between'][2]"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);


//Choose Tesla for a make model
            Select dropdownbox = new Select(driver.findElement(By.xpath("//select[@name = 'make']")));
            dropdownbox.selectByVisibleText("Tesla");
//Verify that the default option in Models dropdown is "Any Model"  and default years are 2012 and 2023 in year field:
        Assert.assertTrue((driver.findElement(By.xpath("//select[@id = 'usurp-make-select']")).isDisplayed()));

        driver.findElement(By.xpath("//input[@value='2012']")).click();
            Assert.assertTrue((driver.findElement(By.xpath("//input[@value='2012']"))).isDisplayed());
        driver.findElement(By.xpath("//input[@value='2023']")).click();
            Assert.assertTrue((driver.findElement(By.xpath("//input[@value='2023']"))).isDisplayed());
//7. Verify that Model dropdown options are [Any Model, Model 3, Model S, Model X, Model Y, Cybertruck, Roadster]:
            Select selectModel = new Select(driver.findElement(By.xpath("//select[@id='usurp-model-select']")));
        List<WebElement> models = selectModel.getOptions();


            List<String> actualModels = new ArrayList<>();
            for (WebElement model : models) {
                actualModels.add(model.getText());
            }
        List<String> expectedModels = List.of("Add Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster");
        Assert.assertEquals(actualModels, expectedModels);
            //8. In Models dropdown choose Model 3:
            selectModel.selectByVisibleText("Model 3");
            //9. Enter 2020 for year min field and hit enter (clear the existing year first):
        Thread.sleep(1000);
            driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).click();
        driver.findElement(By.xpath("//input[@id='min-value-input-Year']")).sendKeys("2020", Keys.ENTER);
            //driver.findElement(By.className("inventory-button p-0_5 see-results-btn btn-primary-b text-capitalize ml-1 w-100 btn btn-secondary")).click();
/* 10.In the results page, verify that there are 21 search results, excluding the sponsored
 result/s. And verify that each search result title contains 'Tesla Model 3'
To isolate the 21 results, excluding the sponsored one/s, use a custom xpath which uses
the common class for the search results that you need.*/
Thread.sleep(1000);
List<WebElement> searchResults = driver.findElements(By.xpath("//div[@id = 'results-container']"));
int actualCount = searchResults.size();
int expectedCount = 21;
boolean sameTitle = true;
for (WebElement result: searchResults) {
    if(!result.getText().contains("Tesla Model 3")) {
        sameTitle = false;
        break;
    }
}
if(actualCount == expectedCount && sameTitle) {
    System.out.println("Search for NOT sponsored and same model is successful");
} else {
    System.out.println("!The search count/title failed!") ;
}

//11. Extract the year from each search result title and verify that each year is within the selected range (2020-2023):
boolean rangeYears = true;
for(WebElement result: searchResults){
    String title = result.findElement(By.className("title")).getText();
    String yearStr = title.substring(0, 4);
    int year = Integer.parseInt(yearStr);
    if(year < 2020 || year > 2023) {
        rangeYears = false;
        break;
    }
}
if (rangeYears){
    System.out.println("Years in range = SUCCESS");
}else{
    System.out.println("Years in range = !FAILED!");
}
        //12. From the dropdown on the right corner choose “Price: Low to High” option and verify that the results are sorted from lowest to highest price.:
Select sortDropdown = new Select(driver.findElement(By.xpath("//select[@data-tracking-id='select_sort_by']")));
sortDropdown.selectByVisibleText("Price: Low to High");
List<WebElement> prices = driver.findElements(By.xpath("//*[@id=\"sort-by\"]/option[4]"));
boolean sortedByPrice = true;
        for (int i = 0; i < prices.size() - 1; i++) {
            String price1 = prices.get(i).getText().replaceAll("[^\\d.]", "");
            String price2 = prices.get(i + 1).getText().replaceAll("[^\\d.]", "");
            if(Double.parseDouble(price1) > Double.parseDouble(price2)){
                sortedByPrice = false;
                break;
            }

        }
        if (sortedByPrice){
            System.out.println("Sorted result = Success");
        }else {
            System.out.println("Sorted result = Fail");
        }


        //13. From the dropdown menu, choose “Price: High to Low” option and verify that the results are sorted from highest to lowest price.:
sortDropdown.selectByVisibleText("Price: High to Low");
        List<WebElement> pricesHighToLow = driver.findElements(By.xpath("//*[@id=\"sort-by\"]/option[5]"));
        boolean sortedByPriceHightoLow = true;
        for (int i = 0; i < pricesHighToLow.size() - 1; i++) {
            String price1 = pricesHighToLow.get(i).getText().replaceAll("[^\\d.]", "");
            String price2 = pricesHighToLow.get(i + 1).getText().replaceAll("[^\\d.]", "");
            if(Double.parseDouble(price1) > Double.parseDouble(price2)){
                sortedByPriceHightoLow = false;
                break;
            }

        }
        if (sortedByPriceHightoLow){
            System.out.println("Sorted result from HIGHtoLOW= Success");
        }else {
            System.out.println("Sorted result from HIGHtoLOW = Fail");
        }



        //14. From the dropdown menu, choose “Mileage: Low to High” option and verify that the results are sorted from highest to lowest mileage.:
sortDropdown.selectByVisibleText("Mileage: Low to High");
    List<WebElement> mileages = driver.findElements(By.xpath("//*[@id=\"sort-by\"]/option[8]"));
    boolean sortedByMileage = true;
        for (int i = 0; i < mileages.size() - 1; i++) {
            int mileage1 = Integer.parseInt(mileages.get(i).getAttribute("data-mileage"));
            int mileage2 = Integer.parseInt(mileages.get(i+1).getAttribute("data-mileage"));
            if (mileage1 > mileage2) {
              sortedByMileage = false;
              break;
            }

        }
        if(sortedByMileage){
            System.out.println("Sort by mileage = SUCCESS");
        } else {
            System.out.println("Sort by mileage = !FAIL!");
        }
        //15. Find the last result and store its title, price and mileage (get the last result dynamically, i.e., your code should click on the last result regardless of how many results are there). Click on it to open the details about the result.:
WebElement lastResult = searchResults.get(searchResults.size() - 1) ;
        String lastResultTitle = lastResult.findElement(By.className("title")).getText();
        String lastResultPrice = lastResult.findElement(By.className("price")).getText();
        String lastResultMileage = lastResult.getAttribute("data-mileage");
        lastResult.click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("vehicle-header")));
Thread.sleep(5000);
        //16. Verify the title price and mileage matches the info from the previous step.:
WebElement detailsTitle = driver.findElement(By.xpath("//h1[@class = 'd-inline-block mb-0 heading-2 mt-0_25']"));
        WebElement detailsPrice = driver.findElement(By.xpath("//div[@class = 'price-summary-section align-items-baseline row']"));
        WebElement detailsMileage = driver.findElement(By.xpath("//div[@class = 'pr-0 font-weight-bold text-right ml-1 col']"));
if(detailsTitle.getText().equals(lastResultTitle) && detailsPrice.getText().equals(lastResultPrice) && detailsMileage.getText().contains(lastResultMileage)) {
    System.out.println("Details page title, price and mileage verified SUCCESSFULLY");
} else {
    System.out.println("Details page title, price and mileage !FAILED! ");
}
        //17. Go back to the results page and verify that the clicked result has “Viewed” element on it.
driver.navigate().back();
WebElement viewedElement = lastResult.findElement(By.className("Viewed"));
if(viewedElement.isDisplayed()) {
    System.out.println("SUCCESS for viewed indicator");
}else{
    System.out.println("!FAIL! for viewed indicator");
}
        //18.close the browser
        driver.quit();





        }
    }



