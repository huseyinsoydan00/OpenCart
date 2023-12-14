package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;

import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass  // TestCase'lerimize bu şekilde isim vermeliyiz.
{
    @Test (groups = {"Regression", "Master"})  // 46. derste, gruplama olayı için yaptık.
    void test_account_Registration()  // Her test metodu, "test" keyword'ü ile başlamalı.
    {
        logger.debug("Application Logs............");
        // "Debug", tüm "client-server" iletişim Loglarını yakalar.

        logger.info("*** Starting TC_001_AccountRegistrationTest ***");
        /*
        -> Bu mesaj, LogFile'a kaydedilecek.
        -> Logların açık ve net bir şekilde gözükmesi için Pavan mesajın başına ve sonuna "*" işaretleri koydu.
        */

        try
        {
            HomePage hp = new HomePage(driver);

            hp.clickMyAccount();
            logger.info("Clicked on MyAccount link");

            hp.clickRegister();
            logger.info("Clicked on Register link");

            /*
            -> "HomePage" classındaki Constructor bir "WebDriver driver" parametresi beklediği için burada parametre olarak biz de
               "driver" nesnesini gönderdik.
               Peki gönderdiğimiz bu "driver" parametresi nereden geliyor?
               Tabiki de bu classa "extends" kalıtım keyword'ü ile aktardığımız "BaseClass" classındaki "PUBLIC WebDriver driver"
               nesnesinden geliyor.
            -> Ve buradan bir durum daha ortaya çıkıyor:
               Bu classa "extends" kalıtım keyword'ü ile sağladığımız "BaseClass" classındaki "WebDriver driver" nesnesi
               "public" olmalı ki bu classda kullanabilelim.
            */

            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            // bu "driver" parametresi için de üstte yazdığım olay geçerli.

            //**************************************
            logger.info("Providing Customer data");

            regpage.setFirstname(randomString().toUpperCase());  // random olarak üretilecek olan FirstName'i büyük harflere çeviriyoruz.
            regpage.setLastname(randomString().toUpperCase());  // random olarak üretilecek olan LastName'i büyük harflere çeviriyoruz.

            // regpage.setEmail("abcxyz@gmail.com");
            /*
            -> OpenCart uygulamasında email "unique" bir değer.
               Yani aynı email adresiyle birden fazla kayıt oluşturamıyoruz.
               Böyle "unique" değerleri statik bir şekilde Manuel olarak girmeyiz.
               Bu değerlerin dinamik bir şekilde sürekli olarak değişmesi ve birbirinden farklı olması gerekir.
               Yani TestCase'i her koştuğumuzda bu email bilgisi random olarak generate edilmeli.
               Ve bu işlem her TestCase'i koştuğumuzda ortak bir şekilde gerçekleştirilmesi gereken bir işlem olduğu için
               (çünkü her kayıt işleminde email bilgisinin kayıt formunda doldurulması gerekiyor) bu tarz ortak işlemleri
               "BaseClass" classında tanımlıyoruz.
            -> Bundan dolayı bu kodu aşağıdaki gibi yazmalıyız:
            */
            regpage.setEmail(randomString() + "@gmail.com");

            regpage.setTelephone(randomNumber());

            String password = randomAlphaNumeric();
            regpage.setPassword(password);
            regpage.setConfirmPassword(password);
            /*
            -> OpenCart uygulamasında kayıt formunda hem şifre hem de şifreyi onaylama kutuları var.
               Yani bu iki inputbox'a yazacağımız şifreler aynı olmalı.
               Ancak burada random bir şifre üretmek için kullandığımız "randomAlphaNumeric()" metodu her seferinde farklı değerler üretir.
               Dolayısıyla bu metodu 1 kere çalıştırıp üretilen değeri bir değişkende tutmalıyız.
               Daha sonra da "setPassword()" ve "setConfirmPassword()" metotlarına bu aynı değeri parametre olarak göndermeliyiz.
               Bu sayede her iki inputbox alanına girilen değerler aynı olacak ve şifreler birbirleriyle eşleşecek.
               Yani aşağıdaki gibi bir kod yazmamalıyız:
               -> regpage.setPassword(randomAlphaNumeric());
               -> regpage.setConfirmPassword(randomAlphaNumeric());
               Bu şekilde yaparsak "randomAlphaNumeric()" metodu iki satırda da farklı String değerler üretir ve
               iki inputbox alanına girilen şifreler birbiriyle eşleşmez.
            */

            regpage.setPrivacyPolicy();
            //**************************************

            regpage.clickContinue();
            logger.info("Clicked on Continue");

            String confmsg = regpage.getConfirmationMessage();
            logger.info("Validating expected message");
            Assert.assertEquals(confmsg, "Your Account Has Been Created!", "Test failed");
        }
        catch (Exception e)
        {
            logger.error("Test failed");
            Assert.fail();
        }

        logger.info("*** Finished TC_001_AccountRegistrationTest ***");

    }  // "@Test test_account_Registration()"

}  // "class TC_001_AccountRegistrationTest"


/*
-> Kayıt formuna girdiğimiz bilgilerden herhangi birinin hatalı olması, bir elementte sorun olması ya da yazdığımız Locator'lardan
   herhangi birinin yanlış olması durumunda program bir Exception fırlatacağı için yazdığımız tüm kodları TRY bloğu içerisine aldık.
   Bu sayede olası bir hata durumunda program Exception fırlatmayacak ve otomatik olarak CATCH bloğuna atlanacak.
   CATCH bloğunda da "Assert.fail()" kodu ile kodlar FAILED'a çekilecek.
*/