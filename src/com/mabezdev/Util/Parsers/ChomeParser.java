package com.mabezdev.Util.Parsers;

import com.mabezdev.Models.Link;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Mabez on 19/08/2016.
 */
public class ChomeParser extends BookmarkParser {

    private File bookmarksFile;
    private JSONParser jsonParser;

    @Override
    public boolean open(String filePath) {
        //open file, and prepare for parsing, returns false if file fails to open
        bookmarksFile = new File(filePath);
        if(bookmarksFile.exists()){
            return true;
        }
        return false;
    }

    @Override
    public boolean close() {
        //clean up
        return false;
    }

    @Override
    public ObservableList<Link> parseLinks() {
        // specific implementation of chrome bookmark data objects
        ObservableList<Link> links = FXCollections.observableArrayList();
        jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(bookmarksFile.getAbsoluteFile()));
            JSONObject roots = (JSONObject) jsonObject.get("roots");
            Iterator<String> iterator = roots.keySet().iterator();
            while(iterator.hasNext()){
                String child = iterator.next();
                if(!child.equals("sync_transaction_version")) {
                    System.out.println("CHILD: " + child);
                    links.addAll(getFromChildren((JSONObject) roots.get(child)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return links;
    }

    private ObservableList<Link> getFromChildren(JSONObject parent){
        ObservableList<Link> links = FXCollections.observableArrayList();
        JSONArray children = (JSONArray) parent.get("children");
        Iterator<JSONObject> iterator = children.iterator();
        while(iterator.hasNext()){
            JSONObject item = iterator.next();
            //System.out.println("Name: "+item.get("name"));
            //System.out.println("URL: "+item.get("url"));
            //System.out.println("TYPE: "+item.get("type"));
            if(item.get("type").equals("folder")){
                System.out.println("Folder detected, grabbing links...");
                links.addAll(getFromChildren(item));
                System.out.println("Done!");
            } else {
                links.add(new Link((String) item.get("name"), (String) item.get("url")));
            }
        }
        return links;
    }
}
