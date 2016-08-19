package com.mabezdev.Util;

import com.mabezdev.Models.Link;
import com.mabezdev.Models.LinkDataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Mabez on 19/08/2016.
 */
public class LinkDataBaseHandler {

    public static void saveDataBase(String path, ObservableList<Link> data) throws IOException{
        LinkDataBase dataBase = new LinkDataBase();
        dataBase.setData(new ArrayList<>(data));
        FileOutputStream out = new FileOutputStream(path);
        ObjectOutputStream outObj = new ObjectOutputStream(out);
        outObj.writeObject(dataBase);
        outObj.close();
        out.close();
    }

    public static ObservableList<Link> loadDataBase(String path) throws IOException, ClassNotFoundException{
        FileInputStream inputStream = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(inputStream);
        LinkDataBase dataBase = (LinkDataBase) in.readObject();
        return FXCollections.observableArrayList(dataBase.getData());
    }
}
