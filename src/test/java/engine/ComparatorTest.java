package engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import util.Summary;

public class ComparatorTest {

	@Test
	public void testEquals() {
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);		
		newFailures.put("class", tests);
		
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==0);
	}
	
	@Test
	public void testRegression1(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		
		List<String> newTests = new ArrayList<String>();
		newTests.add("test1");
		newTests.add("test2");
		newTests.add("test4");
		newFailures.put("class", newTests);
		
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==-1);
	}
	
	@Test
	public void testRegression2(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		
		newFailures.put("class1", tests);
		
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==-1);
	}

	@Test
	public void testRegression3(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		
		List<String> newTests = new ArrayList<String>();
		newTests.add("test1");
		newTests.add("test2");
		newTests.add("test3");
		newTests.add("test4");
		newFailures.put("class", newTests);
		
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==-1);
	}
	
	@Test
	public void testRegression4(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		initial.put("class2", tests);
		
		List<String> newTests = new ArrayList<String>();
		newTests.add("test1");
		newTests.add("test2");
		newTests.add("test3");
		newTests.add("test4");
		newFailures.put("class", newTests);
		
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==-1);
	}
	
	@Test
	public void testReparation1(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		
		List<String> newTests = new ArrayList<String>();
		newTests.add("test1");
		newTests.add("test2");
		newFailures.put("class", newTests);
		
		Summary.resetRepairedTests();
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==1);
		assertTrue(Summary.getRepairedTests()==1);
	}
	
	@Test
	public void testReparation2(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
						
		Summary.resetRepairedTests();
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==1);
		assertTrue(Summary.getRepairedTests()==3);
	}
	
	@Test
	public void testReparation3(){
		HashMap<String, List<String>> initial = new HashMap<String, List<String>>();
		HashMap<String, List<String>> newFailures = new HashMap<String, List<String>>();
		
		List<String> tests = new ArrayList<String>();
		tests.add("test1");
		tests.add("test2");
		tests.add("test3");
		initial.put("class", tests);
		initial.put("class2", tests);
		
		List<String> newTests = new ArrayList<String>();
		newTests.add("test1");
		newTests.add("test2");
		newFailures.put("class", newTests);
		
		Summary.resetRepairedTests();
		Comparator cmp = new Comparator();
		assertTrue(cmp.compareResults(initial,newFailures)==1);
		assertTrue(Summary.getRepairedTests()==4);
	}
}
