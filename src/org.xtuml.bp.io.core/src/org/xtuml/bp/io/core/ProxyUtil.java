package org.xtuml.bp.io.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Stream;

public final class ProxyUtil {

	@SuppressWarnings("unchecked")
	public static <T> T newProxy(Class<T> iface, Object inst) {
		return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[] { iface, ProxyInstance.class },
				new ReflectionInvocationHandler(inst));
	}

	private static final class ReflectionInvocationHandler implements InvocationHandler {

		private final Object inst;

		public ReflectionInvocationHandler(Object inst) {
			this.inst = inst;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getDeclaringClass().equals(ProxyInstance.class)
					&& method.getName().equals("getBasisObject")) {
				return inst;
			} else {
				try {
					return Stream.of(inst.getClass().getMethods())
							.filter(m -> m.getName().equals(method.getName())
									&& m.getGenericReturnType().equals(method.getGenericReturnType())
									&& Arrays.equals(m.getGenericParameterTypes(), method.getGenericParameterTypes()))
							.findAny().orElseThrow(NoSuchMethodException::new).invoke(inst, args);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}
		}

	}

	public interface ProxyInstance {
		Object getBasisObject();
	}

}
