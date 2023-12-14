package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage  // İlk oluşturduğumuz POM class.
{
    WebDriver driver;

    public BasePage (WebDriver driver)
    {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}


/*
-> POM Classları şu bölümlerden oluşuyordu: "WebDriver driver" - "Constructor" - "Elements" - "ActionMethods".
   Her POM Classında "WebDriver driver" nesnesi tanımlamak ve Constructor oluşturmak yerine bu iki işlemi bir "Base" POM Class'ında yapıp
   bu "Base" POM Classını diğer POM Class'lara "extends" kalıtım keyword'ü ile aktardık ("reusability")
   ("extends" = Java'da inheritance keyword'ü).
*/