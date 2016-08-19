package com.mabezdev.Controllers;

import com.mabezdev.Constants;
import com.mabezdev.Interfaces.Stoppable;
import com.mabezdev.Models.Link;
import com.mabezdev.Util.LinkDataBaseHandler;
import com.mabezdev.Util.Parsers.BookmarkParser;
import com.mabezdev.Util.Parsers.ChomeParser;
import com.sun.deploy.net.protocol.chrome.ChromeURLConnection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable, Stoppable{

    @FXML
    private SplitPane splitPane;

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<Link> linkListView;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane rootPane;

    private WebEngine wEngine;
    private ObservableList<Link> linkArray = FXCollections.observableArrayList();

    private String currentURL = "http://www.google.com"; //set this to a homepage that I create in html

    private final FileChooser fileChooser = new FileChooser();

    //instead of one pane, add a tab system?
    // add loading bar or something to indicate loading

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeLinkList();
        initializeSearch();

        wEngine = webView.getEngine();
        wEngine.load(currentURL);

        //BookmarkParser chrome = new ChomeParser();
        //chrome.open(Constants.GOOLE_IMPORT_MAC);
        //linkArray.addAll(chrome.parseLinks());
    }

    private void initializeSearch() {
        searchBar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.equals("")){
                    ObservableList<Link> searched = FXCollections.observableArrayList();
                    for(Link linkData : linkArray){
                        if(linkData.getDisplayName().toLowerCase().contains(newValue.toLowerCase())){
                            searched.add(linkData);
                        }
                    }
                    if(searched.size() > 0){
                        //System.out.println("Search yielded "+searched.size()+ " result(s)");
                        linkListView.setItems(null);
                        linkListView.setItems(searched);

                    } else {
                        linkListView.setItems(null);//show no items
                    }
                    //search through all all names in array and match them using a letter difference algorithm
                    // set the search data as the listviews data
                } else {
                    // if search is nothing display all like normal
                    linkListView.setItems(null);
                    linkListView.setItems(linkArray);

                }

            }
        });
    }

    private void initializeLinkList() {
        linkArray.add(new Link("Reddit","http://www.reddit.com"));
        linkListView.setItems(linkArray);

        linkListView.setCellFactory(new Callback<ListView<Link>, ListCell<Link>>() {
            @Override
            public ListCell<Link> call(ListView<Link> param) {
                ListCell<Link> cell = new ListCell<Link>(){

                    @Override
                    protected void updateItem(Link t, boolean bln) {
                        if (t != null) {
                            setText(t.getDisplayName());
                            super.updateItem(t,bln);
                        } else {
                            setText(null);
                        }

                    }

                };

                return cell;
            }
        });

        linkListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Link>() {

            @Override
            public void changed(ObservableValue<? extends Link> observable, Link oldValue, Link newValue) {
                System.out.println(newValue.getDisplayName()+" has a url : " + newValue.getUrl());
                openLink(newValue.getUrl());
            }
        });
    }



    @FXML
    private void openDB(){
        File toOpen = fileChooser.showOpenDialog((rootPane.getScene().getWindow()));
        System.out.println("Opening DB from: "+toOpen.getAbsolutePath());
        ObservableList<Link> links = null;
        try {
            links = LinkDataBaseHandler.loadDataBase(toOpen.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(links!=null){
            linkArray.addAll(links);
        }
    }

    @FXML
    private void saveDB(){
        File toSave = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
        System.out.println("Saving DB to: "+toSave.getAbsolutePath());
        try {
            LinkDataBaseHandler.saveDataBase(toSave.getAbsolutePath() + ".ldb", linkArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void importLinks(){
        // open dialog choose where to import from (Chrome, Firefox, Safari, or open file chooser dialog)
    }

    @FXML
    private void exportLinks(){
        //not very high priority, for portabilty and maybe cloud storage
    }

    /*
        WebView methods
     */

    private void openLink(String url) {
        if(!url.equals(currentURL)) {
            wEngine.load(url);
            currentURL = url;
        }
    }

    @FXML
    private void goForward(){
        final WebHistory history = wEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        int currentPageIndex = history.getCurrentIndex();
        Platform.runLater(() ->
        {
            history.go(entries.size() > 1
                    && currentPageIndex < entries.size() - 1
                    ? 1
                    : 0);
        });
    }

    @FXML
    private void goBack(){
        final WebHistory history = wEngine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();
        int currentPageIndex = history.getCurrentIndex();
        Platform.runLater(() ->
        {
            history.go(entries.size() > 1
                    && currentPageIndex > 0
                    ? -1
                    : 0);
        });
    }

    @FXML
    private void refresh(){
        wEngine.reload();
    }

    @FXML
    private void exit(){
        Platform.exit();
    }

    @Override
    public void stop() {
        System.out.println("RootController.stop: Stopping...");
    }
}
