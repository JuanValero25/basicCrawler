package com.agileengine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class ClawerAgente {

	public static void navigatingPage(DataForSearch data, String url, final String elementId) throws IOException {

		File input = new File(url);
		Document document = Jsoup.parse(input, "UTF-8", input.getParent());
		Optional<Element> elementFinded =Optional.empty();

		for (Element internalElement : document.getAllElements()) {
			elementFinded = Optional.ofNullable(matchElements(data, internalElement));
			if (elementFinded.isPresent()) {
				reverseCascadePrint(elementFinded.get());
				break;
			}
		}

		if (elementFinded.isPresent()) {
			return;
		}
		document.select("a[href*=html]").forEach(element -> {
			try {
				File otherField = new File(input.getParent() + "\\" + element.attr("href"));
				Document otherDocument = Jsoup.parse(otherField, "UTF-8", input.getParent());
				Optional<Element> otherElementFinded = Optional.ofNullable(otherDocument.getElementById(elementId));
				otherElementFinded.ifPresent(otherElement -> {
					reverseCascadePrint(otherElement);
				});
				if (otherElementFinded.isPresent()) {
					return;
				}
			} catch (Exception e) {

			}
		});

	}

	public static void reverseCascadePrint(Element element) {
		List<String> listaToPrint = new ArrayList<String>();
		while (element.hasParent()) {
			listaToPrint.add(" > " + element.tag());
			element = element.parent();
		}
		Collections.reverse(listaToPrint);
		listaToPrint.forEach(singelElement -> {
			System.out.print(singelElement);
			
		});
		System.out.println("");
	}

	public static Element matchElements(DataForSearch data, Element element) {

		for (Attribute atribute : element.attributes()) {
			if (!StringUtil.isBlank(data.getAtributes().get(atribute.getKey()))  
					&& data.getAtributes().get(atribute.getKey()).equals(atribute.getValue())) {
				return element;
			}
		}

		for (TextNode node : element.textNodes()) {
			for (TextNode innerNode : data.getTextNode()) {
				if (innerNode.getWholeText().trim().equals(node.getWholeText().trim())) {
					return element;
				}
			}
		}

		return null;
	}

	public static DataForSearch loadDataforSearch( String url, String InitialElementId) throws IOException {
			DataForSearch data =null;
			File input = new File(url);
			Document document = Jsoup.parse(input, "UTF-8", input.getParent());
			Optional<Element> elementFinded = Optional.ofNullable(document.getElementById(InitialElementId));
			if (elementFinded.isPresent()) {
				data = new DataForSearch();
				data.setAtributes(elementFinded.get().attributes());
				data.setTextNode(elementFinded.get().textNodes());
			}
		
		return data;
	}

}
