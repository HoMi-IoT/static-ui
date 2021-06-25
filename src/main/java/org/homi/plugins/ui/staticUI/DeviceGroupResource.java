package org.homi.plugins.ui.staticUI;

import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.actions.Action;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import deviceRegistrySpec.Device;

public class DeviceGroupResource extends ServerResource{
	@Get
	public Representation get_action() {
		String groupID = getAttribute("groupID");
		
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("GETGROUP");
		
		Action<Device[]> action;
		Device[] devices;
		JSONArray jsonArray = new JSONArray();
		try {
			action = Action.getAction(aq);
			action.set("0", groupID);
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
	
	@Delete
	public Representation delete_action() {
		String groupID = getAttribute("groupID");
		
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("DeviceRegistry").command("DELETEGROUP");
		try {
			Action<Void> action = Action.getAction(aq);
			action.set("0", groupID);
		
			action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Group deleted\n");
	}
	
}
