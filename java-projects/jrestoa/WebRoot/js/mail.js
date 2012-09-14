/*
 * Mail window
 */
mail = {};

mail.inboxDs = new Ext.data.Store({
	autoLoad : true,
	reader : new Ext.data.XmlReader({
		record : 'receivedMail'
	}, [{
		name : 'id'
	},{
		name : 'sender'
	}, {
		name : 'title'
	}, {
		name : 'sendTime'
	}]),
	url : 'mail/inbox/list'
});

mail.outboxDs = new Ext.data.Store({
	autoLoad : true,
	reader : new Ext.data.XmlReader({
		record : 'sentMail'
	}, [{
		name : 'id'
	},{
		name : 'receiver'
	}, {
		name : 'title'
	}, {
		name : 'sendTime'
	}]),
	url : 'mail/outbox/list'
});

mail.inboxModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
	header : "发件人",
	width : 100,
	sortable : true,
	dataIndex : 'sender'
}, {
	header : "主题",
	width : 400,
	sortable : true,
	dataIndex : 'title'
}, {
	header : "接收时间",
	width : 150,
	sortable : true,
	dataIndex : 'sendTime'
}]);

mail.outboxModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
	header : "收件人",
	width : 100,
	sortable : true,
	dataIndex : 'receiver'
}, {
	header : "主题",
	width : 400,
	sortable : true,
	dataIndex : 'title'
}, {
	header : "发送时间",
	width : 150,
	sortable : true,
	dataIndex : 'sendTime'
}]);

MyDesktop.MailWindow = Ext.extend(Ext.app.Module, {
	id : 'win-mail',
	init : function() {
		this.launcher = {
			text : '邮件',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-mail');
		var detailPanel = new Ext.Panel({
			id : 'mail-detail',
			region : 'south',
			split : true,
			collapsible : true,
			title : '邮件主题',
			border : true,
			bodyStyle : 'padding: 5px 5px 5px 5px',
			minSize : 100,
			height : 200,
			maxSize : 300,
			html : '邮件'
		});
		var gridPanel = new Ext.grid.GridPanel({
			id : 'mail-list',
			region : 'center',
			minSize : 200,
			maxSize : 250,
			border : true,
			ds : mail.inboxDs,
			cm : mail.inboxModel,
			tbar : [{
				id : 'tb-new-mail',
				text : '写信',
				tooltip : '编写并发送新邮件',
				iconCls : 'add',
				handler : this.addNewMail
			}, '-', {
				id : 'tb-inbox',
				text : '收件箱',
				tooltip : '查看邮件',
				iconCls : 'option',
				handler : function() {
					mail.inboxDs.reload();
					gridPanel.reconfigure(mail.inboxDs, mail.inboxModel);
				}
			}, '-', {
				id : 'tb-outbox',
				text : '发件箱',
				tooltip : 'option',
				iconCls : 'option',
				handler : function() {
					mail.outboxDs.reload();
					gridPanel.reconfigure(mail.outboxDs, mail.outboxModel);
				}
			}],
			listeners : {
				rowclick : function(grid, rowIndex, e) {
					var sm = grid.getSelectionModel();
					var record = sm.getSelected();
					var mailId = record.data['id'];
					var title = record.data['title'];
					var flag = record.data['sender'];
					detailPanel.setTitle(title);
					if(flag)
						detailPanel.load("mail/inbox/"+ mailId);
					else
						detailPanel.load("mail/outbox/" + mailId);
				}
			}
		});
		if (!win) {
			win = desktop.createWindow({
				id : 'win-mail',
				title : '邮件',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				layout : 'border',
				items : [gridPanel, detailPanel]
			});
		}
		/*
		 * Ext.get('mail-list').on('rowclick',function(theGrid,rowIndex,event){
		 * Ext.MessageBox.alert('Hello','Hello,' + rowIndex); });
		 */
		win.show();
	},

	addNewMail : function() {
		var win = Ext.get('win-mail-add');
		if (!win) {
			var form = new Ext.FormPanel({
				frame : true,
				labelAlign : 'left',
				width : 560,
				waitMsgTarget : true,
				errorReader : new Ext.form.XmlErrorReader(),
				items : [{
					xtype : "fieldset",
					header : false,
					border : false,
					defaultType : 'textfield',
					labelWidth : 55,
					autoHeight : true,
					items : [{
						fieldLabel : "收件人",
						name : "receiver",
						width : 450
					}, {
						fieldLabel : "主题",
						name : "title",
						width : 450
					}, {
						xtype : 'htmleditor',
						hideLabel : true,
						name : "body",
						style : "margin-top:10px;",
						width : 510,
						height : 400
					}]
				}]
			});
			form.addButton({
				text : '发送',
				handler : function() {
					form.getForm().submit({
						url : 'sendmail',
						waitMsg : '正在发送邮件……',
						success : function() {
							mail.outboxDs.reload();
							Ext.MessageBox.alert('成功', '已经成功发送邮件');
						},
						failure : function() {
							Ext.MessageBox.alert('错误', '无法发送邮件，请检查错误信息');
						}
					});
				}
			});
			win = new Ext.Window({
				id : "win-mail-add",
				title : "撰写新邮件",
				iconCls : 'icon-grid',
				resizable : false,
				width : 560,
				height : 560,
				shim : false,
				animCollapse : false,
				layout : 'fit',
				items : form
			});
		}
		win.show();
	}
});
