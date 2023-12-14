:: cd D:\Yazılım\Test\7) Pavan Kumar - SDET Bootcamp\Bölüm-8 - CI - Maven - Git - GitHub - Jenkins\OpenCart
mvn test

:: Bu projede pom.xml'deki "surefire" plugin bloğuna ekleyip çalışmasını istediğimiz "master.xml"i Command Prompt aracılığıyla
:: çalıştırmak için "cmd" ile Command Prompt'u açtıktan sonra öncelikle "cd" ile OpenCart projesinin bulunduğu klasöre erişip
:: ardından da "mvn test" ile pom.xml'i çalıştırıyorduk ve pom.xml de master.xml'in çalışmasını tetikliyordu.
:: Yani Command Prompt'da pom.xml içerisinden master.xml'i çalıştırmış oluyorduk.
:: Command Prompt'da bu kodları yazmak yerine burada projeye sağ click yapıp yeni bir "file" oluşturduk.
:: Adı ve uzantısına da "run.bat" dedik ("batch" dosyası).
:: Ve Command Prompt'da yazacağımız komutları burada yazdık.
:: OpenCart klasöründeki bu dosyaya çift tıkladığımızda otomatik olarak CMD açılıyor ve testler koşmaya başlıyor.

:: Pavan ilk satırı yazmasına rağmen CMD açıldığı anda zaten otomatik olarak bu dosyanın bulunduğu path ile başlıyor.
:: Yani ekstra olarak "cd" ile OpenCart proje klasörüne geçiş yapmaya gerek yok.
:: Direkt olarak "mvn test" yazdığımızda zaten CMD, OpenCart proje klasörünün path'i ile başladığı için sadece sonuna
:: "mvn test" ifadesinin eklenmesi yeterli.

:: OpenCart projesini, D'deki dosya adında boşluklar olduğu ve bundan dolayı CMD'de sıkıntı olduğu için Masaüstüne kopyalamıştım
:: ve sorunu o şekilde halletmiştim (OpenCart proje klasörü path'inin düzgün ve kısa olması için).
:: Ancak bu dosya sayesinde o sorun da otomatik olarak ortadan kalktı.
:: Çünkü bu dosya sayesinde "cd" komutuna gerek kalmadı.