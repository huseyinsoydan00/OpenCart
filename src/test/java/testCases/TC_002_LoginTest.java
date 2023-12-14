/*
-> Bu "LoginTest" TestCase'i için 3 tane POM classı kullanacağız: "HomePage.cs" | "LoginPage.cs" | "MyAccountPage.cs"
*/

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass  // TestCase'lerimize bu şekilde isim vermeliyiz.
{
    /*
    -> Her test metodu, "test" keyword'ü ile başlamalı.
    -> "TC_001_AccountRegistrationTest" TestCase'inde test metodunu "PUBLIC" yapmamıştık.
    */
    @Test (groups = {"Sanity", "Master"})  // 46. derste, gruplama olayı için yaptık.
    public void test_login()
    {
        try
        {
            logger.info("*** Starting TC_002_LoginTest ***");
            /*
            -> Bu mesaj, LogFile'a kaydedilecek.
            -> Logların açık ve net bir şekilde gözükmesi için Pavan mesajın başına ve sonuna "*" işaretleri koydu.
            */

            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on MyAccount link");
            homePage.clickLogin();
            logger.info("Clicked on Login link");

            LoginPage loginPage = new LoginPage(driver);
            logger.info("Providing Login details...");
            loginPage.setEmail(resourceBundle.getString("email"));
            loginPage.setPassword(resourceBundle.getString("password"));
            loginPage.clickLogin();
            logger.info("Clicked on Login button");
            /*
            -> "config.properties"de, TestCase'lerde ortak olarak kullanılan "appURL, email, password" key'lerini tanımlamıştık.
               Bu dosyanın amacı, TestCase'lerde ortak olarak kullandığımız bu gibi değişkenleri "hardcoded/sabit kodlama" yapmadan depolayıp
               gerektiğinde kullanmaktı.
               "config.properties"de bulunan bu ortak değişkenlere erişebilmek için de tüm TestCase'ler için ortak olarak kullanılacak olan
               değişkenleri, metotları vs depoladığımız "BaseClass" classı içerisinde "public ResourceBundle resourceBundle" Java classı referanslı
               nesneyi oluşturmuştuk.
               Yani "BaseClass"da bulunan "resourceBundle" nesnesi üzerinden "config.properties"de tanımladığımız "appURL, email, password"
               ortak TestCase değişkenlerine erişebiliriz.
            -> " resourceBundle.getString("key") " yaptığımızda ilgili key'in karşılığı olan "value" çekiliyor.
               Örneğin; "email" key'inin sahip olduğu value => "pavanolraining@gmail.com"
                      ; "password" key'inin sahip olduğu value => "test@123"
            */

            MyAccountPage myAccountPage = new MyAccountPage(driver);
            boolean targetPage = myAccountPage.isMyAccountPageExist();
            Assert.assertEquals(targetPage, true, "Invalid login data");
        }
        catch (Exception e)
        {
            Assert.fail();
        }

        logger.info("*** Finished TC_002_LoginTest ***");

    }  // "@Test public void test_login()"

}  // "class TC_002_LoginTest"