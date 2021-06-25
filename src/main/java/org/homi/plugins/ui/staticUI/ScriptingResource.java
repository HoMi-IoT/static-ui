package org.homi.plugins.ui.staticUI;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.*;
import org.restlet.ext.jackson.*;

import java.io.IOException;

import org.json.*;
import org.restlet.ext.json.*;
import org.homi.plugins.ar.specification.*;
import org.homi.plugins.ar.specification.actions.Action;
import org.homi.plugins.ar.specification.actions.ActionQuery;
import org.homi.plugins.ar.specification.actions.ActionQuery.TYPE;
public class ScriptingResource extends ServerResource{
	@Post
	public Representation post_action(Representation rep) throws IOException{
		try {
			String s = rep.getText();
			ActionQuery aq = new ActionQuery();
			aq.type(TYPE.SPECIFICATION).pluginID("ScriptingEngine").command("EVAL_SCRIPT");
			Action<Object> action = Action.getAction(aq);
			action.run();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new StringRepresentation("Script evaluated");
		
	}
	
	@Get
	public Representation get_action() {
		return new StringRepresentation("hello from the scripting place");
	}
}
