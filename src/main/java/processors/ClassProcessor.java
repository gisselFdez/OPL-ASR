package processors;

import java.util.List;

import engine.TestAnalyser;
import reparator.AppTest;
import spoon.processing.AbstractManualProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ClassProcessor extends AbstractManualProcessor{

	@Override
	public void process() {
		//Get all classes
		List<CtClass> classes = getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class));
		String root= getFactory().Package().getRootPackage().getQualifiedName();
		for(CtClass cls: classes){
			
			  run(cls,root);			  
		  }		
	}
	
	private void run(CtClass className,String root){
		//run test
		TestAnalyser test = new TestAnalyser();
		test.runTest(AppTest.class);
		System.out.println(className.getSimpleName().toString());
			
	
	}

}
