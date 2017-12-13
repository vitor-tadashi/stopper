$(document).ready(function() {
	localStorage.removeItem("refresh");
// FORM VALIDATION ON TABS
	// =================================================================
	$('#bv-form').bootstrapValidator({
		excluded: [':disabled'],
		fields: {
			usuario: {
				validators: {
					notEmpty: {
						message: 'O campo usuário é obrigatório.'
					}
				}
			},
			senha: {
				validators: {
					notEmpty: {
						message: 'O campo senha é obrigatório.'
					}
				}
			}
		}
	});	
});