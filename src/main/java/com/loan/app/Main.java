package com.loan.app;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CreditAssessment assessment = new CreditAssessment();

        List<LoanApplication> applications = new ArrayList<>();

        Customer customer1 = new Customer(
                "C001",
                "Rahul",
                30,
                "GOV12345",
                60000,
                780,
                5000
        );

        Customer customer2 = new Customer(
                "C002",
                "Priya",
                25,
                "GOV67890",
                40000,
                680,
                12000
        );

        Customer customer3 = new Customer(
                "C003",
                "Amit",
                20,
                "",
                20000,
                600,
                15000
        );

        LoanApplication application1 =
                new LoanApplication("L001", customer1, 500000, 10000);

        LoanApplication application2 =
                new LoanApplication("L002", customer2, 500000, 10000);

        LoanApplication application3 =
                new LoanApplication("L003", customer3, 600000, 15000);

        applications.add(application1);
        applications.add(application2);
        applications.add(application3);

        System.out.println("==============================================");
        System.out.println(" SMART LOAN APPROVAL & RISK CLASSIFICATION");
        System.out.println("==============================================");

        for (LoanApplication application : applications) {

            System.out.println("\nApplication ID: "
                    + application.getApplicationId());

            try {
                System.out.println(
                        assessment.assess(application));
            } catch (InvalidLoanDataException e) {
                System.out.println(
                        "INVALID APPLICATION: " + e.getMessage());
            }

            System.out.println("----------------------------------------------");
        }
    }
}
