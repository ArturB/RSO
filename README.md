[![Build Status](https://travis-ci.org/ArturB/RSO.svg?branch=master)](https://travis-ci.org/ArturB/RSO)

# RSO 2018L

## Funkcjonalność
Na chwilę obecną, projekt zawiera dwie usługi sieciowe:
 - serwer HTTPS (s_server z OpenSSL), który wypisuje trochę informacji o samym sobie
 - serwer HTTPS ncat, który przekierowuje do HTTPS

## Continuous Integration
CI z użyciem Travisa. Travis kompiluje projekt Mavena poprzez mvn install, a nastęnie wysyła na serwer w celu wdrożenia. Aby zmienić cele i sposób kompilacji (np. mvn install na mvn package itp) należy edytować plik .travis.yml. 

## Continuous Deployment
CD również z użyciem Travisa i Dockera, przez SSH.

### Docker
 - Po każdym commicie, Travis generuje i wysyła do HubDockera zdefiniowane kontenery Dockera, a następnie uruchamia je na docelowym serwerze. 
 - Zarządzanie kontenerami poprzez docker-compose. 
 - lista obrazów dockera oraz sposób ich budowania zdefiniowany w pliku docker-compose.yml
 - sposób uruchamiania tych obrazów na serwerze docelowym (np. liczba replik, load balancery itp) zdefiniowany w pliku docker-stack.yml
 - informacje uwierzytelniające do HubDockera przechowywane są w pliku .travis.yml w formie zaszyfrowanej (w polach secure). 

### SSH
 - klucze SSH do autoryzacji przechowywane są w pliku keys.tar.enc, w formie zaszyfrowanej. Może je odczytać tylko Travis. 
 - klucze SSL używane podczas stawiania serwera HTTPS przechowywane są na docelowym serwerze i są dynamicznie wczytywane przez Dockera podczas uruchamiania usług. Nie ma możliwości ich odczytania z poziomu użytkownia rso. 
 - klucze RSA używane przez Travisa do obsługi szyfrowania są zapisane w polach "secure".
 - pełen skrypt uruchamiający kontenery na docelowym serwerze znajduje się w pliku deploy.sh
 
