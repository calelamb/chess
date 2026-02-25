package server;

import com.google.gson.Gson;

/**
 *
 */
public class Handler {

    protected static final Gson gson = new Gson();

    /**
     *
     * @param json
     * @param classType
     * @return
     * @param <T>
     */
    public <T>T deserialize(String json, Class<T> classType) {

    }
}
