package net.einsteinsci.betterbeginnings.config.json;

import java.lang.reflect.Type;

import com.google.gson.*;

import net.minecraft.nbt.*;

public class BBJsonLoader
{
	private static Gson gson;

	public static void initialize()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.disableInnerClassSerialization();
		builder.registerTypeAdapter(NBTTagCompound.class, new NBTTagSerialiser());
		builder.setPrettyPrinting();
		builder.serializeNulls();
		gson = builder.create();
	}

	public static String serializeObject(Object obj)
	{
		return gson.toJson(obj);
	}

	public static <T> T deserializeObject(String json, Class<T> type)
	{
		if (json == null || json.equals(""))
		{
			return null;
		}

		return gson.fromJson(json, type);
	}

	public static Gson getGson()
	{
		return gson;
	}
	
	private static class NBTTagSerialiser implements JsonDeserializer<NBTTagCompound>, JsonSerializer<NBTTagCompound>
	{
	    @Override
	    public JsonElement serialize(NBTTagCompound src, Type typeOfSrc, JsonSerializationContext context)
	    {
		return new JsonPrimitive(src.toString());
	    }
	    
	    @Override
	    public NBTTagCompound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		    throws JsonParseException
	    {
		try
		{
		    NBTTagCompound nbt = JsonToNBT.getTagFromJson(json.getAsString());
		    return nbt;
		}
		catch (NBTException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return null;
	    }   
	}
}
