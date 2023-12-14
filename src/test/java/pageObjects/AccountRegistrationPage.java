package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage  // Üçüncü oluşturduğumuz POM class.
{
    public AccountRegistrationPage (WebDriver driver)
    {
        super(driver);
        /*
        -> "super" keyword'ü ile bir classın super classı, yani parent classındaki değişkene, metoda, Constructor'a vs erişim sağlayabiliyorduk.
           Burada da bu "AccountRegistrationPage" classının SuperClassı/ParentClassı, "BasePage" classı.
           Yani burada bu kod ile parent class olan "BasePage" classının Constructor'ını çağırıyoruz (invoke ediyoruz).
           Zaten burada bu kodun yaptığı şey de, Constructor parametresi olan "driver"ı, "super" keyword'ü ile parent class olan
           "BasePage" classındaki Constructor'a parametre olarak göndermek.
        */
    }

    //******************************************************************************************************************

    // ELEMENTS:

    @FindBy (name = "firstname")
    WebElement txtFirstname;

    @FindBy (name = "lastname")
    WebElement txtLastname;

    @FindBy (name = "email")
    WebElement txtEmail;

    @FindBy (name = "telephone")
    WebElement txtTelephone;

    @FindBy (name = "password")
    WebElement txtPassword;

    @FindBy (name = "confirm")
    WebElement txtConfirmPassword;

    @FindBy (name = "agree")
    WebElement chkdPolicy;

    @FindBy(xpath = "//input[@value='Continue']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;
    /*
    -> Tüm bilgileri doldurup "Continue" butonuna tıkladıktan sonra "Your Account Has Been Created!" şeklinde bir doğrulama mesajı geliyor.
       Biz de bu mesajı TestCase classlarında Validation amaçlı kullanacağımız için burada bu doğrulama mesajını da bir element olarak tanımladık.
    -> "msgConfirmation" ismiyle tanımladığımız bu element, "Your Account Has Been Created!" text'ine sahip.
       Yani bu elementin kendisi bir text değil; ilgili text'e sahip bir WebElement.
       Yani bu elementin ActionMethod'unda "msgConfirmation.getText()" yaparak elementin sahip olduğu text'i çekebiliriz.
    */

    //******************************************************************************************************************

    // ACTION METHODS:

    public void setFirstname (String firstname)
    {
        txtFirstname.sendKeys(firstname);
    }

    public void setLastname (String lastname)
    {
        txtLastname.sendKeys(lastname);
    }

    public void setEmail (String email)
    {
        txtEmail.sendKeys(email);
    }

    public void setTelephone (String telephone)
    {
        txtTelephone.sendKeys(telephone);
    }

    public void setPassword (String password)
    {
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword (String confirmPassword)
    {
        txtConfirmPassword.sendKeys(confirmPassword);
    }

    public void setPrivacyPolicy()
    {
        chkdPolicy.click();
    }

    // Buton için ActionMethod:
    public void clickContinue()
    {
        btnContinue.click();

        /*
        *** Butona tıklamanın diğer yolları ***

        -> Solution-2:
        btnContinue.submit();

        -> Solution-3:
        Actions actions = new Actions(driver);
        actions.moveToElement(btnContinue).click().perform();

        -> Solution-4:
        JavascriptExecutor jsx = (JavascriptExecutor) driver;
        jsx.executeScript("arguments[0].click();", btnContinue);

        -> Solution-5:
        btnContinue.sendKeys(Keys.RETURN);
        // "Keys.RETURN" => Klavyeden ENTER tuşuna basma işlemi.

        -> Solution-6 ("ExplicitWait"):
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
        */
    }

    // Başarılı kayıt işlemi sonrası gelen doğrulama mesajı için ActionMethod:
    public String getConfirmationMessage()
    {
        try
        {
            return (msgConfirmation.getText());
        }
        catch (Exception e)
        {
            return (e.getMessage());
        }

        /*
        -> Confirmation mesajını return ile döndürmeliyiz ki TestCase classında Validation amaçlı kullanabilelim.
           Ancak olur da hatalı bir kayıt işlemi gerçekleşirse herhangi bir Confirmation mesajı dönmez.
           Bu durumda program Exception fırlatır.
           Programın Exception fırlatmaması için de kodumuzu TRY-CATCH bloğu içerisinde yazdık.
           Metodumuz String döndüren bir metot olduğu için CATCH bloğu da "String" döndüren bir return ifadeye sahip olmalı.
           Bundan dolayı "Exception e" ifadesine "getMessage()" metodunu uyguladık.
           Yanlış hatırlamıyorsam bu metot hatanın tipini Console'a yazdırıyordu.
           Ancak bizim için önemli olan şey, CATCH bloğunda da String döndüren bir return ifade olması ki zaten
           "e.getMessage()" ifadesi de String döndürüyor (yani tam istediğimiz gibi).
        */
    }

}