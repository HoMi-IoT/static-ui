package org.homi.plugins.ui.staticUI;

import java.io.IOException;
import java.util.Map;

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
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BluetoothResource extends ServerResource{
	@Get
	public Representation get_action() {
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("BLE").command("GET_DEVICE_INFO");
		try {
			Action<Map<String, Object>> action = Action.getAction(aq);
			Map<String, Object> res = action.run();
			ObjectMapper om = new ObjectMapper();
			String json = om.writeValueAsString(res);
			return new JsonRepresentation(json);
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new StringRepresentation("Failed to get devices\n");
		}
		
	}
	
	@Post
	public Representation post_action(Representation rep) {
		try {
			JsonRepresentation jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			String mac = (String)json.get("mac");
			String state = getAttribute("state");
			
			ActionQuery aq = new ActionQuery();
			if(state.equals("connect")) {
				aq.type(TYPE.SPECIFICATION).pluginID("BLE").command("CONNECT");
			}
			else {
				aq.type(TYPE.SPECIFICATION).pluginID("BLE").command("DISCONNECT");
			}
			Action<Boolean> action = Action.getAction(aq);
			if(action.run(mac)) {
				return new StringRepresentation("success\n");
			}
			else {
				return new StringRepresentation("failure\n");
			}
			
		} catch (IOException | InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new StringRepresentation("failure\n");
		}
		
	}
}
