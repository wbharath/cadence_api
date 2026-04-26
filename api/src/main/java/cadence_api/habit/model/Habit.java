package cadence_api.habit.model;

import cadence_api.common.enums.FrequencyType;
import cadence_api.common.enums.HabitType;
import cadence_api.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Table(name = "habits")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private HabitType type;

    @Enumerated(EnumType.STRING)
    private FrequencyType frequency;


    @Column(nullable = false)
    private String color;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

    }

    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }


}
