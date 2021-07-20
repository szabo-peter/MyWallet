package hu.flowacademy.MyWallet.repository;

import hu.flowacademy.MyWallet.model.IncomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory,String> {
}
