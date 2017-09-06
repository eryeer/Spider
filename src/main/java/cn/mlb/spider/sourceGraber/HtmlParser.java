package cn.mlb.spider.sourceGraber;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
			String href = element.attr(attr);
			if (!attrList.contains(href)) {
				attrList.add(href);
			}
		}
		return attrList;
	}
}
