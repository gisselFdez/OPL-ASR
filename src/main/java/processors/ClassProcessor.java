package processors;

import java.util.List;

import spoon.processing.AbstractManualProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.filter.TypeFilter;

public class ClassProcessor extends AbstractManualProcessor{

	@Override
	public void process() {
		//Get all classes
		List<CtClass> classes = getFactory().Package().getRootPackage().getElements(new TypeFilter(CtClass.class));
		  for(CtClass cls: classes){
			  System.out.println(cls.getSimpleName().toString());
		  }		
	}

}
