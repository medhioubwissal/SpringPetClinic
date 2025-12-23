package org.springframework.samples.petclinic;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumUITests {

    private WebDriver driver;
    private String baseUrl = "http://localhost:8085"; // Port par défaut Spring Boot

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Obligatoire pour Jenkins/WSL
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test(priority = 1)
    public void testHomePageTitle() {
        // Test 1: Vérifier que la page d'accueil s'affiche avec le bon titre
        driver.get(baseUrl);
        String welcomeMsg = driver.findElement(By.tagName("h2")).getText();
        Assert.assertEquals(welcomeMsg, "Welcome");
    }

    @Test(priority = 2)
    public void testNavigationToVets() {
        // Test 2: Vérifier l'accès à la liste des vétérinaires
        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@title='vets']")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("/vets.html"));
        Assert.assertNotNull(driver.findElement(By.id("vets")));
    }

    @Test(priority = 3)
    public void testFindOwnerPage() {
        // Test 3: Vérifier que le formulaire de recherche des propriétaires est
        // accessible
        driver.get(baseUrl + "/owners/find");
        Assert.assertTrue(driver.findElement(By.id("lastName")).isDisplayed());
    }

    @Test(priority = 4)
    public void testPetCreationFormAccess() {
        // Test 4: Vérifier l'accès au formulaire d'ajout d'animal (lié à votre
        // PetController @GetMapping("/pets/new"))
        // Note: On accède via un propriétaire existant (ID 1 par défaut)
        driver.get(baseUrl + "/owners/1/pets/new");
        String header = driver.findElement(By.tagName("h2")).getText();
        Assert.assertTrue(header.contains("Pet") || header.contains("New Pet"));
    }

    @Test(priority = 5)
    public void testErrorOnEmptyPetName() {
        // Test 5: Simuler une erreur de validation (lié à votre
        // @PostMapping("/pets/new"))
        driver.get(baseUrl + "/owners/1/pets/new");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        // Vérifie que le contrôleur nous renvoie sur le formulaire suite aux erreurs de
        // validation
        Assert.assertTrue(driver.getCurrentUrl().contains("/pets/new"));
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
