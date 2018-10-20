# QV21 Coding Challenge

# Intro

The purpose of the app is to allow the user to view the contents of a .csv file that has well data information stored in each cell. The user should be able to view a summary of the items in the list and then have the option to tap on that item to view details about the well data and be able to edit the contents of the well data. Any edits made to the well data should be reflected everywhere within the app.

# Additional Features

In addition to providing the basic functionality mentioned above here are extras that were done:

-	The app utilizes an embedded database so that any edits made are preserved between app sessions.

-	The user not only has the ability to edit a well data entry, but they can also delete an entry.

-	Errors are handling by providing a series of modal dialogs informing the user of anything that can potentially go wrong or warning the user about destructive operations such as deleting a well data entry.
 
-	Progress dialogs are used to indicate long running tasks such as seeding well data and CRUD operations on cached content within the Database.

# Installation Steps:

1) Please make sure that Android Studio is up to date with latest SDK. The minimum SDK supported is API23, and compiler SDK and target SDK
are all set to API28.

2) I've setup a public repository for the code where the latest code changes are in master here:
https://github.com/ErnestHolloway8482/QV21-codingexercise-holloway

3) Please clone the repository using your favorite Git tools. You should not need any credentials to clone.

4) Once you successfully clone the project please make go to Android Studio->Settings->Preferences->Build, Execution, Deployment
within the Android Studio IDE.

5) Select Compiler and then make sure that Configure on Demand is unchecked. I ran into issues with the latest version of the compiler
and build tools and disabling this feature allows the app to be built and deployed.

6) Choose either your favorite emulator or device and install the debug build variant of the app onto it. I didn't make a production key, so this is the only one that will work.

# Architecture Overview And Overall Approach

The architecture and overall framework are based entirely on MVVM and heavily utilizes the android data binding library. The MVVM architecture was chosen for the following reasons:

1)	Modern best practices for Android Development have embraced Model, View, View Model(MVVM) in favor of MVC(Model View Controller) and has been adopted as part of Google’s recommended best practices for building apps.

2)	Outside of the strong push from Google, MVVM allows the previously mountainous controller logic that would typically reside within fragments or activities to be broken up into View Models that can individually be unit tested to confirm the correctness of the app. This was the approach taken to carefully test the relevant areas of the business logic for the app. As a matter of fact, the UI portions of the app were written lasts since a data driven approach was taken to build up all components that the UI will consume first and verifying those classes and data objects for correctness.

3)	MVVM architecture allows for the views to be extremely simple. We no longer have to physically inflate the layouts, and programmatically assign click listeners to them that will perform a specific action. With android data binding it also eliminates the need to manually update the UI presentation layer and allows for updates to the UI to be entirely data driven. It also supports the concept of having a reactive app that is always up to date based on when data changes. It also allows our data structures that will be consumed by the UI to be extremely simple and written as POJO’s (Plan Old Java Objects).


In addition to utilizing MVVM these other key design patterns were utilized:

-	Asynchronous/Reactive programming was utilized thanks to the incorporation of RxJava within the application and ObjectBox DB. Following suite with modern best practices, the outdated and troubles Async Task that is part of the standard Android libraries is no longer used in favor of RXJava. RXJava not only gives us better controller on background tasks, but also inherently has subscription and publication of data built into the framework as well. This makes use of the subscriber/publication design pattern that allows objects to register to topics of interest and be notified when new topic data is available. With respect to the cached data content, ObjectBox DB is utilized which is a NO SQL database is utilized. One of the great features with ObjectBox is that not only are database operations extremely fast compared to SQL, but reading content is especially memory efficient thanks to built-in Lazy loading and the ability to auto re-run queries when data contents change. ObjectBox DB will not load contents into memory until it is accessed and even with that only a few elements will remain within memory at a time. The lazy loading is on a class and field basis which means that content is only accessed when required. Combining the two concepts of RXJava and ObjectBox DB made it simple to make sure that any edits made to the well data was reflected everywhere else within the application where well data contents is displayed.

