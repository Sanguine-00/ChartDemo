package com.mobcb.base.http.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import com.mobcb.base.http.bean.ApiBody;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by lvmenghui
 * on 2017/4/19.
 */
public class JsonUtils {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static Gson mGson = new Gson();
    private static final TypeAdapter<ApiBody> adapter = mGson.getAdapter(ApiBody.class);

    public static String toJson(Object object) {
        return mGson.toJson(object);
    }

    public static <T> T fromJson(String str, Type type) {
        return mGson.fromJson(str, type);
    }

    public static <T> T fromJson(String str, Class<T> type) {
        return mGson.fromJson(str, type);
    }

    public static <T> T toJavaObject(String jsonString, Class<T> clazz) {
        return mGson.fromJson(jsonString, clazz);
    }

    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(mGson.fromJson(jsonElement, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> Map<String, T> toMap(String jsonString, Type typeOfT) {
        try {
            Map<String, T> map = mGson.fromJson(
                    jsonString, typeOfT);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public static RequestBody createRequestBody(String json, long tm) {
        ApiBody apiBody = ApiUtils.createRequestBodyApi(json, tm);
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = null;
        try {
            jsonWriter = mGson.newJsonWriter(writer);
            adapter.write(jsonWriter, apiBody);
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }

}
