# Country Explorer

Country Explorer, Kyung Geun Lee  
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
You can download app's package file here (https://drive.google.com/drive/folders/1D0nAixvuRdoxCJQmlPAYdJLu4ff9OHPI?usp=sharing).  
Please download BBCE_live_release_1.0_1_20210902.apk file and install, execute it.

## Source Repository
Github public repository, https://github.com/kgeun/Country-Explorer

## Tech stacks

- Language : **Kotlin**
- **MVVM Architecture, LiveData, DataBinding** : The architecture is used for clean code and instant view updates.
- **Room** : Used for data persistnce.
- **Hilt** : For clean architecture application, used Hilt, which is Google's official library and much simpler to use than Dagger.
- **Navigation Architecture Component** : Chosen to quickly construct multi-step screens.
- **Glide** : Used for caching images and loading external images, optimal image processing and scalability.
- **Retrofit** : It was judged to be an efficient form of communication library to be applied to reactive programming and used.


## Introduction to the project focus
- Aimed simple MVVM architecture to construct and read fast and easy.
- Applied dark but elegant design to fit the atmosphere of the subject of the project.
- Through Livedata and asyncronous processing, filtering result showed immedialtely without clicking submit button.
- functions like product flavor seperation, proguard application, sign with key applied to real field are also applied.


## Test Cases and Results (Unit Test through JUnit4)
1. Testing for API (instrumented test)  

> Test Case : Check for Response of server request  
> Result : **Pass**  

2. Testing for Memory Persistence with Livedata (instrumented test)

> Test Case : Check for equality for data input and output of Room database and Livedata  
> Result : **Pass**  

3. Testing for Memory Persistence (instrumented test)  

> Test Case : Without Liveadata, Check data equality with pure database  
> Result : **Pass**  

4. Testing for Business Logic (pure logic test)

> Test Case : Check for number of selected seasons  
> Result : **Pass**    

> Test Case : Check for dynamic query for keyword and season appearance search  
> Result : **Pass**    

> Test Case : Check for dynamic query for season appearance search  
> Result : **Pass**    

**Total** : 6 cases  
**Pass**: 6 cases  
**Fail** : 0 cases  
    