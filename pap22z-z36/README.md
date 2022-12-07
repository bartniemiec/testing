# ***Projekt PAP***

---

## **Skład zespołu**

- Bartosz Niemiec 318393
- Bartosz Bąbolewski 318362
- Jakub Lemański 318385
- Adam Makowski 318387

## **Temat projektu**

Obsługa magazynu z częściami samochodowymi

## **Wykorzystywana technologia**

JavaFX

# ***Instrukcja instalacji "od zera" dla środowiska Linux***

---

## **Środowisko programistyczne**

Z racji swojej uniwersalności instrukcja instalacji projektu będzie przeprowadzona dla Visual Studio Code. Zatem pierwszym krokiem powinno być pobranie środowiska programistycznego. Zalecamy skorzystanie z linku

`https://code.visualstudio.com/download`

oraz wybranie rozszerzenia pliku ***.deb*** (*Uwaga! Pliki .deb przeznaczone są dla dystrybucji Linuxa typu Debian oraz Ubuntu. Dalsze instrukcje prowadzone są dla tego typu systemów Linux, gdyż są one najczęściej wykorzystywane przez użytkowników.*)

### **Instalacja Visual Studio Code**

Instalacje VSC najlepiej przeprowadzić z użyciem terminala przy użyciu komendy

`sudo apt install /path/to/file.deb`

Po tych działaniach ikona programu VSC powinna pojawić się na naszym komputerze. Jednak w razie problemów z jej znalezieniem VSC możemy uruchomić przy użyciu terminala wpisując prostą instrukcje

`code`

## **Instalacja oprogramowania JDK**

Do pracy z językiem Java wymagane jest pobranie oprogramowania Java Development Kit (JDK). Zainstalować je można wpisując komendę

`sudo apt install openjdk-19-jre-headless`

Przeprowadzona zostanie instalacja oprogramowania do Javy w wersji 19.

Po jej ukończeniu powinniśmy jeszcze ustawić zmienną środowiskową ***JAVA_HOME*** wpisując komendę

`export JAVA_HOME=/path/to/jdk-19`

Ścieżkę tę możemy sprawdzić wpisując komendę 

`which java`

## **Instalacja MySQL**

Do komunikacji i obsługi baz danych używamy systemu MySQL. Najłatwiej zainstalować go przy użyciu terminala, komendą

`sudo apt install mysql-server`

Następnie powinniśmy uruchomić skrypt ***mysql_secure_installation*** zwiększający poziom zabezpieczeń.

Robimy to przy użyciu komendy

`sudo mysql_secure_installation`

Przy wystąpieniu błędu 

***error: 'Can't connect to local MySQL server through socket '/var/run/mysqld/mysqld.sock' (2)***

należy najpierw użyć komendy 

`sudo service mysql start`

i dopiero wtedy użyć komendy

`sudo mysql_secure_installation`

Po jej wpisaniu zostaniemy zapytani o użycie VALIDATE PASSWORD COMPONENT który wprowadza dodatkowe zabezpieczenie w postaci wymogu użycia odpowiednio silnego hasła do mysql.

Wybieramy opcję Yes.

Następnie wybieramy siłę hasła, odpowiednio 

LOW - długość hasła dłuższa niż 8 znakóœ
MEDIUM - długość hasła dłuższa niż 8 znaków, conajmniej 1 wielka litera i znak specjalny
STRONG - długość hasła dłuższa niż 8 znaków, conajmniej 1 wielka litera, znak specjalny oraz "dictionairy file"

Zalecamy wybranie opcji MEDIUM.

Następnie ustawiamy nasze hasło którym będziemy logować się do baz danych MySQL.

Bardzo pradowdopodobne jest, że zostaniemy zaskoczeni informacją o kolejnym błędzie mimo poprawnej siły hasła.

`Failed! Error: SET PASSWORD has no significance for user 'root'@'localhost' as the authentication method used doesn't store authentication data in the MySQL server. Please consider using ALTER USER instead if you want to change authentication parameters.`

Powinniśmy wtedy zalogować się do serwera mysql jako root bez uwierzytelniania przez terminal komendą

