package com.javaegitimleri.petclinic.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="t_owner")
public class Owner {
	//bu anotasyonlar ile model siniflari ayni zamanda DB tablolari olarak kullaniliyor

	@Id // primary key
	@SequenceGenerator(name="petClinicSeqGen",sequenceName="petclinic_sequence")
	@GeneratedValue(strategy= GenerationType.SEQUENCE,generator="petClinicSeqGen")
	private Long id;

	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@OneToMany(mappedBy = "owner")
	private Set<Pet> pets = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Pet> getPets() {
		return pets;
	}

	public void setPets(Set<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
