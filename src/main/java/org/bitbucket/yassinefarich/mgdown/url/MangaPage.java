package org.bitbucket.yassinefarich.mgdown.url;

import java.util.List;

/**
 * Created by farich on 24/03/17.
 */
public class MangaPage {

    private List<Integer> chaptersPageSequnce;

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public List<Integer> getChaptersPageSequnce() {
        return chaptersPageSequnce;
    }

    public void setChaptersPageSequnce(List<Integer> chaptersPageSequnce) {
        this.chaptersPageSequnce = chaptersPageSequnce;
    }

    private String pageUrl;


    public MangaPage(List<Integer> chaptersPageSequnce, String pageUrl) {
        this.chaptersPageSequnce = chaptersPageSequnce;
        this.pageUrl = pageUrl;
    }

    @Override
    public String toString() {
        return pageUrl;
    }
}
