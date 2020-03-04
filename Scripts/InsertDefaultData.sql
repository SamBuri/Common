
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
call uspEditLookupObject('9', 'Customer Category', 0);
call uspEditLookupObject('10', 'Vender Category', 0);
call uspEditLookupObject('11', 'Price Group', 0);
call uspEditLookupObject('12', 'Unit Measure', 0);
call uspEditLookupObject('13', 'Item Group', 0);
call uspEditLookupObject('14', 'Business Group', 0);
call uspEditLookupObject('15', 'VAT Item Group', 0);
call uspEditLookupObject('16', 'VAT Business Group', 0);
call uspEditLookupObject('17', 'Inventory Group', 0);
call uspEditLookupObject('18', 'Customer Group', 0);
call uspEditLookupObject('19', 'Vendor Group', 0);
call uspEditLookupObject('20', 'Location', 0);
---- Schoolsoft
call uspEditLookupObject('100', 'House', 0);

------------ Access Objects
call uspEditAccessObject ('LookupData', 'Lookup Data', '0');
call uspEditAccessObject ('AccessObject', 'Access Object', '0');
call uspEditAccessObject ('UserRole', 'User Role', '0');
call uspEditAccessObject ('AccountCategory', 'Account Category', '0');
call uspEditAccessObject ('AppUser', 'App User', '0');
call uspEditAccessObject ('IDGenerator', 'ID Generator', '0');
call uspEditAccessObject ('Client', 'Client', '0');
call uspEditAccessObject ('County', 'County', '0');
call uspEditAccessObject ('SubCounty', 'SubCounty', '0');
call uspEditIDGenerator('SubCounty', '0', 2, '', 0, '');
call uspEditAccessObject ('Licence', 'Licence', '0');
call uspEditAccessObject ('Staff', 'Staff', '0');
call uspEditIDGenerator('Staff', '0', 2, '', 0, '');


call uspEditAccessObject ('Options', 'Options', '0');
call uspEditIDGenerator('Options', '0', 2, '', 0, '');

call uspEditAccessObject ('Currency', 'Currency', '0');
call uspEditIDGenerator('Currency', '0', 2, '', 0, '');

call uspEditAccessObject ('FinancialPeriod', 'Financial Period', '0');
call uspEditIDGenerator('FinancialPeriod', '0', 2, '', 0, '');

call uspEditAccessObject ('AccountCategory', 'Account Category', '0');
call uspEditIDGenerator('AccountCategory', '0', 3, '', 0, '');

call uspEditAccessObject ('ChartAccount', 'Chart of Account', '0');
call uspEditIDGenerator('ChartAccount', '0', 2, '', 0, '');

call uspEditAccessObject ('BankAccount', 'Bank Account', '0');
call uspEditIDGenerator('BankAccount', '0', 2, '', 0, '');

