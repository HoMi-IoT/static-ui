module org.homi.plugins.ui.staticUI {
	requires org.homi.plugin.api;
	requires org.restlet;
	
	exports org.homi.plugins.ui.staticUI;
	
	provides org.homi.plugin.api.basicplugin.IBasicPlugin
		with org.homi.plugins.ui.staticUI.StaticUIPlugin;
}