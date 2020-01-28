/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;
import com.saburi.common.utils.CommonEnums.DocumentTypes;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.saburi.common.utils.CommonEnums.AccountActions;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class VendorLedger extends DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private int ledgerID;
    @OneToOne
    @JoinColumn(name = "journalEntryDetailID", foreignKey = @ForeignKey(name = "fkJournalEntryDetailIDVendorLedger"))
    private JournalEntryDetail journalEntryDetail;
    @Size(max = 20, message = "The field: Vendor ID size cannot be greater than 20")
    @Column(length = 20)
    private String vendorID;
    @Size(max = 100, message = "The field: Vendor Name size cannot be greater than 100")
    @Column(length = 100)
    private String vendorName;
    private LocalDate postingDate;
    @Enumerated
    private DocumentTypes documentType;
    @Size(max = 100, message = "The field: Description size cannot be greater than 100")
    @Column(length = 100)
    private String description;
    @Size(max = 100, message = "The field: Document No size cannot be greater than 100")
    @Column(length = 100)
    private String documentNo;
    @Size(max = 100, message = "The field: Reference No size cannot be greater than 100")
    @Column(length = 100)
    private String referenceNo;
    private double amount;
    private double debit;
    private double credit;
    private double balance;

    public VendorLedger() {
    }

    public VendorLedger(JournalEntryDetail journalEntryDetail, String vendorID, String vendorName, LocalDate postingDate, DocumentTypes documentType, String description, String documentNo, String referenceNo, double amount, double balance) {
        this.journalEntryDetail = journalEntryDetail;
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.postingDate = postingDate;
        this.documentType = documentType;
        this.description = description;
        this.documentNo = documentNo;
        this.referenceNo = referenceNo;
        this.amount = amount;
        AccountActions accountAction = journalEntryDetail.getAccountAction();
        this.debit = accountAction.equals(AccountActions.Debit)?amount:0;
        this.credit = accountAction.equals(AccountActions.Credit)?amount:0;
        this.balance = balance;

    }

    public int getLedgerID() {
        return ledgerID;
    }

    public void setLedgerID(int ledgerID) {
        this.ledgerID = ledgerID;
    }

    public JournalEntryDetail getJournalEntryDetail() {
        return journalEntryDetail;
    }

    public void setJournalEntryDetail(JournalEntryDetail journalEntryDetail) {
        this.journalEntryDetail = journalEntryDetail;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public DocumentTypes getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypes documentType) {
        this.documentType = documentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorLedger)) {
            return false;
        }

        VendorLedger vendorLedger = (VendorLedger) o;

        return this.getId().equals(vendorLedger.getId());
    }

    @Override
    public int hashCode() {
        return this.ledgerID;

    }

    @Override
    public Object getId() {
        return this.ledgerID;
    }

    @Override
    public String getDisplayKey() {
        return this.vendorName;
    }

}
