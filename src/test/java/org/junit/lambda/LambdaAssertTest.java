package org.junit.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.lambda.LambdaAssert.expectThrows;

import org.junit.Test;

public class LambdaAssertTest {

	@Test
	public void failsWhenNoExceptionIsThrown() {
		try {
			expectThrows(IllegalArgumentException.class, () -> {});
			throw new IllegalStateException("AssertionError expected");
		} catch (AssertionError expected) {
			assertEquals("expected <java.lang.IllegalArgumentException>", expected.getMessage());
		}
	}

	@Test
	public void failsWhenWrongExceptionIsThrown() {
		try {
			expectThrows(IllegalArgumentException.class, () -> { 
				throw new NullPointerException("wrong exception");
			});
			throw new IllegalStateException("AssertionError expected");
		} catch (AssertionError expected) {
			assertEquals("expected <java.lang.IllegalArgumentException>"
					+ " but was <java.lang.NullPointerException>", expected.getMessage());
			assertTrue("correct cause", expected.getCause() instanceof NullPointerException);
			assertEquals("wrong exception", expected.getCause().getMessage());
		}
	}

	@Test
	public void returnsExpectedExceptionWhenSuccessful() {
		IllegalArgumentException expected = expectThrows(IllegalArgumentException.class, () -> { 
			throw new IllegalArgumentException("expected exception");
		});
		assertEquals("expected exception", expected.getMessage());
	}

	@Test
	public void expectationCanBeExpressedMoreStrictly() {
		try {
			expectThrows(RuntimeException.class, Class::equals, () -> { 
				throw new NullPointerException("wrong exception");
			});
			throw new IllegalStateException("AssertionError expected");
		} catch (AssertionError expected) {
			assertEquals("expected <java.lang.RuntimeException>"
					+ " but was <java.lang.NullPointerException>", expected.getMessage());
		}
	}

	@Test
	public void customMessageCanBeSupplied() {
		try {
			expectThrows(() -> "argument should be illegal", IllegalArgumentException.class, () -> { 
				throw new NullPointerException("wrong exception");
			});
			throw new IllegalStateException("AssertionError expected");
		} catch (AssertionError expected) {
			assertEquals("argument should be illegal, expected <java.lang.IllegalArgumentException>"
					+ " but was <java.lang.NullPointerException>", expected.getMessage());
		}
	}

}
