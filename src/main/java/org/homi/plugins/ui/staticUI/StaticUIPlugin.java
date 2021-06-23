package org.homi.plugins.ui.staticUI;

import java.net.URISyntaxException;

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
//	public static Server server;
	private static String ROOT_URI;

	@Override
	public void setup() {
//		ROOT_URI = "";
//		System.out.println(this.getClass().getResource("/org/homi/plugins/ui/staticUI/"));
//		try {
//			ROOT_URI = this.getClass().getResource("/org/homi/plugins/ui/staticUI/").toURI().toString();
//		} catch (URISyntaxException e1) {
//			e1.printStackTrace();
//		}
//		
//		System.out.println(ROOT_URI);
//		// Create a component
//		Component component = new Component();
//		component.getServers().add(Protocol.HTTP, 8182);
//		component.getClients().add(Protocol.JAR);
//
//		// Create an application
//		Application application = new Application() {
//		    @Override
//		    public Restlet createInboundRoot() {
//		    	Directory d =  new Directory(getContext(), ROOT_URI);
//		    	System.out.println(d.getDescription());
//		    	System.out.println(d.getName());
//		    	System.out.println(d.getIndexName());
//		    	return d;
//		    }
//		};
//
//		// Attach the application to the component and start it
//		component.getDefaultHost().attach(application);
		
		
		this.addWorker(()->{
			try {
				UIServer ui = new UIServer();
				ui.startup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("working");
		});
		    
	}
	
//	@Override
//	public void setup() {
//		server = new Server(Protocol.HTTP, 3002, UIServer.class);
//
//		this.addWorker(()->{
//			try {
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("working");
//		});
//		    
//	}
	
	@Override
	public void teardown() {
//		try {
//			server.stop();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}

}
