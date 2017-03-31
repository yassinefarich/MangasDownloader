# README 
Simple command line java batch to download Mangas from various websites .
### Requirment 
* Java 1.8
### How to run 

1. Create a new configuration file according to the webSite ( *see example below* )
2. Run java -jar MangasDownloader.jar configurationFile.properties

### Configuration file template :

    [DownloadInformation]
    DOWNLOAD_URL = http://website/[START_NUM:END_NUM|ZFILLE].jpg
    DOWNLOAD_URL_MODIFICATION = [DOWNLOAD_URL]
    DOWNLOAD_DESTINATION_FOLDER = /home/ubuntu/workspace/Hello/Orange_Yane
    [ParserInformations]
    USE_WEB_SCRAPPING = FALSE
    IMAGE_SRC_XPATH = //*[@id="img-1"]/@src

