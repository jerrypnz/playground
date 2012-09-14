﻿/*
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net Copyright
 * (C) 2003-2007 Frederico Caldeira Knabben == BEGIN LICENSE ==
 * 
 * Licensed under the terms of any of the following licenses at your choice: -
 * GNU General Public License Version 2 or later (the "GPL")
 * http://www.gnu.org/licenses/gpl.html - GNU Lesser General Public License
 * Version 2.1 or later (the "LGPL") http://www.gnu.org/licenses/lgpl.html -
 * Mozilla Public License Version 1.1 or later (the "MPL")
 * http://www.mozilla.org/MPL/MPL-1.1.html == END LICENSE ==
 * 
 * Editor configuration settings.
 * 
 * Follow this link for more information:
 * http://wiki.fckeditor.net/Developer%27s_Guide/Configuration/Configurations_Settings
 */

FCKConfig.CustomConfigurationsPath = '';

FCKConfig.EditorAreaCSS = FCKConfig.BasePath + 'css/fck_editorarea.css';
FCKConfig.EditorAreaStyles = '';
FCKConfig.ToolbarComboPreviewCSS = '';

FCKConfig.DocType = '';

FCKConfig.BaseHref = '';

FCKConfig.FullPage = false;

// The following option determines whether the "Show Blocks" feature is enabled
// or not at startup.
FCKConfig.StartupShowBlocks = false;

FCKConfig.Debug = false;
FCKConfig.AllowQueryStringDebug = true;

FCKConfig.SkinPath = FCKConfig.BasePath + 'skins/default/';
FCKConfig.PreloadImages = [FCKConfig.SkinPath + 'images/toolbar.start.gif',
		FCKConfig.SkinPath + 'images/toolbar.buttonarrow.gif'];

FCKConfig.PluginsPath = FCKConfig.BasePath + 'plugins/';

// FCKConfig.Plugins.Add( 'autogrow' ) ;
// FCKConfig.Plugins.Add( 'dragresizetable' );
FCKConfig.AutoGrowMax = 400;

// FCKConfig.ProtectedSource.Add( /<%[\s\S]*?%>/g ) ; // ASP style server side
// code <%...%>
// FCKConfig.ProtectedSource.Add( /<\?[\s\S]*?\?>/g ) ; // PHP style server side
// code
// FCKConfig.ProtectedSource.Add(
// /(<asp:[^\>]+>[\s|\S]*?<\/asp:[^\>]+>)|(<asp:[^\>]+\/>)/gi ) ; // ASP.Net
// style tags <asp:control>

FCKConfig.AutoDetectLanguage = true;
FCKConfig.DefaultLanguage = 'zh-cn';
FCKConfig.ContentLangDirection = 'ltr';

FCKConfig.ProcessHTMLEntities = true;
FCKConfig.IncludeLatinEntities = true;
FCKConfig.IncludeGreekEntities = true;

FCKConfig.ProcessNumericEntities = false;

FCKConfig.AdditionalNumericEntities = ''; // Single Quote: "'"

FCKConfig.FillEmptyBlocks = true;

FCKConfig.FormatSource = true;
FCKConfig.FormatOutput = true;
FCKConfig.FormatIndentator = '    ';

FCKConfig.StartupFocus = false;
FCKConfig.ForcePasteAsPlainText = false;
FCKConfig.AutoDetectPasteFromWord = true; // IE only.
FCKConfig.ShowDropDialog = true;
FCKConfig.ForceSimpleAmpersand = false;
FCKConfig.TabSpaces = 0;
FCKConfig.ShowBorders = true;
FCKConfig.SourcePopup = false;
FCKConfig.ToolbarStartExpanded = true;
FCKConfig.ToolbarCanCollapse = false;
FCKConfig.IgnoreEmptyParagraphValue = true;
FCKConfig.PreserveSessionOnFileBrowser = false;
FCKConfig.FloatingPanelsZIndex = 10000;
FCKConfig.HtmlEncodeOutput = false;

FCKConfig.TemplateReplaceAll = true;
FCKConfig.TemplateReplaceCheckbox = true;

FCKConfig.ToolbarLocation = 'In';

FCKConfig.ToolbarSets["Basic"] = [['FontName', 'FontSize', '-', 'Bold',
		'Italic', '-', 'OrderedList', 'UnorderedList', '-', 'JustifyLeft',
		'JustifyCenter', 'JustifyRight', '-', 'TextColor', 'BGColor', '-',
		'Link', 'Unlink', '-', 'Image', 'Smiley']];

FCKConfig.EnterMode = 'p'; // p | div | br
FCKConfig.ShiftEnterMode = 'br'; // p | div | br

FCKConfig.Keystrokes = [[CTRL + 65 /* A */, true], [CTRL + 67 /* C */, true],
		[CTRL + 70 /* F */, true], [CTRL + 83 /* S */, true],
		[CTRL + 88 /* X */, true], [CTRL + 86 /* V */, 'Paste'],
		[SHIFT + 45 /* INS */, 'Paste'], [CTRL + 88 /* X */, 'Cut'],
		[SHIFT + 46 /* DEL */, 'Cut'], [CTRL + 90 /* Z */, 'Undo'],
		[CTRL + 89 /* Y */, 'Redo'], [CTRL + SHIFT + 90 /* Z */, 'Redo'],
		[CTRL + 76 /* L */, 'Link'], [CTRL + 66 /* B */, 'Bold'],
		[CTRL + 73 /* I */, 'Italic'], [CTRL + 85 /* U */, 'Underline'],
		[CTRL + SHIFT + 83 /* S */, 'Save'],
		[CTRL + ALT + 13 /* ENTER */, 'FitWindow'],
		[CTRL + 9 /* TAB */, 'Source']];

