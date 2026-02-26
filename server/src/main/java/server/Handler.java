package server;

import com.google.gson.Gson;

/**
 * Base handler class that handles the serialization and deserialization of JSON objects using the GSON framework
 */
public class Handler {

    protected static final Gson gson = new Gson();

    /**
     * Takes in a JSON string object and deserializes the contained data to an arbitrary type defined by the class.
     *
     * @param json      JSON String object
     * @param classType The desired type to be cast
     * @param <T>       Generic type T
     * @return the deserialized data in an object of type T
     */
    public <T> T deserialize(String json, Class<T> classType) {
        return gson.fromJson(json, classType);
    }

    /**
     *
     * @param o
     * @return
     */
    public String serialize(Object o) {
        return gson.toJson(o);
    }
}
