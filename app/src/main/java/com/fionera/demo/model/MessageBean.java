package com.fionera.demo.model;

public class MessageBean{

	private String name;
	private String date;
	private String text;
	private int type;

	public MessageBean(String name, String date, String text, int type){
		super();
		this.name = name;
		this.date = date;
		this.text = text;
		this.type = type;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getDate(){
		return date;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}

	public int getType(){
		return type;
	}

	public void setType(int type){
		this.type = type;
	}
}
