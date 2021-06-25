package org.homi.plugins.ui.staticUI;

import java.io.IOException;

import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.Action;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

public class DeviceIDResource extends ServerResource{
	@Post
	public Representation post_action(Representation rep) {
		JsonRepresentation jRep;
		
		try {
			jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			String deviceID = getAttribute("deviceID");
			String groupID = (String) json.get("groupID");
			
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("ADDTOGROUP");
			try {
				Action<Boolean> action = Action.getAction(aq);
				action.set("0", deviceID);
				action.set("1", groupID);
				action.run();
			} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Device added to group\n");
		
	}
	
	@Put
	public Representation put_action(Representation rep) {
		JsonRepresentation jRep;
		
		try {
			jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			String deviceID = getAttribute("deviceID");
			String attrName = (String) json.get("attribute");
			Object attrValue = json.get("value");
			
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("SETATTRIBUTE");
			try {
				Action<Boolean> action = Action.getAction(aq);
				action.set("0", deviceID);
				action.set("1", attrName);
				action.set("2", attrValue);
				action.run();
			} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Device attribute set\n");
		
	}
	
	@Delete
	public Representation delete_action() {
		
		String deviceID = getAttribute("deviceID");
		
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("DELETEDEVICE");
		try {
			Action<Boolean> action = Action.getAction(aq);
			action.set("0", deviceID);
		
			action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Device deleted\n");
	}
}
