package org.homi.plugins.ui.staticUI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.homi.plugins.dbs.nosqlspec.record.*;
import org.homi.plugins.dbs.nosqlspec.record.Record;
public class ScriptStorageVisitor implements IStorageComponentVisitor<Object> {
	private Map<String, String> map = new HashMap<String, String>();
    @Override
    public Map<String, String> visit(FieldList fieldList) {
    	
        fieldList.getList().forEach((Consumer<? super IStorageComponent>)r->{
        	r.accept(this);
        });
        return map;
    }

    @Override
    public Void visit(Record record) {
    	record.getFields().keySet().forEach((k)->{
    		map.put(k, (String) record.getFields().get(k).accept(this));
    	});
        
        return null;
    }

    @Override
    public <T extends Serializable> String visit(Value<T> value) {

        return value.getValue().toString();
    }


	
}