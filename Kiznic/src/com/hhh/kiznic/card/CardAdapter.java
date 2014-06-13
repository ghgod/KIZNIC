package com.hhh.kiznic.card;

import java.util.ArrayList;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

public class CardAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Card> cardList = new ArrayList<Card>();
	
	public CardAdapter(Context context){
		this.context = context;
	}
	
	public void addItem(Card inputCard){
		cardList.add(inputCard);
	}
	
	public void removeAll(){
		cardList.removeAll(cardList);
	}
	
	@Override
	public int getCount(){
		return cardList.size();
	}
	
	@Override
	public Card getItem(int position){
		return cardList.get(position);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ViewHolder holder;
		
		Card card = cardList.get(position);
		return card.getCardView();
	}
	
	private class ViewHolder{
		public LinearLayout linearLayout;
	}
}
