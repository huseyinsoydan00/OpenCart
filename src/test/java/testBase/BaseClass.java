/*
-> "testBase" package'ında oluşturduğumuz bu "BaseClass", TestCase'lerde kullanacağımız ortak şeyleri tutacak (reusable components).
   Yani proje boyunca TestCase'lerde ortak olarak kullanacağımız şeyleri burada tanımlayacağız (nesneler, metotlar vs).
   Örneğin; her test metodunda olması gereken "@BeforeClass setup()" ve "@AfterClass tearDown()" metotlarını bu classa koyduk.
   Bu sayede her TestCase classında bu ortak metotları yeniden yazmamıza gerek kalmayacak => reusability.
   Tek yapmamız gereken, bu classı "extends" keyword'ü ile (yani kalıtım ile) diğer TestCase'lere aktarmak.
*/

package testBase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;  // Logging package.
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.ResourceBundle;  // "config.properties" için gerekli olan Java classı.

public class BaseClass
{
    // GLOBAL VARIABLES / CLASS VARIABLES:

    public static WebDriver driver;
    /*
    -> Buradaki "WebDriver driver" nesnesini "public" olarak tanımladık ki bu classı "extends" keyword'ünü kullanarak
       kalıtım yoluyla aktardığımız TestCase'lerde bu nesneyi kullanabilelim.
       Örneğin; bu nesneyi "private" yaptığımda "TC_001_AccountRegistrationTest" TestCase'inde bu nesneyi kullanamıyorum.
       Çünkü bu durumda nesne "private" olduğu için projenin başka yerinden erişilebilir olmuyor.
       Sadece bu classda kullanılabilir oluyor.
    -> STATIC olayının açıklaması, "ExtentReportManager" classındaki "onTestFailure()" metodunda mevcut.
    */

    public Logger logger;  // "logging" için.
    /*
    -> "log4j2" yapılandırma (configuration) ayarını buradaki BaseClass'ımızda yapacağız; çünkü "log4j2" tüm TestCase'ler için ortak.
       Ayrıca projede ilk çalışan metot alttaki "setup()" metodu olduğu için "log4j2"ye ait işlemleri "setup()" metoduna yazacağız.
    -> Pek çok "Logger" package'ı mevcut, import ederken dikkat et.
       Bizim import ettiğimiz package, bir interface => import org.apache.logging.log4j.Logger;
    */

    public ResourceBundle resourceBundle;
    /*
    -> "config.properties" içerisinde oluşturduğumuz her TestCase'de kullanacağımız ortak değişkenleri vs. burada kullanabilmek için
       gerekli olan Java classı ve ona ait olan nesne.
    */

