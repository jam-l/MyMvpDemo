package com.jam.mymvpdemo.parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jam.mymvpdemo.util.ExpManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonParse {
	private static Gson gson;

	public static Gson getGson(){
		if(gson == null){
			gson = new Gson();
		}
		return gson;
	}
	public static <T> T getEntity(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = getGson().fromJson(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
			ExpManager.sendException(e);
		}
		return t;
	}
	
	//此方法暂时不可用，待完善
	public static <T> List<T> getEntitys(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = getGson().fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
			ExpManager.sendException(e);
		}
		return list;
	}

	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = getGson().fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
			e.printStackTrace();
			ExpManager.sendException(e);
		}
		return list;
	}
}
