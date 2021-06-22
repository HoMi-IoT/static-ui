package org.homi.plugins.ui.staticUI;

import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;

@PluginID(id="StaticUIPlugin")
public class StaticUIPlugin extends AbstractBasicPlugin {
	public static Server server;
	private static String ROOT_URI;
	public static void main(String[] args) {
		// URI of the root directory.
		ROOT_URI = "file://./src/main/resources/";

		

		// Create a component
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 8182);
		component.getClients().add(Protocol.FILE);

		// Create an application
		Application application = new Application() {
		    @Override
		    public Restlet createInboundRoot() {
		            return new Directory(getContext(), ROOT_URI);
		    }
		};

		// Attach the application to the component and start it
		component.getDefaultHost().attach(application);
		try {
			component.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void setup() {
		server = new Server(Protocol.HTTP, 3002, UIServer.class);
		this.addWorker(()->{
			try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("working");
		});
		
	}
	
	@Override
	public void teardown() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
