package com.agileengine;

import java.util.List;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.TextNode;

public class DataForSearch {
	
	private Attributes atributes;
	private List<TextNode> textNode;
	
	
	public DataForSearch() {
		super();
	}


	public Attributes getAtributes() {
		return atributes;
	}


	public void setAtributes(Attributes atributes) {
		this.atributes = atributes;
	}


	public List<TextNode> getTextNode() {
		return textNode;
	}


	public void setTextNode(List<TextNode> textNode) {
		this.textNode = textNode;
	}
	
	
	
}
