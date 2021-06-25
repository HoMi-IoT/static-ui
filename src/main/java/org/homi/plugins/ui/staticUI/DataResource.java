package org.homi.plugins.ui.staticUI;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.*;
import org.homi.plugins.ar.specification.actions.*;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;
import org.json.JSONObject;

public class DataResource extends ServerResource{
	@Get
	public Representation get_action() {
		// use the GET_ALL command from the DSSpec
		// [{"key": keyname, "value": val} ...]
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DataStorePlugin").command("GET_ALL");
		
		Action<Map<String, Object>> action;
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			action = Action.getAction(aq);
			data = action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonRepresentation(data);
	}
	
	@Put
	public Representation put_action(Representation rep) {
		JsonRepresentation jRep;
		try {
			jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			String key = (String)json.get("key");
			Object val = json.get("value");
			
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("DataStorePlugin").command("CREATE");
			try {
				Action<Void> action = Action.getAction(aq);
				action.set("0", key);
				action.set("1", val);
				action.run();
			} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Data field created\n");
		
	}
	
	@Post
	public Representation post_action(Representation rep) {
		JsonRepresentation jRep;
		
		try {
			jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			String key = getAttribute("key");
			Object val = json.get("value");
			
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("DataStorePlugin").command("UPDATE");
			try {
				Action<Void> action = Action.getAction(aq);
				action.set("0", key);
				action.set("1", val);
				action.run();
			} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Data field updated\n");
	}
	
	@Delete
	public Representation delete_action(Representation rep) {
		JsonRepresentation jRep;
		
		//jRep = new JsonRepresentation(rep);
		//JSONObject json = jRep.getJsonObject();
		String key = getAttribute("key");
		//Object val = json.get("value");
		
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DataStorePlugin").command("DELETE");
		try {
			Action<Void> action = Action.getAction(aq);
			action.set("0", key);
			//action.set("1", val);
			action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Data field deleted\n");
	}
}
