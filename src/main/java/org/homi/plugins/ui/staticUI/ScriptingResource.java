package org.homi.plugins.ui.staticUI;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.representation.*;
import org.restlet.ext.jackson.*;

import java.io.IOException;

import org.json.*;
import org.restlet.ext.json.*;
public class ScriptingResource extends ServerResource{
	@Post
	public Representation post_action(Representation rep) throws IOException{
		try {
			System.out.println(rep.getText());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new StringRepresentation(rep.getText());
		
	}
	
	@Get
	public Representation get_action() {
		return new StringRepresentation("hello from the scripting place");
	}
}
