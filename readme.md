# Lichtstad - Android
[![master](https://img.shields.io/badge/branch-master-orange.svg)](https://github.com/move4mobile/lichtstad-android/tree/master)[![Build Status](https://travis-ci.org/move4mobile/lichtstad-android.svg?branch=master)](https://travis-ci.org/move4mobile/lichtstad-android/) [![develop](https://img.shields.io/badge/branch-develop-orange.svg)](https://github.com/move4mobile/lichtstad-android/tree/develop)[![Build Status](https://travis-ci.org/move4mobile/lichtstad-android.svg?branch=develop)](https://travis-ci.org/move4mobile/lichtstad-android/)

De Android app voor de Lichtstad Gramsbergen feestweek.

-----

### Google services

Download `google-services.json` en plaats deze in `./app`.
Hiervoor zul je eerst een Firebase project moeten aanmaken in de [console](https://console.firebase.google.com). Heb je dit al gedaan, of al toegang tot een project, dan is [hier](https://support.google.com/firebase/answer/7015592) te vinden hoe je het bestand genereert.

### Play Store

Deze applicatie wordt door Travis automatisch geüpload naar de Play Store.
Pushes naar develop worden in het alpha channel gezet. Het alpha channel gebruikt closed alpha testing.
Pushes naar master worden in het beta channel gezet. Het alpha channel maakt gebruik van open beta testing,
wat betekent dat de release voor iedereen te downloaden is. Zorg dus dat eerst goed is getest met de develop versie.
De release naar de productietrack wordt handmatig gedaan vanuit de Play Developer Console.
