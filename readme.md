## Table of contents
* [Technologies](#technologies)
* [General info](#general-info)
* [Application description](#application-description)
* [JUnit](#junit)
	

## Technologies
<img src="https://hsto.org/webt/rg/a1/3b/rga13bp-mbl4ljkpbd-fuu6pzfw.png" alt="drawing" height=70px/>
<img src="https://vignette.wikia.nocookie.net/jfx/images/5/5a/JavaFXIsland600x300.png/revision/latest?cb=20070917150551" alt="drawing" height=70px/>
<img src="https://i0.wp.com/gluonhq.com/wp-content/uploads/2015/02/SceneBuilderLogo.png?fit=781%2C781&ssl=1" alt="drawing" height=70px/>
<img src="https://www.techcentral.ie/wp-content/uploads/2019/07/Java_jdk_logo_web-372x210.jpg" alt="drawing" height=70px/>
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/0b/Maven_logo.svg/1024px-Maven_logo.svg.png" alt="drawing" height=70px/> 
<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/IntelliJ_IDEA_Logo.svg/1024px-IntelliJ_IDEA_Logo.svg.png" alt="drawing" height=70px/> 
<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRAu04kDKShXILw-Tl0JF3KQA1ItFijZLj02w&usqp=CAU" alt="drawing" height=80px/>


## General info
Projekt stworzony w celach (zabicia nudy i) ćwiczenia Javy, Mavena oraz JUnit.


## Application description
Aplikacja jest implementacją klasycznej gry Minesweeper. 

Gracz ma możliwość wyboru 3 rozmiarów planszy:
* 8x8 (standardowo 10 min)
* 16x16 (standardowo 40 min)
* 24x24 (standardowo 130 min)

![](images/s1.png)

Dla każdej planszy można wybrać niestandardową liczbę min z przedziału <1, size*size/2>.

![](images/s2.png)

W grze zaimplementowane zostały wszystkie zasady klasycznego Minesweepera, oraz ułatwienia, 
jak stawianie min, otwieranie pusty pól jednym kliknięciem, czy odkrywanie bezpiecznego otoczenia pola, 
które już spełnia wymaganą liczbę otaczających min.

Przykładowa rozgrywka: 

![](images/game.gif)

![](images/s4.png)

Po oznaczeniu wszystkich min, bądź po odkryciu wszystkich pól bezpiecznych stoper przestaje mierzyć czas, 
a swój wynik można zapisać pod własnym pseudonimem:

![](images/s5.png)

Wyniki można przeglądać w oknie Scoreboard:

![](images/s6.png)
![](images/s7.png)

Wyniki zapisywane są w pamięci komputera w formacie Json, a następnie czytane do Scoreboard.

![](images/s8.png)


## JUnit

W celu ćwiczeniowym zostały napisane testy jednostkowe sprawdzające poprawne funkcjonowanie logiki gry.