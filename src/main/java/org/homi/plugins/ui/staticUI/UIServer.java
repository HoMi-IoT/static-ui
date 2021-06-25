package org.homi.plugins.ui.staticUI;

import java.net.URISyntaxException;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public class UIServer extends Application {
	
	private String ROOT_URI;
	private Directory directory;
	public void startup() {
		try {
			Component component = new Component();
			component.getServers().add(Protocol.HTTP, 8182);
			component.getClients().add(Protocol.JAR);
			component.getDefaultHost().attach(new UIServer());
			component.start();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public Restlet createInboundRoot() {
		try {
			ROOT_URI = this.getClass().getResource("/org/homi/plugins/ui/staticUI/").toURI().toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		directory =  new Directory(getContext(), ROOT_URI);
		directory.setIndexName("index.html");
		Router router = new Router(getContext());
		
		router.attach("/scripting", ScriptingResource.class);
		router.attach("/scripting/{name}", ScriptingResource.class);
		
		router.attach("/data", DataResource.class);
		router.attach("/data/{key}", DataResource.class);
		
		router.attach("/devices", DeviceResource.class);
		router.attach("/devices/id/{deviceID}", DeviceIDResource.class);
		//router.attach("/devices/ids", DeviceGetMultipleResource.class);
		router.attach("/devices/group/{groupID}", DeviceGroupResource.class);
		
		router.attach("/", directory);
		
		return router;
	}
}
