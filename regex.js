/* Imię:
 * - zaczyna się z wielkiej litery
 * - potem składa się z małych liter
 * - zawiera co najmniej 2 znaki
 */
var user-name      = /^[A-ZĄĆĘŁÓŚŹŻ][a-ząćęłóśźż]+$/
var candidate-name = user-name

/* Nazwisko:
 * - zaczyna się z wielkiej litery
 * - potem składa się z małych bądź wielkich liter bądź myślnika, spacji lub apostrofu
 * - kończy się małą literą
 * - zawiera co najmniej 2 znaki
 */
var user-surname      = /^[A-ZĄĆĘŁÓŚŹŻ][A-ZĄĆĘŁÓŚŹŻa-ząćęłóśźż\ \-\']*[a-ząćęłóśźż]$/
var candidate-surname = user-surname

/* PESEL składa się z 11 cyfr */
var user-pesel = /^[0-9]{11}$/

/* Nazwa okręgu i gminy:
 * - zaczyna się z wielkiej litery
 * - potem składa się z małych bądź wielkich liter bądź cyfr bądź myślnika, spacji lub apostrofu
 * - kończy się małą literą bądź cyfrą
 * - zawiera co najmniej 2 znaki
 */
var ward-name    = /^[A-ZĄĆĘŁÓŚŹŻ][A-ZĄĆĘŁÓŚŹŻa-ząćęłóśźż0-9\ \-\']*[a-ząćęłóśźż0-9]$/
var commune-name = ward-name

/* Nazwa partii:
 * - zaczyna się w wielkiej litery
 * - potem zawiera dowolny ciąg znaków bo nazwy partii bywają bardzo różne
 * - zawiera co najmniej 2 znaki
 */
var party-name = /^[A-ZĄĆĘŁÓŚŹŻ].+$/

/* Skrót nazwy partii:
 * - zawiera co najmniej 2 dowolne znaki, bo skróty bywają bardzo różne
*/
var party-abbreviation = /^..+$/

/* Wiek kandydate:
 * - jest to liczba całkowita z przedziału 18 - 129
 * - napisałem do tego regexa, ale chyba łatwiej to sprawdzić programowo :)
 */
var candidate-age = /^1[8-9]$|^[2-9][0-9]$|^1[0-2][0-9]$/

