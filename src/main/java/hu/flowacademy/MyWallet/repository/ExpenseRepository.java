package hu.flowacademy.MyWallet.repository;

import hu.flowacademy.MyWallet.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,String> {
}
