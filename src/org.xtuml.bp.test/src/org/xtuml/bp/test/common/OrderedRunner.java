package org.xtuml.bp.test.common;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

public class OrderedRunner extends BlockJUnit4ClassRunner {

	public OrderedRunner(Class aClass) throws InitializationError {
		super(aClass);
	}

	//	@Override
	//	protected List computeTestMethods() {
	//		List list = super.computeTestMethods();
	//		List copy = new ArrayList(list);
	//		Collections.sort(copy, new Comparator() {
	//			public int compare(FrameworkMethod o1, FrameworkMethod o2) {
	//				int value1 = 0, value2 = 0;
	//				Annotation[] annotations = o1.getAnnotations();
	//				for (Annotation annotation : annotations) {
	//					String string = annotation.toString();
	//					Class<? extends Annotation> annotationType = annotation.annotationType();
	//					System.out.println(string);
	//					if (annotation instanceof Nehad) {
	//						value1 = ((Nehad) annotation).value();
	//						//						System.out.println(value);
	//					}
	//				}
	//				Annotation[] annotations2 = o2.getAnnotations();
	//				for (Annotation annotation2 : annotations2) {
	//					String string = annotation2.toString();
	//					Class<? extends Annotation> annotationType = annotation2.annotationType();
	//					System.out.println(string);
	//					if (annotation2 instanceof Nehad) {
	//						value2 = ((Nehad) annotation2).value();
	//						//						System.out.println(value);
	//					}
	//				}
	//				Integer v1 = value1, v2 = value2;
	//				return v1.compareTo(v2);
	//			}
	//
	//			@Override
	//			public int compare(Object o1, Object o2) {
	//				FrameworkMethod o11 = (FrameworkMethod) o1;
	//				FrameworkMethod o22 = (FrameworkMethod) o2;
	//				return compare(o11, o22);
	//
	//			}
	//		});
	//		return copy;
	//	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		final List<FrameworkMethod> list = super.computeTestMethods();
		try {
			final List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(list);
			Collections.sort(copy, MethodComparator.getFrameworkMethodComparatorForJUnit4());
			return copy;
		} catch (Throwable throwable) {
			//			logger.fatal("computeTestMethods(): Error while sorting test cases! Using default order (random).",
			//					throwable);
			return list;
		}
	}
}
