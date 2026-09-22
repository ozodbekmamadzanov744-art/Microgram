package kg.attractor.microgram.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "publication_likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_publication_like",
                        columnNames = {
                                "publication_id",
                                "user_id"
                        }
                )
        }
)
public class PublicationLike {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "publication_id",
            nullable = false
    )
    private Publication publication;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;
}