USE [Pause]
GO
/****** Object:  Table [dbo].[PAUSEAfastamento]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAUSEAfastamento](
	[idAfastamento] [int] IDENTITY(1,1) NOT NULL,
	[dataInicio] [date] NULL,
	[dataFim] [date] NULL,
	[dataInclusao] [date] NULL,
	[idFuncionario] [int] NULL,
	[idTipoAfastamento] [int] NULL,
	[idUsuarioInclusao] [int] NULL,
 CONSTRAINT [PK_PAUSEAfastamento] PRIMARY KEY CLUSTERED 
(
	[idAfastamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PAUSEApontamento]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PAUSEApontamento](
	[idApontamento] [int] IDENTITY(1,1) NOT NULL,
	[data] [date] NULL,
	[horario] [time](0) NULL,
	[tipoImportacao] [bit] NULL,
	[dataInclusao] [date] NULL,
	[observacao] [varchar](100) NULL,
	[idTipoJustificativa] [int] NULL,
	[idControleDiario] [int] NULL,
	[idEmpresa] [int] NULL,
	[idUsuarioInclusao] [int] NULL,
	[idArquivoApontamento] [int] NULL,
 CONSTRAINT [PK_PAUSEApontamentos] PRIMARY KEY CLUSTERED 
(
	[idApontamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PAUSEArquivoApontamento]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PAUSEArquivoApontamento](
	[idArquivoApontamento] [int] IDENTITY(1,1) NOT NULL,
	[data] [date] NULL,
	[caminho] [varchar](500) NULL,
	[dataInclusao] [date] NULL,
	[idEmpresa] [int] NULL,
	[idUsuarioInclusao] [int] NULL,
 CONSTRAINT [PK_PAUSEArquivoApontamento] PRIMARY KEY CLUSTERED 
(
	[idArquivoApontamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PAUSEAtestado]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAUSEAtestado](
	[idAtestado] [int] IDENTITY(1,1) NOT NULL,
	[quantidadeHora] [float] NULL,
	[idControleDiario] [int] NULL,
	[dataInclusao] [date] NULL,
	[idUsuarioInclusao] [int] NULL,
	[idTipoAtestado] [int] NULL,
 CONSTRAINT [PK_PAUSEAtestado] PRIMARY KEY CLUSTERED 
(
	[idAtestado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PAUSEControleDiario]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAUSEControleDiario](
	[idControleDiario] [int] IDENTITY(1,1) NOT NULL,
	[horaTotal] [float] NULL,
	[bancoHora] [float] NULL,
	[adcNoturno] [float] NULL,
	[sobreAviso] [float] NULL,
	[sobreAvisoTrabalhado] [float] NULL,
	[data] [date] NULL,
	[idControleMensal] [int] NULL,
 CONSTRAINT [PK_PAUSEControleDiario] PRIMARY KEY CLUSTERED 
(
	[idControleDiario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PAUSEControleMensal]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAUSEControleMensal](
	[idControleMensal] [int] IDENTITY(1,1) NOT NULL,
	[horaTotal] [float] NULL,
	[bancoHora] [float] NULL,
	[adcNoturno] [float] NULL,
	[sobreAviso] [float] NULL,
	[sobreAvisoTrabalhado] [float] NULL,
	[mes] [int] NULL,
	[ano] [int] NULL,
	[idFuncionario] [int] NULL,
 CONSTRAINT [PK_PAUSEControleMensal] PRIMARY KEY CLUSTERED 
(
	[idControleMensal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PAUSESobreAviso]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PAUSESobreAviso](
	[idSobreAviso] [int] IDENTITY(1,1) NOT NULL,
	[horaInicio] [time](0) NULL,
	[horaFim] [time](0) NULL,
	[dataInclusao] [date] NULL,
	[idControleDiario] [int] NULL,
	[idUsuarioInclusao] [int] NULL,
	[data] [date] NULL,
 CONSTRAINT [PK_PAUSESobreAviso] PRIMARY KEY CLUSTERED 
(
	[idSobreAviso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PAUSETipoAfastamento]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PAUSETipoAfastamento](
	[idTipoAfastamento] [int] IDENTITY(1,1) NOT NULL,
	[descricao] [varchar](100) NULL,
 CONSTRAINT [PK_PAUSETipoAfastamento] PRIMARY KEY CLUSTERED 
(
	[idTipoAfastamento] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PAUSETipoAtestado]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PAUSETipoAtestado](
	[idTipoAtestado] [int] IDENTITY(1,1) NOT NULL,
	[descricao] [varchar](100) NOT NULL,
 CONSTRAINT [PK_PAUSETipoAtestado] PRIMARY KEY CLUSTERED 
(
	[idTipoAtestado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PAUSETipoJustificativa]    Script Date: 27/10/2017 17:52:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PAUSETipoJustificativa](
	[idTipoJustificativa] [int] IDENTITY(1,1) NOT NULL,
	[descricao] [varchar](50) NULL,
 CONSTRAINT [PK_PAUSEMotivo] PRIMARY KEY CLUSTERED 
(
	[idTipoJustificativa] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[PAUSEAfastamento]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEAfastamento_PAUSETipoAfastamento] FOREIGN KEY([idTipoAfastamento])
REFERENCES [dbo].[PAUSETipoAfastamento] ([idTipoAfastamento])
GO
ALTER TABLE [dbo].[PAUSEAfastamento] CHECK CONSTRAINT [FK_PAUSEAfastamento_PAUSETipoAfastamento]
GO
ALTER TABLE [dbo].[PAUSEApontamento]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEApontamento_PAUSEArquivoApontamento] FOREIGN KEY([idArquivoApontamento])
REFERENCES [dbo].[PAUSEArquivoApontamento] ([idArquivoApontamento])
GO
ALTER TABLE [dbo].[PAUSEApontamento] CHECK CONSTRAINT [FK_PAUSEApontamento_PAUSEArquivoApontamento]
GO
ALTER TABLE [dbo].[PAUSEApontamento]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEApontamentos_PAUSEControleDiario] FOREIGN KEY([idControleDiario])
REFERENCES [dbo].[PAUSEControleDiario] ([idControleDiario])
GO
ALTER TABLE [dbo].[PAUSEApontamento] CHECK CONSTRAINT [FK_PAUSEApontamentos_PAUSEControleDiario]
GO
ALTER TABLE [dbo].[PAUSEApontamento]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEApontamentos_PAUSEMotivo] FOREIGN KEY([idTipoJustificativa])
REFERENCES [dbo].[PAUSETipoJustificativa] ([idTipoJustificativa])
GO
ALTER TABLE [dbo].[PAUSEApontamento] CHECK CONSTRAINT [FK_PAUSEApontamentos_PAUSEMotivo]
GO
ALTER TABLE [dbo].[PAUSEAtestado]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEAtestado_PAUSEAtestado] FOREIGN KEY([idControleDiario])
REFERENCES [dbo].[PAUSEControleDiario] ([idControleDiario])
GO
ALTER TABLE [dbo].[PAUSEAtestado] CHECK CONSTRAINT [FK_PAUSEAtestado_PAUSEAtestado]
GO
ALTER TABLE [dbo].[PAUSEAtestado]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEAtestado_PAUSETipoAtestado] FOREIGN KEY([idTipoAtestado])
REFERENCES [dbo].[PAUSETipoAtestado] ([idTipoAtestado])
GO
ALTER TABLE [dbo].[PAUSEAtestado] CHECK CONSTRAINT [FK_PAUSEAtestado_PAUSETipoAtestado]
GO
ALTER TABLE [dbo].[PAUSEControleDiario]  WITH CHECK ADD  CONSTRAINT [FK_PAUSEControleDiario_PAUSEControleMensal] FOREIGN KEY([idControleMensal])
REFERENCES [dbo].[PAUSEControleMensal] ([idControleMensal])
GO
ALTER TABLE [dbo].[PAUSEControleDiario] CHECK CONSTRAINT [FK_PAUSEControleDiario_PAUSEControleMensal]
GO
ALTER TABLE [dbo].[PAUSESobreAviso]  WITH CHECK ADD  CONSTRAINT [FK_PAUSESobreAviso_PAUSEControleDiario] FOREIGN KEY([idControleDiario])
REFERENCES [dbo].[PAUSEControleDiario] ([idControleDiario])
GO
ALTER TABLE [dbo].[PAUSESobreAviso] CHECK CONSTRAINT [FK_PAUSESobreAviso_PAUSEControleDiario]
GO
