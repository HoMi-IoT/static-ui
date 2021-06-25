package org.homi.plugins.ui.staticUI;

import org.restlet.resource.Get;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import deviceRegistrySpec.Device;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.Action;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
public class DeviceResource extends ServerResource{
	@Get
	public Representation get_action() {
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("GETALLDEVICES");
		
		Action<Device[]> action;
		Device[] devices;
		JSONArray jsonArray = new JSONArray();
		try {
			action = Action.getAction(aq);
			devices = action.run();
			for(Device d : devices) {
				String name = d.getName();
				JSONArray jGroups = new JSONArray(d.getGroups());
				JSONObject jAddrs = new JSONObject(d.getAddresses());
				JSONObject jAttrs = new JSONObject(d.getAttributes());
				JSONObject jDevice = new JSONObject();
				jDevice.append("name", name)
				.append("addresses", jAddrs)
				.append("attributes", jAttrs)
				.append("groups", jGroups);
				
				jsonArray.put(jDevice);
				
			}
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonRepresentation(jsonArray);
	}
	
	@Post
	public Representation post_action(Representation rep) {
		JsonRepresentation jRep;
		try {
			jRep = new JsonRepresentation(rep);
			JSONObject json = jRep.getJsonObject();
			
			ObjectMapper om = new ObjectMapper();
			
			String deviceName = (String)json.get("name");
			String jsonAddrs = ((JSONObject)json.get("addresses")).toString();
			String jsonAttrs =((JSONObject)json.get("attributes")).toString();
			String jsonGroups = ((JSONArray)json.get("groups")).toString();
			Map<String, String> addresses = om.readValue(jsonAddrs, Map.class);
			Map<String, Serializable> attributes= om.readValue(jsonAttrs, Map.class);
			Set<String> groups = (Set<String>) Arrays.asList(om.readValue(jsonGroups, String[].class));
			
			Device device = new Device(deviceName);
			device.setAddresses(addresses);
			device.setAttributes(attributes);
			device.setGroups(groups);
			
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("CREATEDEVICE");
			try {
				Action<Void> action = Action.getAction(aq);
				action.set("0", device);
				
				action.run();
			} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Device created\n");
	}
}
