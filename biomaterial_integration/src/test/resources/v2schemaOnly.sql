-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.6.14 - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.4.0.5151
-- --------------------------------------------------------

SET NAMES utf8;
-- Дамп структуры базы данных laboratory_integration
DROP DATABASE IF EXISTS `laboratory_integration`;
CREATE DATABASE `laboratory_integration` DEFAULT CHARACTER SET utf8;
USE `laboratory_integration`;

-- ------------------------------------------------------------------------------------------------
-- Таблицы справочники
-- ------------------------------------------------------------------------------------------------
CREATE TABLE `rbLaboratory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT 'Код',
  `name` varchar(1024) NOT NULL COMMENT 'Наименование',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Лабораторные системы';

-- ------------------------------------------------------------------------------------------------
-- Таблицы для сохранения состояний передаваемых данных
-- ------------------------------------------------------------------------------------------------

CREATE TABLE `Biomaterial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `externalId` varchar(128) NOT NULL COMMENT 'Внешний идентифкатор ТМИС ',
  `barcodePrefix` varchar(50) NOT NULL COMMENT 'префикс ШК',
  `barcodeNumber` varchar(50) NOT NULL COMMENT 'номер ШК',
  `plannedDateTime` datetime NOT NULL COMMENT 'Планируемая дата биозабора (на когда назначен)',
  `facticalDateTime` datetime DEFAULT NULL COMMENT 'Фактическая дата биозабора (когда материал забран у пациента)',
  `amount` VARCHAR(128) NOT NULL COMMENT 'Количество забранного материала {Measurement}',
  `testTubeType` VARCHAR(128) NOT NULL COMMENT 'Тип пробирки {rbTestTubeType}',
  `biomaterialType` VARCHAR(128) NOT NULL COMMENT 'Тип биоматериала {rbBiomaterialType}',
  `person` VARCHAR(256) NOT NULL COMMENT 'Врач, выполнивший биозабор {Person}',
  `note` varchar(50) DEFAULT NULL COMMENT 'Примечание к биозабору',
  PRIMARY KEY (`id`),
  INDEX `barcode` (`barcodeNumber`,`barcodePrefix`),
  INDEX `externalId` (`externalId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Биоматериал';

CREATE TABLE `Message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `correlationId` char(36) NOT NULL COMMENT 'Dashed-string UUID (length=36)',
  `direction` enum('IN','OUT') NOT NULL COMMENT 'Направление запроса (IN - входящий, OUT-исходящий)',
  `routingKey` varchar(64) NOT NULL COMMENT 'Ключ с которым поступило или было отправлено сообщение',
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Временная метка сообщения',
  `type` varchar(64) NOT NULL COMMENT 'AMQP property',
  `body` mediumtext NOT NULL COMMENT 'Сами данные сообщения',
  `biomaterial_id` int(11) NOT NULL COMMENT 'Ссылка на биоматериал, к которому относится сообщение',
  PRIMARY KEY (`id`),
  INDEX `correlationId` (`correlationId`),
  CONSTRAINT `FK_Message__Biomaterial` FOREIGN KEY (`biomaterial_id`) REFERENCES `Biomaterial` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Таблица входящих и исходящих сообщений';

CREATE TABLE `Research` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `externalId` varchar(128) NOT NULL COMMENT 'Внутренний идентифкатор ТМИС ',
  `biomaterial_id` int(11) NOT NULL COMMENT 'Биоматериал, используемый в ходе исследования {Biomaterial}',
  `message_id` int(11) NOT NULL COMMENT 'Сообщение в котором запрошено это исследование',
  `cancelled` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'флаг отмены этого исследования',
  `researchType` VARCHAR(128) DEFAULT NULL COMMENT 'Тип исследования {rbResearchType}',
  `urgent` tinyint(4) NOT NULL COMMENT 'Признак срочности',
  `assigner` VARCHAR(128) DEFAULT NULL COMMENT 'Ответственный  за исследование врач {Person}',
  `begDate` datetime DEFAULT NULL COMMENT 'Дата начала исследования',
  `endDate` datetime DEFAULT NULL COMMENT 'Дата окончания исследования',
  `note` varchar(4096) DEFAULT NULL COMMENT 'Примечание',
  PRIMARY KEY (`id`),
  INDEX `externalId` (`externalId`),
  CONSTRAINT `FK_Research_Biomaterial` FOREIGN KEY (`biomaterial_id`) REFERENCES `Biomaterial` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_Research_Message` FOREIGN KEY (`message_id`) REFERENCES `Message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Исследование';

CREATE TABLE `Test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `research_id` int(11) NOT NULL COMMENT 'Исследование, в котором запрошен индикатор {Research}',
  `testType` VARCHAR(128) NOT NULL COMMENT 'Тип запрошенного теста {rbTestType}',
  `externalId` varchar(128) NOT NULL COMMENT 'Внутренний идентифкатор ТМИС ',
  PRIMARY KEY (`id`),
  INDEX `externalId` (`externalId`),
  CONSTRAINT `FK_Test_Research` FOREIGN KEY (`research_id`) REFERENCES `Research` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Показатель';

-- ------------------------------------------------------------------------------------------------
-- Таблицы с маппингом лабораторий на справочники
-- ------------------------------------------------------------------------------------------------
CREATE TABLE `mapBiomaterialTypeToLaboratory` (
	`laboratory_id` INT(11) NOT NULL COMMENT 'Лаборатория {rbLaboratory}',
	`code` VARCHAR(64) NOT NULL COMMENT 'Тип биоматериала {rbBiomaterialType}',
	`replaceCode` VARCHAR(64) DEFAULT NULL COMMENT 'Заменить код на указанный (NULL-не менять)',
	`replaceName` VARCHAR(1024) DEFAULT NULL COMMENT 'Заменить наименование на указанное (NULL-не менять)',
    PRIMARY KEY (`laboratory_id`, `code`),
    CONSTRAINT `FK_mmBiomaterialTypeToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Список переопределений кодов и наименований типов БМ {rbBiomaterialType} при их отправке в ЛИС. Если нет привязки к ЛИС, отправляется без изменений';

CREATE TABLE `mapTestTubeTypeToLaboratory` (
	`laboratory_id` INT(11) NOT NULL COMMENT 'Лаборатория {rbLaboratory}',
	`code` VARCHAR(64) NOT NULL COMMENT 'Тип пробирки {rbTestTubeType}',
	`replaceCode` VARCHAR(64) NULL DEFAULT NULL COMMENT 'Заменить код на указанный (NULL-не менять)',
	`replaceName` VARCHAR(1024) NULL DEFAULT NULL COMMENT 'Заменить наименование на указанное (NULL-не менять)',
    PRIMARY KEY (`laboratory_id`, `code`),
    CONSTRAINT `FK_mmTestTubeTypeToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Список переопределений кодов и наименований типов пробирок {rbTestTubeType} при их отправке в ЛИС. Если нет привязки к ЛИС, отправляется без изменений';

CREATE TABLE `mapResearchTypeToLaboratory` (
	`laboratory_id` INT(11) NOT NULL COMMENT 'Лаборатория {rbLaboratory}',
	`code` VARCHAR(64) NOT NULL COMMENT 'Тип исследования {rbResearchType}',
	`replaceCode` VARCHAR(64) NULL DEFAULT NULL COMMENT 'Заменить код на указанный (NULL-не менять)',
	`replaceName` VARCHAR(1024) NULL DEFAULT NULL COMMENT 'Заменить наименование на указанное (NULL-не менять)',
    PRIMARY KEY (`laboratory_id`, `code`),
	CONSTRAINT `FK_mmResearchTypeToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Список переопределений кодов и наименований типов исследований {rbResearchType} при их отправке в ЛИС. Если нет привязки к ЛИС, отправляется без изменений';

CREATE TABLE `mapTestTypeToLaboratory` (
	`laboratory_id` INT(11) NOT NULL COMMENT 'Лаборатория {rbLaboratory}',
	`code` VARCHAR(64) NOT NULL COMMENT 'Тип теста {rbTestType}',
	`replaceCode` VARCHAR(64) NULL DEFAULT NULL COMMENT 'Заменить код на указанный (NULL-не менять)',
	`replaceName` VARCHAR(1024) NULL DEFAULT NULL COMMENT 'Заменить наименование на указанное (NULL-не менять)',
    PRIMARY KEY (`laboratory_id`, `code`),
	CONSTRAINT `FK_mmTestTypeToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Список переопределений кодов и наименований тестов {Indicator} при их отправке в ЛИС. Если нет привязки к ЛИС, отправляется без изменений';

CREATE TABLE `mapToLaboratory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `laboratory_id` int(11) NOT NULL COMMENT 'К какой лаборатории приписан',
  `testType` VARCHAR(64) DEFAULT NULL COMMENT 'Тип теста {rbTestType} (NULL-любой)',
  `researchType` VARCHAR(64) DEFAULT NULL COMMENT 'Тип исследования {rbResearchType} (NULL-любой)',
  `biomaterialType` VARCHAR(64) DEFAULT NULL COMMENT 'Тип биоматериала {rbBiomaterialType} (NULL-любой)',
  `testTubeType` VARCHAR(64) DEFAULT NULL COMMENT 'Тип пробирки {rbTestTubeType} (NULL-любой)',
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_mmToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Сопоставление типов исследований с лабораториями ( по оригинальному коду типа исследования, типу биоматериала, типу пробирки) с возможностью замены кода и наименования типа при отправке';


CREATE TABLE `mmResearchToLaboratory` (
	`research_id` INT(11) NOT NULL COMMENT 'Исследование {Research}',
  	`laboratory_id` INT(11) NOT NULL COMMENT 'Лаборатория {rbLaboratory}',
  	`status` enum('WAIT', 'SENT', 'ERROR') NOT NULL COMMENT 'Статус обмена с ЛИС. WAIT-отослано отправщику, SENT-успешный ответ от отправщика, ERROR-отправщик сообщил об ошибке',
     PRIMARY KEY (`research_id`, `laboratory_id`),
     CONSTRAINT `FK_mmResearchToLaboratory_rbLaboratory` FOREIGN KEY (`laboratory_id`) REFERENCES `rbLaboratory` (`id`) ON DELETE CASCADE,
     CONSTRAINT `FK_mmResearchToLaboratory_Research` FOREIGN KEY (`research_id`) REFERENCES `Research` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Куда {rbLaboratory} было отправлено исследование{Research} и статус отправки';



INSERT INTO rbLaboratory(code, name) VALUES ('HEPA', 'ХепаБД'), ('INNOVA', 'Innova Systems');
INSERT INTO mapToLaboratory (laboratory_id, researchType, biomaterialType, testTubeType) VALUES
(1,  'A1', NULL, NULL),
(2,  'A1', NULL, NULL),
(1,  'A2', NULL, NULL),
(2,  'A999', NULL, NULL),
(1,  'A3', 'BLOOD_AND_FAECES', NULL),
(2,  'A3', NULL, 'FLAKON'),
(2,  'A4', 'NOT_BLOOD_AND_FAECES', NULL),
(2,  'A4', NULL, 'NOT_FLAKON'),
(2,  'A4', 'BLOOD_AND_FAECES', 'NOT_FLAKON'),
(2,  'A4', 'NOT_BLOOD_AND_FAECES', 'FLAKON')
;

INSERT INTO mapResearchTypeToLaboratory (`laboratory_id`, `code`, `replaceName`) VALUES ('2', 'A1', 'A1_REPLACE_NAME');
INSERT INTO mapResearchTypeToLaboratory (`laboratory_id`, `code`, `replaceCode`) VALUES ('1', 'A1', 'A1_WITH_NAME_REPLACEMENT');
INSERT INTO mapTestTypeToLaboratory (`laboratory_id`, `code`, `replaceCode`, `replaceName`) VALUES ('1', 'TST#1', '1', 'Какой-то анализ в ХЕпе');
INSERT INTO mapBiomaterialTypeToLaboratory (`laboratory_id`, `code`, `replaceCode`, `replaceName`) VALUES (1, 'BLOOD_AND_FAECES', '10', 'Надо было смапить на Хеповые коды ');
