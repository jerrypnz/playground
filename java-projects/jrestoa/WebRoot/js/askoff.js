/*
 * Mail window
 */
MyDesktop.AskOffWindow = Ext.extend(Ext.app.Module, {
	id : 'win-askoff',
	init : function() {
		this.launcher = {
			text : '请假',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-askoff');
		if (!win) {
			win = desktop.createWindow({
				id : 'win-askoff',
				title : '请假',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : new Ext.grid.GridPanel({
					id : 'todo-list',
					minSize : 200,
					maxSize : 250,
					border : true,
					ds : new Ext.data.Store({
						autoLoad : true,
						reader : new Ext.data.XmlReader({
							record : 'askOffRecord'
						}, [{
							name : 'type'
						}, {
							name : 'reason'
						}, {
							name : 'status'
						}, {
							name : 'startTime'
						}, {
							name : 'endTime'
						}]),
						url : 'dummyXML/askoff.xml'
					}),
					cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
							{
								header : "类型",
								width : 50,
								sortable : true,
								dataIndex : 'type'
							}, {
								header : "请假原因",
								width : 400,
								sortable : true,
								dataIndex : 'reason'
							}, {
								header : "开始时间",
								width : 100,
								sortable : true,
								dataIndex : 'startTime'
							}, {
								header : "结束时间",
								width : 100,
								sortable : true,
								dataIndex : 'endTime'
							}, {
								header : "状态",
								width : 50,
								sortable : true,
								dataIndex : 'status'
							}]),
					tbar : [{
						id : 'tb-new-mail',
						text : '填请假条',
						tooltip : '想请假，请点击我',
						iconCls : 'add'
					}]
				})
			});
		}
		win.show();
	}
});
