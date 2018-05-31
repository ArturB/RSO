[![Build Status](https://travis-ci.org/ArturB/RSO.svg?branch=master)](https://travis-ci.org/ArturB/RSO)

# RSO 2018L

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
 ## Issues
 W zakładce issues są problemy do rozwiązania dotyczące projektu. Ponieważ sposób działania systemu może znacząco się różnić w zależności od zalogowanego użytkownika, przyjmuje się zasadę, że każdy issue jest tagowany nazwą konta, na którym pojawił się błąd. Jeśli błąd występuje na wszystkich kontach, piszemy tag [all]. 
 
