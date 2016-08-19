package com.mabezdev.Util;

import com.mabezdev.Models.Link;
import javafx.collections.ObservableList;

/**
 * Created by Mabez on 19/08/2016.
 */
public class ChomeParser extends BookmarkParser {

    @Override
    public boolean open(String filePath) {
        return false;
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public ObservableList<Link> parseLinks() {
        return null;
    }
}
