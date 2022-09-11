package agents;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.List;

public class Validationagent extends GuiAgent {
	protected Validationgui gui;
	protected AID[]Modelling;
	
	@Override
	protected void setup() {
		if(getArguments().length==1) {
			gui=(Validationgui)getArguments()[0];
			gui.validationagent=this;
		}
	ParallelBehaviour parallelBehaviour=new ParallelBehaviour();
	addBehaviour(parallelBehaviour);
	parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
		private int counter=0;
		private java.util.List<ACLMessage> replies=new ArrayList<ACLMessage>();
		@Override
		public void action() {
			MessageTemplate messageTemplate=
					MessageTemplate.or(
							MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
							MessageTemplate.or(
									MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
									MessageTemplate.or(
											MessageTemplate.MatchPerformative(ACLMessage.AGREE),
													MessageTemplate.MatchPerformative(ACLMessage.REFUSE)
													)));
			ACLMessage aclMessage=receive(messageTemplate);
			if(aclMessage!=null) {
				switch (aclMessage.getPerformative()) {
				
				case ACLMessage.REQUEST:
					String livre=aclMessage.getContent();
					ACLMessage aclMessage3=new ACLMessage(ACLMessage.CONFIRM);
					aclMessage3.setContent(livre);
					
					for(AID aid:Modelling) {
						aclMessage3.addReceiver(aid);
					}
					send(aclMessage3);
					
					break;
					
case ACLMessage.AGREE:
	ACLMessage aclMessage2=new ACLMessage(ACLMessage.CONFIRM);
	aclMessage2.addReceiver(new AID("Client",AID.ISLOCALNAME));
	aclMessage2.setContent(aclMessage.getContent());
	send(aclMessage2);
	break;
case ACLMessage.REFUSE:

	
	break;
				default:
					break;
				}
				String Season=aclMessage.getContent();
				gui.logoMessage(aclMessage);
				ACLMessage reply=aclMessage.createReply();
				reply.setContent(" ok pour " +aclMessage.getContent());
				send(reply);
				ACLMessage aclMessage2=new ACLMessage(ACLMessage.CFP);
				aclMessage2.setContent(Season);
				aclMessage2.addReceiver(new AID("Operator",AID.ISLOCALNAME));
				send(aclMessage2);
			}
			else block();
		}
	});
	
	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
