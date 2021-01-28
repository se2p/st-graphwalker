package de.uni_passau.fim.se2.graphwalker;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import io.github.bonigarcia.wdm.WebDriverManager;
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

@GraphWalker(start = "e_startBrowser", value = "random(edge_coverage(100))")
public class UniPassauExecutor extends ExecutionContext implements UniPassau {

  private WebDriver driver = null;
  private WebDriverWait waiter = null;

  @BeforeExecution
  public void setup() {
    WebDriverManager.firefoxdriver().setup();
  }

  @AfterExecution
  public void cleanup() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Override
  public void e_search() {
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field")));
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field"))).clear();
    waiter
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field")))
        .sendKeys("Gordon Fraser" + Keys.ENTER);
  }

  @Override
  public void e_back() {
    String path = "/html/body/div/div[7]/div/nav/ul/li[1]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void e_logo() {
    String path = "/html/body/div[1]/div[7]/header/div[1]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void v_BaseURL() {
    waiter.until(ExpectedConditions.titleContains("Universität Passau"));
  }

  @Override
  public void e_enterBaseURL() {
    driver.get("https://www.uni-passau.de");
  }

  @Override
  public void e_studierende() {
    String path = "/html/body/div[1]/div[8]/div[1]/div/aside/div/div/section/nav/ul/li[1]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void v_BrowserStarted() {
    assertNotNull(driver);
  }

  @Override
  public void e_semesterTerminPlan() {
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Semesterterminplan"))).click();
  }

  @Override
  public void v_searchResult() {
    String linkText = "Prof. Dr. Gordon Fraser - Lehrstuhl für Software Engineering II an der Universität Passau";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
    String href = driver.findElement(By.linkText(linkText)).getAttribute("href");
    assertThat(href, containsString("software-engineering-ii"));
  }

  @Override
  public void e_startBrowser() {
    driver = new FirefoxDriver();
    waiter = new WebDriverWait(driver, 10);
  }

  @Override
  public void v_SemesterTerminPlan() {
    waiter.until(ExpectedConditions.titleContains("Termine und Fristen"));
  }

  @Override
  public void e_studSearch() {
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field")));
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field"))).clear();
    waiter
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("search_field")))
        .sendKeys("Gordon Fraser" + Keys.ENTER);

  }

  @Override
  public void v_Studierende() {
    String linkText = "Semesterterminplan";
    waiter.until(ExpectedConditions.titleContains("Studierende"));
    waiter.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
    String href = driver.findElement(By.linkText(linkText)).getAttribute("href");
    assertThat(href, containsString("termine-fristen"));
  }
}
