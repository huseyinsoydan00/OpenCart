/*
-> Bu TestCase aslında "TC_002_LoginTest" ile aynı yapı ve işleve sahip.
   Çünkü her iki TestCase de Login işlemini otomatize etmekten sorumlu.
   Tek fark ise bu TestCase Login için gerekli olan kullanıcı adı ve şifreyi DataProvider metottan sağlıyor.
   DataProvider metot da gerekli kullanıcı adı ve şifre bilgilerini Excel tablosundan çekiyor
   ("utilities" package'ındaki "DataProviders.cs" Utility classında bulunan "getData()" metodu bizim DataProvider metodumuz)
   (o metot, bu TestCase'e username-password verilerini sağlayacak).
*/

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDataDrivenTest extends BaseClass  // TestCase'lerimize bu şekilde isim vermeliyiz.
{

    /*
    -> Her test metodu, "test" keyword'ü ile başlamalı.
    -> "TC_001_AccountRegistrationTest" TestCase'inde test metodunu "PUBLIC" yapmamıştık.
    -> Bu test metoduna DataProvider metottan veri sağlanacak; çünkü bu TestCase, adı üstünde "LoginDataDrivenTest".
       Yani Login işlemi, "utilities" package'ındaki "DataProviders.cs" classında bulunan DataProvider metot tarafından
       sağlanacak olan farklı veri setleri ile birkaç defa tekrarlanacak
       (ilgili classdaki "getData()" isimli DataProvider metot, Excel'deki tablodan çekilen "username-password" ikililerini
       içeren "loginData" isimli 2 boyutlu Array elemanını döndürüyor).
       Veriler de o classdaki DataProvider metot tarafından, Pavan'ın paylaştığı Excel dosyasındaki tabloda bulunan
       "username-password" ikililerinden çekiliyor.
    */
    @Test (dataProvider = "LoginData", dataProviderClass = DataProviders.class)
    public void test_loginDataDrivenTest(String email, String password, String expectedResult)
    {
        /*
        -> !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
           BURADA ANLAMADIĞIM BİRŞEY VAR:
           Biz "DataProviders.cs" Utility classında, Excel tablosundaki 3. sütunda bulunan "res - Expected Results" değerlerini
           çekmemiştik. Sadece ilk 2 sütunda bulunan "username-password" bilgilerini 2 boyutlu "loginData [] []" Array'inde depolamıştık.
           O halde burada nasıl oluyor da test metoduna 3. parametre olarak eklediğimiz "expectedResult" parametresine,
           Excel tablosundaki 3. sütun olan "res - Expected Results" sütunundan veri sağlanıyor?
           Bu kısmı anlamadım.
           !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        -> "DataProviders.cs" Utility classındaki "getData()" DataProvider metodunun adını "LoginData" olarak belirlemiştik.
           Eğer ilgili DataProvider metot bu class içerisinde tanımlanmış olsaydı, o zaman "@Test" parantezinde sadece
           " dataProvider = "LoginData" " yazmamız yeterli olurdu.
           Ancak bu projedeki gibi DataProvider metot/metotlar farklı bir class içerisinde tanımlandığı zaman,
           ek olarak "dataProviderClass = DataProviders.class" ifadesini de "@Test" parantezine yazıyoruz
           ("DataProviders.cs" classı, "utilities" package'ında bulunan ve DataProvider metot/metotları tutan Utility file/class).
        -> DataProvider metot "username-password" ikili bilgilerini bize sağlayacağı için, burada test metodu parametresi olarak
           benzer şekilde 2 tane parametre tanımladık (ilk parametre "username" ya da "email", farketmez).
           3. parametre olan "String expectedResult" parametresini de Excel tablosundaki 3.sütun olan "res = Expected Result"
           sütununa karşılık tanımladık.
           Çünkü Data-Driven Test'de Validation yaparken öncelikle datanın/dataların "valid/geçerli" olup olmadığını kontrol etmeliyiz
           ("Notlar" klasöründeki "17.4" numaralı SS'de detaylı olarak açıkladım).
           Yani burada TRY bloğundaki son işlemde Validation yaparken "TC_002_LoginTest" TestCase'inde yaptığımız gibi
           sadece "targetPage" kontrolü yapmayacağız !!!
        */

        try
        {
            logger.info("*** Starting TC_003_LoginDataDrivenTest ***");
            /*
            -> Bu mesaj, LogFile'a kaydedilecek.
            -> Logların açık ve net bir şekilde gözükmesi için Pavan mesajın başına ve sonuna "*" işaretleri koydu.
            */

            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            homePage.clickLogin();

            LoginPage loginPage = new LoginPage(driver);
            loginPage.setEmail(email);        // TC_002_LoginTest => loginPage.setEmail(resourceBundle.getString("email"));
            loginPage.setPassword(password);  // TC_002_LoginTest => loginPage.setPassword(resourceBundle.getString("password"));
            loginPage.clickLogin();
            /*
            -> Burada "TC_002_LoginTest"den farklı olarak "email-password" bilgileri "config.properties"den çekilmeyecek.
               Onun yerine veriler DataProvider metottan sağlanacak.
               Bundan dolayı "setEmail()" ve "setPassword()" metotlarına parametre olarak, DataProvider'dan gelecek olan verileri
               tutacak olan "email" ve "password" test metodu parametrelerini yazdık.
            */

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExist();

            /*
            -> 1. DURUM:
               Excel'deki 3.sütun olan "res = Expected Result" verisi "Valid" olduğunda Login işleminin gerçekleşip(targetPage == true)
               gerçekleşmediğine(else) göre testi PASSED(true) ya da FAILED(false) yapma:
            */
            if (expectedResult.equals("Valid"))
            {
                if (targetPage == true)
                {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(true);
                }
                else
                {
                    Assert.assertTrue(false);
                }
            }

            /*
            -> 2. DURUM:
               Excel'deki 3.sütun olan "res = Expected Result" verisi "Invalid" olduğunda Login işleminin gerçekleşip(targetPage == true)
               gerçekleşmediğine(else) göre testi PASSED(true) ya da FAILED(false) yapma:
            */
            if (expectedResult.equals("Invalid"))
            {
                if (targetPage == true)
                {
                    myAccountPage.clickLogout();
                    Assert.assertTrue(false);
                }
                else
                {
                    Assert.assertTrue(true);
                }
            }

            /*
            -> if bloğunun başında "boolean targetPage = myAccountPage.isMyAccountPageExist();" değişkenini tanımladık.
               "MyAccountPage.cs" POM classında "msgHeading" isminde bir WebElement tanımlamıştık
               (bu element, başarılı bir Login sonrası gelen sayfada bulunan "My Account" text'ine sahip elementti)
               (ve bu elementi, başarılı bir Login işleminin gerçekleşip gerçekleşmediğini kontrol etmek için, yani
               Validation amaçlı kullanacaktık).
               "MyAccountPage.cs" POM classında bulunan "isMyAccountPageExist()" metodu da bu "msgHeading" WebElement'inin
               görüntülenip görüntülenmeme durumuna göre true ya da false döndüren boolean bir metottu.
               Biz de burada if bloğunun en başında "boolean targetPage" değişkenini tanımladık ki, eğer başarılı bir Login
               işlemi gerçekleşmişse bu durumda "targetPage = true" olacaktır.
               Ayrıca bu durumda Login işlemi gerçekleştiği için önce "Logout" yapılmalıdır.
               Başarısız bir Login işlemi gerçekleşmişse de bu durumda "targetPage = false" olacaktır (else durumu).
               Yani bu durumda "Logout" yapılması zaten mümkün olmayacaktır.
            -> Buradaki olay ise şu:
               Eğer Excel'deki 3. sütunda bulunan "res - ExpectedResult" değeri "Valid" ise
               ("Valid" ifadesi Excel'deki tabloda nasıl yazıldıysa o şekilde yazılmalı, büyük harf/küçük harf olayına dikkat edilmeli)
               ve ayrıca "targetPage = true" ise (yani başarılı bir Login işlemi yapılmışsa) bu durumda önce "Logout" yapılıyor
               ve ardından da hem beklenen sonuç "Valid" hem de Login işlemi başarılı olduğu için "Assert.assertTrue(true);" kodu ile
               test PASSED yapılıyor.
               Ancak beklenen sonuç "Valid" olmasına rağmen "targetPage = false" ise(else), yani başarılı bir Login işlemi yapılmamışsa
               bu durumda "Assert.assertTrue(false);" kodu ile test FAILED yapılıyor.
            -> Aynı mantıkla aşağıdaki 2. if bloğu da düşünülebilir.
               Tek fark, aşağıdaki 2. if bloğunda, Excel tablosundaki 3.sütun olan "res - Expected Result" değeri "Invalid" ise,
               yani geçersiz verilerle Login işlemi yapılmaya çalışılıyorsa bu durumda "targetPage" elementinin "true" olmaması gerekir.
               Çünkü bu durumda veri geçersizdir ve geçersiz veri ile Login işleminin de başarısız olması beklenir.
               Yani "Invalid" veri ile "targetPage = true" ise bu bir hatadır ve test FAILED olmalıdır
               (tabi bu durumda Login işlemi gerçekleştiği için yine önce Logout yapılmalıdır).
               Ancak Login verileri "Invalid" ve "targetPage = false" ise bu durumda geçersiz verilerle Login işlemi denenmiş
               ve başarılı bir Login işlemi gerçekleşmemiştir ki zaten bu da gerçekleşmesini istediğimiz bir olaydır.
               Dolayısıyla buradan test sonucunu PASSED yapmamız gerekir.
            */
        }
        catch (Exception e)
        {
            Assert.fail();
        }

        logger.info("*** Finished TC_003_LoginDataDrivenTest ***");

    }  // "@Test public void test_loginDataDrivenTest()"

}  // "class TC_003_LoginDataDrivenTest"