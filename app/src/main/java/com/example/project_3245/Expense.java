package com.example.project_3245;

public class Expense {
    private String expenseId;
    private String expenseDate;
    private String expenseAmount;

    public Expense() {}

    public Expense(String expenseDate, String expenseAmount) {
        this.expenseDate = expenseDate;
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }


    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
