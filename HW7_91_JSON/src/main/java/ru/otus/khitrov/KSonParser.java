package ru.otus.khitrov;

import javax.json.*;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ClassUtils;


public class KSonParser {

   private JsonObjectBuilder  parseAllObjects( Object  analyzeObject  ){

       JsonObjectBuilder jsonBuild = Json.createObjectBuilder();

       for (Field field : analyzeObject.getClass().getDeclaredFields() ) {

           field.setAccessible(true);
           if (Modifier.isTransient(field.getModifiers())) continue;

           String keyValue = field.getName();
           Class fieldDescr =  field.getType();
           Object rValue = ReflectionHelper.getFieldValue( analyzeObject, keyValue );

           if (Map.class.isAssignableFrom( rValue.getClass() )) {
               writeMap( jsonBuild, keyValue, rValue );
               }
           else if  (List.class.isAssignableFrom( rValue.getClass() )) {
               writeList( jsonBuild, keyValue, rValue );
           }

           else if (fieldDescr.isArray()){
               writeArray( jsonBuild, keyValue, rValue  );
           }
           else if (ClassUtils.isPrimitiveOrWrapper( fieldDescr )
                   || String.class.isAssignableFrom( rValue.getClass() )) {
               writePrimitive(  jsonBuild, keyValue, rValue );
           }
           else if (!ClassUtils.isPrimitiveOrWrapper( fieldDescr )){
               jsonBuild.add( keyValue, parseAllObjects(  rValue ) );
        }

       }
           return jsonBuild;
   }

    private void writeMap(JsonObjectBuilder jsonBuild, String keyValue, Object rValue) {
        JsonObjectBuilder mapBuild = Json.createObjectBuilder();

        for (Map.Entry<Object,Object>currElement : ((Map<Object,Object>) rValue).entrySet()) {
            Object itemKey   = currElement.getKey();
            Object itemValue = currElement.getValue();
            Class  clazz = itemValue.getClass();

            if  (ClassUtils.isPrimitiveOrWrapper( clazz )
                    || String.class.isAssignableFrom( clazz )) {
                writePrimitive(mapBuild, itemKey.toString(), itemValue);
            }  else {
                 mapBuild.add(itemKey.toString(), parseAllObjects( itemValue ));
                }
            }

        jsonBuild.add(keyValue,mapBuild);
        }


    private void writeList(JsonObjectBuilder jsonBuild, String keyValue, Object rValue) {

       JsonArrayBuilder jArray = Json.createArrayBuilder();

       for (Object currElement : ((List<Object>) rValue)) {
           Class clazz = currElement.getClass();
           if  (ClassUtils.isPrimitiveOrWrapper( clazz )
                   || String.class.isAssignableFrom( clazz )){
               writePrimitive( jArray, currElement );
               }
               else {
              jArray.add(parseAllObjects(currElement));
          }
        }

        jsonBuild.add(keyValue,jArray);
    }


    private void writePrimitive(JsonObjectBuilder jsonBuild, String keyValue, Object rvalue ){
        if (String.class.isAssignableFrom( rvalue.getClass() ))
              jsonBuild.add( keyValue, rvalue.toString() );
        else if (Integer.class.isAssignableFrom( rvalue.getClass() ))
            jsonBuild.add( keyValue, (int) rvalue );
        else if (Long.class.isAssignableFrom( rvalue.getClass() ))
            jsonBuild.add( keyValue, (long) rvalue );
   }

    private void writePrimitive(JsonArrayBuilder jsonBuild, Object rvalue ){
        if (String.class.isAssignableFrom( rvalue.getClass() ))
            jsonBuild.add(rvalue.toString());
        else if (Integer.class.isAssignableFrom( rvalue.getClass() ))
            jsonBuild.add((int) rvalue);
        else if (Long.class.isAssignableFrom( rvalue.getClass() ))
            jsonBuild.add((long) rvalue);
    }


    private void writeArray(JsonObjectBuilder jsonBuild, String keyValue, Object rValue) {

        JsonArrayBuilder jArray = Json.createArrayBuilder();

           for(int i=0;i<Array.getLength(rValue);i++) {
            Object obj =  Array.get(rValue,i);
            Class clazz = obj.getClass();
            if (ClassUtils.isPrimitiveOrWrapper(clazz)
                    || String.class.isAssignableFrom(clazz)){
                writePrimitive ( jArray, obj );
            } else {
                jArray.add(parseAllObjects(obj));
            }
        }

        jsonBuild.add(keyValue,jArray);
    }

    public JsonObject parseObject( Object analyzeObject  ){
        JsonObjectBuilder  jsonBuild = parseAllObjects(  analyzeObject );
        return jsonBuild.build();
    }

  public static String getJsonString( JsonObject jsonObj ){
      Objects.requireNonNull( jsonObj, "Not parsed yet" );

      StringWriter stWriter = new StringWriter();
      try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
         jsonWriter.writeObject( jsonObj );
      }
      return stWriter.toString();
  }

}

