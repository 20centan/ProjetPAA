import java.util.ArrayList;

public class Menu {
	
	public static void ajoutRelation(Colonie c) {
		
		Colon a,b;
		java.util.Scanner entree =   new java.util.Scanner(System.in);
		
		//je dis a l'utilisateur de mettre un ":" au lieu d'un espace car avec .split() je n'arrive à utiliser le délimiteur espace du coup je vais utiliser le ":"
		System.out.println("Veuillez specifiez les deux colons qui ne s'aiment pas (format: A:B) :");
		
		String entree1 = entree.next();
		String [] entree2 = entree1.split(":");
		
		//Verif que les colons existent
		a = c.getColon(entree2[0].charAt(0));
		b = c.getColon(entree2[1].charAt(0));
		
		//si les deux colons existent alors set leurs relation sinon gerer erreur
		if(a!=null && b!=null) {
			a.ajoutEnnemi(b);
			System.out.println("Relation ajoutée avec succès !");
		}else {
			System.out.println("Veuillez ressayer avec des colons existant.");
		}
		
		entree.close();
	}
	
	public static void ajoutPreference(Colonie c,ArrayList<Ressource> r) {
		//a finir
		java.util.Scanner entree =   new java.util.Scanner(System.in);
		
		System.out.println("Veuillez specifiez les préférences du colon (format: A:1:2:3...) :");
		
		String entree1 = entree.next();
		String [] entree2 = entree1.split(":");
		
		//Verification que le bon nombre de ressources a été mis
		if((entree2.length)-1!=c.getNbColons()) {
			
			System.out.println("Le nombre de ressources mises ne correspond pas au nombre de ressources attendu (Le nombre de ressource = le nombre de colons)");
			System.out.println("Veuillez ressayer avec le bon nombre.");
		}else {
			
			Colon a = c.getColon(entree2[0].charAt(0));
			
			//verification que le colon existe
			if(a!=null) {
				
				//On mets maitenant les préférence dans le tableau preference du colon
				for(int i = 0; i < r.size() ; i ++){
					String indice = entree2[i+1];
					int ind = Integer.valueOf(indice);
					a.ajoutPreference(r.get(ind-1));
				}
			}else {
				System.out.println("Veuillez ressayer avec des colons existant.");
			}
		}
		entree.close();
	}
	
	public static void verifListePref(Colonie c) {
		//to do
	}
	
	public static void echangeRessource(Colonie c) {
		//a finir
		
		java.util.Scanner entree =   new java.util.Scanner(System.in);
		System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
		
		System.out.println("Colon numéro 1 :");
		String entree1 = entree.next();
		//Verifier que ce colon existe
		
		System.out.println("Colon numéro 2 :");
		String entree2 = entree.next();
		//Verifier que ce colon existe
		
		//si les deux colons existent alors echanger leurs ressources sinon gerer erreur
		
	}
	
	public static void main(String []args) {
		
		java.util.Scanner entree =   new java.util.Scanner(System.in);
		

		System.out.println("De combien de colons dispose votre colonie ? (<=26)");
			
		int nbColons = entree.nextInt();
		//coder exception 26<nb
			
		System.out.println("Initialisation de la colonie...");
		Colonie colonie = new Colonie(nbColons);
			
		//on crée les ressources en fonction de nbColons
		System.out.println("Initialisation des ressources...");

		ArrayList<Ressource> ressources = new ArrayList<>();
		for(int i = 0; i < nbColons ; i ++){
			ressources.add(new Ressource());
		}
			
		boolean stop = false;
			
		while(!(stop)) {
			System.out.println("Choisir une option:");
			System.out.println("[1] Ajouter une relation entre deux colons \n[2] Ajouter les préférences d'un colon \n[3] fin");
			int option = entree.nextInt();
				
			switch(option) {
			case 1: ajoutRelation(colonie);
			break;
			case 2: ajoutPreference(colonie, ressources);
			break;
			case 3: //to do;
			break;
			default: System.out.println("Commande invalide, veuillez ressayer.");
			
			//erreur a corriger dans la boucle while qu'une boucle possible après ca me mets une erreur sur le .nextInt()
			}
		}		
		stop = false;
			
			/**while(!(stop)) {
				System.out.println("Choisir une option:");
				System.out.println("[1] Echanger les ressources de deux colons \n [2] Afficher le nombre de colons jaloux \n [3] fin");
				option = entree.nextInt();
				
				switch(option) {
				case 1: echangeRessource(colonie);
				break;
				case 2: colonie.afficheJaloux();
				break;
				case 3: return;
				break;
				default: System.out.println("Commande invalide, veuillez ressayer.");
				}
				
				colonie.afficheRessources();
		}**/
		
		entree.close();
	}
}