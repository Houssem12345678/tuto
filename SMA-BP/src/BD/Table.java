package BD;

import java.util.List;

import com.mongodb.DBCollection;

import agents.Modellinggui;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by joaquinto on 9/28/17.
 */

public class Table {
   private int id;
   private String season;
   private int guestcount;
   private String dish;
   
  
    public Table() {
        this.id = 0; 
        this.season ="";
        this.guestcount =0;
        this.dish ="";
       
    }
    
    public Table(String season, int guestcount,String dish) {
		this.id=id;
		this.season=season;
		this.guestcount=guestcount;
		this.dish=dish;
		
	}

	public Integer getId() {
        return id;
    }

    public String getSeason() {
        return season ;
    }

    public Integer getGuestcount() {
        return guestcount;
    }

    public String getDish() {
        return dish;
    }

   

    public void setSeason(String season) {
        this.season=season;
    }

    public void setGuestcount(int guestcount) {
        this.guestcount=guestcount;
    }

    public void setDish(String dish) {
        this.dish=dish;
    }

	public Modellinggui getItems() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
