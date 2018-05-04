package co.ceiba.testdatabuilder;

import co.ceiba.domain.Person;

public class PersonTestDataBuilder {
	private String name;
	private String lastName;
	
	public PersonTestDataBuilder() {
		this.name = "Andres";
		this.lastName = "Martínez";
	}
	
	public PersonTestDataBuilder withName (String name)
	{
		this.name = name;
		return this;
	}
	public PersonTestDataBuilder withLastName (String lastName)
	{
		this.lastName = lastName;
		return this;
	}
	public Person build() {
			return new Person(name, lastName);
	}
}
