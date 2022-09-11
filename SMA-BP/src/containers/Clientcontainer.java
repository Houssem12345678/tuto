package containers;

import agents.Clientagent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Clientcontainer extends Application {
	protected Clientagent Clientagent;
	ObservableList<String>observableList;
	public static void main(String[] args) {
	launch(args);
	}
	public void startContainer() {
		try {
			Runtime runtime=Runtime.instance();
			ProfileImpl profileImpl=new ProfileImpl();
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "Localhost");
			AgentContainer container=runtime.createAgentContainer(profileImpl);
			AgentController agentController=container.createNewAgent("Client","agents.Clientagent",new Object[] {this});
			agentController.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		startContainer();
		primaryStage.setTitle("Client");
		HBox hbox=new HBox();
		hbox.setPadding(new Insets(10));
		hbox.setSpacing(10);
		ComboBox<String> comboBox=new ComboBox<String>();
		comboBox.setItems(FXCollections.observableArrayList("Fall","Winter","Summer","Spring"));
		ComboBox<String> comboBox1=new ComboBox<String>();
		comboBox1.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10"));
		Button buttonDemander=new Button("Demander");
		hbox.getChildren().addAll(buttonDemander,comboBox,comboBox1);
		VBox vBox=new VBox();vBox.setPadding(new Insets(10));
		observableList=FXCollections.observableArrayList();
		ListView<String>listeViewMessages=new ListView<String>(observableList);
		vBox.getChildren().add(listeViewMessages);
		BorderPane borderPane=new BorderPane();
		borderPane.setTop(hbox);
		borderPane.setCenter(vBox);
		Scene scene=new Scene(borderPane,600,400);
		primaryStage.setScene(scene);
				primaryStage.show(); 
				buttonDemander.setOnAction(evt->{
					String Season=comboBox.getValue();
					String Guest=comboBox1.getValue();
					GuiEvent event=new GuiEvent(this,1);
					event.addParameter(Season);
					event.addParameter(Guest);
					Clientagent.onGuiEvent(event);
					
				});
	}
	public void setClientagent(Clientagent clientagent) {
		this.Clientagent = clientagent;
	}


	public void logMessage(ACLMessage aclMessage) {
		Platform.runLater(()->{
			observableList.add(" Demander " +aclMessage.getContent()+", "+aclMessage.getSender().getName());
		});
	}

}
