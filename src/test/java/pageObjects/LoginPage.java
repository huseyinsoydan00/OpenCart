package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage  // Dördüncü oluşturduğumuz POM class (45. derste oluşturduk).
{
    public LoginPage (WebDriver driver)
    {
        super(driver);
        /*
        -> "super" keyword'ü ile bir classın super classı, yani parent classındaki değişkene, metoda, Constructor'a vs erişim sağlayabiliyorduk.
           Burada da bu "LoginPage" classının SuperClassı/ParentClassı, "BasePage" classı.
           Yani burada bu kod ile parent class olan "BasePage" classının Constructor'ını çağırıyoruz (invoke ediyoruz).
           Zaten burada bu kodun yaptığı şey de, Constructor parametresi olan "driver"ı, "super" keyword'ü ile parent class olan
           "BasePage" classındaki Constructor'a parametre olarak göndermek.
        */
    }

    //******************************************************************************************************************

    // ELEMENTS:

    @FindBy (xpath = "//input[@id='input-email']")
    WebElement txtEmailAddress;

    @FindBy (xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    @FindBy (xpath = "//input[@value='Login']")
    WebElement btnLogin;

    //******************************************************************************************************************

    // ACTION METHODS:

    public void setEmail (String email)
    {
        txtEmailAddress.sendKeys(email);
    }

    public void setPassword (String password)
    {
        txtPassword.sendKeys(password);
    }

    public void clickLogin()
    {
        btnLogin.click();
    }

}