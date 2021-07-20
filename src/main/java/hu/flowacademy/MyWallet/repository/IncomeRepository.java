package hu.flowacademy.MyWallet.repository;

import hu.flowacademy.MyWallet.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income,String> {
}
