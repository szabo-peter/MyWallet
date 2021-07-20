package hu.flowacademy.MyWallet.repository;

import hu.flowacademy.MyWallet.model.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory,String> {
}
