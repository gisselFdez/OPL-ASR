package util;

import java.lang.reflect.Method;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

public class TestAnalyser {

	public void runTest(Class<?> className){
		
		for (Method m : className.getDeclaredMethods()) {
			if (m.getAnnotations()[0].annotationType().getName().equals("org.junit.Test")) {
				
				Request request = Request.method(className,m.getName());
				Result result = new JUnitCore().run(request);
				result.getFailures();
			}
		}
	}
	
	
}
