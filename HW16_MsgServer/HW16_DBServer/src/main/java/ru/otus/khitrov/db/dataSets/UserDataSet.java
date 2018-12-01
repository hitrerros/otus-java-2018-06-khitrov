package ru.otus.khitrov.db.dataSets;

import ru.otus.khitrov.messages.json.JsonBean;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "user_list")
public  class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy="user")
    private List<PhoneDataSet> phones;

    // Hibernate
    public UserDataSet(){
     }

     public   UserDataSet(String name, int age, AddressDataSet address, List<PhoneDataSet> phones) {
        this.setId(-1);
        this.name    = name;
        this.age     = age;
        this.address = address;
        this.phones  = phones;
        this.phones.forEach(x -> x.setUser(this));
       }

    public   UserDataSet(long id, String name, int age, AddressDataSet address ) {
        this.id      = id;
        this.name    = name;
        this.age     = age;
        this.address = address;
      }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    @Override
    public String toString() {

        if (phones != null) {
            return "UserDataSet{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address=" + address.toString() +
                    ", phones=" + getPhonesAsString() +
                    '}';
        } else {
            return "UserDataSet{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address=" + address.toString()+
                    '}';

        }
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    public static UserDataSet generateFromParameters(String name, String age, String address, String phones  ) {

        return new UserDataSet( name, Integer.valueOf( age ),
                     new AddressDataSet( address),
                     Stream.of( phones.split(","))
                             .map( PhoneDataSet::new )
                             .collect( Collectors.toList() ) );
    }

    public  String getPhonesAsString(  ) {
        return (phones == null ) ? "" : getPhones().stream().
                                        map(Object::toString).collect(Collectors.joining(","));
    }


    public JsonBean generateBean( ) {
        return new JsonBean("",
                this.getId(),
                String.valueOf(this.getAge()),
                this.getName(),
                this.getAddress().toString(),
                this.getPhonesAsString());

    }


}
