package com.mabezdev.Util.Parsers;

import com.mabezdev.Models.Link;
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
        jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(bookmarksFile.getAbsoluteFile()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // specific implementation of chrome bookmark data objects
        return null;
    }
}
