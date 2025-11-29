package streams;

import java.util.*;
import java.util.stream.Collectors;

public class Practice {

    public static void main(String[] args) {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("T1", TransactionType.DEPOSIT, 1000.0, null, "A1"),
                new Transaction("T2", TransactionType.WITHDRAWAL, 200.0, null, "A1"),
                new Transaction("T3", TransactionType.DEPOSIT, 500.0, null, "A1"),
                new Transaction("T4", TransactionType.WITHDRAWAL, 300.0, null, "A2")
        );

        double totalDeposits = transactions.stream().
                filter(transaction -> transaction.getType() == TransactionType.DEPOSIT)
                .mapToDouble(Transaction::getAmount).sum();

        double totalWithdrawals = transactions.stream()
                .filter(transaction -> transaction.getType() == TransactionType.WITHDRAWAL)
                .mapToDouble(Transaction::getAmount).sum();

        System.out.println("Total Deposits: " + totalDeposits);
        System.out.println("Total Withdrawals: " + totalWithdrawals);

        // Sample Problems for practice
        filterTransaction(TransactionType.DEPOSIT, transactions);
        highestTransactionAmount(transactions);
        totalAmountPerTransactionType(transactions);
        transactionsGroupingTransactionType(transactions);
        totalBalancePerAccount(transactions);

//        Category 2:
        transactionsPerAccount(transactions);
        transactionTypeCountPerCustomer(transactions);
        sumOfTxnTypePerCustomer(transactions);

//        Category 3:
        sortTxnByAmount(transactions);
        transactionAboveThreshold(transactions);

    }

    private static void transactionAboveThreshold(List<Transaction> transactions) {
        List<String> txnId = transactions.stream()
                .filter(transaction -> transaction.getAmount() > 300)
                .map(Transaction::getTransactionId)
                .toList();
        txnId.forEach(s -> System.out.println("Transaction ID: " + s));
    }

    private static void sortTxnByAmount(List<Transaction> transactions) {
        List<Transaction> sortedByAmount = transactions.stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .toList();
        System.out.println("Transactions sorted by Amount:");
        sortedByAmount.forEach(txn ->
                System.out.println("Transaction ID: " + txn.getTransactionId() + ", Amount: " + txn.getAmount())
        );
    }

    private static void sumOfTxnTypePerCustomer(List<Transaction> transactions) {
        Map<String,Map<TransactionType,Double>> result = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId,
                        Collectors.groupingBy(Transaction::getType,
                                Collectors.summingDouble(Transaction::getAmount))));
        result.forEach((accountId, typeMap) -> {
            System.out.println("Account ID: " + accountId);
            typeMap.forEach((type, totalAmount) ->
                    System.out.println("  Transaction Type: " + type + ", Total Amount: " + totalAmount)
            );
        });
    }

    private static void transactionTypeCountPerCustomer(List<Transaction> transactions) {

        Map<String,List<Transaction>> result = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        result.forEach((accountId, txnList) -> {
            Map<String,Map<TransactionType,Double>> finalResult = new HashMap<>(Map.of());
            finalResult.put(accountId,txnList.stream()
                    .collect(Collectors.groupingBy(Transaction::getType))
                    .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> (double) entry.getValue().size())));
            System.out.println("Account ID: " + accountId);
            finalResult.get(accountId).forEach((type, count) ->
                    System.out.println("  Transaction Type: " + type + ", Count: " + count)
            );
        });

    }

    private static void transactionsPerAccount(List<Transaction> transactions) {
        Map<String,List<Transaction>> result = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        result.forEach((accountId, txnList) -> {
            System.out.println("Account ID: " + accountId);
            txnList.forEach(txn ->
                    System.out.println("  Transaction ID: " + txn.getTransactionId() + ", Type: " + txn.getType() + ", Amount: " + txn.getAmount())
            );
        });
    }

    private static void totalBalancePerAccount(List<Transaction> transactions) {
        Map<String,Double> result = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getAccountId))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(Practice::getAmount)
                                .sum()
                ));
        System.out.println("Total Balance per Account:");
        result.forEach((accountId, balance) ->
                System.out.println("Account ID: " + accountId + ", Balance: " + balance));
    }

    private static double getAmount(Transaction txn) {
        if (txn.getType() == TransactionType.DEPOSIT) {
            return txn.getAmount();
        } else {
            return -txn.getAmount();
        }
    }

    private static void transactionsGroupingTransactionType(List<Transaction> transactions) {
        Map<TransactionType, List<Transaction>> grpResult = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
        grpResult.forEach((type , txnList) -> {;
            System.out.println("Transaction Type: " + type);
            txnList.forEach(txn ->
                    System.out.println("  Transaction ID: " + txn.getTransactionId() + ", Amount: " + txn.getAmount())
            );
        });
    }

    private static void totalAmountPerTransactionType(List<Transaction> transactions) {
        Map<TransactionType, Double> grpByResult = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(Transaction::getAmount)
                                .sum()));
        System.out.println("Total Amount per Transaction Type:");
        grpByResult.forEach((type, totalAmount) ->
                System.out.println(type + ": " + totalAmount)
        );
    }

    private static void highestTransactionAmount(List<Transaction> transactions) {
        Optional<Double> highestAmount = transactions.stream()
                .map(Transaction::getAmount)
                .max(Double::compare);
        System.out.println("Highest Transaction Amount: " + highestAmount.orElse(0.0));
    }


    private static void filterTransaction(TransactionType transactionType, List<Transaction> transactions) {
        List<Transaction> filteredResult =  transactions.stream().
                filter(transaction -> transaction.getType() == transactionType)
                .toList();
        System.out.printf("Filtered Transactions of type %s:\n", transactionType);
        filteredResult.forEach(transaction ->
                System.out.println("Transaction ID: " + transaction.getTransactionId() + ", Amount: " + transaction.getAmount())
        );

    }
}

