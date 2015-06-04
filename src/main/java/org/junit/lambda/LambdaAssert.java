package org.junit.lambda;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class LambdaAssert {

	@FunctionalInterface
	interface ThrowingRunnable {
		void run() throws Throwable;
	}

	public static <T extends Throwable> T expectThrows(Class<T> expectedClass, ThrowingRunnable runnable) {
		return expectThrows(noMessage(), expectedClass, noAdditionalClassCheck(), runnable);
	}

	public static <T extends Throwable> T expectThrows(Supplier<String> messageSupplier, Class<T> expectedClass, ThrowingRunnable runnable) {
		return expectThrows(Optional.of(messageSupplier), expectedClass, noAdditionalClassCheck(), runnable);
	}

	public static <T extends Throwable> T expectThrows(Class<T> expectedClass,
			BiPredicate<? super Class<T>, ? super Class<? extends Throwable>> classCheck, ThrowingRunnable runnable) {
		return expectThrows(noMessage(), expectedClass, classCheck, runnable);
	}

	public static <T extends Throwable> T expectThrows(Supplier<String> messageSupplier, Class<T> expectedClass,
			BiPredicate<? super Class<T>, ? super Class<? extends Throwable>> classCheck, ThrowingRunnable runnable) {
		return expectThrows(Optional.of(messageSupplier), expectedClass, classCheck, runnable);
	}

	private static <T extends Throwable> T expectThrows(Optional<Supplier<String>> messageSupplier, Class<T> expectedClass,
			BiPredicate<? super Class<T>, ? super Class<? extends Throwable>> classCheck, ThrowingRunnable runnable) {
		try {
			runnable.run();
		} catch (Throwable actual) {
			if (expectedClass.isInstance(actual) && classCheck.test(expectedClass, actual.getClass())) {
				return expectedClass.cast(actual);
			}
			throw new AssertionError(unexpectedExceptionMessage(messageSupplier, expectedClass, actual), actual);
		}
		throw new AssertionError(expectedExceptionMessage(messageSupplier, expectedClass));
	}

	private static String unexpectedExceptionMessage(
			Optional<Supplier<String>> messageSupplier, Class<? extends Throwable> expectedClass, Throwable actual) {
		return expectedExceptionMessage(messageSupplier, expectedClass)
				+ " but was " + exceptionClassDescription(actual.getClass());
	}

	private static String expectedExceptionMessage(Optional<Supplier<String>> messageSupplier, Class<? extends Throwable> expectedClass) {
		return prepend(messageSupplier, "expected " + exceptionClassDescription(expectedClass));
	}

	private static String prepend(Optional<Supplier<String>> prefixSupplier, String suffix) {
		if (prefixSupplier.isPresent()) {
			return prefixSupplier.get().get() + ", " + suffix;
		}
		return suffix;
	}

	private static String exceptionClassDescription(Class<? extends Throwable> exceptionClass) {
		return "<" + exceptionClass.getName() + ">";
	}

	private static Optional<Supplier<String>> noMessage() {
		return Optional.empty();
	}

	private static <T extends Throwable> BiPredicate<? super Class<T>, ? super Class<? extends Throwable>> noAdditionalClassCheck() {
		return (a, b) -> true;
	}

}
