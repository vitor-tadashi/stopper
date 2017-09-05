/************************************************************************** 
* Verity TI
* --------------------------- 
* Criado por...:           Guilherme de Oliveira e Igor Cunha
* Em...........:           31/08/2017
* Projeto......:           PAUSE
* Descrição....:           Script para criação do banco PAUSE
* Tabelas envoldidas:      todas
**************************************************************************/ 
use Pause
DECLARE @nomeScript VARCHAR(MAX);
SET @nomeScript = '01. nome do script aqui'
PRINT 'Iniciando execução script ['+ @nomeScript +']'
BEGIN TRY
    BEGIN TRANSACTION;

	CREATE TABLE PAUSEArquivoApontamento(
		idArquivoApontamento int IDENTITY(1,1) NOT NULL,
		data date NULL,
		caminho varchar(500) NULL,
		dataInclusao date NULL,
		idEmpresa int NULL,
		idUsuarioInclusao int NULL,
	 CONSTRAINT PK_PAUSEArquivoApontamento PRIMARY KEY CLUSTERED 
	(
		idArquivoApontamento ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSEAfastamento(
		idAfastamento int IDENTITY(1,1) NOT NULL,
		dataInicio date NULL,
		dataFim date NULL,
		dataInclusao date NULL,
		idFuncionario int NULL,
		idTipoAfastamento int NULL,
		idUsuarioInclusao int NULL,
	 CONSTRAINT PK_PAUSEAfastamento PRIMARY KEY CLUSTERED 
	(
		idAfastamento ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSEApontamento(
		idApontamento int IDENTITY(1,1) NOT NULL,
		pis varchar(13) NULL,
		data date NULL,
		horario time(0) NULL,
		tipoImportacao bit NULL,
		dataInclusao date NULL,
		observacao varchar(100) NULL,
		idTipoJustificativa int NULL,
		idControleDiario int NULL,
		idEmpresa int NULL,
		idUsuarioInclusao int NULL,
		idArquivoApontamento int NULL,
	 CONSTRAINT PK_PAUSEApontamentos PRIMARY KEY CLUSTERED 
	(
		idApontamento ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSEAtestado(
		idAtestado int IDENTITY(1,1) NOT NULL,
		quantidadeHora nchar(10) NULL,
		idControleDiario int NULL,
	 CONSTRAINT PK_PAUSEAtestado PRIMARY KEY CLUSTERED 
	(
		idAtestado ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSEControleDiario(
		idControleDiario int IDENTITY(1,1) NOT NULL,
		horaTotal float NULL,
		bancoHora float NULL,
		adcNoturno float NULL,
		sobreAviso float NULL,
		sobreAvisoTrabalhado float NULL,
		data date NULL,
		idControleMensal int NULL,
	 CONSTRAINT PK_PAUSEControleDiario PRIMARY KEY CLUSTERED 
	(
		idControleDiario ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSEControleMensal(
		idControleMensal int IDENTITY(1,1) NOT NULL,
		horaTotal float NULL,
		bancoHora float NULL,
		adcNoturno float NULL,
		sobreAviso float NULL,
		sobreAvisoTrabalhado float NULL,
		mes int NULL,
		ano int NULL,
		idFuncionario int NULL,
	 CONSTRAINT PK_PAUSEControleMensal PRIMARY KEY CLUSTERED 
	(
		idControleMensal ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSESobreAviso(
		idSobreAviso int IDENTITY(1,1) NOT NULL,
		horaInicio time(0) NULL,
		horaFim time(0) NULL,
		dataInclusao date NULL,
		idControleDiario int NULL,
		idUsuarioInclusao int NULL,
		data date NULL,
	 CONSTRAINT PK_PAUSESobreAviso PRIMARY KEY CLUSTERED 
	(
		idSobreAviso ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSETipoAfastamento(
		idTipoAfastamento int IDENTITY(1,1) NOT NULL,
		descricao varchar(100) NULL,
	 CONSTRAINT PK_PAUSETipoAfastamento PRIMARY KEY CLUSTERED 
	(
		idTipoAfastamento ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]

	CREATE TABLE PAUSETipoJustificativa(
		idTipoJustificativa int IDENTITY(1,1) NOT NULL,
		descricao varchar(50) NULL,
	 CONSTRAINT PK_PAUSEMotivo PRIMARY KEY CLUSTERED 
	(
		idTipoJustificativa ASC
	)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
	) ON [PRIMARY]


	ALTER TABLE PAUSEApontamento  WITH CHECK ADD  CONSTRAINT FK_PAUSEApontamento_PAUSEArquivoApontamento FOREIGN KEY(idArquivoApontamento)
	REFERENCES PAUSEArquivoApontamento (idArquivoApontamento)

	ALTER TABLE PAUSEApontamento CHECK CONSTRAINT FK_PAUSEApontamento_PAUSEArquivoApontamento

	ALTER TABLE PAUSEApontamento  WITH CHECK ADD  CONSTRAINT FK_PAUSEApontamentos_PAUSEControleDiario FOREIGN KEY(idControleDiario)
	REFERENCES PAUSEControleDiario (idControleDiario)

	ALTER TABLE PAUSEApontamento CHECK CONSTRAINT FK_PAUSEApontamentos_PAUSEControleDiario

	ALTER TABLE PAUSEApontamento  WITH CHECK ADD  CONSTRAINT FK_PAUSEApontamentos_PAUSEMotivo FOREIGN KEY(idTipoJustificativa)
	REFERENCES PAUSETipoJustificativa (idTipoJustificativa)

	ALTER TABLE PAUSEApontamento CHECK CONSTRAINT FK_PAUSEApontamentos_PAUSEMotivo

	ALTER TABLE PAUSEAfastamento  WITH CHECK ADD  CONSTRAINT FK_PAUSEAfastamento_PAUSETipoAfastamento FOREIGN KEY(idTipoAfastamento)
	REFERENCES PAUSETipoAfastamento (idTipoAfastamento)

	ALTER TABLE PAUSEAfastamento CHECK CONSTRAINT FK_PAUSEAfastamento_PAUSETipoAfastamento

	ALTER TABLE PAUSEAtestado  WITH CHECK ADD  CONSTRAINT FK_PAUSEAtestado_PAUSEAtestado FOREIGN KEY(idControleDiario)
	REFERENCES PAUSEControleDiario (idControleDiario)

	ALTER TABLE PAUSEAtestado CHECK CONSTRAINT FK_PAUSEAtestado_PAUSEAtestado

	ALTER TABLE PAUSEControleDiario  WITH CHECK ADD  CONSTRAINT FK_PAUSEControleDiario_PAUSEControleMensal FOREIGN KEY(idControleMensal)
	REFERENCES PAUSEControleMensal (idControleMensal)

	ALTER TABLE PAUSEControleDiario CHECK CONSTRAINT FK_PAUSEControleDiario_PAUSEControleMensal

	ALTER TABLE PAUSESobreAviso  WITH CHECK ADD  CONSTRAINT FK_PAUSESobreAviso_PAUSEControleDiario FOREIGN KEY(idControleDiario)
	REFERENCES PAUSEControleDiario (idControleDiario)

	ALTER TABLE PAUSESobreAviso CHECK CONSTRAINT FK_PAUSESobreAviso_PAUSEControleDiario

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