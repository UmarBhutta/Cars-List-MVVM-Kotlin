# Car List

Car List is sample app, which fetch the Cars list form the server and show them on the map as marker and also display them as plain list.
The main goal of this app is to be a sample of how to build an high quality Android application that uses the Architecture components,MVVM,Coroutines,Jetpack,Hilt,Google Maps, etc. in Kotlin.

## Architecture
The app uses MVVM [Model-View-ViewModel] architecture to have a unidirectional flow of data, separation of concern, testability, and a lot more.

**MVVM Best Practice:**
- Avoid references to Views in ViewModels.
- Instead of pushing data to the UI, let the UI observe changes to it.
- Distribute responsibilities, add a domain layer if needed.
- Add a data repository as the single-point entry to your data.
- Expose information about the state of your data using a wrapper or another LiveData.
- Consider edge cases, leaks and how long-running operations can affect the instances in your architecture.
- Don’t put logic in the ViewModel that is critical to saving clean state or related to data. Any call you make from a ViewModel can be the last one.

**Keep your code clean according to MVVM**
-----------------------------
 - Yes , liveData is easy , powerful , but you should know how to use.
 - For livedata which will emit data stream , it has to be in your
   data layer , and don't inform those observables any thing else like
   in which thread those will consume , cause it is another
 - For livedata which will emit UI binding events, it has to be in your ViewModel Layer.
 - Observers in UI Consume and react to live data values and bind it.
   responsibility , and according to `Single responsibility principle`
  in `SOLID (object-oriented design)` , so don't break this concept by
   mixing the responsibilities .

## Coroutines

**What is Coroutines ?**
-------------------

 **Coroutines :**
Is light wight threads for asynchronous programming, Coroutines not only open the doors to
asynchronous programming, but also provide a wealth of other possibilities such as concurrency, actors, etc.

----------

**Coroutines VS RXJava**
-------------------
They're different tools with different strengths. Like a tank and a cannon, they have a lot of overlap but are more or less desirable under different circumstances.
        - Coroutines Is light wight threads for asynchronous programming.
        - RX-Kotlin/RX-Java is functional reactive programming, its core pattern relay on
        observer design pattern, so you can use it to handle user interaction with UI while you
        still using coroutines as main core for background work.

**How does Coroutines concept work ?**
------------
 - Kotlin coroutine is a way of doing things asynchronously in a sequential manner. Creating a coroutine is a lot cheaper vs creating a thread.


**When I can choose Coroutines or RX-Kotlin to do some behaviour ?**
--------------------------
 - Coroutines : *When we have concurrent tasks , like you would fetch data from Remote connections
 , database , any background processes , sure you can use RX in such cases too, but it looks like
  you use a tank to kill ant.*
 - RX-Kotlin : *When you would to handle stream of UI actions like : user scrolling , clicks ,
 update UI upon some events .....ect .*


**What is the Coroutines benefits?**
-----------------------------

 - Writing an asynchronous code is sequential manner.
 - Costing of create coroutines are much cheaper to crate threads.
 - Don't be over engineered to use observable pattern, when no need to use it.
 - parent coroutine can automatically manage the life cycle of its child coroutines for you.


## Libraries and tools 🛠

<li><a href="https://developer.android.com/topic/libraries/architecture/navigation/">Navigation</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/viewmodel">ViewModel</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a></li>
<li><a href="https://developer.android.com/guide/navigation">Navigation Component</a></li>
<li><a href="https://material.io/develop/android/docs/getting-started/">Material Design</a></li>
<li><a href="https://developer.android.com/kotlin/coroutines">Coroutines</a></li>
<li><a href="https://developers.google.com/maps/documentation/android-sdk/overview">Google Maps</a></li>
<li><a href="https://dagger.dev/hilt/">Hilt</a></li>
<li><a href="https://square.github.io/retrofit/">Retrofit</a></li>
<li><a href="https://github.com/square/okhttp">OkHttp</a></li>
<li><a href="https://github.com/google/gson">Gson</a></li>
<li><a href="https://github.com/bumptech/glide">Glide</a></li>

## Testing 🧪
<li><a href="https://github.com/junit-team/junit4">Junit</a></li>
<li><a href="https://developer.android.com/training/testing/espresso">Espresso</a></li>


## Maintainers
This project is mantained by:
* [Muhammad Umar](https://github.com/UmarBhutta)
