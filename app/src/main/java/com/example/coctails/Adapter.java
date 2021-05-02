package com.example.coctails;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Coctails> drinks;  //tie duomenys, kuriuos perduodame tam adapteriui per konstruktoriu (pagal pavadinimus)

    // create constructor to initialize context and data sent from SearchActivity
    //sukonstruojame adapterio konstruktoriu, perduodame is SearchActivity konteksta (langa, kuriame esame), ir sarasa pagal kokteilius
    public Adapter(Context context, List<Coctails> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.drinks = data;
    }

    // Inflate the layout when ViewHolder created
    //f-ija onCreateViewHolder sukuria vaizdo laikytuva
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //sukuria sablona kortelems
        View view = inflater.inflate(R.layout.container_coctails, parent, false); //naudosime container_coctails xml
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {    //apjungia konteineri, kuri mes sukuriame
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;  //pirmas parametras
        Coctails current = drinks.get(position);    //antras onBindViewHolder'io parametras; //pozicija yra elementu indeksas sarase; pvz., jei turime 10 elementu sarase, tai sukurs 10 korteliu ir i kiekv kortele patalpins konkretu irasa su duomenimis
        //uzpildysime kortele (myHolder) duomenimis is saraso
        myHolder.textName.setText(current.getName());
        myHolder.textCategory.setText("Category: " + current.getCategory());
        myHolder.textAlcoholic.setText("Alcoholic: " + current.getAlcoholic());
        myHolder.textGlass.setText("Glass type: " + current.getGlass());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return drinks.size();
    }
    //galimybe spausdinti, kiek is viso turime irasu

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //MyHolder yra vidine klase - 7 temai - kai atspausdinsime visas korteles, galesime ant bet kurios korteles spausti ir atidaryti naujame lange daugiau duomenu
        TextView textName;
        TextView textCategory;
        TextView textAlcoholic;
        TextView textGlass;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textName= (TextView) itemView.findViewById(R.id.textName);   //viskas imama is konteinerio (visi 4 R.id.)
            textCategory = (TextView) itemView.findViewById(R.id.textCategory);
            textAlcoholic = (TextView) itemView.findViewById(R.id.textAlcoholic);
            textGlass = (TextView) itemView.findViewById(R.id.textGlass);
            itemView.setOnClickListener(this);
        }

        // Click event for all items - ant bet kokios korteles paspaudus, gaus toki pati pranesima (7 temoje galima pakeisti, kad kiekv kortelei butu konkreti info)
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "You clicked an item", Toast.LENGTH_LONG).show();
        }
    }
}
