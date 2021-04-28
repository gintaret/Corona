package com.example.corona;

import android.content.Context;
import android.content.Intent;

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
    List<Corona> data;  //tie duomenys, kuriuos perduodame tam adapteriui per konstruktoriu (pagal valstybes)

    public static final String ENTRY="com.corona.coronazp20t.ENTRY";

    // create constructor to initialize context and data sent from SearchActivity
    //sukonstruojame adapterio konstruktoriu, perduodame is SearchActivity langa, kuriame esame, ir sarasa pagal valstybe
    public Adapter(Context context, List<Corona> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder created
    //f-ija onCreateViewHolder sukuria vaizdo laikytuva
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //sukuria sablona kortelems
        View view = inflater.inflate(R.layout.container_corona, parent, false); //naudosime container_corona xml
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;  //pirmas parametras
        Corona current = data.get(position);    //antras onBindViewHolder'io parametras; //pozicija yra elementu indeksas sarase; pvz., jei turime 10 elementu sarase, tai sukurs 10 korteliu ir i kiekv kortele patalpins konkretu irasa su duomenimis
        //uzpildysime kortele duomenimis is saraso
        myHolder.textKeyId.setText(current.getKeyID());
        myHolder.textLastUpdate.setText("Last update: " + current.getLastUpdate());
        myHolder.textConfirmed.setText("Confirmed: " + current.getConfirmed());
        myHolder.textDeaths.setText("Deaths: " + current.getDeaths());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }
    //galimybe spausdinti, kiek is viso turime irasu

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //MyHolder yra vidine klase - 7 temai - kai atspausdinsime visas korteles, galesime ant bet kurios korteles spausti ir atidaryti naujame lange
        TextView textKeyId;
        TextView textLastUpdate;
        TextView textConfirmed;
        TextView textDeaths;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textKeyId = (TextView) itemView.findViewById(R.id.textKeyId);   //viskas imama is konteinerio (visi 4 R.id.)
            textLastUpdate = (TextView) itemView.findViewById(R.id.textLastUpdate);
            textConfirmed = (TextView) itemView.findViewById(R.id.textConfirmed);
            textDeaths = (TextView) itemView.findViewById(R.id.textDeaths);
            itemView.setOnClickListener(this);
        }

        // Click event for all items - ant bet kokios korteles paspaudus, gaus toki pati pranesima (7 temoje galima pakeisti, kad kiekv kortelei butu konkreti info)
        @Override
        public void onClick(View v) {
//            Intent goToNewEntryActivity = new Intent(context, NewEntryActivity.class);
//            int itemPosition = getAdapterPosition();
//            Corona corona = data.get(itemPosition);
//
//            goToNewEntryActivity.putExtra(ENTRY,corona);
//            context.startActivity(goToNewEntryActivity);
            Toast.makeText(context, "You clicked an item", Toast.LENGTH_LONG).show();
        }
    }
}
