package agents;

import containers.Clientcontainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class Clientagent extends GuiAgent{
	private transient Clientcontainer gui;
	
	@Override
	protected void setup() {
		if(getArguments().length==1) {
			
	gui=(Clientcontainer) getArguments()[0];
	gui.setClientagent(this);
		}
		ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
	addBehaviour(parallelBehaviour);
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage aclMessage=receive();
				if(aclMessage!=null) {
					switch (aclMessage.getPerformative()) {
					case ACLMessage.CONFIRM:
						gui.logMessage(aclMessage);
						break;

					default:
						break;
					}
				
				}
				else block();
				
			}
		});
		
	}
@Override
protected void beforeMove() {
System.out.println("**********");
System.out.println("avant migration");
System.out.println("******");
}
@Override
protected void afterMove() {
	System.out.println("**********");
	System.out.println("apres migration");
	System.out.println("******");
	}

@Override
protected void takeDown() {
	System.out.println("**********");
	System.out.println("im going to die...");
	System.out.println("******");
	}
@Override
public void onGuiEvent(GuiEvent params) {
	 
		String Season=(String) params.getParameter(1);
		String Guest=(String) params.getParameter(1);
		ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
		aclMessage.setContent(Season);
		aclMessage.setContent(Guest);
		aclMessage.addReceiver(new AID("Validation",AID.ISLOCALNAME));
		send(aclMessage);
	}
	
	
}


