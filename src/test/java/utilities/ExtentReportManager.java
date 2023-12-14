/*
-> Pavan bu Utility classını hazır olarak paylaştı.
   Biz yazmadık.
*/

package utilities;

import java.io.IOException;
import java.net.URL;  // Oluşan raporu Email ile göndermek için gereken package.

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener  // İlk Utility File/Class.
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());  // timestamp.
		repName = "Test-Report-" + timeStamp + ".html";  // Rapor adı.

		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);  // specific location of the report.
		/*
		-> Rapor adı DİNAMİK olarak oluşturuluyor!
		   Eğer rapor adını "hardcoded" şekilde, yani sabit bir kod olarak yazarsak, rapor geçmişini görüntüleyemeyiz.
		   Rapor geçmişini tutmak istediğimiz için de rapor adı "timestamp = zaman damgası" ile beraber oluşturuluyor.
		*/

		sparkReporter.config().setDocumentTitle("opencart Automation Report");  // Title of Report.
		sparkReporter.config().setReportName("opencart Functional Testing");  // Name of Report.
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		// Rapora bazı bilgileri gönderiyoruz (Key-Value):
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
	}

	public void onTestSuccess(ITestResult result)  // "ITestResult result" = Execute edilen metotla alakalı bilgi içeren object.
	{
		test = extent.createTest(result.getName());
		test.log(Status.PASS, "Test Passed");
	}

	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());

		try
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			/*
			-> İlk satırda FAILED olan TestCase'ler için SS alınıyor
			   ("captureScreen()" metodu, alınan SS'in bulunduğu location'ı String formatında döndürüyor).
			   İkinci satırda ise bu SS'ler test raporuna ekleniyor
			   ("test" nesnesi, "public ExtentTest test" class değişkeninden geliyor).
			   Olay bu.
			-> "captureScreen()" metodu, her FAILED olan TestCase için ortak bir metot.
			   Çünkü her FAILED olan TestCase için SS alma işlemi gerçekleşecek ve bu SS de test raporuna eklenecek.
			   Bundan dolayı "captureScreen()" metodunu, her TestCase için ortak olan değişkenleri, metotları vs tuttuğumuz
			   "BaseClass" içerisinde oluşturmalıyız.
			-> "new BaseClass()" kodu, "BaseClass baseClass = new BaseClass()" kodunun aynısı.
			   Yani bir object/instance oluşturuyor.
			-> !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			   "new BaseClass()" kodu, "BaseClass"da bir "WebDriver driver" nesnesi bulunduğu için bu nesneden 1 tane daha oluşturur.
			   Çünkü bu kod, bir new'leme işlemidir.
			   Yeni bir "entity" oluşturur ("entity" = varlık | Pavan bu tabiri kullandı).
			   Yani "new BaseClass()" kodu ile oluşan "BaseClass" nesnesine/instance'ına ait yeni bir "WebDriver driver" nesnesi oluşmuş olur.
			   Ancak "BaseClass"da bulunan "setup()" metodunda "driver = new ChromeDriver()" işlemi ile zaten bir "WebDriver driver"
			   nesnesi oluşturuluyor ki bu da "BaseClass" classına ait.
			   Dolayısıyla burada 2 tane "WebDriver driver" nesnesi oluşmuş olur:
			   Biri "BaseClass"a ait, diğeri de "BaseClass" nesnesine/instance'ına ait olan "driver".
			   Bu şekilde 2 tane driver'a sahip olursak karışıklık olur ve testimiz FAILED olur.
			   Bundan dolayı "BaseClass"da Global değişken olarak bulunan "public WebDriver driver" nesnesini STATIC yapmalıyız:
			   "public static WebDriver driver;" in "BaseClass".
			   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			*/
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext)
	{
		extent.flush();

		/*
		// Oluşan raporu Email ile göndermek istersek aşağıdaki kod bloğunu kullanmalıyız:

		try
		{
		URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);

		// Create Email Message (for Gmail !!!):
		ImageHtmlEmail email = new ImageHtmlEmail();
		email.setDataSourceResolver(new DataSourceUrlResolver(url));
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password"));
		email.setSSLOnConnect(true);
		email.setFrom("pavanoltraining@gmail.com");  // Sender (maili gönderen).
		email.setSubject("Test Results");
		email.setMsg("Please find Attached Report....");
		email.addTo("pavankumar.busyqa@gmail.com");  // Receiver (maili alan).
		email.attach(url, "extent report", "please check report...");
		email.send();  // send the email.
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		*/
	}

}