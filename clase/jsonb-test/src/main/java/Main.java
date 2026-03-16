import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.name = "Falco";
        dog.age = 4;
        dog.bitable = false;

        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(dog);

        System.out.println(result);

        dog = jsonb.fromJson("{\"age\":4,\"bitable\":false,\"name\":\"Poochy\"}", Dog.class);
        System.out.println(dog.name);
    }
}