    @BeforeClass (groups = {"Master", "Sanity", "Regression"})  // 46. dersteki gruplama olayı için ekledik.
    @Parameters ("browser")
    public void setup (String br)
    {
        /*
        -> 46. derste, PDF'de 8. adım olan "Grouping Tests" olayı için bu "BaseClass"da bulunan ortak "setup()" ve "tearDown()"
           metotlarının tüm test grupları için çalışması gerektiği belirtilmiş.
           Bundan dolayı bu metoda ve aşağıdaki "tearDown()" metoduna "groups" özelliğini ekledik ve tüm test gruplarını yazdık.
        */

        /*
        -> "setup()" metodu, her TestCase için TestCase öncesinde çalıştırılması gereken bir metot.
           Bundan dolayı başında "public" access modifier'ı bulunmalı.
        */

        /*
        -> "@Parameters ("browser")" açıklaması:
           Öncelikle "master.xml"de bir "browser" parametresi tanımladık ve değerini de "chrome" olarak belirledik.
           Daha sonra da "setup()" metoduna bu "browser" parametresini temsilen "String br" metot parametresini ekledik.
           Yani "br" aslında "@Parameters ("browser")" annotation'ında belirttiğimiz "browser" parametresi oluyor.
           Yani value'su = "chrome".
           "browser = br"nin değerini de if-else bloklarında karşılaştırarak otomasyonu istediğimiz tarayıcıda gerçekleştirebiliriz.
        */

        resourceBundle = ResourceBundle.getBundle("config");
        /*
        -> Bu kod, "config.properties" dosyasını (file) yükler (load).
        -> Parantez içerisine configuration dosyasının (file) adını yazıyoruz => "config".
           Uzantısını yazıp yazmamak ise opsiyonel
           (yani ister sadece "config" yazarız; istersek de dosyanın adını uzantısıyla beraber "config.properties" şeklinde yazarız).
        */

        logger = LogManager.getLogger(this.getClass());  // logging.
        /*
        -> Yukarıda da yazdığım gibi, "logging" işlemi tüm TestCase'lere ait bir işlem ve projede çalışan ilk metot da
           "setup()" metodu.
           Bundan dolayı "logging" için gerekli kodu buraya "setup()" metodu içerisine yazıyoruz.
           Buradaki "this.getClass()" ifadesi, execute edilen TestCase classının adı.
           Çünkü tüm TestCase'ler için tek bir LogFile oluşturuluyor.
           Yani birkaç TestCase'de hata olduğunda bu hataların hepsi tek bir dosyada (LogFile) gözükecek.
           Hangi hatanın hangi TestCase'e (TestCase classına) ait olduğunu anlamak için de "this.getClass()" ifadesini kullanıyoruz.
        */

        // ChromeOptions options = new ChromeOptions();
        // options.setExperimentalOption("excludeSwitches", new String[] {"enable-automation"});
        /*
        -> Testi koştuğumuzda otomasyon yoluyla açılan tarayıcıda "Chrome is being controlled by automated test software"
           mesajını görmemek için bu kodları kullanıyoruz.
           Ayrıca buradaki "options" nesnesini aşağıda "ChromeDriver"a parametre olarak göndermeliyiz ki Chrome ayarları
           bu şekilde aktive edilsin => "driver = new ChromeDriver(options)".
        -> Diğer tarayıcılar için de ayrı ayrı "EdgeOptions options = new EdgeOptions()" vs yapılıp "options" nesneleri
           "new EdgeDriver()" vb driverlara parametre olarak gönderilmeli.
        -> "setExperimentalOption()" metodu, "String name" ve "Object value" olmak üzere 2 tane parametre alıyor.
        */

        //*********************************
        if (br.equals("chrome"))
        {
            driver = new ChromeDriver();
        }
        else if (br.equals("edge"))
        {
            driver = new EdgeDriver();
        }
        else
        {
            driver = new FirefoxDriver();
        }
        //*********************************

        driver.manage().deleteAllCookies();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get(resourceBundle.getString("appURL"));
        /*
        driver.get("https://tutorialsninja.com/demo");
        -> OpenCart app'ini kendi bilgisayarımıza indirirsek "driver.get("https://www.opencart.com")" yerine
           "driver.get("http://localhost/opencart/upload/index.php")" yapıyoruz.
        -> Klasik "driver.get("URL")" işlemini yapmak yerine bu işlem için "config.properties" dosyasını kullandık.
           "ResourceBundle" Java classından türettiğimiz "resourceBundle" nesnesi üzerinden "config.properties" dosyası
           içerisindeki değişkenlere erişebiliriz.
           Kullandığımız "getString()" metoduna, "config.properties" içerisindeki key'lerden "appURL"i gönderiyoruz
           (appURL = https://tutorialsninja.com/demo).
           Yani "appURL"in value'sini çekmiş oluyoruz.
        */

        driver.manage().window().maximize();
    }

    @AfterClass (groups = {"Master", "Sanity", "Regression"})  // 46. dersteki gruplama olayı için ekledik.
    public void tearDown()
    {
        /*
        -> 46. derste, PDF'de 8. adım olan "Grouping Tests" olayı için bu "BaseClass"da bulunan ortak "setup()" ve "tearDown()"
           metotlarının tüm test grupları için çalışması gerektiği belirtilmiş.
           Bundan dolayı bu metoda ve yukarıdaki "setup()" metoduna "groups" özelliğini ekledik ve tüm test gruplarını yazdık.
        */

        /*
        -> "tearDown()" metodu, her TestCase için TestCase sonunda çalıştırılması gereken bir metot.
           Bundan dolayı başında "public" access modifier'ı bulunmalı.
        */

        driver.quit();
    }

    //******************************************************************************************************************

    /*
    -> "Email" alanında kullanmak üzere otomatik olarak random bir şekilde değer üretecek "ortak" metotlar:
       ("ortak" ifadesinden kastettiğim, her TestCase için ortak olarak kullanılabilecek metotlar olması)
       (çünkü "BaseClass"'ı oluşturmamızın amacı, proje boyunca ortak olarak kullanacağımız değişkenleri, metotları vs
       burada bir kere tanımlamak ve bu classı diğer TestCase'lere "extends" kalıtım keyword'ü ile aktarmak).
    */

