package agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Operatorgui extends Application {
	protected OperatorAgent operatorAgent;
	protected ObservableList<String>observableList;
	
	
	public static void main(String[] args) {
	launch(args);	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("operatoragent");
		BorderPane borderPane=new BorderPane();
		VBox vBox=new VBox();
		observableList=FXCollections.observableArrayList();
	ListView<String>listView=new ListView<String>(observableList);
	Button buttonafficher=new Button("Afficher");
	vBox.getChildren().addAll(listView,buttonafficher);
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
		AgentController agentController=agentContainer.createNewAgent("Operator","agents.OperatorAgent",new Object[] {this});
		agentController.start();
		agentContainer.start();
	}
public void logoMessage(ACLMessage aclMessage) {
	Platform.runLater(()->{
		observableList.add(aclMessage.getContent()+", "+aclMessage.getSender().getName());
	});
}
}
