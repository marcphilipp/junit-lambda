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

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Unit tests for {@link ToStringBuilder}.
 *
 * @since 5.0
 */
public class ToStringBuilderTests {

	@Test(expected = IllegalArgumentException.class)
	public void withNullObject() {
		new ToStringBuilder((Object) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void withNullClass() {
		new ToStringBuilder((Class<?>) null);
	}

	@Test
	public void withZeroFields() {
		assertEquals("RoleModel []", new ToStringBuilder(new RoleModel()).toString());
		assertEquals("RoleModel []", new ToStringBuilder(RoleModel.class).toString());
	}

	@Test
	public void withOneField() {
		assertEquals("RoleModel [name = 'Dilbert']",
			new ToStringBuilder(new RoleModel()).append("name", "Dilbert").toString());
	}

	@Test
	public void withNullField() {
		assertEquals("RoleModel [name = null]", new ToStringBuilder(new RoleModel()).append("name", null).toString());
	}

	@Test
	public void withTwoFields() {
		assertEquals("RoleModel [name = 'Dilbert', age = 42]",
			new ToStringBuilder(new RoleModel()).append("name", "Dilbert").append("age", 42).toString());
	}

	@Test
	public void withIntegerArrayField() {
		assertEquals("RoleModel [magic numbers = [1, 42, 99]]",
			new ToStringBuilder(new RoleModel()).append("magic numbers", new Integer[] { 1, 42, 99 }).toString());
	}

	@Test
	public void withIntArrayField() {
		assertEquals("RoleModel [magic numbers = [1, 42, 23]]",
			new ToStringBuilder(new RoleModel()).append("magic numbers", new int[] { 1, 42, 23 }).toString());
	}

	@Test
	public void withCharArrayField() {
		assertEquals("RoleModel [magic characters = [a, b]]",
			new ToStringBuilder(new RoleModel()).append("magic characters", new char[] { 'a', 'b' }).toString());
	}

	@Test
	public void withPrimitiveBooleanArrayField() {
		assertEquals("RoleModel [booleans = [true, false, true]]",
			new ToStringBuilder(new RoleModel()).append("booleans", new boolean[] { true, false, true }).toString());
	}

	@Test
	public void withShortArrayField() {
		assertEquals("RoleModel [values = [23, 42]]",
			new ToStringBuilder(new RoleModel()).append("values", new short[] { 23, 42 }).toString());
	}

	@Test
	public void withByteArrayField() {
		assertEquals("RoleModel [values = [23, 42]]",
			new ToStringBuilder(new RoleModel()).append("values", new byte[] { 23, 42 }).toString());
	}

	@Test
	public void withPrimitiveLongArrayField() {
		assertEquals("RoleModel [values = [23, 42]]",
			new ToStringBuilder(new RoleModel()).append("values", new long[] { 23, 42 }).toString());
	}

	@Test
	public void withPrimitiveFloatArrayField() {
		assertEquals("RoleModel [values = [23.45, 17.13]]",
			new ToStringBuilder(new RoleModel()).append("values", new float[] { 23.45f, 17.13f }).toString());
	}

	@Test
	public void withPrimitiveDoubleArrayField() {
		assertEquals("RoleModel [values = [23.45, 17.13]]",
			new ToStringBuilder(new RoleModel()).append("values", new double[] { 23.45d, 17.13d }).toString());
	}

	@Test
	@SuppressWarnings("serial")
	public void withMapField() {
		// @formatter:off
		Map<String,Object> map = new LinkedHashMap<String,Object>() {{
			put("foo", 42);
			put("bar", "enigma");
		}};
		// @formatter:on
		assertEquals("RoleModel [mystery map = {foo=42, bar=enigma}]",
			new ToStringBuilder(new RoleModel()).append("mystery map", map).toString());
	}

	@Test
	public void withDemoImplementation() {
		RoleModel roleModel = new RoleModel("Dilbert", 42);
		assertEquals("RoleModel [name = 'Dilbert', age = 42]", roleModel.toString());
	}

	static class RoleModel {

		String name;
		int age;

		RoleModel() {
		}

		RoleModel(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			// @formatter:off
			return new ToStringBuilder(this)
				.append("name", this.name)
				.append("age", this.age)
				.toString();
			// @formatter:on
		}
	}

}
