use TestDB

/**
 * Author:  ClinicMaster13
 * Created: Oct 16, 2018
 */
-- LookupObject
call uspEditLookupObject('1', 'Country', 0);
call uspEditLookupObject('2', 'Marital Status', 0);
call uspEditLookupObject('3', 'Staff Title', 0);
call uspEditLookupObject('4', 'Ocupation', 0);
call uspEditLookupObject('5', 'District', 0);
call uspEditLookupObject('6', 'Religion', 0);
call uspEditLookupObject('7', 'Qualification', 0);
call uspEditLookupObject('8', 'Department', 0);



------------ Access Objects
call uspEditAccessObject ('LookupData', 'Lookup Data', '0');
call uspEditIDGenerator('LookupData', '0', 2, '', 0, '');

call uspEditAccessObject ('AccessObject', 'Access Object', '0');
call uspEditAccessObject ('UserRole', 'User Role', '0');
call uspEditAccessObject ('UserRoleDetail', 'User Role Details', '0');
call uspEditAccessObject ('AppUser', 'App User', '0');
call uspEditAccessObject ('IDGenerator', 'ID Generator', '0');
call uspEditAccessObject ('County', 'County', '0');
call uspEditIDGenerator('County', '0', 2, '', 0, '');

call uspEditAccessObject ('SubCounty', 'SubCounty', '0');
call uspEditIDGenerator('SubCounty', '0', 2, '', 0, '');

call uspEditAccessObject ('Village', 'Village', '0');
call uspEditIDGenerator('Village', '0', 2, '', 0, '');

call uspEditAccessObject ('Parish', 'Parish', '0');
call uspEditIDGenerator('Parish', '0', 2, '', 0, '');

call uspEditAccessObject ('Licence', 'Licence', '0');

call uspEditAccessObject ('Staff', 'Staff', '0');
call uspEditIDGenerator('Staff', '0', 2, '', 0, '');


call uspEditAccessObject ('Options', 'Options', '0');
call uspEditIDGenerator('Options', '0', 2, '', 0, '');

ditAccessObject ('Company', 'Company', '0');
call uspEditIDGenerator('Company', '0', 2, '', 0, '');

--- Options
call uspEditOptions ('001', 'Show Company Phone', 2, 'true');
call uspEditOptions ('002', 'Show Company Email', 2, 'true');
call uspEditOptions ('003', 'Show Company Website', 2, 'true');
call uspEditOptions ('004', 'Show Tag Line', 2, 'true');
















