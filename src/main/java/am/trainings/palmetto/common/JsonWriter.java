package am.trainings.palmetto.common;

import am.trainings.palmetto.customer.exceptions.JsonStorageException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class JsonWriter {

    private static final JsonWriter INSTANCE = new JsonWriter();

    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    private JsonWriter() {
    }

    public static JsonWriter getInstance() {
        return INSTANCE;
    }

    public synchronized <T> void write(Path path, Collection<T> data) {
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            mapper.writeValue(path.toFile(), data);
        } catch (IOException e) {
            throw new JsonStorageException("Failed to write " + path, e);
        }
    }

    public synchronized <T> List<T> read(Path path, Class<T> type) {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(
                    path.toFile(),
                    mapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (IOException e) {
            throw new JsonStorageException("Failed to read " + path, e);
        }
    }
}