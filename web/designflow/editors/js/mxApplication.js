/*
 * $Id: mxApplication.js,v 1.21 2010-11-08 20:09:40 gaudenz Exp $
 * Copyright (c) 2006-2010, JGraph Ltd
 *
 * Defines the startup sequence of the application.
 *
 */
{

	/**
	 * Constructs a new application (note that this returns an mxEditor
	 * instance).
	 */
	function mxApplication(config,flag,id,xml)
	{
		var hideSplash = function()
		{
			// Fades-out the splash screen
			var splash = document.getElementById('splash');

			if (splash != null)
			{
				try
				{
					mxEvent.release(splash);
					mxEffects.fadeOut(splash, 100, true);
				}
				catch (e)
				{
					splash.parentNode.removeChild(splash);
				}
			}
		};

		try
		{
			if (!mxClient.isBrowserSupported())
			{
				mxUtils.error('Browser is not supported!', 200, false);
			}
			else
			{
				var node = mxUtils.load(config).getDocumentElement();
				var editor = new mxEditor(node);
				//onlyeditor = editor;

				// Updates the window title after opening new files
				var title = document.title;
				var funct = function(sender)
				{
					document.title = title + ' - ' + sender.getTitle();
				};
				//$.load('${ctx}/datadriver/designflow/projectflow.ht')
				editor.addListener(mxEvent.OPEN, funct);

				// Prints the current root in the window title if the
				// current root of the graph changes (drilling).
				editor.addListener(mxEvent.ROOT, funct);
				funct(editor);

				// Displays version in statusbar
				editor.setStatus('mxGraph '+mxClient.VERSION);
				//var s=${projectId};
				editor.graph.getDefaultParent().setAttribute('projectId',id);
				var xmlcontent = mxUtils.parseXml(xml);
				editor.readGraphModel(xmlcontent.documentElement);
				//editor.filename=filename;
				var enc=new mxCodec();
				//window.console.log(mxUtils.getPrettyXml(enc.encode(editor.graph.getModel())));
				// Shows the application
				hideSplash();
				if(flag==1) mxUtils.show(editor.graph, document, 10, 10);


			}
		}
		catch (e)
		{
			hideSplash();

			// Shows an error message if the editor cannot start
			//mxUtils.alert('Cannot start application: '+e.message);
			//throw e; // for debugging
		}

		return editor;
	}

}
