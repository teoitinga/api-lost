--
-- Table structure for table `compra`
--
CREATE TABLE `compra` (
  `id` int(11) NOT NULL auto_increment,
  `acertado_em` datetime default NULL,
  `data_compra` datetime default NULL,
  `debAtual` double default NULL,
  `entregue_a` varchar(255) default NULL,
  `valor_compra` double default NULL,
  `fk_cliente` int(11) default NULL,
  `entregue_por` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_clncos6iqrgwmkhb3i8pwh8tw` (`fk_cliente`),
  KEY `FK_1i8sb5pmpn5j4bm54yybhen34` (`entregue_por`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `detalhecompra`
--
CREATE TABLE `detalhecompra` (
  `id` int(11) NOT NULL auto_increment,
  `desconto` double default NULL,
  `dsc` varchar(255) default NULL,
  `qtd` double default NULL,
  `vltotal` double default NULL,
  `vlunit` double default NULL,
  `fKcompra` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_ciki2o1g5627253mmkituiomd` (`fKcompra`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `perfil`
--

CREATE TABLE `perfil` (
  `ID` varchar(10) NOT NULL default '0',
  `DSC` varchar(250) default '0',
  `DESENV` varchar(250) default '0',
  `CONTATODESV` varchar(250) default '0',
  `VERSAO` varchar(10) default '0',
  `EMAIL` varchar(30) default '0',
  `CODINSTALL` varchar(10) default NULL,
  `UPDATEKEY` varchar(10) default NULL,
  `ESTABELECIMENTO` varchar(250) default '0',
  `ENDERECOEST` varchar(250) default '0',
  `FONEEST` varchar(30) default '0',
  `RESPONSAVELEST` varchar(250) default '0',
  `MSG1EST` varchar(250) default '0',
  `MSG2EST` varchar(250) default '0',
  `CIDADEEST` varchar(50) default NULL,
  `ULTUPDATE` datetime default NULL,
  `TEMPOEXPIRA` int(2) unsigned default NULL,
  `ESTADO` enum('l','r') default NULL,
  `VALORINSTALL` double default NULL,
  `VALORMENSAL` double default NULL,
  `DRIVERBCK` char(3) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Descrição do sistema para uso em apresentação e layout';