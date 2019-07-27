

package com.example.warehouseapplication.utils;

public class MessageHandler {

    public static <T> String serializeMessage(T object){
        JsonSerializerDeserializer Serializer = new JsonSerializerDeserializer();
        String message = Serializer.serialize(object, object.getClass(), true);
        //Log.d(Contract.LOG_TAG, "Serialized: " + message);
        return message;
    }

    public static <T> T deSerializeMessage(String message, Class<T> typeParameterClass){
        JsonSerializerDeserializer Serializer = new JsonSerializerDeserializer();

        return (T) Serializer.deserialize(message, typeParameterClass, true);
    }

}
