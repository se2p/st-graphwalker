package de.uni_passau.fim.se2.graphwalker;

import static org.junit.Assert.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.AfterExecution;
import org.graphwalker.java.annotation.BeforeExecution;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@GraphWalker(start = "v_start", value = "random(edge_coverage(100))")
public class UniPassauExecutor extends ExecutionContext implements uni {

  private WebDriver driver = null;
  private WebDriverWait waiter = null;

  @BeforeExecution
  public void setup() {
    WebDriverManager.chromedriver().setup();
    ChromeOptions options = new ChromeOptions();
    driver = new ChromeDriver(options);
    waiter = new WebDriverWait(driver, Duration.ofSeconds(5));
  }

  @AfterExecution
  public void cleanup() {
    if (driver != null) {
      driver.quit();
    }
  }

  @Override
  public void v_students() {
    String path = "/html/body/div[1]/div[10]/div[1]/div[2]/main/header/h1";
    String text =
        waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).getText();
    assertEquals("Studierende", text);
  }

  @Override
  public void e_goToStudents() {
    String path = "/html/body/div[1]/div[10]/div[1]/div/aside/div/div/section/nav/ul/li[1]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void e_back() {
    String path = "/html/body/div[1]/div[10]/div[1]/div[1]/div/nav/ol/li[1]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void v_terminplan() {
    waiter.until(ExpectedConditions.titleContains("Termine und Fristen"));
  }

  @Override
  public void e_goToTerminplan() {
    String path = "/html/body/div[1]/div[10]/div[1]/div[2]/main/section[2]/div/div[1]/div/div/div[2]/div/div/div/div/div[5]/a";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void e_goToMainPage() {
    driver.get("https://www.uni-passau.de");
  }

  @Override
  public void v_mainPage() {
    waiter.until(ExpectedConditions.titleContains("Universität Passau"));
  }

  @Override
  public void e_clickLogo() {
    String path = "/html/body/div[1]/div[9]/header/div[1]/a/img";
    waiter.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path))).click();
  }

  @Override
  public void v_start() {}
}
