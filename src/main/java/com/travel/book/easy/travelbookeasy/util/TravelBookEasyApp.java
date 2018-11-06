package com.travel.book.easy.travelbookeasy.util;

import java.util.function.Function;

public interface TravelBookEasyApp {

	static <T, E> T ofNullable(E e, Function<E, T> function) {
		if (e == null) {
			return null;
		} else {
			return function.apply(e);
		}

	}
}
