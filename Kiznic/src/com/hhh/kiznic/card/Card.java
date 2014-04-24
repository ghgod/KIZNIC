package com.hhh.kiznic.card;

import android.content.Context;
import android.view.View;

public class Card {
	protected View cardView;
	String cardName;
	int cardId;
	protected Context context;
	
	public Card(int layout, String cardName, Context context, int cardId){
		this.cardName = cardName;
		this.context = context;
		this.cardId = cardId;
		this.cardView = View.inflate(context, layout, null);
	}
	
	public View getCardView(){
		return cardView;
	}
	
	public void setCardView(View cardView){
		this.cardView = cardView;
	}
	
	public String getCardName(){
		return cardName;
	}
	
	public void setCardName(String cardName){
		this.cardName = cardName;
	}
	
	public void setListener(){
		
	}
}
