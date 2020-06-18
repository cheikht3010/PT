import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("Veuillez saisir votre prénom :");
		
		String prenom = sc.nextLine();
		
		System.out.println("Veuillez saisir votre nom :");
		
		String nom = sc.nextLine();
		
		Locuteur loc = new Locuteur(prenom, nom);
		
		if(loc.creer_modele())
			System.out.println("OK");
		
		else
			System.out.println("KO");	
	}
}
