package com.loan.app;

import java.util.ArrayList;
import java.util.List;

public class CreditAssessment {

    public static final int MINIMUM_AGE = 21;
    public static final double MINIMUM_INCOME = 25000;
    public static final int MINIMUM_CREDIT_SCORE = 650;
    public static final double MAXIMUM_DTI = 50.0;

    public double calculateDTI(LoanApplication application) {

        Customer customer = application.getCustomer();

        double totalMonthlyDebt =
                customer.getExistingMonthlyObligations()
                + application.getProposedMonthlyLoanPayment();

        return (totalMonthlyDebt / customer.getMonthlyIncome()) * 100;
    }

    public double calculateMaximumPermissibleLoan(Customer customer) {

        double incomeBasedLimit = customer.getMonthlyIncome() * 20;

        double dtiBasedLimit =
                (customer.getMonthlyIncome() * 0.50
                - customer.getExistingMonthlyObligations()) * 20;

        if (dtiBasedLimit < 0) {
            return 0;
        }

        return Math.min(incomeBasedLimit, dtiBasedLimit);
    }

    public String assess(LoanApplication application)
            throws InvalidLoanDataException {

        validateInput(application);

        Customer customer = application.getCustomer();

        List<String> rejectionReasons = new ArrayList<>();

        if (customer.getAge() < MINIMUM_AGE) {
            rejectionReasons.add("Customer must be at least 21 years old");
        }

        if (customer.getGovernmentId() == null
                || customer.getGovernmentId().trim().isEmpty()) {
            rejectionReasons.add("Government identification number is invalid");
        }

        if (customer.getMonthlyIncome() < MINIMUM_INCOME) {
            rejectionReasons.add("Monthly income is below the minimum threshold");
        }

        if (customer.getCreditScore() < MINIMUM_CREDIT_SCORE) {
            rejectionReasons.add("Credit score is below the minimum requirement");
        }

        double dti = calculateDTI(application);

        if (dti > MAXIMUM_DTI) {
            rejectionReasons.add("Debt-to-income ratio exceeds 50%");
        }

        double maximumLoan = calculateMaximumPermissibleLoan(customer);

        if (application.getRequestedLoanAmount() > maximumLoan) {
            rejectionReasons.add("Requested loan exceeds maximum permissible loan amount");
        }

        if (!rejectionReasons.isEmpty()) {

            StringBuilder result = new StringBuilder();

            if (dti > MAXIMUM_DTI
                    || application.getRequestedLoanAmount() > maximumLoan) {
                result.append("HIGH RISK / REJECTED\n");
            } else {
                result.append("REJECTED\n");
            }

            result.append("Reasons:\n");

            for (String reason : rejectionReasons) {
                result.append("- ").append(reason).append("\n");
            }

            result.append(String.format(
                    "Maximum Permissible Loan: Rs. %.2f\n", maximumLoan));

            result.append(String.format(
                    "DTI: %.2f%%", dti));

            return result.toString();
        }

        if (customer.getCreditScore() >= 750 && dti <= 30) {

            return String.format(
                    "APPROVED - LOW RISK\n" +
                    "Customer: %s\n" +
                    "Maximum Permissible Loan: Rs. %.2f\n" +
                    "DTI: %.2f%%",
                    customer.getName(),
                    maximumLoan,
                    dti);
        }

        return String.format(
                "APPROVED - MEDIUM RISK\n" +
                "Customer: %s\n" +
                "Maximum Permissible Loan: Rs. %.2f\n" +
                "DTI: %.2f%%",
                customer.getName(),
                maximumLoan,
                dti);
    }

    private void validateInput(LoanApplication application)
            throws InvalidLoanDataException {

        if (application == null) {
            throw new InvalidLoanDataException(
                    "Loan application cannot be null");
        }

        if (application.getCustomer() == null) {
            throw new InvalidLoanDataException(
                    "Customer details cannot be null");
        }

        Customer customer = application.getCustomer();

        if (customer.getMonthlyIncome() < 0) {
            throw new InvalidLoanDataException(
                    "Monthly income cannot be negative");
        }

        if (customer.getExistingMonthlyObligations() < 0) {
            throw new InvalidLoanDataException(
                    "Existing loan obligations cannot be negative");
        }

        if (application.getRequestedLoanAmount() <= 0) {
            throw new InvalidLoanDataException(
                    "Requested loan amount must be greater than zero");
        }

        if (application.getProposedMonthlyLoanPayment() < 0) {
            throw new InvalidLoanDataException(
                    "Monthly loan payment cannot be negative");
        }
    }
}
