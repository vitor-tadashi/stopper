(function(e) {
	e.fn.bootstrapValidator.validators.range = {
		html5Attributes : {
			message : "message"
		},
		validate : function(e, t, n) {
			var startDate = document.getElementById("periodoDe").value;
			var endDate = document.getElementById("periodoAte").value;

			if ((Date.parse(endDate) < Date.parse(startDate))) {
				return false
			}
			$('#bv-form').data('bootstrapValidator').isValid();
			return true;
		}
	}
})(window.jQuery);