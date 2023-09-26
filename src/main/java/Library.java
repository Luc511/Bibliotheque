import java.util.*;
public class Library {
    private final Scanner sc = new Scanner(System.in);

    public Book encodeBook() {
        System.out.println("Titre: ");
        String title = sc.nextLine();
        System.out.println("Auteur: ");
        String author = sc.nextLine();
        System.out.println("Année de publication: ");
        int year = Integer.parseInt(sc.nextLine());
        System.out.println("ISBN: ");
        String ISBN = sc.nextLine();
        return Book.builder()
                .bookId(-1) //id n'est jamais utilisé après pour un livre encodé
                .authorId(Queries.getAuthorIdByName(author))
                .title(title)
                .yearOfPublication(year)
                .ISBN(ISBN)
                .available(true)
                .build();
    }
    public boolean start() {

        String connect = """
                *********************
                *   Bibliothèque    *
                *********************
                login:""";
        String menuAdmin = """
                *********************
                *   Bibliothèque    *
                *********************
                Admin mode
                1. Afficher les livres disponibles
                2. Afficher les informations d'un livre
                3. Emprunter un livre
                4. Retourner un livre
                5. Ajouter un livre
                6. Modifier un livre
                7. Supprimer un livre
                
                0. Quitter
                """;
        String menu = """
                *********************
                *   Bibliothèque    *
                *********************
                1. Afficher les livres disponibles
                2. Afficher les informations d'un livre
                3. Emprunter un livre
                4. Retourner un livre
                
                0. Quitter
                """;
        User user;
        try {
            String login, password;
            System.out.println(connect);
            login = sc.nextLine();
            System.out.println("password: ");
            password = sc.nextLine();
            user = Queries.connect(login,password);
        }catch (Exception e) {
            System.out.println(e);
            return false;
        }

        if (user.getRole().equals(Role.ADMIN)) {
            menu = menuAdmin;
        }

        int choice;
        while (true) {
            try {
                System.out.println(menu);
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 0:
                        return true;
                    case 1:
                        Queries.displayAvailableBooks();
                        break;
                    case 2:
                        Queries.displayBooks();
                        System.out.println("Choisir par ISBN: ");
                        Queries.findByISBN(sc.nextLine()).displayAll();
                        break;
                    case 3:
                        Queries.displayAvailableBooks();
                        System.out.println("Quel livre emprunter (ISBN)?");
                        Queries.borrowBook(Queries.findByISBN(sc.nextLine()), user);
                        break;
                    case 4:
                        Queries.displayBooks();
                        System.out.println("Quel livre souhaitez-vous rendre");
                        Queries.returnBook(Queries.findByISBN(sc.nextLine()), user);
                        break;
                    case 5:
                        Queries.addBook(encodeBook(), user);
                        break;
                    case 6:
                        Queries.displayBooks();
                        System.out.println("Quel livre modifier ? : ");
                        Queries.modifyBook(Queries.findByISBN(sc.nextLine()), encodeBook(), user);
                        break;
                    case 7:
                        Queries.displayBooks();
                        System.out.println("Quel livre supprimer ? : ");
                        Queries.deleteBook(Queries.findByISBN(sc.nextLine()), user);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
}
