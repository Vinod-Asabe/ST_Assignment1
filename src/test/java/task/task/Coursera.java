package task.task;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Set;

public class Coursera {
    @Test(dataProvider = "testdata")
    public void checkCredentials(String username, String password) throws InterruptedException {
    	
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\vinod\\eclipse-workspace\\Assignment1\\Driver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        
        driver.manage().window().maximize();
        
        driver.get("https://www.coursera.org");

        WebElement logIn = driver.findElement(By.xpath("(//a[normalize-space()='Log In'])[1]"));
        logIn.click();

        WebElement UserName = driver.findElement(By.name("email"));
        UserName.sendKeys(username);
        
        WebElement Password = driver.findElement(By.name("password"));
        Password.sendKeys(password);
        
        WebElement Login = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
        Login.click();


        WebDriverWait wait = new WebDriverWait(driver, 100);
        WebElement blogLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Blog")));

        String blogUrl = blogLink.getAttribute("href");

        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0])", blogUrl);

        // Switch to the newly opened tab
        switchToNewTab(driver);
        
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='s']"));
        searchBox.click();
        searchBox.sendKeys("Interview Questions");
        searchBox.sendKeys(Keys.ENTER);
              
        System.out.println("Test Executed Successfully");

    }

    @DataProvider(name="testdata")
    public Object[][] TestDataFeed() {
        CourseraExcel config = new CourseraExcel("D:\\WorldLine\\Softaware Testing\\CourseraCredentials.xlsx");

        int rows = config.getRowCount(0);

        Object[][] credentials = new Object[rows][2];

        for(int i = 0; i < rows; i++) {
            credentials[i][0] = config.getData(0, i, 0);
            credentials[i][1] = config.getData(0, i, 1);
        }

        return credentials;
    }

    public void switchToNewTab(WebDriver driver) {
        // Switch to the newly opened tab
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
        }
    }
}
