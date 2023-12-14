package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage  // Beşinci oluşturduğumuz POM class (45. derste oluşturduk).
{

    public MyAccountPage (WebDriver driver)
    {
        super(driver);
        /*
        -> "super" keyword'ü ile bir classın super classı, yani parent classındaki değişkene, metoda, Constructor'a vs erişim sağlayabiliyorduk.
           Burada da bu "MyAccountPage" classının SuperClassı/ParentClassı, "BasePage" classı.
           Yani burada bu kod ile parent class olan "BasePage" classının Constructor'ını çağırıyoruz (invoke ediyoruz).
           Zaten burada bu kodun yaptığı şey de, Constructor parametresi olan "driver"ı, "super" keyword'ü ile parent class olan
           "BasePage" classındaki Constructor'a parametre olarak göndermek.
        */
    }

    //******************************************************************************************************************

    // ELEMENTS:

    @FindBy (xpath = "//h2[text()='My Account']")
    WebElement msgHeading;
    /*
    -> Başarılı bir Login sonrası gelen sayfada bulunan "My Account" text'ine sahip elementi tanımladık.
       Bu elementi, başarılı bir Login işleminin gerçekleşip gerçekleşmediğini kontrol etmek için kullanacağız (Validation).
    */

    @FindBy (xpath = "//a[@class='list-group-item' and text()='Logout']")
    WebElement lnkLogout;
    /*
    -> Bir kullanıcı adı ve şifre ile giriş yapıldıktan sonra "MyAccount" sayfasına geçiliyor.
       O sayfada da "Logout" link elementi bulunuyor.
       "testData" directory'sine kopyaladığımız "OpenCart_LoginData" Excel belgesinde de birkaç tane kullanıcı adı ve şifre ile
       giriş-çıkış yapacağız.
       Dolayısıyla bir üyelikle giriş yapıldıktan sonra gelen "MyAccount" sayfasında yer alan "Logout" link elementini de
       burada "MyAccount" sayfası için oluşturduğumuz "MyAccountPage" POM classında tanımlıyoruz.
       Çünkü "Logout" link elementi, "MyAccount" sayfasında bulunan bir element.
    */

    //******************************************************************************************************************

    // ACTION METHODS:

    public boolean isMyAccountPageExist()
    {
        try
        {
            return msgHeading.isDisplayed();  // TRY bloğu çalıştığı takdirde bu kod "boolean true" döndürür.
            /*
            -> Tanımladığımız "msgHeading" elementi, başarılı Login işlemi sonrası gelen "MyAccount / Hesabım" sayfasında bulunan bir element.
               Ve bu element görüntüleniyorsa başarılı bir Login işlemi yapılmıştır.
               Yani bu TRY bloğu çalışırsa başarılı bir Login işlemi gerçekleşmiştir.
            */
        }
        catch (Exception e)
        {
            return false;  // TRY bloğu çalışmazsa CATCH bloğu çalışır ve bu kod da "boolean false" döndürür.
            /*
            -> TRY bloğunda "msgHeading" elementi görüntülenemezse, ya başarılı bir Login işlemi gerçekleşmemiştir ya da başka bir hata olmuştur.
               Bu durumda programın Exception fırlatmaması için metot tipi olan "boolean" tipindeki "false" değerini döndürüyoruz.
            */
        }
    }

    public void clickLogout()
    {
        lnkLogout.click();
    }

}