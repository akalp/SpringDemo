# SpringDemo

## Kullanılan Teknolojiler
* Spring
* Neo4j
* PostgreSQL
* Bootstrap
* D3js
* JavaScript / JQuery

### Yapılanlar
* Neo4j bağlantısı yapıldı
* Postgresql bağlantısı yapıldı
* Gerekli repositoryler oluşturuldu. (Word, Wordnet ve Definition) (Geriye kalanlar şimdilik işlevsiz)
* SynsetId paramatresi null olmayanlara controller class'ında definition konuldu.
* Basit bir arayüz tasarlandı (Bootstrap), dillere göre filtreleme eklendi.
* Controller class'a dilleri çekmek için fonksiyon eklendi.
* Site ilk açıldığında otomatik olarak eng dil kodu seçili gelecek şekilde ayarlandı.
* Anlamın üzerine gelindiğinde kelime, kelimenin üzerine gelindiğinde anlamın arkaplan rengi değişecek şekilde tasarlandı.
* Kelimelerin üzerine gelindiğinde gözükecek tooltip eklendi. Tooltip içerisine istenilen html kodu yazılabiliyor.(Bootstrap)

### Hatalar
* Sunucu çalıştırıldığında bir hata alınıyor ama bu çalışmaya mani olmuyor.
* İlk aramada dil filtreleri kendilerini tekrarlıyor.

### Notlar
* PostgreSQL, JDBC yerine JPA kullanılarak bağlandı. JDBC ile sonuç çekilemedi.

### Yapılacaklar
* Dil kodları dil isimlerine çevirilmeli.
* İlişki filtrelemesi eklenmeli.
* Node ekleme işlemi eklenmeli.
