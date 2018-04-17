[![Build Status](https://travis-ci.org/ArturB/RSO.svg?branch=master)](https://travis-ci.org/ArturB/RSO)

# RSO 2018L

## Funkcjonalność
Na chwilę obecną, projekt zawiera dwie usługi sieciowe:
 - serwer HTTPS (s_server z OpenSSL), który wypisuje trochę informacji o samym sobie
 - serwer HTTPS ncat, który przekierowuje do HTTPS
 
oraz dwa projekty Mavena:
 - service1
 - service2

jako szablon do dalszej rozbudowy.

## Continuous Integration
CI z użyciem Travisa. W celu zbudowania projektu, Travis wykonuje skrypt build.sh.  

## Continuous Deployment
CD również z użyciem Travisa i Dockera, przez SSH. Po zalogowaniu na hoście docelowym, Travis wykonuje skrypt deploy.sh

### Docker
 - Po każdym commicie, Travis generuje i wysyła do HubDockera zdefiniowane kontenery, a następnie uruchamia je na docelowym serwerze. 
 - Zarządzanie kontenerami poprzez docker-compose; lista konenerów oraz sposób ich budowania i uruchamiania na hoście docelowym zdefiniowany jest w pliku docker-compose.yml
 - informacje uwierzytelniające do HubDockera przechowywane są w pliku .travis.yml w formie zaszyfrowanej (w polach secure). 

### SSH
 - klucze SSH do autoryzacji przechowywane są w pliku keys.tar.enc, w formie zaszyfrowanej. Może je odczytać tylko Travis. 
 - klucze SSL używane podczas stawiania serwera HTTPS przechowywane są na docelowym serwerze i są dynamicznie wczytywane przez Dockera (za pomocą mechanizmu woluminów) podczas uruchamiania usług. Nie ma możliwości ich odczytania z poziomu użytkownia rso. 
 - klucze RSA używane przez Travisa do obsługi szyfrowania są zapisane w polach "secure".

 
