# SpringDemo
### Yapılanlar
* Neo4j bağlantısı yapıldı
* Postgresql bağlantısı yapıldı
* Gerekli repositoryler oluşturuldu. (Word, Wordnet ve Definition) (Geriye kalanlar şimdilik işlevsiz)
* SynsetId paramatresi null olmayanlara controller class'ında definition konuldu.

### Hatalar
* Dönen json'da kimi objenin hyponym, hypernym veya antonym listesi gözükmüyor.
* Sunucu çalıştırıldığında bir hata alınıyor ama bu çalışmaya mani olmuyor.

### Notlar
* PostgreSQL, JDBC yerine JPA kullanılarak bağlandı. JDBC ile sonuç çekilemedi.
