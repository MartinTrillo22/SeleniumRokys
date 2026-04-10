package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By btnIngresar = By.xpath("//span[@class='text-left pt-2 pr-2']");
    private By inputEmail = By.id("email");
    private By inputPassword = By.id("password");
    private By btnSubmitLogin = By.xpath("//button[@type='submit']");
    private By msgErrorLogin = By.xpath("//div[@class='bg-[#ff0033] h-full w-full shadow-lg text-white text-sm sm:text-base font-medium opacity-100']");
    private By indicadorSesionIniciada = By.xpath("//div[@class='text-jq_a2 underline truncate text-[13px] font-bold leading-[17px]']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Espera dinámica
    }

    public void navegarARokys() {
        driver.get("https://rokys.com/");
    }

    public void hacerLogin(String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(btnIngresar)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail)).sendKeys(email);
        driver.findElement(inputPassword).sendKeys(password);
        driver.findElement(btnSubmitLogin).click();
    }

    public String obtenerMensajeError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(msgErrorLogin)).getText();
    }


    public boolean verificarLoginExitoso() {
        try {
            // Le decimos al robot que espere hasta 10 segundos a que aparezca tu perfil
            return wait.until(ExpectedConditions.visibilityOfElementLocated(indicadorSesionIniciada)).isDisplayed();
        } catch (Exception e) {
            // Si pasan los 10 segundos y no cargó tu perfil, el login falló
            return false;
        }
    }
}