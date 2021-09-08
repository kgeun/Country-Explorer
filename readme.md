# Country Explorer

Gamesys Take Home Assignment, Kyung Geun Lee  
4th Sep ~ 8th Sep 2021  

## Introduction
It is an app that shows information about countries around the world using the open API of restcountries.eu.  

## Screenshots
![Screenshot 001](https://user-images.githubusercontent.com/7823937/132509593-bfd790d2-bdb7-427a-8816-136cf8b7e399.jpeg) 
![Screenshot 002](https://user-images.githubusercontent.com/7823937/132509597-479e3f5a-58ae-4ca7-9065-c1352bcaeff6.jpeg)  
![Screenshot 003](https://user-images.githubusercontent.com/7823937/132509605-f07323e6-f2a3-42f1-8080-b4300d20a863.jpeg) 
![Screenshot 004](https://user-images.githubusercontent.com/7823937/132509581-ea6cb7e6-b55f-4c7d-9dc7-21787259d42f.jpeg)  
![Screenshot 005](https://user-images.githubusercontent.com/7823937/132509569-31afb1e3-bed2-43bb-90d4-39159f68fd82.jpeg) 

## How to use the app

You can download app's package file here (https://drive.google.com/drive/folders/1Sy07h5YAfLV0BRgVXqKFngyfYYBZArjv?usp=sharing).  
Please download CountryExplorer_live_release_1.0_1_20210908.apk file and install, execute it.

## Source Repository

Github public repository, https://github.com/kgeun/Country-Explorer

## Tech stacks

- Language : **Kotlin**
- **MVVM Architecture, LiveData, DataBinding** : The architecture is used for clean code and instant view updates with async way.
- **Room** : Used for data persistnce with Livedata
- **Hilt** : Used to adhere to clean architecture and object-oriented SOLID principles.
- **Navigation Architecture Component** : Chosen to quickly construct multi-step screens.
- **Glide** : Used for caching images and loading external images, fast and optimal image processing.
- **Retrofit** : Widely used library as a network standard in Android, and supports coroutine standards well, so used it without any worries.

## Additional functions other than essential functions

- Filterable by continent and name
- Applied grid-based 2 colomn dynamic layout
- Apply various tests
- Latitude and longitude-based maps can be checked

## Introduction to the project focus

- Aimed simple MVVM architecture to construct and read fast and easy.
- Care was taken to separate the UI, model, and network layers.
- Through Livedata and asyncronous processing, filtering result showed immedialtely without clicking submit button.
- functions like product flavor seperation, proguard application, sign with key applied to real field are also applied.
- Almost of abnormal cases such as network delay loading screen, network failure screen, and retry button were handled.
- The navigation architecture component is applied to experience smooth animations and screen transitions.


## Points for improvement

- More thorough separation of layers
- Application of Kotlin Flow to asynchronous communication
- (being written)


## Test Cases and Results (Unit Test through JUnit4)

1. Testing for API Fetch (instrumented test)  

> Test Case : Check for Response of server request (test one item of response) 
> Result : **Pass**  

2. Testing for Memory Persistence with Livedata (instrumented test)

> Test Case : Check for equality for data input and output of Room database and Livedata  
> Result : **Pass**  

3. Testing for Memory Persistence, Filtering Logic  (instrumented test)  

> Test Case : Check for finding one country by alphabet code  
> Result : **Pass**  

> Test Case : Check for filterling logic like user input keyword and click buttons  
> Result : **Pass**  

4. Testing for Business Logic (pure logic test)

> Test Case : Check for number of selected buttons  
> Result : **Pass**    

**Total** : 5 cases  
**Pass**: 5 cases  
**Fail** : 0 cases  
    

## Introduction to the project focus
I downloaded some reoureces from flaticon.com