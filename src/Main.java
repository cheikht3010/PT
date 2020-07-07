import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {

		Scanner scChoix = new Scanner(System.in);

		System.out.println(" Que souhaitez vous faire ?");
		System.out.println(" 1. CREER UN NOUVEAU MODELE DE LOCUTEUR ");
		System.out.println(" 2. TESTER UN LOCUUTEUR");
		System.out.println(" Faites votre choix : ");

		String choix = scChoix.nextLine();

		do {

			if (choix.equals("1")) {
				Scanner sc = new Scanner(System.in);

				System.out.println("Veuillez saisir votre prénom :");

				String prenom = sc.nextLine();

				System.out.println("Veuillez saisir votre nom :");

				String nom = sc.nextLine();

				Locuteur loc = new Locuteur(prenom, nom);

				if (loc.creer_modele())
					System.out.println("Création du modèle terminée avec succés !");
				else
					System.out.println("La création du modèle a échouée !");
			} else if (choix.equals("2")) {
				TestLocuteur testLoc = new TestLocuteur();
				if (testLoc.tester())
					System.out.println("Le processus de test s'est terminé avec succés");
				else
					System.out.println("Le processus de test a échoué");
			} else {
				System.out.println("Choix non valide Veuillez recommencer!");
			}

		} while ((!choix.equals("1")) && (!choix.equals("2")));

	}
}
