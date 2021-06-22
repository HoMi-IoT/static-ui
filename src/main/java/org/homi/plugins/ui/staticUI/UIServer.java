package org.homi.plugins.ui.staticUI;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class UIServer extends ServerResource {
	
	@Get
	public String toString() {
		return "hello test";
		
	}
	
}
