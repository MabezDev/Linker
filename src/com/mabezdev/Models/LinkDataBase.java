package com.mabezdev.Models;

import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mabez on 19/08/2016.
 */
public class LinkDataBase implements Serializable{

    /*
        Model of the link database, will have to handle sorting, but for now just going to serialize and observable array list
     */
    private ArrayList<Link> links;

    public LinkDataBase(){

    }

    public void setData(ArrayList<Link> linkArray){
        links = linkArray;
    }

    public ArrayList<Link> getData(){
        return links;
    }
}
