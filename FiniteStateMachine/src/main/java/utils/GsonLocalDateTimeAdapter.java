package utils;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

	@Override
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	
	
	@Override
    public LocalDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        String timestamp = element.getAsJsonPrimitive().getAsString();
        return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
