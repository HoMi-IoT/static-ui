package org.homi.plugins.ui.staticUI;

import org.homi.plugin.api.PluginID;
import org.homi.plugin.api.basicplugin.AbstractBasicPlugin;
import org.restlet.Server;
import org.restlet.data.Protocol;

@PluginID(id="StaticUIPlugin")
public class StaticUIPlugin extends AbstractBasicPlugin {
	public static Server server;
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
