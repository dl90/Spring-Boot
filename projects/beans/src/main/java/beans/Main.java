package beans;

import beans.annotations.Config;
import beans.annotations.Designer;
import beans.annotations.Developer;
import beans.annotations.Lifecycle;
import beans.xml.Cat;
import beans.xml.Dog;
import beans.xml.Pettable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    public static void main(String[] args) {
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("beans.xml");
        Pettable pet = xmlContext.getBean(Cat.class);
        pet.pet();
        System.out.println(pet);

        xmlContext.getBean(Dog.class).setName("Foo");
        System.out.println(xmlContext.getBean(Dog.class));
        System.out.printf("%n".repeat(2));


        ApplicationContext annotationContext = new AnnotationConfigApplicationContext(Config.class);
        Developer developer = annotationContext.getBean(Developer.class);
        System.out.println(developer.getRole());

        Designer designer1 = annotationContext.getBean(Designer.class);
        Designer designer2 = annotationContext.getBean(Designer.class);
        Designer designer3 = annotationContext.getBean(Designer.class);

        designer2.setName("Bar");
        designer3.setName("Baz");

        System.out.println(designer1);
        System.out.println(designer2);
        System.out.println(designer3);
        System.out.println(annotationContext.getBean(Designer.class));

        annotationContext.getBean(Lifecycle.class);
    }
}
