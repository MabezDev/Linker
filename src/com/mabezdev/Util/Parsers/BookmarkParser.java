package com.mabezdev.Util.Parsers;

import com.mabezdev.Models.Link;
import javafx.collections.ObservableList;

/**
 * Created by Mabez on 19/08/2016.
 */
public abstract class BookmarkParser {

    /*
        Abstract class for easy implementation of other parsers from other bookmark types and link files (like csv)
     */

    public abstract boolean open(String filePath);

    public abstract boolean close();

    public abstract ObservableList<Link> parseLinks();
}
