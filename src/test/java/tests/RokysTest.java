package tests;

import config.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;

public class RokysTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private MenuPage menuPage;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.getDriver(browser);
        loginPage = new LoginPage(driver);
        menuPage = new MenuPage(driver);
    }

    @Test(priority = 1)
    public void testLoginNegativo() {
        loginPage.navegarARokys();
        loginPage.hacerLogin("correo_falso@test.com", "claveIncorrecta123");

        String mensajeError = loginPage.obtenerMensajeError();
        Assert.assertTrue(mensajeError.contains("Credenciales invalidas"), "El sistema no bloqueó el acceso o no mostró el error.");
    }

    @Test(priority = 2)
    public void testLoginPositivo() {
        loginPage.navegarARokys();
        loginPage.hacerLogin("martin.trillo.c@gmail.com", "Polleria2026@");
        boolean estaLogueado = loginPage.verificarLoginExitoso();
        Assert.assertTrue(estaLogueado, "Error: El login no fue exitoso, no se encontró el perfil del usuario.");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}