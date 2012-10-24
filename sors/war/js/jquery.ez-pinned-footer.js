/******************************************************
	* jQuery plug-in
	* Easy Pinned Footer
	* Developed by J.P. Given (http://johnpatrickgiven.com)
	* Useage: anyone so long as credit is left alone
******************************************************/
(function($) {
	// plugin definition
	$.fn.pinFooter = function(options) {		
		// Get the height of the footer and window + window width
		var wH = getWindowHeight();
		wW = getWindowWidth();
		var fH = $("#footer").outerHeight(true);
		var bH = $("body").outerHeight(true);
		var mB = parseInt($("body").css("margin-bottom"));
		
		if (options == 'relative') {
			if (bH > getWindowHeight()) {
				$("#footer").css("position","absolute");
				$("#footer").css("width",wW + "px");
				$("#footer").css("top",bH - fH + "px");
				$("body").css("overflow-x","hidden");
			} else {
				$("#footer").css("position","fixed");
				$("#footer").css("width",wW + "px");
				$("#footer").css("top",wH - fH + "px");
			}
		} else { // Pinned option
			// Set CSS attributes for positioning footer
			$("#footer").css("position","fixed");
			$("#footer").css("width",wW + "px");
			$("#footer").css("top",wH - fH + "px");
			$("body").css("height",(bH + mB) + "px");
		}
	};
	
	// private function for debugging
	function debug($obj) {
		if (window.console && window.console.log) {
			window.console.log('Window Width: ' + $(window).width());
			window.console.log('Window Height: ' + $(window).height());
		}
	};
	
	// Dependable function to get Window Height
	function getWindowHeight() {
		var windowHeight = 0;
		if (typeof(window.innerHeight) == 'number') {
			windowHeight = window.innerHeight;
		}
		else {
			if (document.documentElement && document.documentElement.clientHeight) {
				windowHeight = document.documentElement.clientHeight;
			}
			else {
				if (document.body && document.body.clientHeight) {
					windowHeight = document.body.clientHeight;
				}
			}
		}
		return windowHeight;
	};
	
	// Dependable function to get Window Width
	function getWindowWidth() {
		var windowWidth = 0;
		if (typeof(window.innerWidth) == 'number') {
			windowWidth = window.innerWidth;
		}
		else {
			if (document.documentElement && document.documentElement.clientWidth) {
				windowWidth = document.documentElement.clientWidth;
			}
			else {
				if (document.body && document.body.clientWidth) {
					windowWidth = document.body.clientWidth;
				}
			}
		}
		return windowWidth;
	};
})(jQuery);