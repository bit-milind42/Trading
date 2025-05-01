package com.milind.modal;

@Entity
@Data
public class Watchlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private List<Coin> coins= new ArrayList<>();
}
