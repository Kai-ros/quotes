# Lab 06 
* Use the file recentquotes.json to show random popular book quotes. Your program should use GSON to parse the .json file. The app needs no functionality other than showing the quote and the author when it is run. The app should choose one quote each time it is run.

## Quotes.java
* ```
    Quotes() // Default constructor
    Quotes() // Constructor for API calls
    Quotes() // Constructor for file reads
    readFromFile() // Reads from file and uses GSON to convert into a Java object
    readFromAPI() // Reads from Ron Swanson API.
    writeToFile() // Writes to recentquotes.json 
    toString() // Prints desired values from object
* Collaborators: Brandon Hurrington, Kevin Couture
* Instructions: Open main and press run, in the CLI type ./gradlew test, or open test file and press run.