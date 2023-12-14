package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage  // İkinci oluşturduğumuz POM class.
{
    // CONSTRUCTOR:

    public HomePage(WebDriver driver)
    {
        super(driver);
        /*
        -> "super" keyword'ü ile bir classın super classı, yani parent classındaki değişkene, metoda, Constructor'a vs erişim sağlayabiliyorduk.
           Burada da bu "HomePage" classının SuperClassı/ParentClassı, "BasePage" classı.
           Yani burada bu kod ile parent class olan "BasePage" classının Constructor'ını çağırıyoruz (invoke ediyoruz).
           Zaten burada bu kodun yaptığı şey de, Constructor parametresi olan "driver"ı, "super" keyword'ü ile parent class olan
           "BasePage" classındaki Constructor'a parametre olarak göndermek.
        */
    }

    //******************************************************************************************************************

    // ELEMENTS:

    @FindBy (xpath = "//span[text()='My Account']")
    WebElement lnkMyAccount;

    @FindBy (linkText = "Register")
    WebElement lnkRegister;

    @FindBy (linkText = "Login")  // 45. derste ekledik.
    WebElement lnkLogin;

    //******************************************************************************************************************

    // ACTION METHODS:

    public void clickMyAccount()
    {
        lnkMyAccount.click();
    }

    public void clickRegister()
    {
        lnkRegister.click();
    }

    public void clickLogin()  // 45. derste ekledik.
    {
        lnkLogin.click();
    }

}