Ext.onReady(function() {

	Ext.QuickTips.init();

	Ext.form.Field.prototype.msgTarget = 'side';
	var form = new Ext.form.FormPanel({
		frame : true,
		labelAlign : 'right',
		labelWidth : 85,
		width : 340,
		waitMsgTarget : true,
		errorReader : new Ext.form.XmlErrorReader(),
		items : new Ext.form.FieldSet({
			autoHeight : true,
			defaultType : 'textfield',
			items : [{
				fieldLabel : '用户名',
				name : 'user',
				width : 190
			}, {
				fieldLabel : '密码',
				name : 'password',
				inputType : 'password',
				width : 190
			}]
		})

	});
	form.addButton({
		text : '登录',
		handler : function() {
			form.getForm().submit({
				url : 'login',
				waitMsg : '正在登录……',
				success : function() {
					window.location.replace(window.location.href.substring(0,
							window.location.href.indexOf('index.html'))
							+ 'main.jsp');
				},
				failure : function() {
					Ext.MessageBox.alert('错误', '登录失败，用户不存在或密码错误');
				}
			});
		}
	});
	var win = new Ext.Window({
		id : 'win-login',
		title : '登录',
		closable : false,
		resizable : false,
		width : 360,
		height : 160,
		shim : false,
		animCollapse : false,
		layout : 'fit',
		items : form
	});
	win.show();
});

Ext.form.XmlErrorReader = function() {
	Ext.form.XmlErrorReader.superclass.constructor.call(this, {
		record : 'field',
		success : '@success'
	}, ['id', 'msg']);
};

Ext.extend(Ext.form.XmlErrorReader, Ext.data.XmlReader);