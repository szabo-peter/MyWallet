package hu.flowacademy.MyWallet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Expense {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private ExpenseCategory expenseCategory;
    private LocalDateTime expenseTime;
    @ManyToOne
    @JsonIgnore
    private Account account;
    @Column(nullable = false)
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private Currency currency;


}