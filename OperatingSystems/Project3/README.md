Project3 Description
====================

Bir restorana ait yer ayırtma işlemlerini simüle eden bir program hazırlanacaktır. Restoran gelen müşterilere masa ayırırken şu adımlar izlenmektedir: müşterilerden rezervasyon yapmak için önceden telefon edenlerin isimleri FIFO olarak gerçeklenen bir bekleme listesine eklenmekte ve restorana geldikleri zaman, boş bir masa varsa veya bir masa boşalınca oturtulmaktadırlar. Masaların 4 kişilik olduğunu, toplam 5 masa bulunduğunu ve müşterilerin de 4 kişilik gruplar halinde geldiklerini kabul edebilirsiniz. Bekleme listesine adını yazdırmış ancak sırası geldiğinde henüz restorana gelmemiş bir grup söz konusu olursa, listede onu izleyen bir diğer gruba masa verilmektedir. Rezervasyon yapmadan doğrudan restorana gelen gruplar ise, o anda boş bir masa yoksa, doğrudan bekleme listesinin sonuna eklenmekte ve bekletilmektedir.

Ayrıca, restoran sahibi bazı gruplara öncelik tanımaktadır (örneğin, hatırlı müşteriler, belirli işyeri çalışanları, vs...). Bu tür bir gruba ait olanların rezervasyon için telefon etmelerine gerek yoktur; restorana geldiklerinde ilk boş/boşalan masaya oturtulurlar. Tanımlanan bu çalışma düzenini denemek üzere müşteri gruplarını, rezervasyon prosesini ve müşterileri masalarına yerleştiren restoran görevlisinin davranışlarını simüle edecek kodu, karşılıklı dışlama ve senkronizasyon için semafor yapısından yararlanarak yazın.

Aşağıdaki senaryoda GELEN MÜŞTERİ TÜRÜ bilgisini ödevinizi çalıştırırken input olarak bizim tarafımızdan girilecektir. Bu input dan sonra sizin MASALARIN YENİDEN YERLEŞİMİ ni ekrana çıktı olarak basmanız beklenmektedir. Tüm masalar dolunca işlem bitmiş olacaktır.

Örnek senaryo:

BAŞLANGIÇTA ,

Masa1= Boş

Masa2= Boş

Masa3= Boş

Masa4= Boş

Masa5= Boş

Masa6= Boş

Masa7= Boş

Masa8= Boş

Masa9= Boş

Masa10= Boş

Kuyrukta bekleyen müşteri : 5 adet telefonla rezervasyon yapan grup

GELEN MÜŞTERİ TÜRÜ:

5 adet telefonla rezervasyon yapan grup

1 adet hatırlı müşteri grubu

**`NOT: Bu müşterilerin aynı anda geldikleri düşünülecek`**

MASALARIN YENİDEN YERLEŞİMİ:

Masa1= Hatırlı müşteri grubu

Masa2= Telefonla rezervasyon yapan grup

Masa3= Telefonla rezervasyon yapan grup

Masa4= Telefonla rezervasyon yapan grup

Masa5= Telefonla rezervasyon yapan grup

Masa6= Telefonla rezervasyon yapan grup

Masa7= Boş

Masa8= Boş

Masa9= Boş

Masa10= Boş

Kuyrukta bekleyen müşteri: 2 adet telefonla rezervasyon yapan grup

GELEN MÜŞTERİ TÜRÜ:

1 adet telefonla rezervasyon yapan grup

3 adet rezervasyon yapmadan doğrudan gelen grup

1 adet hatırlı müşteri grubu

**`NOT: Bu müşterilerin aynı anda geldikleri düşünülecek`**

MASALARIN YENİDEN YERLEŞİMİ:

Masa1= Hatırlı müşteri grubu

Masa2= Telefonla rezervasyon yapan grup

Masa3= Telefonla rezervasyon yapan grup

Masa4= Telefonla rezervasyon yapan grup

Masa5= Telefonla rezervasyon yapan grup

Masa6= Telefonla rezervasyon yapan grup

Masa7= Hatırlı müşteri grubu

Masa8= Telefonla rezervasyon yapan grup

Masa9= Rezervasyon yapmadan doğrudan gelen grup

Masa10= Rezervasyon yapmadan doğrudan gelen grup

Kuyrukta bekleyen müşteri:

1 adet telefonla rezervasyon yapan grup

1 adet rezervasyon yapmadan doğrudan gelen grup
