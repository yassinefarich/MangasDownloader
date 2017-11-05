package com.yfarich.mangasdownloader.url;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * Created by farich on 24/03/17.
 */
public class MangaPage {

	private List<Integer> chaptersPageSequnce;
	private String pageUrl;

	private String pagePath;

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

	public MangaPage(List<Integer> chaptersPageSequnce, String pageUrl) {
		this.chaptersPageSequnce = chaptersPageSequnce;
		this.pageUrl = pageUrl;
		this.pagePath = genPagePath();
	}

	private String genPagePath() {
		List<Integer> ints = Lists.newArrayList(chaptersPageSequnce);
		ints.remove(ints.size() - 1);
		return ints.stream().map(x -> x.toString()).collect(Collectors.joining("/"));

	}

	@Override
	public String toString() {
		return pageUrl;
	}

	public String getPagePath() {
		return pagePath;
	}
}
