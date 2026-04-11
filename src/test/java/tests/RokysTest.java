package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import config.DriverFactory;
import pages.LoginPage;
import pages.MenuPage;

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
        Assert.assertTrue(mensajeError.contains("Credenciales invalidas"),
                "El sistema no bloqueó el acceso o no mostró el error.");
    }

    @Test(priority = 2)
    public void testLoginPositivo() {
        loginPage.navegarARokys();
        loginPage.hacerLogin("martin.trillo.c@gmail.com", "Polleria2026@");
        boolean estaLogueado = loginPage.verificarLoginExitoso();
        Assert.assertTrue(estaLogueado, "Error: El login no fue exitoso, no se encontró el perfil del usuario.");
    }

    @Test(priority = 3)
    public void testFlujoCompraCompletoSinPago() {

        menuPage.navegarAInicio();
        menuPage.seleccionarProducto();
        menuPage.configurarDireccionDelivery("Av. Petit Thouars 116 Lima, Perú", "primer piso", "cerca del parque");
        menuPage.confirmarProductoEnModal();
        boolean estamosEnCheckout = menuPage.verificarEstamosEnCheckout();
        Assert.assertTrue(estamosEnCheckout, "Fallo la prueba: No se pudo llegar a la pantalla final de Checkout.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}