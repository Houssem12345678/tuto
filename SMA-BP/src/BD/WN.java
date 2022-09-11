package BD;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class WN implements Initializable{
    private final static String HOST = "localhost";
    private final static int PORT = 27017;
    @FXML
    private TextField SEASON;
    @FXML
    public ComboBox<String> Guestcount;
    @FXML
    private Label Dish;
    @FXML
    private Button att;

//  create an observable list to hold the content of the combobox
    ObservableList<String> list  = FXCollections.observableArrayList("1", "2","3", "4","5", "6","7", "8","9", "10");

//  create a primary stage object
    Stage primaryStage = new Stage();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//      set the items of the combobox
        Guestcount.setItems(list);
    }

    public void getFieldValues(ActionEvent event){
        try{
//          create a connection to mongodb server
            MongoClient mongoClient = new MongoClient(HOST, PORT);

//          create a database name
            MongoDatabase mongoDatabase = mongoClient.getDatabase("BP");

//          create a collection
            MongoCollection coll = mongoDatabase.getCollection("Dish");

//          get the values of the fields
            Document doc = new Document("Season", SEASON.getText())
                    .append("Guestcount", Guestcount.getValue())
                    .append("Dish", Dish.getText());
                   

//          save the document
            coll.insertOne(doc);

//          display a success message


//          set the fields to null or empty
            SEASON.setText("");
            Guestcount.setValue("null");
            Dish.setText("");
           
        }
        catch (Exception e){
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
//          display the error message
           
        }
    }

    public void goToAttendanceList() throws Exception{
//      get the current window
        Stage stage = (Stage)att.getScene().getWindow();

//      close the current window
        stage.close();

//      load the attendance list window
        Parent root = FXMLLoader.load(getClass().getResource("BD/List.fxml"));
        primaryStage.setTitle(" List");
        primaryStage.setScene(new Scene(root, 747, 400));
        primaryStage.show();

    }


}
