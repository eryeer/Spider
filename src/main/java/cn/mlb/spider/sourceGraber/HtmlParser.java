package cn.mlb.spider.sourceGraber;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.mlb.spider.entity.ParsedDocument;

/**
 * 这是一个HTML解析类,用于从html文档中提取所需的标签元素,属性或文档内容
 * 
 * @author eryeer
 * 
 */
public class HtmlParser {
	/**
	 * 这个方法用于提取HTML标签元素中的属性,传入html文本,选择器和所需属性,返回属性内容的集合
	 * 
	 * @param html
	 *            全网页代码
	 * @param selector
	 *            选择器 写法与css选择器相同
	 * @param attr
	 *            元素被提取的属性
	 * @return 属性集合
	 */
	public List<String> getAttrList(String html, String selector, String attr) {
		// 创建空的属性集合
		List<String> attrList = new ArrayList<String>();
		// 解析html文本
		Document document = Jsoup.parse(html);
		// 通过选择器获取元素
		Elements elements = document.select(selector);
		for (Element element : elements) {
			// 获取元素中的属性
			String content = element.attr(attr);
			if (!attrList.contains(content)) {
				attrList.add(content);
			}
		}
		return attrList;
	}

	public List<String> getTextList(String html, String selector) {
		List<String> textList = new ArrayList<String>();
		Document document = Jsoup.parse(html);
		// 通过选择器获取元素
		Elements elements = document.select(selector);
		for (Element element : elements) {
			// 获取元素中的属性
			String text = element.text();
			if (!textList.contains(text)) {
				textList.add(text);
			}
		}
		return textList;
	}

	public ParsedDocument getAttrDocument(String html, String selector,
			String attr, String title) {
		List<String> attrList = getAttrList(html, selector, attr);
		return new ParsedDocument(title, attrList);
	}

	public ParsedDocument getTextDocument(String html, String selector,
			String title) {
		List<String> textList = getTextList(html, selector);
		return new ParsedDocument(title, textList);
	}
}
