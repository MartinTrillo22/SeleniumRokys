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

    // SELECTORES DEL PRODUCTO

    private By tarjetaBrasas = By.xpath(
            "//div[@class='grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-x-8 gap-y-5']//div[2]//div[1]//div[1]//div[1]//div[1]");
    private By btnVerMas = By.xpath("//div//div//div//div//div//div//div[2]//div[1]//div[1]//div[1]//div[1]//span[1]");
    private By btnAgregarCuartoPollo = By.xpath("//div[3]//div[2]//div[1]//div[2]//div[3]//span[1]");
    private By btnAgregarPrecio = By.xpath("//button[@id='option_redirect_submit_button']");

    // SELECTORES DEL MODAL DE DIRECCIÓN
    private By btnDelivery = By.xpath("//button[normalize-space()='DELIVERY']");
    private By inputDireccion = By.xpath("//input[@id='address_input_id']");
    // private By opcionAutocompletado = By.xpath("//*[contains(text(), 'Av. Petit
    // Thouars 116 Lima, Perú')]");
    private By opcionAutocompletado = By.xpath(
            "//p[contains(@class,'font-Helveticaltstd text-[#000000] font-light text-[16px] leading-[22.8px]')]");

    private By btnConfirmarDireccionMapa = By.xpath("//button[normalize-space()='Confirmar dirección']");

    private By inputPiso = By.xpath("//input[@id='place']");
    private By inputDescripcion = By.xpath("//input[@id='note']");

    private By btnGuardarDireccion = By.xpath("//button[@type='submit']");

    // SELECTOR DEL NUEVO MODAL
    private By btnFinalizarCompra = By.xpath("//button[normalize-space()='Finalizar tu compra']");

    // SELECTORES DEL CHECKOUT
    private By tituloCheckout = By.xpath("//p[contains(@class,'text-[20px] font-bold p-2')]");

    public MenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navegarAInicio() {
        driver.get("https://rokys.com/");
    }

    public void seleccionarProducto() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement tarjeta = wait.until(ExpectedConditions.presenceOfElementLocated(tarjetaBrasas));

        // Scroll a Brasas y Hover
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", tarjeta);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Actions acciones = new Actions(driver);
        acciones.moveToElement(tarjeta).perform();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.elementToBeClickable(btnVerMas)).click();

        WebElement btnCuarto = wait.until(ExpectedConditions.presenceOfElementLocated(btnAgregarCuartoPollo));

        // scroll
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", btnCuarto);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click con javascript
        js.executeScript("arguments[0].click();", btnCuarto);

        WebElement btnPrecio = wait.until(ExpectedConditions.presenceOfElementLocated(btnAgregarPrecio));
        js.executeScript("arguments[0].click();", btnPrecio);
    }

    public void configurarDireccionDelivery(String direccion, String piso, String descripcion) {
        // 1. Seleccionar Delivery
        wait.until(ExpectedConditions.elementToBeClickable(btnDelivery)).click();

        // 2. Escribir la dirección base
        WebElement inputDir = wait.until(ExpectedConditions.visibilityOfElementLocated(inputDireccion));
        inputDir.sendKeys(direccion);

        // Pausa explícita de 1 segundo para dejar que Google Maps procese y muestre la
        // lista
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3. LA CORRECCIÓN: Esperar a que la opción sea visible y hacer CLIC en ella
        wait.until(ExpectedConditions.visibilityOfElementLocated(opcionAutocompletado));
        wait.until(ExpectedConditions.elementToBeClickable(opcionAutocompletado)).click();

        // Pausa breve para que el mapa se posicione tras seleccionar la dirección
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Confirmar la ubicación en el mapa
        wait.until(ExpectedConditions.elementToBeClickable(btnConfirmarDireccionMapa)).click();

        // 5. Llenar los campos obligatorios
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPiso)).sendKeys(piso);
        driver.findElement(inputDescripcion).sendKeys(descripcion);

        // 6. Guardar la dirección final
        wait.until(ExpectedConditions.elementToBeClickable(btnGuardarDireccion)).click();
    }

    public void confirmarProductoEnModal() {
        wait.until(ExpectedConditions.elementToBeClickable(btnFinalizarCompra)).click();
    }

    public boolean verificarEstamosEnCheckout() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tituloCheckout)).isDisplayed();
    }

}