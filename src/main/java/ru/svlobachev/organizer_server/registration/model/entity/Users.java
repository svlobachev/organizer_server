package ru.svlobachev.organizer_server.registration.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

//	public UUID getId() {
//		return uuid;
//	}
//
//	public void setId(UUID uuid) {
//		this.uuid = uuid;
//	}
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "uid", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
//	private UUID uuid;


//    @Id
//    @SequenceGenerator(
//            name = "users_sequence",
//            sequenceName = "users_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = SEQUENCE,
//            generator = "users_sequence"
//    )
//    @Column(
//            name = "id",
//            updatable = false
//    )
//    private Long id;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String level1;
    @Column(columnDefinition = "TEXT")
    private String value1;
    @Column(columnDefinition = "TEXT")
    private String level2;
    @Column(columnDefinition = "TEXT")
    private String value2;
    @Column(columnDefinition = "TEXT")
    private String level3;
    @Column(columnDefinition = "TEXT")
    private String value3;
    @Column(columnDefinition = "TEXT")
    private String level4;
    @Column(columnDefinition = "TEXT")
    private String value4;
    @Column(columnDefinition = "TEXT")
    private String level5;
    @Column(columnDefinition = "TEXT")
    private String value5;
    @Column(columnDefinition = "TEXT")
    private String level6;
    @Column(columnDefinition = "TEXT")
    private String value6;
    @Column(columnDefinition = "TEXT")
    private String level7;
    @Column(columnDefinition = "TEXT")
    private String value7;

}
