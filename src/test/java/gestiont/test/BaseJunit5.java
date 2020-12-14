package gestiont.test;

import javax.ejb.embeddable.EJBContainer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/*
 * Cette classe regroupe les outils nécessaires afin de faciliter
 * l'écriture des tests unitaires sur les instances CDI et EJB. 
 */
public class BaseJunit5 {

    /*
     * Le nom de votre application (en clair le nom de votre projet)
     */
    public final String BASE = "java:global/votre-application";

    /*
     * Le conteneur qui va regrouper à la fois les instances CDI et les EJB.
     */
    public static EJBContainer container;

    /*
     * Création du conteneur avant les tests.
     */
    @BeforeAll
    static public void beforeAll() throws Exception {
        if (container == null) {
            System.out.println("\nSTARTING CONTAINER\n");
            container = EJBContainer.createEJBContainer();
            // prevoir la fermeture
            Thread t = new Thread(BaseJunit5::afterAll);
            Runtime.getRuntime().addShutdownHook(t);
        }
    }

    static public void afterAll() {
        System.out.println("\nSTOPPING CONTAINER\n");
        container.close();
        container = null;
    }

    /*
     * Injecter dans le conteneur l'instance du test afin de réaliser les
     * initialisations et les injections nécessaires.
     */
    @BeforeEach
    public void before() throws Exception {
        container.getContext().bind("inject", this);
    }

    /*
     * Utiliser l'API JNDI (Java Naming and Directory Interface) pour trouver une
     * instance à partir de son nom.
     */
    public <T> T lookup(String name, Class<T> theClass) {
        try {
            Object o = container.getContext().lookup(name);
            return theClass.cast(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}