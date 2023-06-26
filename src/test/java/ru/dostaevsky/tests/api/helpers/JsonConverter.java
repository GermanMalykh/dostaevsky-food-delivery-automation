package ru.dostaevsky.tests.api.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonConverter {

    public static <T> T deserialize(String input, Class<T> expectedType) {
        T result = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            result = mapper.readValue(input, expectedType);
        } catch (IOException ignored) {
        }

        return result;
    }

    public static <T> T deserialize(File input, Class<T> expectedType) {
        String contents = null;

        try {
            contents = new String(Files.readAllBytes(Paths.get(input.toPath().toString())));
        } catch (IOException ignored) {
        }

        return deserialize(contents, expectedType);
    }

}
