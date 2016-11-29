package lyu.klt.frame.util;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.GsonBuilder;
import lyu.klt.frame.google.gson.JsonDeserializationContext;
import lyu.klt.frame.google.gson.JsonDeserializer;
import lyu.klt.frame.google.gson.JsonElement;
import lyu.klt.frame.google.gson.JsonParseException;
import lyu.klt.frame.google.gson.JsonPrimitive;
import lyu.klt.frame.google.gson.JsonSerializationContext;
import lyu.klt.frame.google.gson.JsonSerializer;
import lyu.klt.frame.google.gson.reflect.TypeToken;

public class GsonUtils
{
	
	
	
	public static Gson getGson(){
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.setDateFormat("yyyy-MM-dd hh:mm:ss");  
		gsonBuilder.enableComplexMapKeySerialization();
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter());  
		Gson gson = gsonBuilder.create();  
		return gson;
	}
	
	
    /**
     * json字符串转化为对象
     * @param jsonString
     * @param cls  
     * @return [参数说明]
     * 
     * @author  linxw
     * @version  [版本号, 2014-7-25] 	
     * @return T [返回类型说明]
     */
    public static <T> T getObj(String jsonString, Class<T> cls)
    {
        T t = null;
        try
        {
            Gson gson = getGson();
            t = gson.fromJson(jsonString, cls);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return t;
    }
    
    /**
     * json字符串转化为list对象
     * @param jsonString
     * @param cls  
     * @return [参数说明] 由于使用泛型T，导致T变成map形式，如果要变成对象 就不使用泛型
     * 
     * @author  linxw
     * @version  [版本号, 2014-7-25]   
     * @return T [返回类型说明]
     */
    public static <T> List<T> getObjList(String jsonString, Class<T> cls)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            Gson gson =  getGson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>()
            {
            }.getType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public static <T> List<T> getObjList(String jsonString, Type type)
    {
        List<T> list = new ArrayList<T>();
        try
        {
            Gson gson =  getGson();
            list = gson.fromJson(jsonString, type);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    
    
    /**
     * 对象转化为json字符串
     * @param value
     * @return [参数说明]
     * 
     * @author  linxw
     * @version  [版本号, 2014-7-25] 	
     * @return String [返回类型说明]
     */
    public static String objToJson(Object value)
    {
        Gson gson =  getGson();
        String Str = gson.toJson(value);
        return Str;
        
    }
    
    public static List<String> getStringList(String jsonString)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            Gson gson =  getGson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>()
            {
            }.getType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
        
    }
    
    public static List<Map<String, Object>> listKeyMap(String jsonString)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try
        {
            Gson gson =  getGson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>()
                    {
                    }.getType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public static class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp>{  
        private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");      
        
	
		@Override
		public Timestamp deserialize(JsonElement json,
				java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			if (!(json instanceof JsonPrimitive)) {      
                throw new JsonParseException("The date should be a string value");      
            }      
         
            try {      
                Date date = format.parse(json.getAsString());      
                return new Timestamp(date.getTime());      
            } catch (ParseException e) {      
                throw new JsonParseException(e);      
            }      
		}

		@Override
		public JsonElement serialize(Timestamp src,
				java.lang.reflect.Type typeOfSrc,
				JsonSerializationContext context) {
			 String dfString = format.format(new Date(src.getTime()));      
	            return new JsonPrimitive(dfString); 
		}      
    }   
}
