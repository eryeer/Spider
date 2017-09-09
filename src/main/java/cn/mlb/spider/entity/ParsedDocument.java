package cn.mlb.spider.entity;

import java.util.List;

public class ParsedDocument {
	private String title;
	private List<String> contents;

	/**
	 * @param title
	 * @param contents
	 */
	public ParsedDocument(String title, List<String> contents) {
		super();
		this.title = title;
		this.contents = contents;
	}

	/**
	 * 
	 */
	public ParsedDocument() {
		super();

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "ParsedDocument [title=" + title + ", contents=" + contents
				+ "]";
	}

}
