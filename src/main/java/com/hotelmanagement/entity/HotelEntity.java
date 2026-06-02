package com.hotelmanagement.entity;

import com.hotelmanagement.enums.HotelStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @Column(nullable = false, length = 255) private String name;

    @Column(columnDefinition = "TEXT") private String description;

    @Column(name = "address_line_1", nullable = false, length = 255)
    private String addressLine1;

    @Column(name = "address_line_2", length = 255) private String addressLine2;

    @Column(nullable = false, length = 100) private String city;

    @Column(nullable = false, length = 100) private String state;

    @Column(nullable = false, length = 100) private String country;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "contact_number", nullable = false, length = 15)
    private String contactNumber;

    @Column(length = 255) private String email;

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private HotelStatus status;

    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;



    @ManyToMany
    @JoinTable(name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    private Set<AmenityEntity> amenities = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true

    )
    private List<HotelImageEntity> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomTypeEntity> roomTypes = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = HotelStatus.PENDING;
        }

        if (this.averageRating == null) {
            this.averageRating = 0.0;
        }

        if (this.totalReviews == null) {
            this.totalReviews = 0;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}