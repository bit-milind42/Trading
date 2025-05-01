package com.milind.modal;

@Data
@Entity
public class Withdrawal {
    @Id
    @GeneratedValue(startegy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus status;

    private Long amount;

    @ManyToOne
    private User user;

    private LocalDateTime date=LocalDateTime.now();
}
