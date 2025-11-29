# Java Streams Practice Project

This project demonstrates Java 8+ Stream API operations using a banking transaction model for hands-on practice.​

Project Overview
The repository contains practical exercises for mastering Java Streams through transaction processing scenarios. Core files include Transaction.java (model class), TransactionType.java (enum), and Practice.java (main demonstration class with multiple stream examples). Sample data includes four transactions across two accounts (A1, A2) with deposit and withdrawal types.​

Key Features
Basic stream operations: filter, mapToDouble, sum, max.

Grouping and aggregation: by transaction type, account, nested maps for type counts and sums.

Sorting, thresholding, and custom logic (e.g., balance calculation treating withdrawals as negative).

Method references and collectors like groupingBy, summingDouble, toList.

Compile: javac streams/*.java.

Run: java streams.Practice. Outputs totals, filtered lists, groupings, balances, and sorted results for sample data.​

Stream Examples Covered
Total deposits/withdrawals via filter + sum.​

Highest amount using map + max.​

Group by type/account with totals/counts (e.g., sumOfTxnTypePerCustomer).​

Sort descending by amount; filter above threshold.​

Account balances (deposits positive, withdrawals negative).
