$('.upload-demo').change(function()	{
	var filename = $(this).val().split('\\').pop();
	$(this).parent().find('span').attr('data-title',filename);
	$(this).parent().find('label').attr('data-title','Selecione');
	$(this).parent().find('label').addClass('selected');
});