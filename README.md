# Engineer-calculator

An engineer calculator written on Java with use JavaFX. This calculator has some mathematical functions, 
constants and calculating of mathematical limits.

![audio-player](https://github.com/Chukak/engineer-calculator/blob/main/engineer-calculator.png)

## Building from sources

### Command line

Download the archive with [sources](https://github.com/Chukak/engineer-calculator/releases). 
Then run the following command:
```bash
mvn compile
mvn exec:java -Dexec.mainClass="calc.math.Main" # run application
```

### Intellij IDEA
Download the archive with [sources](https://github.com/Chukak/engineer-calculator/releases).
Open `File -> Project structure` and download javafx dependencies (see `pom.xml`). 
Then open `Run -> Edit configurations...` and add `VM options`:
```bash
--module-path
/path/to/javafx-sdk # or openjfx on linux
--add-modules javafx.controls,javafx.graphics,javafx.base,javafx.fxml,javafx.web
```


## Packages

See this site [page](https://github.com/Chukak/engineer-calculator/packages/666291)

## Usage

Run the calculator and see the help.