call uspEditAccessObject ('GeneralPostingGroup', 'General Posting Group', '0');
call uspEditIDGenerator('GeneralPostingGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('CustomerPostingGroup', 'Customer Posting Group', '0');
call uspEditIDGenerator('CustomerPostingGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('VendorPostingGroup', 'Vendor Posting Group', '0');
call uspEditIDGenerator('VendorPostingGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('VATPostingGroup', 'VATPostingGroup', '0');
call uspEditIDGenerator('VATPostingGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('InventoryPostingGroup', 'Inventory Posting Group', '0');
call uspEditIDGenerator('InventoryPostingGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('Company', 'Company', '0');
call uspEditIDGenerator('Company', '0', 2, '', 0, '');

call uspEditAccessObject ('Customer', 'Customer', '0');
call uspEditIDGenerator('Customer', '0', 2, '', 0, '');

call uspEditAccessObject ('Vendor', 'Vendor', '0');
call uspEditIDGenerator('Vendor', '0', 2, '', 0, '');


call uspEditAccessObject ('JournalEntry', 'JournalEntry', '0');
call uspEditIDGenerator('JournalEntry', '0', 2, '', 0, '');

call uspEditIDGenerator('JournalEntryDetails', '0', 2, '', 0, '');
call uspEditIDGenerator('Options', '0', 2, '', 0, '');
call uspEditIDGenerator('LookupData', '0', 2, '', 0, '');
call uspEditIDGenerator('County', '0', 2, '', 0, '');

call uspEditAccessObject ('Village', 'Village', '0');
call uspEditIDGenerator('Village', '0', 2, '', 0, '');

call uspEditAccessObject ('Parish', 'Parish', '0');
call uspEditIDGenerator('Parish', '0', 2, '', 0, '');

call uspEditAccessObject ('AcademicPeriod', 'Academic Period', '0');

call uspEditAccessObject ('Subject', 'Subject', '0');
call uspEditIDGenerator('Subject', '0', 2, '', 0, '');

call uspEditAccessObject ('MeasureGroup', 'Measure Group', '0');
call uspEditIDGenerator('MeasureGroup', '0', 2, '', 0, '');

call uspEditAccessObject ('ItemCategory', 'Item Category', '0');
call uspEditIDGenerator('ItemCategory', '0', 2, '', 0, '');
call uspEditAccessObject ('ItemTemplate', 'Item Template', '0');
call uspEditAccessObject ('Item', 'Item', '0');
call uspEditIDGenerator('Item', '0', 2, '', 0, '');

call uspEditAccessObject ('Client', 'Client', '0');
call uspEditIDGenerator('Client', '0', 2, '', 0, '');

call uspEditAccessObject ('SaleOrder', 'Sale Orders', '0');
call uspEditIDGenerator('SaleOrder', '0', 2, '', 0, '');
call uspEditAccessObject ('SaleOrderDetail', 'Sale Order Details', '0');

call uspEditAccessObject ('Invoice', 'Invoice', '0');
call uspEditIDGenerator('Invoice', '0', 2, '', 0, '');
call uspEditAccessObject ('InvoiceDetails', 'Invoice Details', '0');

call uspEditAccessObject ('GeneralLedger', 'General Ledger', '0');
call uspEditAccessObject ('CustomerLedger', 'Customer Ledger', '0');
call uspEditAccessObject ('VendorLedger', 'Vendor Ledger', '0');
call uspEditAccessObject ('BankLedger', 'Bank Ledger', '0');

call uspEditAccessObject ('Receipt', 'Receipt', '0');
call uspEditIDGenerator('Receipt', '0', 2, '', 0, '');

call uspEditAccessObject ('ReceiptInvoice', 'Receipt Invoice', '0');

call uspEditAccessObject ('Paper', 'Paper', '0');

call uspEditAccessObject ('Combination', 'Combination', '0');
call uspEditIDGenerator('Combination', '0', 2, '', 0, '');

call uspEditAccessObject ('ExaminationSet', 'Examination Set', '0');
call uspEditAccessObject ('Grading', 'Grading', '0');
call uspEditAccessObject ('AcademicReportOptions', 'Academic Report Options', '0');
call uspEditAccessObject ('RemarkStudent', 'Remark Student', '0');
call uspEditIDGenerator('RemarkStudent', '0', 2, '', 0, '');


call uspEditAccessObject ('Classes', 'Classes', '0');
call uspEditIDGenerator('Classes', '0', 2, '', 0, '');

call uspEditAccessObject ('Streams', 'Streams', '0');
call uspEditIDGenerator('Streams', '0', 2, '', 0, '');

call uspEditAccessObject ('Student', 'Student', '0');
call uspEditIDGenerator('Student', '0', 2, '', 0, '');
call uspEditAccessObject ('StudentSubject', 'Student Subject', '2');
call uspEditAccessObject ('Registration', 'Registration', '0');
call uspEditIDGenerator('Registration', '0', 2, '', 0, '');

call uspEditAccessObject ('Marks', 'Marks', '0');
call uspEditAccessObject ('ClassMarks', 'Class Marks', '2');
call uspEditAccessObject ('Remark', 'Remark', '0');

call uspEditAccessObject ('SubjectTeacher', 'Subject Teacher', '0');
call uspEditIDGenerator('SubjectTeacher', '0', 2, '', 0, '');
call uspEditAccessObject ('AssignOptionalPapers', 'Assign Optional Papers', '2');

call uspEditAccessObject ('ClassItem', 'Class Item', '0');
call uspEditAccessObject ('Promotion', 'Promotion', '0');
call uspEditAccessObject ('ClassPromotion', 'Class Promotion', '0');
call uspEditAccessObject ('ClassRegistration', 'Class Registration', '0');


--- Options
call uspEditOptions ('001', 'Show Company Phone', 2, 'true');
call uspEditOptions ('002', 'Show Company Email', 2, 'true');
call uspEditOptions ('003', 'Show Company Website', 2, 'true');
call uspEditOptions ('004', 'Show Tag Line', 2, 'true');
call uspEditOptions ('005', 'Enable Auto General Ledger Posting', 2, 'false');

--- Schoolsoft specific
call uspEditOptions ('006', 'Generate Bills On Registration', 2, 'true');
call uspEditOptions ('007', 'Take Best 3 Performed Subjects on Promotion to S3', 2, 'true');















