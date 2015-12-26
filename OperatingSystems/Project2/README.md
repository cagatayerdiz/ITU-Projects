Project2 Description
====================

1000 arasındaki 2 ve 3’e tam bölünemeyen tamsayıları bulup yazdıran bir program yazılacaktır. Problemin çözümünde üç adet proses (P1, P2 ve P3) ve her biri bir tamsayı taşıyabilecek boyutta olan iki tampon alan (T1 ve T2) (paylaşılan bellek) şöyle kullanılacaktır:

1. Ana proses P1, P2 ve P3 proseslerini yaratır ve onlar sonlandıktan sonra kendisi de sonlanır.

2. P1 prosesi 1’den 1000’e kadar tamsayıları üretir ve bu sayıları birer birer T1 tampon alanına (alanı boş buldukça) yerleştirir. 1000 değerini yerleştirdikten sonra 0 değerini bir sonlanma işareti olarak T1’e yazar ve kendini sonlandırır.

3. P2 prosesi T1 tampon alanından tamsayıları okur. Okuduğu değer 2’ye tam bölünemez ise, bu değeri T2 tampon alanına yerleştirir. Eğer değer 2’ye tam bölünür ise, bu değeri görmezden gelir. Eğer okuduğu değer 0 ise, T2’ye 0 yazar ve kendini sonlandırır.

4. P3 prosesi T2 tampon alanından tamsayılar okur. Okuduğu değer 3’e tam bölünemez ise, bu sayıyı sonuc.txt dosyasına yazar ve sonlanmadan önce bu bilgileri çıkışa aktarır. Eğer değer 3’e tam bölünür ise, bu değeri görmezden gelir. Eğer okuduğu değer 0 ise, kendini sonlandırır.

Not: Çözüm için üretici-tüketici probleminden yararlanın.

![alt tag](http://i67.tinypic.com/fcv4sj.png)
