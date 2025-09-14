package utils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
	private static final Logger logger = LogManager.getLogger(Utils.class);

	public static void printf(String format, Object... args) {
		System.out.printf(format + System.lineSeparator(), args);
	}
	
	public static void print(String content) {
        System.out.print(content);
    }
	
	public static void println(String content) {
		logger.debug(content);
    }
	
	public static <T> Set<T> toSet(T[] items){
		return Arrays.asList(items).stream().collect(Collectors.toSet());
	}
	
	public static <T> Set<T> toSet(List<T> items){
		return items.stream().collect(Collectors.toSet());
	}
	
	public static <T> List<T> toList(T[] items){
		return Arrays.asList(items).stream().collect(Collectors.toList());
	}
	
	public static <T> List<T> toList(Set<T> items){
		return items.stream().collect(Collectors.toList());
	}
	
	public static <T, C> boolean isSameType(T obj, Class<C> clazz) {
		return obj.getClass() == clazz;
	}
	
	public static String rightPadding(final String str, final int size, String padStr) {
		StringBuilder sb = new StringBuilder(Optional.ofNullable(str).orElse(""));
		for (int i = 0; i < size; i++) {
			sb.append(padStr);
		}
		return sb.substring(0, size);
	}
	
	public static String leftPadding(final String str, final int size, String padStr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(padStr);
		}
		sb.append(Optional.ofNullable(str).orElse(""));
		return sb.substring(sb.length() - size);
	}
	
	public static String repeat(final String str, final int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static Gson getGson() {
		return new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
	}
	
}
