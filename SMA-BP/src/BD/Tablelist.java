package BD;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.bson.Document;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by joaquinto on 9/28/17.
 */
public class Tablelist implements Initializable{
    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    private String Season;
    private Integer Guestcount;
    private String Dish;
 
    private int pos;

    @FXML
    private Label status;
    @FXML
    private TableView<Table> table;
    @FXML
    private TableColumn<Table, Integer> id;
    @FXML
    private TableColumn<Table, String> season;
    @FXML
    private TableColumn<Table, Integer> guestcount;
    @FXML
    private TableColumn<Table, String>dish ;
    @FXML
  
    private Button addAttend;

//  create a primary stage object
    Stage primaryStage = new Stage();

//  create an observable list to hold the Attendees object in the Attendees class
    public ObservableList<Table> list;

    public List attend = new ArrayList();


//  create a mongodb connection
    MongoClient mongoClient = new MongoClient(HOST, PORT);

//  create a database name
    MongoDatabase mongoDatabase = mongoClient.getDatabase("BP");

//  create a collection
    MongoCollection coll = mongoDatabase.getCollection("Dishs");
//  call the find all method
    MongoCursor<Document> cursor = coll.find().iterator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        table.setEditable(true);

        try{
            for(int i = 0; i < coll.count(); i++){
                pos = i +1;

                Document doc = cursor.next();
                Season = doc.getString("season");
                Guestcount = doc.getInteger("guestcount");
                Dish = doc.getString("dish");

                attend.add(new Table(Season,Guestcount,Dish));
            }
            list = FXCollections.observableArrayList(attend);


        }
        finally {
//          close the connection
            cursor.close();
        }

//      call the setTable method
        setTable();
    }



    public void addAttendance() throws Exception{
//      get the current window
        Stage stage = (Stage)addAttend.getScene().getWindow();

//      close the current window
        stage.close();

//      load the main class window
        Main mainClass = new Main();

        mainClass.start(primaryStage);
    }

    public void editAttendanceList() {
        Table selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
//          display an error message if no row was selected
            status.setText("Please select a row and perform this action again");
        }
        else{
            Season = selectedItem.getSeason();
            Guestcount = selectedItem.getGuestcount();
            Dish = selectedItem.getDish();
            
//          here i am using the email as my primary key to find each document to update it in the database
            coll.updateOne(eq("Dish", dish), new Document("$set",
                    new Document("Season",season )
                            .append("Guestcount", guestcount)
                            .append("Dish", dish)));
                           
//          call the rePopulateTable method
            rePopulateTable();

//          call the setTable method
            setTable();

//          hide the error message
            status.setText("");
        }

    }


    public void deleteAttendance(){
//      get the selected row
    	Table selectedItem = table.getSelectionModel().getSelectedItem();
        if (selectedItem == null){
//          display an error message
            status.setText("Please select a row and perform this action again");
        }
        else{
//          get the value of the selected email column
            String Dish = selectedItem.getDish();

//          here i am using the email as my primary key to find each document to delete from the database
            coll.deleteOne(eq("Dish", Dish));

//          call the rePopulateTable method
            rePopulateTable();

//          call the setTable method
            setTable();

//          hide the error message
            status.setText("");
        }
    }

    public void setTable(){
//      this makes the table editable
        table.setEditable(true);

//      make firstname column editable with a textfield
        season.setCellFactory(TextFieldTableCell.forTableColumn());

//      gets the new value and calls the setFirstname method
        season.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Table, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Table, String> event) {

                ((Table)event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setSeason(event.getNewValue());

            }
        });

//      make guestcount column editable with a textfield
       
//      gets the new value and calls the setFirstname method
        guestcount.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Table, Integer>>() {
            public void handle(TableColumn.CellEditEvent<Table, Integer> event) {
                ((Table)event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setGuestcount(event.getNewValue());
            }
        });

//      make phone number column editable with a textfield
        dish.setCellFactory(TextFieldTableCell.forTableColumn());

//      gets the new value and calls the setFirstname method
        dish.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<Table, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Table, String> event) {
                ((Table)event.getTableView().getItems().get(event.getTablePosition().getRow()))
                        .setDish(event.getNewValue());
            }
        });

//      set the values of each columns to display on the table
        id.setCellValueFactory(new PropertyValueFactory<Table, Integer>("id"));
        season.setCellValueFactory( new PropertyValueFactory<Table, String>("season"));
        guestcount.setCellValueFactory( new PropertyValueFactory<Table, Integer>("guestcount"));
        dish.setCellValueFactory( new PropertyValueFactory<Table, String>("dish"));
        table.setItems(list);
    }

    private void rePopulateTable(){
//      calls the find all methods from the mongodb database
        MongoCursor<Document> cursor = coll.find().iterator();

//      clears the attend list so that the previous data won't be displayed together with this new ones on the table
        attend.clear();
        try{
//          loop through the database and then populate the list
            for(int i = 0; i < coll.count(); i++){
                pos = i +1;

                Document doc = cursor.next();
                Season = doc.getString("season");
                Guestcount = doc.getInteger("guestcount");
                Dish = doc.getString("dish");
               
                attend.add(new Table( ));
            }
            list = FXCollections.observableArrayList(attend);


        }
        finally {
//          close the connection
            cursor.close();
        }
    }
}
