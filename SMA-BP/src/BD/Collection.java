package BD;
import com.mongodb.*;
public class Collection {
	public static void main(String[]args) {
		try {
			MongoClient mongo1=new MongoClient("localhost", 27017);
			DB db1=mongo1.getDB("BP");
			DBCollection coll=db1.createCollection("Dish", null);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
