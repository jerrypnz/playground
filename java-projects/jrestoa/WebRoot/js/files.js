/*
 * Address book window
 */
files = {};

files.sm = new Ext.grid.RowSelectionModel(),

files.cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
	header : "文件名",
	width : 120,
	dataIndex : 'fileName',
	resizable : true,
	sortable : true,
	renderer : function(fileName) {
		var imageDir = "images/fileicon/";
		var temparray = fileName.split(/\./);
		var ext = temparray[temparray.length - 1];
		var imageFile = "default.gif";
		switch (ext) {
			case "jpg" :
			case "bmp" :
			case "png" :
			case "gif" :
				imageFile = "image.gif";
				break;
			case "doc" :
				imageFile = "doc.gif";
				break;
			case "xls" :
				imageFile = "xls.gif";
				break;
			case "ppt" :
				imageFile = "ppt.gif";
				break;
			case "zip" :
			case "jar" :
			case "rar" :
			case "7z" :
			case "jar" :
				imageFile = "zip.gif";
				break;
			case "mp3" :
			case "wav" :
			case "mid" :
			case "wma" :
			case "ogg" :
			case "ape" :
				imageFile = "audio.gif";
				break;
			case "rm" :
			case "rmvb" :
			case "avi" :
			case "mov" :
			case "mkv" :
			case "wmv" :
				imageFile = "video.gif";
				break;
			case "exe" :
			case "bat" :
			case "sh" :
				imageFile = "exe.gif";
				break;
			case "html" :
			case "htm" :
			case "mht" :
				imageFile = "html.gif";
				break;
			case "pdf" :
				imageFile = "pdf.gif";
				break;

		}
		var imageURL = imageDir + imageFile;
		return "<img style='margin-right:5px;margin-bottom:-4px;'  src='"
				+ imageURL + "' />" + fileName;
	}
}, {
	header : "文件描述",
	width : 250,
	sortable : true,
	dataIndex : 'description'
}, {
	header : "下载",
	width : 20,
	sortable : true,
	dataIndex : 'filePath',
	renderer : function(value) {
		return "<a href='" + value + "'><img src='images/save.gif'></a>";
	}
}]);

files.ds = new Ext.data.Store({
	autoLoad : true,
	reader : new Ext.data.XmlReader({
		record : 'personalFile'
	}, [{
		name : 'id'
	}, {
		name : 'fileName'
	}, {
		name : 'filePath'
	}, {
		name : 'description'
	}]),
	url : 'file/list'
});

MyDesktop.FileWindow = Ext.extend(Ext.app.Module, {
	id : 'win-files',
	init : function() {
		this.launcher = {
			text : '个人文件柜',
			iconCls : 'icon-grid',
			handler : this.createWindow,
			scope : this
		}
	},

	createWindow : function() {
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow('win-files');
		if (!win) {
			win = desktop.createWindow({
				id : 'win-files',
				title : '个人文件柜',
				width : 740,
				height : 480,
				iconCls : 'icon-grid',
				shim : false,
				animCollapse : false,
				constrainHeader : true,

				layout : 'fit',
				items : new Ext.grid.GridPanel({
					border : false,
					ds : files.ds,
					cm : files.cm,
					sm : files.sm,
					viewConfig : {
						forceFit : true
					},
					tbar : [{
						text : '上传新文件',
						tooltip : '从本地选择一个文件上传到文件柜',
						iconCls : 'add',
						handler : this.uploadNewFile
					}, '-', {
						text : '删除文件',
						tooltip : '从服务器删除选中文件',
						iconCls : 'remove'
					}]
				})
			});
		}
		win.show();
	},

	uploadNewFile : function() {
		var win = Ext.get('win-files-upload');
		if (!win) {
			var form = new Ext.FormPanel({
				frame : true,
				labelAlign : 'left',
				width : 300,
				waitMsgTarget : true,
				fileUpload : true,
				errorReader : new Ext.form.XmlErrorReader(),
				items : [{
					xtype : "fieldset",
					header : false,
					border : false,
					defaultType : 'textfield',
					labelWidth : 55,
					autoHeight : true,
					items : [{
						fieldLabel : "选择文件",
						name : "theFile",
						inputType : "file",
						width : 200
					}, {
						fieldLabel : "文件描述",
						name : "description",
						xtype : 'textarea',
						width : 200
					}]
				}]
			});
			form.addButton({
				text : '上传',
				handler : function() {
					form.getForm().submit({
						url : 'fileupload',
						waitMsg : '正在上传文件到服务器……',
						success : function() {
							form.getForm().reset();
							files.ds.reload();
							Ext.MessageBox.alert('成功', '已经成功上传文件');
						},
						failure : function() {
							Ext.MessageBox.alert('错误', '无法上传文件，请检查错误信息');
						}
					});
				}
			});
			win = new Ext.Window({
				id : "win-files-upload",
				title : "撰写新邮件",
				iconCls : 'icon-grid',
				resizable : false,
				width : 320,
				height : 180,
				shim : false,
				animCollapse : false,
				layout : 'fit',
				items : form
			});
			win.show();
		}
	}

});
