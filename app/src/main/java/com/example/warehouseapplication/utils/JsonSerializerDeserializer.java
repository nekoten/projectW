

package com.example.warehouseapplication.utils;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonSerializerDeserializer<T> {

    Gson handlerDotNet = null;
    Gson handler = null;

    public JsonSerializerDeserializer() {
        GsonBuilder Builder = new GsonBuilder();
        Builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                        return expose != null && !expose.deserialize();
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
        });
        Builder.registerTypeAdapter(Date.class, new DateSerializer());
        Builder.registerTypeAdapter(Date.class, new DateDeserializer());
        handlerDotNet = Builder.create();
        handler = new Gson();
    }

    public String serialize(Object instance, Type ObjectType, Boolean ToDotNetFormat) {
        if (ToDotNetFormat) {
            String ret = handlerDotNet.toJson(instance, ObjectType);
            return ret.replace("/", "\\/");
        } else {
            return handler.toJson(instance, ObjectType);
        }
    }

    public T deserialize(String json, Class<T> typeParameterClass, Boolean IsInDotNetFormat) {
        return IsInDotNetFormat ? handlerDotNet.fromJson(json, typeParameterClass) : handler.fromJson(json, typeParameterClass);
    }

    private class DateSerializer implements JsonSerializer<Date> {

        @Override
        public JsonElement serialize(Date t, Type type, JsonSerializationContext jsc) {
            Date date = (Date) t;

            /** Send date to server on Local Time*/
            //return new JsonPrimitive("/Date(" + (date.getTime() + TimeZone.getDefault().getRawOffset()) + ")/");

            /** Send date to server on UTC Time*/
            return new JsonPrimitive("/Date(" + date.getTime()  + ")/");
        }
    }

    public class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            String jsonString = json.getAsJsonPrimitive().getAsString();
            //Go to https://regex101.com Test this regexp \/Date\((\-?\d*?)([\+\-]\d*)?\)\/
            // /Date(12345678989-0000)/ o /Date(12345678989+0000)/
            // /Date(-12345678989-0000)/ o /Date(-12345678989+0000)/
            // /Date(-12345678989)/ o /Date(-12345678989)/
            String Regexp = "\\/Date\\((\\-?\\d*?)([\\+\\-]\\d*)?\\)\\/";
            Pattern MyPattern = Pattern.compile(Regexp);
            Matcher MyMatcher = MyPattern.matcher(jsonString);
            System.out.println(MyMatcher.matches());
            MyMatcher.groupCount();

            /** if date is in format /Date(12345678989)/  - MyMatcher.group(2) is NULL */
            /*if (MyMatcher.group(2) != null && (MyMatcher.group(2).contains("+") || MyMatcher.group(2).contains("-"))) {
                    return new Date(new Long(MyMatcher.group(1)) - TimeZone.getDefault().getRawOffset() + new Long(MyMatcher.group(2)) * 60 * 60 * 10);
            } else {
                return new Date(new Long(MyMatcher.group(1)) - TimeZone.getDefault().getRawOffset());
            }*/

            if (MyMatcher.group(2) != null && (MyMatcher.group(2).contains("+") || MyMatcher.group(2).contains("-"))) {
                return new Date(new Long(MyMatcher.group(1))  + new Long(MyMatcher.group(2)) * 60 * 60 * 10);
            } else {
                return new Date(new Long(MyMatcher.group(1)));
            }
        }
    }
}
