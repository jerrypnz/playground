/*
 * Address book window
 */

var addressBook = {};

addressBook.ds = new Ext.data.Store({
	autoLoad : true,
	reader : new Ext.data.XmlReader({
		record : 'addressItem'
	}, [{
		name : 'id'
	},{
		name : 'name'
	}, {
		name : 'homePhone'
	}, {
		name : 'mobilePhone'
	}, {
		name : 'email'
	}]),
	url : 'addressbook/list'
});

addressBook.sm = new Ext.grid.RowSelectionModel();

addressBook.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
	header : "姓名",
	width : 70,
	sortable : true,
	dataIndex : 'name'
}, {
	header : "手机",
	width : 70,
	sortable : true,
	dataIndex : 'mobilePhone'
}, {
	header : "家庭电话",
	width : 70,
	sortable : true,
	dataIndex : 'homePhone'
}, {
	header : "邮件",
	width : 120,
	sortable : true,
	dataIndex : 'email'
}]);

MyDesktop.AddressBookWindow = Ext.extend(Ext.app.Module, {
	id : 'win-addressbook',
	init : function() {
		this.launcher = {
			text : '个人通讯录',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-addressbook');
		var gridPanel = new Ext.grid.GridPanel({
			id : 'addressbook-grid',
			border : false,
			sm : addressBook.sm,
			cm : addressBook.cm,
			ds : addressBook.ds,
			viewConfig : {
				forceFit : true
			},
			tbar : [{
				text : '添加联系人',
				tooltip : '添加一个新的联系人',
				iconCls : 'add',
				handler : this.addAddressbookItem
			}, {
				text : '删除联系人',
				tooltip : '删除选中联系人',
				iconCls : 'remove',
				handler : function() {
					Ext.MessageBox.wait('正在删除', '正在删除联系人……');
					var sm = gridPanel.getSelectionModel();
					var selected = sm.getSelected();
					var result = selected.data["id"];
					Ext.Ajax.request({
						url:'addressbook/'+result,
						method:'DELETE',
						success:function(){
							addressBook.ds.reload();
							Ext.MessageBox.hide();
						},
						failure:function(){
							Ext.MessageBox.alert('错误','无法删除联系人');
						}
					});
				}
			}]
		});
		if (!win) {
			win = desktop.createWindow({
				id : 'win-addressbook',
				title : '个人通讯录',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : gridPanel
			});
		}
		win.show();
	},

	addAddressbookItem : function() {
		Ext.form.Field.prototype.msgTarget = 'side';
		var win;
		win = Ext.get('win-addressbook-add');
		if (!win) {
			var form = new Ext.form.FormPanel({
				frame : true,
				labelAlign : 'right',
				labelWidth : 85,
				width : 340,
				waitMsgTarget : true,
				errorReader : new Ext.form.XmlErrorReader(),
				items : new Ext.form.FieldSet({
					title : '联系人信息',
					autoHeight : true,
					defaultType : 'textfield',
					items : [{
						fieldLabel : '姓名',
						name : 'name',
						width : 190
					}, {
						fieldLabel : '手机',
						name : 'mobile',
						width : 190
					}, {
						fieldLabel : '家庭电话',
						name : 'homePhone',
						width : 190
					}, {
						fieldLabel : '邮件',
						name : 'email',
						vtype : 'email',
						width : 190
					}]
				})

			});
			form.addButton({
				text : '提交',
				handler : function() {
					form.getForm().submit({
						url : 'addressbook/new',
						waitMsg : '正在添加联系人',
						success : function() {
							Ext.MessageBox.alert('成功', '已经成功添加此联系人');
							addressBook.ds.reload();
							form.getForm().reset();
						},
						failure : function() {
							Ext.MessageBox.alert('错误', '无法添加，请检查输入是否正确');
						}
					});
				}
			});
			var win = new Ext.Window({
				id : 'win-addressbook-add',
				title : '添加新联系人',
				iconCls : 'icon-grid',
				resizable : false,
				width : 360,
				height : 230,
				shim : false,
				animCollapse : false,
				layout : 'fit',
				items : form
			});
		}
		win.show();
	}
});
