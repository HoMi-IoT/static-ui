package org.homi.plugins.ui.staticUI;

import org.restlet.resource.Get;

import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import org.restlet.representation.*;
import org.restlet.ext.jackson.*;

import java.io.IOException;

import org.json.*;
import org.restlet.ext.json.*;
import org.homi.plugin.api.exceptions.PluginException;
import org.homi.plugin.specification.exceptions.ArgumentLengthException;
import org.homi.plugin.specification.exceptions.InvalidArgumentException;
import org.homi.plugins.ar.specification.*;
import org.homi.plugins.ar.specification.actions.Action;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;

import org.homi.plugins.dbs.nosqlspec.record.*;
import org.homi.plugins.dbs.nosqlspec.record.Record;
public class ScriptingResource extends ServerResource{
	@Get
	public Representation get_action() {
		ActionQuery aq = new ActionQuery();
		aq.type(TYPE.SPECIFICATION).pluginID("NoSQLPlugin").command("QUERY");
		
		String collectionName = "scripts";
		Action<FieldList> action;
		try {
			action = Action.getAction(aq);
			FieldList res = action.run(collectionName, null);
			Map<String, String> x = (Map<String, String>)res.accept(new ScriptStorageVisitor());
			// convert map to JSON string
			ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(x);
            return new JsonRepresentation(json);
            

            
		} catch (InvalidArgumentException | ArgumentLengthException | PluginException | JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new StringRepresentation("failed :(");
		}
		
		//FieldList res 
	}
	
	@Post
	public Representation post_action(Representation rep) throws IOException{
		try {
			String s = rep.getText();
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("ScriptingEngine").command("EVAL_SCRIPT");
			Action<Object> action = Action.getAction(aq);
			action.run(s);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new StringRepresentation("Script evaluated");
		
	}
	
	@Put
	public Representation put_action(Representation rep) throws IOException{
		try {
			String scriptText = rep.getText();
			String name = getAttribute("name");
			
			ActionQuery aq = new ActionQuery();
			
			Record r = new Record();
			r.addField(name, new Value<String>(scriptText));
			
			aq.type(TYPE.SPECIFICATION).pluginID("NoSQLPlugin").command("STORE");
			Action<Integer> action = Action.getAction(aq);
	
			action.run("scripts", r);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new StringRepresentation("Script saved");
		
	}
	
	
}