    public String randomString()
    {
        /*
        -> Bu metodun projedeki her TestCase'den erişilebilir olması gerekli.
           Bundan dolayı "public" olmalı.
        */

        String generatedString = RandomStringUtils.randomAlphabetic(5);
        return generatedString;
    }

    public String randomNumber()
    {
        /*
        -> Bu metodun projedeki her TestCase'den erişilebilir olması gerekli.
           Bundan dolayı "public" olmalı.
        */

        String generatedString2 = RandomStringUtils.randomNumeric(10);
        return generatedString2;
    }

    public String randomAlphaNumeric()
    {
        /*
        -> Bu metodun projedeki her TestCase'den erişilebilir olması gerekli.
           Bundan dolayı "public" olmalı.
        */

        String str = RandomStringUtils.randomAlphabetic(4);
        String num = RandomStringUtils.randomNumeric(3);

        return (str + "@" + num);
    }

    /*
    -> "RandomStringUtils", bir Java classı ve bu class "commons.lang3" package'ına ait.
       Bu package'a ait ilgili dependency'yi pom.xml'e eklemiştik.
       "randomAlphabetic()" metodu, parantez içerisinde belirttiğimiz sayı kadar (4) karaktere sahip rastgele String bir değer üretir.
       "randomNumeric()" metodu ise, parantez içerisinde belirttiğimiz sayı kadar (3) basamağa sahip rastgele bir sayı üretir.
       Ancak email'i "sendKeys()" metodu ile set'lediğimiz için ve "sendKeys()" metodu sadece String değer kabul ettiği için
       buradaki sayıyı da String formatında depoluyoruz.
       Ancak aritmetik bir işlem yapsaydık o halde elbette String'i sayıya dönüştürmemiz gerekirdi.
       Son metot ise bunların birleştirilmesinden elde edilen değeri döndürüyor.
       Örneğin; "abcd@123".
    */

    //******************************************************************************************************************

    /*
    -> Her FAILED olan TestCase için çalışacak olan SS alma metodu.
       (bu metot "ExtentReportManager" Utility classındaki "onTestFailure()" metodunda kullanılıyor).
       Bu metot FAILED olan TestCase'ler için ortak olarak çalışacak olduğundan dolayı burada "BaseClass" içerisinde tanımlıyoruz.
    -> Pavan bu metodu hazır olarak Copy-Paste yaptı.
    */

    public String captureScreen(String tname) throws IOException  // "tname" = Test name.
    {
        /*
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern: "yyyyMMddhhmmss");
        Date date = new Date();
        String timeStamp = simpleDateFormat.format(date);

        -> Normalde aşağıdaki tek satırlık kod, bu şekilde 3 satır halinde de yazılabilir.
           Ancak Pavan bu 3 satırlık işlemi aşağıda tek satıra indirgedi.
           Bu sayede değişken oluşturma işlemlerinden kurtulduk ve direkt olarak "new ..." yaptık.
           Zaten normalde de new'leme işlemi bir object/instance oluşturuyor.
        */

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        /*
        -> FAILED olan TestCase'ler için alınacak olan SS'ler, "timestamp" bilgisi ile beraber kaydedilecek
           ("timestamp" dediği şey, zaman bilgisi => gün, ay, yıl, saat, dakika, saniye vb).
        -> "SimpleDateFormat" ve "Date", Java'ya ait classlar.
           "format()" ise bir metot.
        */

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        /*
        -> "String destination" => Location of SS in String format.
        -> System.getProperty("user.dir") => Current project location.
           "screenshots" = Projemizde bulunan klasörün adı.
        */

        try
        {
            FileUtils.copyFile(source, new File(destination));
        }
        catch (Exception e)
        {
            e.getMessage();
            // TRY bloğunda SS alma işleminden bir hata gelirse Exception fırlatılmaması için CATCH bloğunda bu kod var.
        }

        return destination;
        /*
        -> "captureScreen()" metodu, FAILED olan TestCase'ler için alınan SS'lerin location bilgisini bir "timestamp",
           yani zaman bilgisi ile String formatında tutan bu "destination" String değişkenini döndürüyor.
        */
    }

}