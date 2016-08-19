package com.mabezdev.Util;

import com.mabezdev.Models.Link;
import javafx.collections.ObservableList;

/**
 * Created by Mabez on 19/08/2016.
 */
public abstract class BookmarkParser {

    public abstract boolean open(String filePath);

    public abstract boolean close();

    public abstract ObservableList<Link> parseLinks();
}
