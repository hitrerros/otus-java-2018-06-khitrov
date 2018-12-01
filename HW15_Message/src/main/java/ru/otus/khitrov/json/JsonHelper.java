package ru.otus.khitrov.json;

import com.google.gson.*;
import org.springframework.stereotype.Service;
import ru.otus.khitrov.db.dataSets.UserDataSet;

import java.lang.reflect.Type;
import java.util.List;

class UserDataSetAdapter implements JsonSerializer<UserDataSet> {

    @Override
    public JsonElement serialize(UserDataSet userDataSet, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", userDataSet.getId());
        obj.addProperty("name", userDataSet.getName());
        obj.addProperty("age", userDataSet.getAge());
        obj.addProperty("address", userDataSet.getAddress().toString());
        obj.addProperty("phones", userDataSet.getPhones().toString());

        return obj;
    }
}

@Service
public class JsonHelper {

    public static JsonToClientBean deserializeMessage(String json) {
        return new Gson().fromJson(json, JsonToClientBean.class);
    }

    public static String serializeDataSetToJson(List<UserDataSet> usrList) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UserDataSet.class, new UserDataSetAdapter());
        return gsonBuilder.create().toJson(usrList);

    }

}
