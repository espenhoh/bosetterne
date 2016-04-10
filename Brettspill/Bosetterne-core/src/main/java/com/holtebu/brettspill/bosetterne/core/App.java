package com.holtebu.brettspill.bosetterne.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.util.Collection;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Zoo zoo =
                mapper.readValue(new File("input.json"), Zoo.class);
        System.out.println(mapper.writeValueAsString(zoo));
    }
}

class Zoo
{
    public Collection<Animal> animals;
}


class Animal
{
    public String type;
    public String name;
}

//    [
////    {"type":"dog","name":"Spike"},
////    {"type":"cat","name":"Fluffy"}
////    ]
//    public static void main(String[] args) throws Exception
//    {
//        ObjectMapper mapper = new ObjectMapper();
//        Collection<Animal> animals =
//                mapper.readValue(new File("input.json"),
//                        new TypeReference<Collection<Animal>>() {});
//        System.out.println(mapper.writeValueAsString(animals));
//        Collection<Animal> animals2 =
//                mapper.readValue(new File("input.json"),
//                        TypeFactory.defaultInstance().constructParametrizedType(Collection.class, Collection.class, Animal.class));
//        System.out.println(mapper.writeValueAsString(animals2));
//    }