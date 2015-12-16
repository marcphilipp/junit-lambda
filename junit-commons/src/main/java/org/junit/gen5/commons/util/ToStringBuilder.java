/*
 * Copyright 2015 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.junit.gen5.commons.util;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Simple builder for generating strings in custom implementations of
 * {@link Object#toString toString()}.
 *
 * @since 5.0
 */
public class ToStringBuilder {

	private final Class<?> type;

	private final List<String> values = new ArrayList<>();

	public ToStringBuilder(Object obj) {
		Preconditions.notNull(obj, "Object must not be null");
		this.type = obj.getClass();
	}

	public ToStringBuilder(Class<?> type) {
		Preconditions.notNull(type, "Class must not be null");
		this.type = type;
	}

	public ToStringBuilder append(String name, Object value) {
		Preconditions.notBlank(name, "Name must not be null or empty");
		this.values.add(name + " = " + toString(value));
		return this;
	}

	private String toString(Object obj) {
		if (obj == null) {
			return "null";
		}
		if (obj instanceof CharSequence) {
			return "'" + obj + "'";
		}
		if (obj.getClass().isArray()) {
			return arrayToString(obj);
		}
		return Objects.toString(obj);
	}

	private String arrayToString(Object obj) {
		if (obj.getClass().getComponentType().isPrimitive()) {
			if (obj instanceof boolean[]) {
				return Arrays.toString((boolean[]) obj);
			}
			if (obj instanceof char[]) {
				return Arrays.toString((char[]) obj);
			}
			if (obj instanceof short[]) {
				return Arrays.toString((short[]) obj);
			}
			if (obj instanceof byte[]) {
				return Arrays.toString((byte[]) obj);
			}
			if (obj instanceof int[]) {
				return Arrays.toString((int[]) obj);
			}
			if (obj instanceof long[]) {
				return Arrays.toString((long[]) obj);
			}
			if (obj instanceof float[]) {
				return Arrays.toString((float[]) obj);
			}
			if (obj instanceof double[]) {
				return Arrays.toString((double[]) obj);
			}
		}
		return Arrays.deepToString((Object[]) obj);
	}

	@Override
	public String toString() {
		// @formatter:off
		return new StringBuilder(this.type.getSimpleName()).append(" ")
			.append("[")
			.append(this.values.stream().collect(joining(", ")))
			.append("]")
			.toString();
		// @formatter:on
	}

}
