/*
 * Mail window
 */
MyDesktop.ToDoWindow = Ext.extend(Ext.app.Module, {
	id : 'win-todo',
	init : function() {
		this.launcher = {
			text : '任务',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-todo');
		if (!win) {
			win = desktop.createWindow({
				id : 'win-todo',
				title : '任务',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : new Ext.grid.EditorGridPanel({
					id : 'todo-list',
					minSize : 200,
					maxSize : 250,
					border : true,
					ds : new Ext.data.Store({
						autoLoad : true,
						reader : new Ext.data.XmlReader({
							record : 'todoItem'
						}, [{
							name : 'id'
						}, {
							name : 'content'
						}, {
							name : 'alarmTime'
						}, {
							name : 'finished'
						}]),
						url : 'dummyXML/todo.xml'
					}),
					cm : new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
							{
								header : "任务",
								width : 400,
								sortable : true,
								dataIndex : 'content'
							}, {
								header : "时间",
								width : 100,
								sortable : true,
								dataIndex : 'alarmTime'
							}, {
								header : "是否完成",
								width : 50,
								sortable : true,
								dataIndex : 'finished',
								editor : new Ext.form.Checkbox({
									title : '完成'
								})
							}]),
					tbar : [{
						id : 'tb-new-mail',
						text : '新任务',
						tooltip : '编辑新任务',
						iconCls : 'add'
					}, '-', {
						id : 'tb-inbox',
						text : '删除任务',
						tooltip : '删除选中任务',
						iconCls : 'remove'
					}, '-', {
						id : 'tb-outbox',
						text : '删除已完成的任务',
						tooltip : '删除所有已完成的任务',
						iconCls : 'remove'
					}],
					clicksToEdit : 1
				})
			});
		}
		win.show();
	}
});
