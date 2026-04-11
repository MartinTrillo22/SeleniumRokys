package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Selectores
    private By tarjetaBrasas = By.xpath("//div[@class='grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-x-8 gap-y-5']//div[2]//div[1]//div[1]//div[1]//div[1]");
    private By btnVerMas = By.xpath("//div//div//div//div//div//div//div[2]//div[1]//div[1]//div[1]//div[1]//span[1]");
    private By btnAgregarCuartoPollo = By.xpath("//div[3]//div[2]//div[1]//div[2]//div[3]//span[1]");
    private By btnAgregarPrecio = By.xpath("//button[@id='option_redirect_submit_button']");

    private By btnDelivery = By.xpath("//button[normalize-space()='DELIVERY']");
    private By inputDireccion = By.xpath("//input[@id='address_input_id']");
    private By opcionAutocompletado = By.xpath("//p[contains(@class,'font-Helveticaltstd text-[#000000] font-light text-[16px] leading-[22.8px]')]");
    private By btnConfirmarDireccionMapa = By.xpath("//button[normalize-space()='Confirmar dirección']");
    private By inputPiso = By.xpath("//input[@id='place']");
    private By inputDescripcion = By.xpath("//input[@id='note']");
    private By btnGuardarDireccion = By.xpath("//button[@type='submit']");

    private By btnFinalizarCompra = By.xpath("//button[normalize-space()='Finalizar tu compra']");
    private By tituloCheckout = By.xpath("//p[contains(@class,'text-[20px] font-bold p-2')]");

    public MenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // metodos reutilizables

    private WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void click(By locator) {
        waitClickable(locator).click();
    }

    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    private void scrollTo(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    private void hover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    // Flujo completo

    public void navegarAInicio() {
        driver.get("https://rokys.com/");
    }

    public void seleccionarProducto() {

        WebElement tarjeta = waitVisible(tarjetaBrasas);

        scrollTo(tarjeta);
        hover(tarjeta);

        click(btnVerMas);

        WebElement btnCuarto = waitVisible(btnAgregarCuartoPollo);
        scrollTo(btnCuarto);

        try {
            btnCuarto.click();
        } catch (Exception e) {
            jsClick(btnCuarto);
        }

        WebElement btnPrecio = waitVisible(btnAgregarPrecio);
        jsClick(btnPrecio);
    }

    public void configurarDireccionDelivery(String direccion, String piso, String descripcion) {

        click(btnDelivery);

        WebElement inputDir = waitVisible(inputDireccion);
        inputDir.clear();
        inputDir.sendKeys(direccion);

        // Espera a que aparezcan sugerencias (reemplaza sleep)
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(opcionAutocompletado, 0));
        click(opcionAutocompletado);

        click(btnConfirmarDireccionMapa);

        waitVisible(inputPiso).sendKeys(piso);
        waitVisible(inputDescripcion).sendKeys(descripcion);

        click(btnGuardarDireccion);
    }

    public void confirmarProductoEnModal() {
        click(btnFinalizarCompra);
    }

    public boolean verificarEstamosEnCheckout() {
        return waitVisible(tituloCheckout).isDisplayed();
    }
}