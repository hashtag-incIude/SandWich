package com.ak.sandwich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.ak.sandwich.model.Sandwich;
import com.ak.sandwich.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;
    ImageView ingredientsIv;
    private TextView tv_also_known, tv_origin, tv_description, tv_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        tv_also_known = findViewById(R.id.also_known_tv);
        tv_origin = findViewById(R.id.origin_tv);
        tv_description= findViewById(R.id.description_tv);
        tv_ingredients= findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent== null) {
            closeOnError();
        }


        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich= JsonUtils.parseSandwichJson(json);
        if (sandwiches == null) {
            closeOnError();
            return;
        }

        populateUI();
Picasso.get().load(sandwich.getImage()).placeholder(R.drawable.imaaa)
        .error(R.drawable.ic_error_outline_black_24dp)
                .into(ingredientsIv);

//        Picasso.with(this)
//                .load(sandwich.getImage())
//                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI() {
        if (sandwich.getPlaceOfOrigin().isEmpty()){
            tv_origin.setText(R.string.no_data);
        }else {
            tv_origin.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getAlsoKnownAs().isEmpty()){
            tv_also_known.setText(R.string.no_data);
        }else {
            tv_also_known.setText(listModel(sandwich.getAlsoKnownAs()));
        }



        tv_description.setText(sandwich.getDescription());
        tv_ingredients.setText(listModel(sandwich.getIngredients()));

    }
    public StringBuilder listModel(List<String> list){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0;i<list.size();i++){
            stringBuilder.append(list.get(i)).append("\n");
        }
        return stringBuilder;
    }


}