FCKConfig.ContextMenu = ['Generic', 'Link', 'Anchor', 'Image', 'Flash',
		'Select', 'Textarea', 'Checkbox', 'Radio', 'TextField', 'HiddenField',
		'ImageButton', 'Button', 'BulletedList', 'NumberedList', 'Table',
		'Form'];
FCKConfig.BrowserContextMenuOnCtrl = false;

FCKConfig.EnableMoreFontColors = true;
FCKConfig.FontColors = '000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,808080,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF';

FCKConfig.FontFormats = 'p;h1;h2;h3;h4;h5;h6;pre;address;div';
FCKConfig.FontNames = 'Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana';
FCKConfig.FontSizes = 'smaller;larger;xx-small;x-small;small;medium;large;x-large;xx-large';

FCKConfig.StylesXmlPath = FCKConfig.EditorPath + 'fckstyles.xml';
FCKConfig.TemplatesXmlPath = FCKConfig.EditorPath + 'fcktemplates.xml';

FCKConfig.MaxUndoLevels = 15;

FCKConfig.DisableObjectResizing = false;
FCKConfig.DisableFFTableHandles = true;

FCKConfig.LinkDlgHideTarget = true;
FCKConfig.LinkDlgHideAdvanced = true;

FCKConfig.ImageDlgHideLink = true;
FCKConfig.ImageDlgHideInfo = true;
FCKConfig.ImageDlgHideAdvanced = true;

FCKConfig.FlashDlgHideAdvanced = true;

FCKConfig.ProtectedTags = '';

// This will be applied to the body element of the editor
FCKConfig.BodyId = '';
FCKConfig.BodyClass = '';

FCKConfig.DefaultStyleLabel = '';
FCKConfig.DefaultFontFormatLabel = '';
FCKConfig.DefaultFontLabel = '';
FCKConfig.DefaultFontSizeLabel = '';

FCKConfig.DefaultLinkTarget = '';

// The option switches between trying to keep the html structure or do the
// changes so the content looks like it was in Word
FCKConfig.CleanWordKeepsStructure = false;

// Only inline elements are valid.
FCKConfig.RemoveFormatTags = 'b,big,code,del,dfn,em,font,i,ins,kbd,q,samp,small,span,strike,strong,sub,sup,tt,u,var';

FCKConfig.CustomStyles = {
	'Red Title' : {
		Element : 'h3',
		Styles : {
			'color' : 'Red'
		}
	}
};

FCKConfig.CoreStyles = {
	// Basic Inline Styles.
	'Bold' : {
		Element : 'b',
		Overrides : 'strong'
	},
	'Italic' : {
		Element : 'i',
		Overrides : 'em'
	},
	'Underline' : {
		Element : 'u'
	},
	'StrikeThrough' : {
		Element : 'strike'
	},
	'Subscript' : {
		Element : 'sub'
	},
	'Superscript' : {
		Element : 'sup'
	},

	'p' : {
		Element : 'p'
	},
	'div' : {
		Element : 'div'
	},
	'pre' : {
		Element : 'pre'
	},
	'address' : {
		Element : 'address'
	},
	'h1' : {
		Element : 'h1'
	},
	'h2' : {
		Element : 'h2'
	},
	'h3' : {
		Element : 'h3'
	},
	'h4' : {
		Element : 'h4'
	},
	'h5' : {
		Element : 'h5'
	},
	'h6' : {
		Element : 'h6'
	},
	'FontFace' : {
		Element : 'span',
		Styles : {
			'font-family' : '#("Font")'
		},
		Overrides : [{
			Element : 'font',
			Attributes : {
				'face' : null
			}
		}]
	},

	'Size' : {
		Element : 'span',
		Styles : {
			'font-size' : '#("Size","fontSize")'
		},
		Overrides : [{
			Element : 'font',
			Attributes : {
				'size' : null
			}
		}]
	},

	'Color' : {
		Element : 'span',
		Styles : {
			'color' : '#("Color","color")'
		},
		Overrides : [{
			Element : 'font',
			Attributes : {
				'color' : null
			}
		}]
	},

	'BackColor' : {
		Element : 'span',
		Styles : {
			'background-color' : '#("Color","color")'
		}
	}
};
FCKConfig.IndentLength = 40;
FCKConfig.IndentUnit = 'px';
FCKConfig.IndentClasses = [];
FCKConfig.JustifyClasses = [];
FCKConfig.ImageUpload = true;
FCKConfig.ImageUploadURL = FCKConfig.BasePath
		+ 'filemanager/upload/simpleuploader?Type=Image';
FCKConfig.ImageUploadAllowedExtensions = ".(jpg|gif|jpeg|png|bmp)$"; // empty
FCKConfig.ImageUploadDeniedExtensions = ""; // empty for no one
FCKConfig.SmileyPath = FCKConfig.BasePath + 'images/smiley/msn/';
FCKConfig.SmileyImages = ['regular_smile.gif', 'sad_smile.gif',
		'wink_smile.gif', 'teeth_smile.gif', 'confused_smile.gif',
		'tounge_smile.gif', 'embaressed_smile.gif', 'omg_smile.gif',
		'whatchutalkingabout_smile.gif', 'angry_smile.gif', 'angel_smile.gif',
		'shades_smile.gif', 'devil_smile.gif', 'cry_smile.gif',
		'lightbulb.gif', 'thumbs_down.gif', 'thumbs_up.gif', 'heart.gif',
		'broken_heart.gif', 'kiss.gif', 'envelope.gif'];
FCKConfig.SmileyColumns = 8;
FCKConfig.SmileyWindowWidth = 320;
FCKConfig.SmileyWindowHeight = 240;
