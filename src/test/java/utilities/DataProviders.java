/*
-> Pavan bu Utility classını hazır olarak paylaştı, biz yazmadık
   (45. dersteki 7.adım olan "Data Driven Login Test" için).
-> Bu "DataProviders" Utility classında bulunan "@DataProvider getData()" metodu ile "testData" directory'sinde bulunan
   "OpenCart_LoginData.xlsx" Excel belgesinden veriler alınacak ve alınan bu veriler TestCase'lerdeki test metotlarına gönderilecek.
   Süreç bu şekilde işleyecek.
-> Bu classda tüm DataProvider metotları tanımlayabiliriz.
   Burada aşağıda tanımladığımız DataProvider metodunu, Excel belgesinden çekilecek farklı "username-password" kombinasyonlarıyla,
   yani farklı veriler/veri setleri ile Login işlemlerinin gerçekleşmesi için tanımladık.
*/

package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders  // Üçüncü Utility File/Class.
{

	// DataProvider-1:
	
	@DataProvider(name="LoginData")  // Her DataProvider'ın bir ismi olur ve bu isimle TestCase'lerimizde bu DataProvider metoduna erişebiliriz.
	public Object[][] getData() throws IOException
	{
		/*
		-> Pavan metotta "Object" yerine "String" yazmıştı; ancak program hata verdiği için "Object" olarak değiştirdim.
		   Zaten normalde de DataProvider metotlar, "Object" tipinde 2 boyutlu Array döndürür => "Object [][]".
		*/

		String path = ".\\testData\\OpenCart_LoginData.xlsx";
		/*
		-> "testData" directory'sinde bulunan "OpenCart_LoginData.xlsx" Excel dosyasını çekiyoruz.
		-> ".\\" => Current project directory.
		*/

		ExcelUtility excelUtility = new ExcelUtility(path);
		/*
		-> "utilities" package'ında bulunan "ExcelUtility" classı referanslı bir nesne oluşturuyoruz.
		   "ExcelUtility" classındaki Constructor, "String path" parametresi alıyor.
		   Biz de "path" olarak, kaynak olarak kullandığımız "OpenCart_LoginData.xlsx" Excel belgesinin kendisini,
		   konumuyla beraber gönderiyoruz.
		   Yani artık "excelUtility" nesnesini kullanarak "ExcelUtility" classında bulunan metotları çağırdığımızda,
		   bunlar aslında "OpenCart_LoginData.xlsx" Excel belgesine uygulanacaklar.
		*/
		
		int totalRows = excelUtility.getRowCount("Sheet1");  // totalRows = 5
		/*
		-> Excel belgesindeki toplam satır sayısı.
		   Başlık satırı da dahil olmak üzere toplam 6 satır var.
		   Ancak index numarası 0'dan başladığı için 0-1-2-3-4-5 olur.
		   Yani "totalRows = 5" olur.
		*/

		int totalCells = excelUtility.getCellCount("Sheet1",1);  // totalCells = 2
		/*
		-> Excel belgesindeki 1. satırda bulunan toplam hücre sayısı.
		   Toplam hücre sayısını bulmak için herhangi bir satır numarası kullanılabilir.
		   Çünkü her satırın sahip olduğu hücre sayısı aynıdır.
		-> Toplam 3 sütun var ve index numarası da 0'dan başlar; dolayısıyla index numarası 0-1-2 olur.
		   Yani "totalCells = 2" olur.
		*/
				
		String loginData [][] = new String [totalRows] [totalCells];
		// Kaynak olarak kullandığımız "OpenCart_LoginData.xlsx" Excel belgesinin satır-sütun sayısı boyutunda 2 boyutlu bir Array tanımladık.
		
		for (int i=1; i<=totalRows; i++)  // rows.
		{
			for (int j=0; j<totalCells; j++)  // cells.
			{
				loginData [i-1] [j] = excelUtility.getCellData("Sheet1", i, j);  // 1,0
				/*
				-> Burada olay şu:
				   "OpenCart_LoginData.xlsx" isimli Excel belgesindeki tabloda bulunan verileri alıp 2 boyutlu Array'e aktarıyoruz.
				   Excel tablosunda 1.satır olan Headers satırının index numarası 0'dır.
				   Yani Excel tablosundan verileri almaya 2.satırdan (index no=1 olan satır) başlayacağız.
				   Dikkat edilmesi gereken nokta;
				   -> Excel tablosunda 2.satırdan (index no = 1) aldığımız verileri, Array'in ilk elemanına (index no = 0) aktaracağız.
				   -> Excel tablosunda 3.satırdan (index no = 2) aldığımız verileri, Array'in ikinci elemanına (index no = 1) aktaracağız.
				   Yani bir nevi Excel'deki 1 numaradan aldığımız veriyi, Array'in 0'ına (1 düşüğüne => -1) atacağız.
				   Excel'deki 2 numaradan aldığımız veriyi, Array'in 1'ine (1 düşüğüne => -1) atacağız.
				   Özetle; Excel'in i'sinden aldığımız veriyi, Array'in [i-1]'ine atacağız.
				   Ancak bu durum sadece satırlar için böyle ( Excel[i] => Array[i-1] )  |  "String loginData [][]", 2 boyutlu Array.
				   Sütunlarda herhangi bir sıkıntı yok ( Excel[j] => Array[j] ).
				*/
			}

			/*
			-> Excel belgesindeki tabloda bulunan her "username-password" ikilisini, tanımladığımız 2 boyutlu "loginData" Array'inde depoluyoruz.
			-> Excel belgesindeki 1. satır, başlıkların bulunduğu satırdır ve index numarası 0'dır.
			   Bundan dolayı "i" değişkenini 0'dan değil, 1'den başlattık.
			   6. satırın index numarası 5 olduğu için "i <= totalRows=5" değerinde döngüyü bitirdik.
			-> Hücreleri temsil eden "j" değişkenini ise 0'dan başlattık ve "totalCells = 2" olduğu için ve de 3. sütundaki hücreler
			   beklenen sonuçların olduğu "res - Expected Results" sütununa ait değerler olduğu için o sütunu dahil etmedik.
			   Sütun index numaraları da 0'dan başladığı için ve 3 sütuna sahip olduğumuz için sütunların index numaraları 0, 1 ve 2 olur.
			   Yani beklenen sonuçların bulunduğu "res - Expected Results" 3. sütununun index numarası 2'dir.
			   Dolayısıyla burada "j" hücre döngüsü için index=2'yi dahil etmemeliyiz; yani j = 0 ve 1 olmalı.
			   Bundan dolayı j=0'dan başlar ve "j < totalCells=2" değerinde biter.
			   Bu sayede j=0 ve 1 değerlerini alır (yani "username-password" ikili değerlerini alır).
			*/
		}

		return loginData;
		// "username-password" bilgilerini içeren 2 boyutlu "loginData [] []" Array'ini döndürür.
	}

	/*
	-> İlerde başka Data-Driven TestCase'ler olursa, benzer şekilde onlar için de aşağıdaki gibi DataProvider metotlar tanımlayabiliriz:

	   DataProvider-2:

	   DataProvider-3:

	   DataProvider-4:
	*/

}