package com.hhh.kiznicsw.card;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhh.kiznicsw.R;
import com.hhh.kiznicsw.SearchActivity;
import com.hhh.kiznicsw.SearchcategoryDialog;

public class CategoryListCard extends Card {

	TextView category_item_text;
	LinearLayout category_item_layout;
		
	public CategoryListCard(int layout, String cardName, Context context, int cardId){
		super(layout, cardName, context, cardId);
			
		init();
		
	}

	public void init(){
		category_item_text = (TextView)cardView.findViewById(R.id.category_item_text);
		category_item_layout = (LinearLayout)cardView.findViewById(R.id.category_item_layout);
	}
}
