package com.yfarich.mangasdownloader.functions.impl.cloudflaredownload.http;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Credits :
 *      The source code of this class has been adapted from https://github.com/Maximvdw/SpigotSite ,
 *      I am grateful to these developers :)
 */

public class HTTPUnitRequest {
    private WebClient webClient = null;
    private Cache clientCache = new Cache();


    public void initialize() {
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setDownloadImages(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setTimeout(150000);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.setJavaScriptTimeout(100);
        webClient.setCache(clientCache);

        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    }

    public HTTPDownloadResponse downloadFile(String url, Map<String, String> cookies) {
        try {
            WebRequest wr = new WebRequest(new URL(url), HttpMethod.GET);
            for (Map.Entry<String, String> entry : cookies.entrySet())
                webClient.getCookieManager().addCookie(new Cookie("spigotmc.org", entry.getKey(), entry.getValue()));
            InputStream stream = null;
            Page page = webClient.getPage(wr);
            if (page instanceof HtmlPage)
                if (((HtmlPage) page).asXml().contains("DDoS protection by CloudFlare")) {
                    // DDOS protection
                    try {
                        Thread.sleep(9000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();

                    }

                }
            URL outputURL = webClient.getCurrentWindow().getEnclosedPage().getUrl();
            stream = webClient.getCurrentWindow().getEnclosedPage().getWebResponse().getContentAsStream();
            return new HTTPDownloadResponse(stream, outputURL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HTTPResponse get(String url, Map<String, String> cookies, Map<String, String> params) {
        HTTPResponse response = new HTTPResponse();

        try {
            WebRequest wr = new WebRequest(new URL(url), HttpMethod.GET);

            List<NameValuePair> paramsPair = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet())
                paramsPair.add(new NameValuePair(entry.getKey(), entry.getValue()));
            wr.setRequestParameters(paramsPair);
            Page page = webClient.getPage(wr);
            if (page instanceof HtmlPage)
                if (((HtmlPage) page).asXml().contains("DDoS protection by CloudFlare")) {
                    // DDOS protection
                    try {
                        Thread.sleep(9000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if (webClient.getPage(wr) instanceof UnexpectedPage) {
                        UnexpectedPage unexpectedPage = webClient.getPage(wr);
                        System.out.println("UNEXPECTED PAGE: " + unexpectedPage.getWebResponse().getStatusMessage());
                    } else
                        page = webClient.getPage(wr);
                }
            Map<String, String> cookiesMap = new HashMap<String, String>();
            for (Cookie cookie : webClient.getCookieManager().getCookies()) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }

            Document doc = Jsoup.parse(((HtmlPage) page).asXml());
            response.setDocument(doc);
            response.setHtml(doc.html());

            Map<String, String> resultCookies = new HashMap<String, String>();
            for (Cookie cookie : webClient.getCookieManager().getCookies()) {
                resultCookies.put(cookie.getName(), cookie.getValue());
            }
            response.setCookies(resultCookies);
            webClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public HTTPResponse post(String url, Map<String, String> cookies, Map<String, String> params) {

        HTTPResponse response = new HTTPResponse();

        try {
            WebRequest wr = new WebRequest(new URL(url), HttpMethod.POST);

            List<NameValuePair> paramsPair = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet())
                paramsPair.add(new NameValuePair(entry.getKey(), entry.getValue()));
            wr.setRequestParameters(paramsPair);
            Page page = webClient.getPage(wr);
            if (page instanceof HtmlPage)
                if (((HtmlPage) page).asXml().contains("DDoS protection by CloudFlare")) {
                    // DDOS protection
                    try {
                        Thread.sleep(9000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    wr = new WebRequest(new URL(url), HttpMethod.POST);
                    paramsPair = new ArrayList<NameValuePair>();
                    for (Map.Entry<String, String> entry : params.entrySet())
                        paramsPair.add(new NameValuePair(entry.getKey(), entry.getValue()));
                    wr.setRequestParameters(paramsPair);

                    if (webClient.getPage(wr) instanceof UnexpectedPage) {
                        UnexpectedPage unexpectedPage = webClient.getPage(wr);
                        System.out.println("UNEXPECTED PAGE: " + unexpectedPage.getWebResponse().getStatusMessage());
                    } else
                        page = webClient.getPage(wr);
                }

            Map<String, String> cookiesMap = new HashMap<String, String>();
            for (Cookie cookie : webClient.getCookieManager().getCookies()) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }
            if (page instanceof HtmlPage) {
                Document doc = Jsoup.parse(((HtmlPage) page).asXml());
                response.setDocument(doc);
                response.setHtml(doc.html());
            } else {
                response.setHtml(page.getWebResponse().getContentAsString());
                Document doc = Jsoup.parse(response.getHtml());
                response.setDocument(doc);
            }

            Map<String, String> resultCookies = new HashMap<String, String>();
            for (Cookie cookie : webClient.getCookieManager().getCookies()) {
                resultCookies.put(cookie.getName(), cookie.getValue());
            }
            response.setCookies(resultCookies);
            webClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
