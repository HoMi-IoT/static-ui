package org.homi.plugins.ui.staticUI;

import java.io.IOException;
import java.util.HashMap;
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
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class RuleResource extends ServerResource{
	// add rule -> string
	// get rules -> map<int, string>
	// remove rule -> int id 
	@Get
	public Representation get_action() {
		// use the GET_ALL command from the DSSpec
		// [{"key": keyname, "value": val} ...]
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("RuleEngine").command("GET_RULES");
		
		Action<Map<Integer, String>> action;
		Map<Integer, String> rules = new HashMap<Integer, String>();
		try {
			action = Action.getAction(aq);
			rules = action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonRepresentation(rules);
	}
	
	@Post
	public Representation post_action(Representation rep) throws IOException{
		try {
			String s = rep.getText();
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("RuleEngine").command("ADD_RULE");
			Action<Boolean> action = Action.getAction(aq);
			action.run(s);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new StringRepresentation("Rule added");
		
	}
	
	@Delete
	public Representation delete_action(Representation rep) {
		String ruleID = getAttribute("deviceID");
		Integer ruleNum = Integer.parseInt(ruleID);
		
		
		
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("RuleEngine").command("");
		try {
			Action<Void> action = Action.getAction(aq);
			action.set("0", ruleNum);
			//action.set("1", val);
			action.run();
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new StringRepresentation("Rule removed\n");
	}
	
}
