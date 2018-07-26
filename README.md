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
* Basit bir arayüz tasarlandı (Bootstrap).
* Dile göre filtreleme eklendi. (Veritabanında en çok tekrar eden ilk 200 dil kullandı.)
* Controller class'a dilleri çekmek için fonksiyon eklendi.
* Site ilk açıldığında otomatik olarak eng dil kodu seçili gelecek şekilde ayarlandı.
* Anlamın üzerine gelindiğinde kelime, kelimenin üzerine gelindiğinde anlamın arkaplan rengi değişecek şekilde tasarlandı.
* Kelimelerin üzerine gelindiğinde gözükecek tooltip eklendi. Tooltip içerisine istenilen html kodu yazılabiliyor(Örnek olarak İngilizce kelimelere Amerika bayrağı eklendi.).(Bootstrap)
* Grafiğin ekranı takip etmesi sağlandı.
* Grafik üzerindeki node'a tıklayarak arama özelliği eklendi.
* Filtrelerdeki kendini tekrar etme sorunu çözüldü.
* Filtreler bölümüne filtre uygulama butonu eklendi.
* Grafiğin anlaşılabilir olması için ufak görüntü ayarları yapıldı.
* İlişkiye göre filtreleme eklendi. 
* Path'lere tooltip eklendi. 

### Hatalar
* Sunucu çalıştırıldığında bir hata alınıyor ama bu çalışmaya mani olmuyor.

### Notlar
* PostgreSQL, JDBC yerine JPA kullanılarak bağlandı. JDBC ile sonuç çekilemedi.
* İngilizce kelimelerin tooltip'ine eklenen Amerika bayrağı ide'lerde gözükmeyebilir.
* Hedef dillerin tamamı kaldırılınca otomatik eng ayarlanıyor. (Uyarı gösteriliyor.)
* İlişki filtrelerinin tamamı kaldırılınca tüm ilişkiler tekrar seçiliyor. (Uyarı gösteriliyor.)

### Yapılacaklar
* Dil kodları dil isimlerine çevirilmeli.
* Node ekleme işlemi eklenmeli.
