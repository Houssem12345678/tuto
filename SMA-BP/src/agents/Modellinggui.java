package agents;

import java.beans.EventHandler;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import BD.Collection;
import BD.Table;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Modellinggui extends Application {
	
	protected ModellingAgent modellingAgent;
	protected ObservableList<String>observableList;
	private Button buttonAjouter;
	private Button buttonModifier;
	private Button buttonSupprimer;
	
	  private static TextField season;
	  private static TextField Guestcount;
	  private static TextField Dish;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("ModellingAgent");
		BorderPane borderPane=new BorderPane();
		
	TableView table =new TableView<Table>();
	TableColumn seasonNameColumn =new TableColumn<Table,String>("Season");
	seasonNameColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("Season"));
	TableColumn guestcountNameColumn =new TableColumn<Table,Integer>("Guestcount");
	guestcountNameColumn.setCellValueFactory(new PropertyValueFactory<Table,Integer>("Guestcount"));
	TableColumn dishNameColumn =new TableColumn<Table,String>("Dish");
	dishNameColumn.setCellValueFactory(new PropertyValueFactory<Table,String>("Dish"));
	table.getColumns().add(seasonNameColumn);
	table.getColumns().add(guestcountNameColumn);
	table.getColumns().add(dishNameColumn);
	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	HBox hBox=new HBox();
	hBox.setPadding(new Insets(0,10,10,10));
	hBox.setSpacing(10);
	 buttonAjouter=new Button("Ajouter");
	
	
	 
	 
	 buttonModifier=new Button("Modifier");
	 buttonSupprimer=new Button("Supprimer");
	 season=new TextField("");
	 season.setPromptText("Season");
	 season.setTooltip(new Tooltip("Enter the name of season"));
	 Guestcount=new TextField("");
	 Guestcount.setPromptText("Guestcount");
	 Guestcount.setTooltip(new Tooltip("Enter the number of guests"));
	 Dish=new TextField("");
	 Dish.setPromptText("Dish");
	 Dish.setTooltip(new Tooltip("Enter the name of dish"));
	hBox.getChildren().addAll(buttonAjouter,buttonModifier,buttonSupprimer,season,Guestcount,Dish);
		VBox vBox=new VBox();
	vBox.getChildren().addAll(table);
	borderPane.setBottom(hBox);
	borderPane.setCenter(vBox);
	Scene scene=new Scene(borderPane,400,300);
	primaryStage.setScene(scene);
	primaryStage.show();
	}
	private void startContainer() throws Exception {
		Runtime runtime=Runtime.instance();
		ProfileImpl profileImpl=new ProfileImpl();
		profileImpl.setParameter(ProfileImpl.MAIN_HOST,"Localhost");
		AgentContainer agentContainer=runtime.createAgentContainer(profileImpl);
		AgentController agentController=agentContainer.createNewAgent("Modelling","agents.ModellingAgent",new Object[] {this});
		agentController.start();
		agentContainer.start();
	}
	
public void logoMessage(ACLMessage aclMessage) {
	Platform.runLater(()->{
		observableList.add(aclMessage.getContent()+", "+aclMessage.getSender().getName());
	});
	
}

}
