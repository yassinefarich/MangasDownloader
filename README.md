# README 
Simple command line java batch to download Mangas from various websites .

### Features

- Generate pages links using sequence defined on URL and number of leftPads
- Multi threads scrapping and downloading
- Scrapping pages using XPATH expression
- Scrapping from sites that use cloudflare
- Downloading and organizing images 


### Requirment 

- Java 1.8

### How to run 

- Create a new configuration file or adapt the default one according to the webSite ( *see example below* )
- Compile the project using Maven

        mvn -f pom.xml clean compile assembly:single
        
- Run the single generated JAR

        java -jar MangasDownloader-1-jar-with-dependencies.jar yourPropertiesFile.properties
    

### Configuration file template :

        [DownloadInformation]
        DOWNLOAD_URL = http://www.mangapanda.com/hayate-the-combat-butler/[1:20|1]/[1:40|1]
        DOWNLOAD_URL_MODIF = [DOWNLOAD_URL]
        DOWNLOAD_PATH = /home/user/Mangas

        [ParserInformations]
        USE_SCRAPPING = TRUE
        PARSER_XPATH = //img[@id="img"]/@src
        # "CLOUD_FLARE_STRATEGY" for websites that use CloudFlare
        DOWNLOAD_STRATEGY = SIMPLE_STRATEGY

        [MultiProcessingProperties]
        NUMBER_OF_THREADS = 30