-	Dependency Injection which is largely based on the Factory pattern was utilized via Dagger 2.0 to make initializing objects simple. One of the best examples in the app is  the WellDataFacade and any of the defined view models as I primarily rely on constructor injection to make sure that any interaction that needs to occur between multiple classes is clearly defined and made obvious. The ability to generate objects from Factories also makes it simpler to mock out dependencies for unit testing. An example of this is how the AlertDialogManager which is responsible for presenting the user’s with modal dialogues can be mocked as a fake object so that within the view model tests, I can concentrate on the controller logic and not if the alert dialog was displayed.

-	A Fragmentless architecture was utilized which is in line with modern Android development practices. I wrote a custom navigation manager which allows me to simply swap out views that are bound to view models in lieu of Fragments. With custom views there are no complex life cycles to deal with when it comes to pushing and popping items onto the navigation stack that can lead to unwanted side-affects. This also enabled me to completely test navigation via a standard unit test, which is much more difficult to do with Activity or Fragment based navigation.

-	I utilized many of Dr. Bob’s Clean Coding principles when it comes to the overall coding structure, method names, variable names, class names and tried to avoid meaningless code documentation. 

# Testing Approach

The majority of the testing that was done on the project was handled via Android and Java unit based tests. 70% of the testing coverage is handled by unit tests, while 20% is handled by integration testing which is primarily focused on the View Models and WellDataFacade, which accounts for the user interactions that will occur during the app. The last 10% of the testing was handled via manual QA by deploying the app directly on the phone and confirming the acceptance criteria as defined by the coding challenge.


# Third Party Library Choices

In lieu of listing each and every dependency I will list them by group with a brief explanation on why there were used. This grouping follows the setup of the comments of the dependencies within the Gradle file as well.

- Android Support Libraries-This was used to be able to support material design elements which is the standard now for developing modern Android apps.

- Unit Testing Support Libraries-Used to enable Android and Java based testing. I wanted to confirm the majority of the correctness of the code by automated testing and not solely rely on manual QA testing.

- Mockito-This library was essentially for allowing me to mock the behavior of things that don’t necessarily have deterministic behavior such at the NetworkManager or for certain Android dependencies that don’t really make sense to test within the confines of a unit test.

- Lottie-This library by AirBnb allowed me to pull off the nifty little loading spinner animation icon and allowed me to make the app sparkle a bit! 

- Dagger-This was used to handle all dependency injection via application of Factory design pattern as described above.

- RX Java-Used for asynchronous background threading. This is a much better approach and now the standard in lieu of Async Tasks.

- CSV Reader-This was utilized to make it simple to read in the entry rows of the included .csv file that is within the raw directory of the project. The main reason I utilized this library is that it correctly handles parsing of row content that contain commas that should not serve as a delimiter.

- ObjectBox DB-This was utilized to handle storing the seeded content from the included .csv file for the project and make it easier to display and update the contents of each well entry. As explained above, utilizing an embedded database made it simpler to meet the requirements of having edits made to the well data be visible within all screens of the app.


# Things To Improve On/Challenges:

-While the overall approach was great to be able to provide really solid test coverage, it required a TON of work to complete as a result the project took me longer than anticipated. One of the biggest challenges that I had with the project was figuring out what to mock out for the View Model test so that I could run them as unit tests without having to start an activity.

-While the View Models tests pass, I had to introduce thread sleeping to account for the asynchronous nature of the calls which in general should try to be avoided when writing unit tests. For next time, I’d like to figure out a better way to deal with threading within my unit tests or find a way to disable it during unit testing so that the code can be tested sequentially without fear of having unintended side effects should I not wait long enough for a background task to finish before verifying its result.

-I would have liked to add screen animation transitions when swapping out the screens. While the screen transitions are super quick, I would have liked to make them more interesting.

-I would have liked to have done some additional improvements on the overall UI. It looks presentable, but it would have been good to add in additional colors and assets to make the design pop some more.

-The project took longer in order to get all of the architectural components to work well together. I could have taken the simpler approach, but my goal wasn’t just to get the project working. I wanted to get as close to what I would do on a real production project to maximize the chance of the software working well, being easier to maintain, and more importantly being able to change.


