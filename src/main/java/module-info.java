module org.homi.plugins.ui.staticUI {
	requires org.homi.plugin.api;
	requires org.restlet;
	requires org.restlet.ext.jackson;
	requires org.json;
	requires org.restlet.ext.json;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	requires org.homi.plugins.actionRegistry.specification;
	requires org.homi.plugin.specification;
	requires deviceRegistrySpec;
	requires nosqlspec;
	exports org.homi.plugins.ui.staticUI;
	
	provides org.homi.plugin.api.basicplugin.IBasicPlugin
		with org.homi.plugins.ui.staticUI.StaticUIPlugin;
}