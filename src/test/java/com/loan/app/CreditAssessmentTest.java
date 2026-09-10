package com.loan.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAssessmentTest {

    @Test
    void testMinimumAgeBoundary() throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C001",
                "Test User",
                21,
                "GOV123",
                50000,
                700,
                5000
        );

        LoanApplication application =
                new LoanApplication("L001", customer, 300000, 5000);

        CreditAssessment assessment = new CreditAssessment();

        String result = assessment.assess(application);

        assertTrue(result.contains("APPROVED"));
    }

    @Test
    void testBelowMinimumAge() throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C002",
                "Young User",
                20,
                "GOV123",
                50000,
                700,
                5000
        );

        LoanApplication application =
                new LoanApplication("L002", customer, 300000, 5000);

        CreditAssessment assessment = new CreditAssessment();

        String result = assessment.assess(application);

        assertTrue(result.contains("at least 21"));
    }

    @Test
    void testMinimumCreditScoreBoundary()
            throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C003",
                "Credit User",
                25,
                "GOV123",
                50000,
                650,
                5000
        );

        LoanApplication application =
                new LoanApplication("L003", customer, 300000, 5000);

        CreditAssessment assessment = new CreditAssessment();

        String result = assessment.assess(application);

        assertTrue(result.contains("APPROVED"));
    }

    @Test
    void testMaximumDTIBoundary()
            throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C004",
                "DTI User",
                30,
                "GOV123",
                50000,
                700,
                20000
        );

        LoanApplication application =
                new LoanApplication("L004", customer, 300000, 5000);

        CreditAssessment assessment = new CreditAssessment();

        double dti = assessment.calculateDTI(application);

        assertEquals(50.0, dti, 0.01);
    }

    @Test
    void testLowRiskClassification()
            throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C005",
                "Low Risk User",
                35,
                "GOV123",
                100000,
                800,
                5000
        );

        LoanApplication application =
                new LoanApplication("L005", customer, 500000, 10000);

        CreditAssessment assessment = new CreditAssessment();

        String result = assessment.assess(application);

        assertTrue(result.contains("LOW RISK"));
    }

    @Test
    void testMultipleRejectionReasons()
            throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C006",
                "Rejected User",
                20,
                "",
                15000,
                500,
                10000
        );

        LoanApplication application =
                new LoanApplication("L006", customer, 1000000, 20000);

        CreditAssessment assessment = new CreditAssessment();

        String result = assessment.assess(application);

        assertTrue(result.contains("at least 21"));
        assertTrue(result.contains("identification"));
        assertTrue(result.contains("income"));
        assertTrue(result.contains("credit score"));
    }

    @Test
    void testMaximumPermissibleLoan()
            throws InvalidLoanDataException {

        Customer customer = new Customer(
                "C007",
                "Loan Limit User",
                30,
                "GOV123",
                50000,
                700,
                5000
        );

        CreditAssessment assessment = new CreditAssessment();

        double maximumLoan =
                assessment.calculateMaximumPermissibleLoan(customer);

        assertEquals(400000, maximumLoan, 0.01);
    }

    @Test
    void testInvalidLoanAmount() {

        Customer customer = new Customer(
                "C008",
                "Invalid User",
                30,
                "GOV123",
                50000,
                700,
                5000
        );

        LoanApplication application =
                new LoanApplication("L008", customer, -1000, 5000);

        CreditAssessment assessment = new CreditAssessment();

        assertThrows(
                InvalidLoanDataException.class,
                () -> assessment.assess(application)
        );
    }
}
