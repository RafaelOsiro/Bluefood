package io.ucb.rafael.bluefood.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> listOf( T... objs) {
		if (objs == null) {
			return Collections.emptyList();
		}
		
//		Usando o método tradicional
//		List<T> list = new ArrayList<>(objs.length);
//		for (T obj : objs) {
//			list.add(obj);
//		}
//		
//		return list;

//		Usando o método de collections
		return Arrays.stream(objs).collect(Collectors.toList());
	}
}
