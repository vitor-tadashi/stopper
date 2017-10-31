/************************************************************************** 
* Verity TI
* --------------------------- 
* Criado por...:           Igor Cunha
* Em...........:           31/10/2017
* Projeto......:           PAUSE
* Descrição....:           Script para preenchimento das tabelas do banco PAUSE
* Tabelas envoldidas:      Tabela de dominio
**************************************************************************/ 

DECLARE @nomeScript VARCHAR(MAX);
SET @nomeScript = '01. nome do script aqui'
PRINT 'Iniciando execução script ['+ @nomeScript +']'
BEGIN TRY
    BEGIN TRANSACTION;
		USE [Pause]

		INSERT INTO PAUSETipoAfastamento values ('Férias'),
												('Licença não remunerada'),
												('Abono do Cliente'),
												('Novo Funcionario'),
												('Desligamento'),
												('Estagiario'),
												('Greve'),
												('QA360'),
												('Afastamento/INSS'),
												('Abono (Casamento e outro)');

		INSERT INTO PAUSETipoAtestado values ('Consulta médica'),
												('Consulta odontológica'),
												('Acompanhamento de consulta'),
												('Urgência odontológica'),
												('Exames');

		INSERT INTO PAUSETipoJustificativa values ('Regularização de registros'), ('Trabalho externo');

		COMMIT TRANSACTION;
 
    PRINT 'Sucesso na execução do script ['+ @nomeScript +']'
END TRY
BEGIN CATCH
		IF @@TRANCOUNT > 0
			 ROLLBACK TRANSACTION;
             
 		DECLARE @errorNumber INT;
		SET @errorNumber  = ERROR_NUMBER();
		DECLARE @errorLine INT;
		SET @errorLine = ERROR_LINE();
		DECLARE @errorMessage NVARCHAR(4000);
		SET @errorMessage = ERROR_MESSAGE();
		DECLARE @errorSeverity INT;
		SET @errorSeverity = ERROR_SEVERITY();
		DECLARE @errorState INT;
		SET @errorState = ERROR_STATE();
      
		PRINT 'ERRO na execução do script. [' + @nomeScript + ']'
		PRINT 'Número do erro: [' + CAST(@errorNumber AS VARCHAR(10)) + ']';
		PRINT 'Número da linha: [' + CAST(@errorLine AS VARCHAR(10)) + ']';
		PRINT 'Descrição do erro: [' + @errorMessage + ']';
 
		RAISERROR(@errorMessage, @errorSeverity, @errorState);
END CATCH