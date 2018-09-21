UE.commands['systablemanage'] = function() {
	var me = this;
	function deleteIframe() {
		me._iframe && delete me._iframe;
	}

	me.addListener("selectionchange", function() {
		deleteIframe();
	});
	me.commands["systablemanage"] = {
		queryCommandState : function() {
			return this.highlight ? -1 : 0;
		}
	}
};