`sudo mysql`

Następnie wpisujemy komendę

`ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password by 'mynewpassword';`

Po tym możemy wyjść z mysql komendą

`exit`

Teraz możemy jeszcze raz uruchomić skrypt *secure_installation* komendą

`sudo mysql_secure_installation`

Zostaniemy zapytani o usunięcie anonimowych użytkowników. Zgadzamy się.

Następnie zgadzamy się na uniemożliwienie dostępu do serwera użytkownikom zewnętrznym logującym się jako root.

Usuwamy testową bazę danych. (Zaznaczamy opcję *no*).

Odświeżamy "privilige tables".

Zakończyliśmy instalację MySQL.

...

## **Konfiguracja środowiska programistycznego**

**Java**

Przed uruchomieniem jakiegokolwiek projektu w Javie musimy zainstalować odpowiednie paczki dedykowane dla języka JAVA. 

W interfejsie VSC wybieramy zakładkę *Extensions* znajdującą się po lewej stronie. Wyszukujemy tam wtyczki o nazwie ***Extension Pack for Java*** od firmy Microsoft. Istnieje wiele więcej wtyczek ułatwiających pracę z Javą, jednak nie są one konieczne do uruchomienia naszego projektu. 

**MySQL**

Przydatne wtyczki do pracy z bazami danych to **MySQL** oraz **dbclient-jdbc** od wydawcy Weijan Chen.

## **Sklonowanie repozytorium do katalogu z projektem**

**Utworzenie nowego projektu**

W interfejsie VSC wybieramy zakładkę *Help -> Show all commands* i z listy wybieramy pozycję *Java: Create Java Project*.

Następnie wybieramy opcję *No build tools*.

Wybieramy lokalizację projektu.

Oraz nadajemy mu nazwę.

**Klonowanie repozytorium projektowego**

Będąc w katalogu gdzie chcemy by znajdował się nasz projekt w terminlu wpisujemy komendę

`git clone https://gitlab-stud.elka.pw.edu.pl/pap2/pap22z-z36.git`

Zostaniemy zapytani o podanie nazwy użytkownika oraz hasło którym logujemy się do serwera gitlab.

Jeśli zrobimy to poprawnie w naszym katalogu pojawi sie zaimportowany projekt. Niestety jeszcze nie będziemy w stanie go uruchomić.

## **Instalacja technologii JavaFX**

Wszystkie pliki .jar związane z JavaFX zostały przez nas zaimportowane do katalogu /lib projektu. 

Do pliku launch.json również została już dodana konfiguracja związana z polem vmArgs. Użytkownik nie powinien się tym przejmować.

Jednak w razie braku tych plików konfigurację można przeprowadzić ręcznie.

Pierwszym krokiem jest pobranie odpowiedniego JavaFX SDK (Software Development Kit). W tym celu korzystamy ze strony

`https://gluonhq.com/products/javafx/`

W sekcji *Downloads* wybieramy wersję typu ***SDK*** oraz dla architektury odpowiedniej dla systemu używanego przez użytkownika (najczęściej ***x64***).

Wypakowujemy plik zip do wybranego przez nas katalogu.

Następnie konfigurujemy plik *launch.json* dodając pod polem 

`"mainClass": "Magazyn.Main"`

pole 

`"vmArgs": "--module-path path/to/javafx/lib --add-modules javafx.controls,javafx.fxml"`

## Uruchomienie projektu w postaci jednej linijki (plik ***.jar***)

Do naszego projektu został dołączony plik z rozszerzeniem ***.jar*** który umożliwia uruchomienie go w terminalu z użyciem jednej komendy.

By to zrobić przechodzimy do katalogu w którym znajduje się plik ***.jar*** i wpisujemy komendę

`java -jar --enable-preview --module-path path/to/project/lib --add-modules=javafx.controls,javafx.fxml JAVA_projekt.jar`

Zostanie wyświetlone okno do zalogowania się do bazy danych. Musimy w nim podać hasło które ustalaliśmy przy konfiguracji MySQL. Po jego wpisaniu otworzy się stworzona przez nas aplikacja obsługi magazynu z częściami samochodowymi.








