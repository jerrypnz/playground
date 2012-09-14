/*
 * Ext JS Library 2.0.2
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

Ext.app.Module = function(config){
    Ext.apply(this, config);
    Ext.app.Module.superclass.constructor.call(this);
    this.init();
}

Ext.extend(Ext.app.Module, Ext.util.Observable, {
    init : Ext.emptyFn
});


Ext.form.XmlErrorReader = function() {
	Ext.form.XmlErrorReader.superclass.constructor.call(this, {
		record : 'field',
		success : '@success'
	}, ['id', 'msg']);
};

Ext.extend(Ext.form.XmlErrorReader, Ext.data.XmlReader);