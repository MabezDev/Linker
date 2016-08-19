package com.mabezdev.Controllers;

import com.mabezdev.Constants;
import com.mabezdev.Interfaces.Stoppable;
import com.mabezdev.Models.Link;
import com.mabezdev.Util.Parsers.BookmarkParser;
import com.mabezdev.Util.Parsers.ChomeParser;
import com.sun.deploy.net.protocol.chrome.ChromeURLConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable, Stoppable{

    @FXML
    private MenuBar menuBar;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ListView<Link> linkListView;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane rootPane;

    private WebEngine wEngine;
    private ObservableList<Link> linkArray = FXCollections.observableArrayList();

    private String currentURL; //set this to a homepage that I create in html

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initializeLinkList();

        wEngine = webView.getEngine();
        wEngine.load("http://www.google.com");

        BookmarkParser chrome = new ChomeParser();
        System.out.println("File open: "+chrome.open(Constants.GOOLE_IMPORT_MAC));
        chrome.parseLinks();
    }

    private void initializeLinkList() {
        linkArray.add(new Link("Reddit","http://www.reddit.com"));//load from file or smth
        linkListView.setItems(linkArray);

        linkListView.setCellFactory(new Callback<ListView<Link>, ListCell<Link>>() {
            @Override
            public ListCell<Link> call(ListView<Link> param) {
                ListCell<Link> cell = new ListCell<Link>(){

                    @Override
                    protected void updateItem(Link t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getDisplayName());
                        }
                    }

                };

                return cell;
            }
        });

        linkListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Link>() {

            @Override
            public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue) {
                // Your action here
                System.out.println(newValue.getDisplayName()+" has a url : " + newValue.getUrl());
                openLink(newValue.getUrl());
            }
        });
    }

    private void openLink(String url) {
        if(!url.equals(currentURL)) {
            wEngine.load(url);
            currentURL = url;
        }
    }

    @Override
    public void stop() {
        System.out.println("RootController.stop: Stopping...");
    }
}
