package golfResults.hole;

import golfResults.round.Round;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hole")
public class Hole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Integer par;
    private Integer strokes;

    @Enumerated(EnumType.STRING)
    private Score score;

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false)
    private Round round;

}
