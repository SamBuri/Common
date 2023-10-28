use TestDB;
DROP PROCEDURE IF EXISTS uspEditLookupObject;

DELIMITER $$
CREATE PROCEDURE uspEditLookupObject(varLookupObjectID varchar(20), varLookupObjectName varchar(40), varReadOnly bit)
BEGIN
IF EXISTS (SELECT lookupObjectID FROM lookupobject where lookupObjectID = varLookupObjectID) THEN
BEGIN 
  UPDATE lookupobject set lookupObjectName = varLookupObjectName, readOnly = varReadOnly, Hidden = 0,recordDateTime = Current_TimeStamp(), lastupdatedatetime = Current_TimeStamp() 
  WHERE lookupObjectID = varLookupObjectID;
END;
ELSE
BEGIN
INSERT INTO lookupobject(lookupObjectID, lookupObjectName, readOnly, Hidden,recordDateTime, lastupdatedatetime) 
VALUES (varLookupObjectID, varLookupObjectName, varReadOnly, 0, Current_TimeStamp(), Current_TimeStamp());
END;
END IF;
END $$


DROP PROCEDURE IF EXISTS uspEditLookupData;

DELIMITER $$
CREATE PROCEDURE uspEditLookupData(varLookupObjectID varchar(20), varLookupDataID varchar(20), varLookupDataName varchar(40))
BEGIN
IF EXISTS (SELECT itemID FROM lookupData where itemID = varItemID) THEN
BEGIN 
  UPDATE LookupData set lookupObjectID = varLookupObjectID, lookupDataName =  varLookupDataName WHERE itemID = varItemID;
END;
ELSE
BEGIN
INSERT INTO LookupData(lookupObjectID, lookupDataID, lookupDataName, Hidden, isDefault, readOnly, recordDateTime, lastupdatedatetime) 
VALUES (varLookupObjectID, varLookupDataID, varLookupDataName, 0, 0, 0, Current_TimeStamp(), Current_TimeStamp());
END;
END IF;
END $$
  -- call uspEditLookupData(1, '1001', 'Administrator');
-- Edit IDGenerator
DROP PROCEDURE IF EXISTS uspEditIDGenerator;

DELIMITER $$
CREATE PROCEDURE uspEditIDGenerator(varObjectName varchar(100),varpadChar char(1), varPadLength int,varSeperator char(1), varSeporatorPosition int,varStartWith varchar(10))
BEGIN
IF EXISTS (SELECT objectName FROM IDGenerator where objectName = varObjectName) THEN
BEGIN 
  UPDATE IDGenerator set objectName = varObjectName, padChar = varpadChar, padLength = varPadLength, 
  seperator = varSeperator, seporatorPosition= varSeporatorPosition, startWith = varStartWith,  recordDateTime = Current_TimeStamp(), lastupdatedatetime = Current_TimeStamp()
 WHERE objectName = varObjectName;
END;
ELSE
BEGIN
INSERT INTO IDGenerator(objectName, padChar, padLength,seperator, seporatorPosition,startWith, recordDateTime, lastupdatedatetime) 
VALUES (varObjectName, padChar, varPadLength, varSeperator, varSeporatorPosition, varStartWith, Current_TimeStamp(), Current_TimeStamp());
END;
END IF;
END $$

-- Edit AccessObject
DROP PROCEDURE IF EXISTS uspEditAccessObject;

DELIMITER $$
CREATE PROCEDURE uspEditAccessObject(varObjectName varchar(100), varCaption varchar(100), varObjectType char(1))
BEGIN
IF EXISTS (SELECT objectName FROM AccessObject where objectName = varObjectName) THEN
BEGIN 
  UPDATE AccessObject set objectName = varObjectName, caption = varCaption, objectType = varObjectType,  recordDateTime = Current_TimeStamp(), lastupdatedatetime = Current_TimeStamp()
 WHERE objectName = varObjectName;
END;
ELSE
BEGIN
INSERT INTO AccessObject(objectName, caption, objectType, recordDateTime, lastupdatedatetime) 
VALUES (varObjectName, varCaption, varObjectType, Current_TimeStamp(), Current_TimeStamp());
END;
END IF;
END $$


-- Edit Options
DROP PROCEDURE IF EXISTS uspEditOptions;

DELIMITER $$
CREATE PROCEDURE uspEditOptions(varOptionID varchar(20), varOptionName varchar(100), varDataType char(1), varOptionValue varchar(100))
BEGIN
IF EXISTS (SELECT OptionID FROM Options where optionID = varOptionID) THEN
BEGIN 
  UPDATE options set optionName = varOptionName, dataType =varDataType,  OptionValue = varOptionValue,  recordDateTime = Current_TimeStamp(), lastupdatedatetime = Current_TimeStamp()
 WHERE optionID = varOptionID;
END;
ELSE
BEGIN
INSERT INTO Options(optionID, optionName, dataType, optionValue, recordDateTime, lastupdatedatetime) 
VALUES (varOptionID, varOptionName, varDataType, varOptionValue, Current_TimeStamp(), Current_TimeStamp());
END;
END IF;
END $$

--- call uspEditOptions ('001', 'Show PDF', 2, 'true');


--- select * from LookupObject

DROP PROCEDURE IF EXISTS uspEditUserRoles;

DELIMITER $$
CREATE PROCEDURE uspEditUserRoles(varRoleName varchar(40), varDescription varchar(100))
BEGIN
IF EXISTS (SELECT roleName FROM UserRole where roleName = varRoleName) THEN
BEGIN 
END;
ELSE
BEGIN
INSERT INTO UserRole
	( roleName, description, lastUpdateDateTime, recordDateTime) 
VALUES ( varRoleName, varDescription, now(), now());
END;
END IF;
END $$

DROP PROCEDURE IF EXISTS uspEditUserRoleDetails;

DELIMITER $$
CREATE PROCEDURE uspEditUserRoleDetails(varRoleName varchar(40), varObjectName varchar(100))
BEGIN
IF EXISTS (SELECT userRoleID FROM UserRoleDetail where userRoleID = varRoleName and accessObjectID = varObjectName) THEN
BEGIN 
END;
ELSE
BEGIN
INSERT INTO UserRoleDetail
	(userRoleDetailID, userRoleID, accessObjectID, canCreate, CanRead, canUpdate, canDelete, canPrint,  lastUpdateDateTime, recordDateTime) 
VALUES (concat(varRoleName, varObjectName), varRoleName, varObjectName, true, true, true, true, true, now(), now());
END;
END IF;
END $$

call uspEditUserRoles('Administrator', 'Administors the systems');
call uspEditUserRoleDetails('Administrator', 'UserRole');
call uspEditUserRoleDetails('Administrator', 'UserRoleDetail');