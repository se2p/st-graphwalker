package de.uni_passau.fim.se2.graphwalker;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@GraphWalker(value = "random(edge_coverage(100))", start = "e_startBrowser")
public class SearchTest extends ExecutionContext implements Search {

  private WebDriver driver = null;
  private WebDriverWait waiter = null;

  @BeforeExecution
  public void setup() {
    FirefoxDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
  }

  @AfterExecution
  public void cleanup() {
    if (driver != null) {
      driver.quit();
    }
  }

  public void v_searchResult() {
    String linkText =
        "Gordon Fraser übernimmt den Lehrstuhl für Software Engineering II - Universität Passau";
    waiter.until(ExpectedConditions.titleContains("Universität Passau"));
    waiter.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
    String href = driver.findElement(By.linkText(linkText)).getAttribute("href");
    assertThat(href, containsString("software-engineering-ii"));
  }

  public void e_search() {
    // searcharea
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("q")));
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("q"))).clear();
    waiter
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("q")))
        .sendKeys("Gordon Fraser" + Keys.ENTER);
  }

  public void v_BaseURL() {
    waiter.until(ExpectedConditions.titleContains("Universität Passau"));
  }

  public void e_startBrowser() {
    driver = new FirefoxDriver();
    assertNotNull(driver);
    waiter = new WebDriverWait(driver, 10);
  }

  public void e_enterBaseURL() {
    driver.get("http://www.uni-passau.de");
  }

  public void v_BrowserStarted() {
    assertNotNull(driver);
  }
}
