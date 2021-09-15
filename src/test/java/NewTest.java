import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;
	String CiudadOrigen = "Medellín";
	String CiudadDestino = "Bogotá";
	String FechaIda = "10-Diciembre-2021";
	String IdMes = FechaIda.split("-")[1];
	String Iano = FechaIda.split("-")[2];
	String IdDia = FechaIda.split("-")[0];
	String FechaRegreso = "10-Febrero-2022";
	String ReMes = FechaRegreso.split("-")[1];
	String Reano = FechaRegreso.split("-")[2];
	String ReDia = FechaRegreso.split("-")[0];

	By OrigenLocator = By.xpath("((//*[@class='sbox5-places-component--1i-wZ'])/..//input)[1]");
	By SeleccionOrigen = By.xpath("((//*[@class='ac-container'])/..//li/span)[1]");
	By DestinoLocator = By.xpath("((//*[@class='sbox5-places-component--1i-wZ'])/..//input)[2]");
	By SeleccionDestino = By.xpath("((//*[@class='ac-container'])/..//li/span)[1]");
	By OpcionFechaIda = By.xpath("((//*[@class='sbox5-places-component--1i-wZ'])/..//input)[3]");
	By SeleccionFechaIda = By.xpath("");
	By OpcionFechaRegreso = By.xpath("((//*[@class='sbox5-places-component--1i-wZ'])/..//input)[4]");
	By BotonBuscar=By.xpath("((//*[@class='sbox5-box-content--2pcCl sbox5-flightType-roundTrip--fSJm8'])/..//button)");
	By BotonSeleccionar=By.xpath("((//*[@class='cluster-container COMMON'])/..//a[@class='-md eva-3-btn -primary'])[1]");
	By BotonContinuar=By.xpath("((//*[@class='modal-footer -eva-3-bc-gray-4'])/..//button)");
	WebDriverWait TiempoNormaL;

	@BeforeClass
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\luisame\\eclipse-workspace\\CompraTiquetes\\src\\test\\resources\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.despegar.com.co");
		Thread.sleep(3000);
	}

	@Test(priority = 0)
	public void DiligenciamientoInformacion() throws InterruptedException {
		TiempoNormaL = new WebDriverWait(driver, 15);
		TiempoNormaL.until(ExpectedConditions.elementToBeClickable(OrigenLocator));
		Thread.sleep(1000);
		Validador(driver, CiudadOrigen, OrigenLocator, SeleccionOrigen, "Origen");
		Thread.sleep(1000);
		Validador(driver, CiudadDestino, DestinoLocator, SeleccionDestino, "Destino");
		Thread.sleep(1000);
		driver.findElement(OpcionFechaIda).click();
		Thread.sleep(1000);
		ValidadorFechaIda(driver, IdMes, Iano, IdDia);
		Thread.sleep(1000);
		driver.findElement(OpcionFechaRegreso).click();
		Thread.sleep(1000);
		ValidadorFechaRegreso(driver, ReMes, Reano, ReDia);
		Thread.sleep(1000);
		driver.findElement(BotonBuscar).click();
		Thread.sleep(5000);
		TiempoNormaL.until(ExpectedConditions.elementToBeClickable(BotonSeleccionar));
		Thread.sleep(1000);
		driver.findElement(BotonSeleccionar).click();
		Thread.sleep(4000);		
		driver.findElement(BotonContinuar).click();
		Thread.sleep(1500);
	}

	public void Validador(WebDriver driver, String Nombre, By CajaTexto, By Seleccion, String Campo)
			throws InterruptedException {
		boolean seleccion;
		try {
			driver.findElement(CajaTexto).click();
			Thread.sleep(500);
			driver.findElement(CajaTexto).sendKeys(Nombre);
			Thread.sleep(1000);
			driver.findElement(Seleccion).click();
			seleccion = true;
		} catch (Exception e) {
			seleccion = false;
		}
		Thread.sleep(1000);
		if (seleccion == true) {
			Thread.sleep(500);
			System.out.println("Si existe la/el " + Campo);
		} else {
			driver.findElement(CajaTexto).clear();
			Thread.sleep(1000);
			System.out.println("No existe la/el " + Campo);
		}
	}

	public void ValidadorFechaIda(WebDriver driver, String INIMES, String INIANO, String INIDIA)
			throws InterruptedException {

		String Mes = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-month'])[1]")).getText().strip();
		String Year = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-year'])[1] ")).getText().strip();

		while ((!Mes.equals(INIMES)) || (!Year.equals(INIANO))) {
			driver.findElement(By.xpath("((//*[@class='calendar-container '])/a)[2]")).click();
			Thread.sleep(100);
			Mes = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-month'])[1]")).getText();
			Year = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-year'])[1] ")).getText().strip();
		}
		Thread.sleep(2000);
		List<WebElement> dates = driver.findElements(By.xpath("(((//*[@class='calendar-container '])[1])/..//div[@class='sbox5-monthgrid-dates sbox5-monthgrid-dates-31'])[1]/div"));
		for (WebElement e : dates) {
			if (e.getText().equals(INIDIA)) {
				e.click();
				break;
			}
		}

		driver.findElement(DestinoLocator).click();
	}

	public void ValidadorFechaRegreso(WebDriver driver, String RegMES, String RegANO, String RegDIA)
			throws InterruptedException {

		String Mes = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-month'])[1]")).getText().strip();
		String Year = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-year'])[1] ")).getText().strip();

		while ((!Mes.equals(RegMES)) || (!Year.equals(RegANO))) {
			Mes = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-month'])[1]")).getText();
			Year = driver.findElement(By.xpath("((//*[@class='calendar-container '])/..//div[@class='sbox5-monthgrid-title-year'])[1] ")).getText().strip();
			Thread.sleep(100);
			driver.findElement(By.xpath("((//*[@class='calendar-container '])/a)[2]")).click();
		}
		Thread.sleep(2000);
		List<WebElement> dates = driver.findElements(By.xpath("(((//*[@class='calendar-container '])[1])/..//div[@class='sbox5-monthgrid-dates sbox5-monthgrid-dates-31'])[1]/div"));
		for (WebElement e : dates) {
			if (e.getText().equals(RegDIA)) {
				e.click();
				break;
			}
		}
		Thread.sleep(1000);
		driver.findElement(By.xpath("((//*[@class='sbox5-floating-tooltip sbox5-floating-tooltip-opened'])/..//button)[1]")).click();
	}
}
