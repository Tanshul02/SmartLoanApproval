package com.loan.app;

public class LoanApplication {

    private String applicationId;
    private Customer customer;
    private double requestedLoanAmount;
    private double proposedMonthlyLoanPayment;

    public LoanApplication(String applicationId, Customer customer,
                           double requestedLoanAmount,
                           double proposedMonthlyLoanPayment) {

        this.applicationId = applicationId;
        this.customer = customer;
        this.requestedLoanAmount = requestedLoanAmount;
        this.proposedMonthlyLoanPayment = proposedMonthlyLoanPayment;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

    public double getProposedMonthlyLoanPayment() {
        return proposedMonthlyLoanPayment;
    }
}
