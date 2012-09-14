/*
 * Address book window
 */

calendar = {};

calendar.reader = new Ext.data.XmlReader({
	record : 'appointment'
}, [{
	name : 'title'
}, {
	name : 'detail'
}, {
	name : 'startTime'
}, {
	name : 'completeTime'
}, {
	name : 'finished'
}]);

calendar.ds = new Ext.data.Store({
	autoLoad : true,
	reader : calendar.reader,
	url : 'appointment/' + new Date().format('Y-m-d')
});

calendar.timeOfDate = function(value) {
	return value.substring(11,19);
}

calendar.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
	header : "事件",
	width : 100,
	sortable : true,
	dataIndex : 'title'
}, {
	header : "详情",
	width : 200,
	sortable : true,
	dataIndex : 'detail'
}, {
	header : "开始时间",
	width : 50,
	sortable : true,
	dataIndex : 'startTime',
	renderer : calendar.timeOfDate
}, {
	header : "结束时间",
	width : 50,
	sortable : true,
	dataIndex : 'completeTime',
	renderer : calendar.timeOfDate
}, {
	header : "是否完成",
	width : 50,
	sortable : true,
	dataIndex : 'finished',
	editor : new Ext.form.Checkbox({
		title : '完成'
	})
}]);

MyDesktop.CalendarWindow = Ext.extend(Ext.app.Module, {
	id : 'win-calendar',
	init : function() {
		this.launcher = {
			text : '我的日历',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-calendar');
		var datePicker = new Ext.DatePicker({
			id : 'win-calendar-datepicker',
			format : 'Y-m-d',
			todayText : '今天',
			okText : '确定',
			cancelText : '取消'
		});
		var calGrid = new Ext.grid.EditorGridPanel({
			id : 'win-calendar-grid',
			title : 'Today',
			border : false,
			ds : calendar.ds,
			cm : calendar.cm,
			clicksToEdit : 1,
			tbar : [{
				text : '新事件',
				tooltip : '创建新事件',
				iconCls : 'add',
				handler : this.newAppointment
			}, '-', {
				text : '删除事件',
				tooltip : '删除选中事件',
				iconCls : 'remove'
			}]
		});
		if (!win) {
			win = desktop.createWindow({
				id : 'win-calendar',
				title : '我的日历',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : "border",
				items : [{
					region : "center",
					items : new Ext.TabPanel({
						activeTab : 0,
						frame : true,
						defaults : {
							autoHeight : true
						},
						items : calGrid
					})
				}, {
					region : "west",
					title : "Calendar",
					width : 180,
					split : false,
					collapsible : true,
					items : datePicker
				}]
			});

			datePicker.on('select', function(picker, date) {
				// Ext.MessageBox.alert('Hello', 'You picked:'
				// + date.format('Y-m-d'));
				var dateStr = date.format('Y-m-d');
				var newDs = new Ext.data.Store({
					autoLoad : true,
					reader : calendar.reader,
					url : 'appointment/' + dateStr
				});
				calGrid.setTitle(dateStr);
				calGrid.reconfigure(newDs, calendar.cm);
			});

		}
		win.show();
	},

	newAppointment : function() {

		var win = Ext.get('win-calendar-new');
		if (!win) {
			var form = new Ext.FormPanel({
				frame : true,
				labelAlign : 'left',
				width : 300,
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
						fieldLabel : "标题",
						name : "title",
						width : 200
					}, {
						fieldLabel : "内容",
						name : "detail",
						xtype : 'textarea',
						height : 100,
						width : 200
					}, {
						fieldLabel : "日期",
						name : "date",
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 200
					}, {
						fieldLabel : "开始时间",
						name : "startTime",
						xtype : 'timefield',
						format : 'H:i:s',
						width : 200
					}, {
						fieldLabel : "结束时间",
						name : "endTime",
						xtype : 'timefield',
						format : 'H:i:s',
						width : 200
					}]
				}]
			});
			form.addButton({
				text : '提交',
				handler : function() {
					form.getForm().submit({
						url : 'appointment/new',
						waitMsg : '正在建立约会……',
						success : function() {
							form.getForm().reset();
							calendar.ds.reload();
							Ext.MessageBox.alert('成功', '已经成功创建约会');
						},
						failure : function() {
							Ext.MessageBox.alert('错误', '无法建立约会');
						}
					});
				}
			});
			win = new Ext.Window({
				id : "win-calendar-new",
				title : "新建约会",
				iconCls : 'icon-grid',
				resizable : false,
				width : 320,
				height : 300,
				shim : false,
				animCollapse : false,
				layout : 'fit',
				items : form
			});
			win.show();
		}
	}
});
