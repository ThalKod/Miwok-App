package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by Thal Marc on 1/12/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter<Word> {

    private int color;

    public CustomArrayAdapter(Context context, ArrayList<Word> word, int color){
        super(context,0,word);
        this.color = color;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
            }

        TextView textMIwok = (TextView) convertView.findViewById(R.id.text_view1);
        TextView textEnglish = (TextView) convertView.findViewById(R.id.text_view2);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_view);

        textEnglish.setText(word.getDefaultTranslation());
        textMIwok.setText(word.getmiwokTranslation());



        image.setImageResource(word.getImageID());

        if(word.hasImage() == 0){
            image.setVisibility(View.GONE);
        }

        LinearLayout layout = (LinearLayout) convertView.findViewById(R.id.linear_layout);
        layout.setBackgroundResource(color);
        return convertView;
    }
}