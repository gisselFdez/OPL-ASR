package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import util.Summary;

/**
 * 
 * @author Ana Gissel
 *
 */
public class Comparator {

	/**
	 * Returns 0 : No changes, 1 : Repaired Test, -1 : Regression
	 * @return
	 */
	public int compareResults(HashMap<String, List<String>> initialFailures, HashMap<String, List<String>> newFailures){
		Boolean anyTestRepaired = false;

		//EQUAL
		if(initialFailures.equals(newFailures))
			return 0;
		//REPAIRED
		else if(newFailures.isEmpty()){
			Iterator it = initialFailures.entrySet().iterator();
            while (it.hasNext()) {
            	Map.Entry e = (Map.Entry)it.next();
            	List<String> tests =(List<String>)e.getValue();
            	Summary.addRepairedTest(tests.size());
            }
			return 1;
		}
		else{
			//verify if there is any REGRESSION
			Iterator it = newFailures.entrySet().iterator();
            while (it.hasNext()) {
            	Map.Entry e = (Map.Entry)it.next();
            	//verify if a class is on the initial list of failures
            	List<String> tests = initialFailures.get(e.getKey());
            	if(tests!=null){
            		List<String> newTests = (List<String>)e.getValue();
            		for(String newTest: newTests){
            			if(!tests.contains(newTest))
            				return -1;
            		}
            	}
            	else
            		return -1;            	
            }
            //verify if there is any test REPAIRED
            Iterator ite = initialFailures.entrySet().iterator();
            while (ite.hasNext()) {
            	Map.Entry e = (Map.Entry)ite.next();
            	List<String> tests =newFailures.get(e.getKey());
            	if(tests==null){
            		anyTestRepaired = true;
            		Summary.addRepairedTest(initialFailures.get(e.getKey()).size());
            	}
            	else{
            		List<String> inTests = (List<String>)e.getValue();
            		for(String test: inTests){
            			if(!tests.contains(test)){
            				anyTestRepaired = true;
            				Summary.addRepairedTest();
            			}            				
            		}
            	}
            }
		}	
		if(anyTestRepaired)
			return 1;
		else
			return 0;
	}
	
}
