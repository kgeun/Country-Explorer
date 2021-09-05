# Breaking Bad Character Explorer

GAN Home Assignment, Kyung Geun Lee  
29th Aug ~ 2th Sep 2021  

## Introduction
This is an application that provides informations about characters about Breaking Bad drama.  

## Screenshots
![bbce1](https://user-images.githubusercontent.com/7823937/131785335-c5c63577-28e7-4f6f-bf70-e73816d0a8d3.jpeg) 
![bbce2](https://user-images.githubusercontent.com/7823937/131785341-63f6223b-8b68-41a1-94c6-0222d13fa0c3.jpeg) 
![bbce3](https://user-images.githubusercontent.com/7823937/131785347-854f6373-6b5a-4a8e-b608-50146bf93470.jpeg) 

## How to use the app
You can download app's package file here (https://drive.google.com/drive/folders/1D0nAixvuRdoxCJQmlPAYdJLu4ff9OHPI?usp=sharing).  
Please download BBCE_live_release_1.0_1_20210902.apk file and install, execute it.

## Source Repository
Github public repository, https://github.com/kgeun/Breaking-bad-character-explorer

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
    