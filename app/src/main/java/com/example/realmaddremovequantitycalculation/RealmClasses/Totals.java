package com.example.realmaddremovequantitycalculation.RealmClasses;

public class Totals {
    public int totalQty;
    public double subtotal;
    public double tax;

    public Totals(int totalQty, double subtotal, double tax) {
        this.totalQty = totalQty;
        this.subtotal = subtotal;
        this.tax = tax;
    }

    public double getTotalAmount() {
        return subtotal + tax;
    }
}

