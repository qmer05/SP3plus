import java.util.ArrayList;
import java.util.List;

public class StreamingService {
    Movie movie = new Movie("", "", "", 0);
    Series series = new Series("", "", "", 0, "", "");
    MediaContent mediaContent = new MediaContent("", "", "", 0, "", "");
    private String userInputUsername;
    private String userInputPassword;
    private final List<MediaContent> mediaContents = mediaContent.mediaContentSeparator();
    private final List<Movie> movieContents = movie.movieSeparator();
    private final List<String> genreList = new ArrayList<>();
    private final FileIO io = new FileIO();
    private final ArrayList<String> userData = io.readUserData();
    private final ArrayList<String> mediaContentData = io.readMediaData();
    private final DataValidator dataValidator = new DataValidator();
    private final ArrayList<User> users = new ArrayList<>();
    private final TextUI ui = new TextUI();
    private final MyList myList = new MyList();
    private final WatchedList watchedList = new WatchedList();
    private final DataBaseIO db = new DataBaseIO();
    private final ArrayList<String> usernameData = db.readUsernameData();


    public void startMenu() {
        ui.displayMessage("Hej og velkommen til landets værste streamingtjeneste");
        String i = ui.getInput("Vælg en funktion:" +
                "\n1) Logge ind." +
                "\n2) Lave en ny bruger.");
        if (i.equals("1")) {
            login();
        } else if (i.equals("2")) {
            addUser();
        } else {
            ui.displayMessage("Forkert valg. Vælg funktion.");
            startMenu();
        }
    }

    public void mainMenu() {
        String i = ui.getInput("\nDu har følgende valgmuligheder:" +
                "\n1) Vis listen over alle medier" +
                "\n2) Vælg/søg efter en bestemt medie" +
                "\n3) Vælg/søg medie i en kategori" +
                "\n4) Se din liste over sete medier" +
                "\n5) Se din liste over gemte medier" +
                "\n6) logud" +
                "\n7) Luk programmet");

        if (i.equals("1")) {
            displayMediaContent();
        }
        if (i.equals("2")) {
            searchByName();
        }
        if (i.equals("3")) {
            displayGenre();
            searchByGenre();
        }
        if (i.equals("4")) {
            displayWatchedList();
            mainMenu();
        }
        if (i.equals("5")) {
            displayMyList();
            mainMenu();
        }
        if (i.equals("6")) {
            logout();
        }
        if (i.equals("7")) {
            System.exit(0);
        } else {
            ui.displayMessage("Forkert valg. Vælg funktion.");
            mainMenu();
        }
    }

    public void login() {
        userInputUsername = ui.getInput("Brugernavn: ");
        if (dataValidator.checkLoginUsernameNEW(usernameData, userInputUsername)) {
            loginPassword(userInputUsername);
        } else {
            login();
        }
    }

    public void loginPassword(String user) {
        userInputPassword = ui.getInput("Kodeord: ");
        if (dataValidator.checkLoginPassword(userData, userInputPassword)) {
            ui.displayMessage("Du er nu logget ind som: " + user);
            mainMenu();
        } else {
            loginPassword(user);
        }
    }

    public void logout() {
        String i = ui.getInput("Er du sikker du vil logge ud, bro?\nTast 1 hvis du gerne vil logge ud:\nTast 2 hvis du ikke vil logge ud:");
        if (i.equals("1")) {
            startMenu();
        } else if (i.equals("2")) {
            mainMenu();
        } else {
            ui.displayMessage("Forkert valg. Vælg funktion 1 eller 2.");
            logout();
        }
    }

    public void addUser() {
        String userInputUsername = ui.getInput("Indtast et nyt brugernavn: ");
        if (dataValidator.checkRegisterUsername(usernameData,userInputUsername)) {
            String userInputPassword = ui.getInput("Indsæt ønskede kodeord\nKodeord skal minimum være 8 karakterer, skal have et tal og et stort bogstav");
            boolean validpassword = dataValidator.validatePassword(userInputPassword);
            if (validpassword) {
                String userInputPassword2 = ui.getInput("Gentag kodeord");
                if (userInputPassword.equals(userInputPassword2)) {
                    registerUser(userInputUsername, userInputPassword);
                    ui.displayMessage("Du er nu registreret som bruger:");
                    startMenu();
                } else {
                    ui.displayMessage("Forkert gentaget kodeord");
                    addUser();
                }
            } else if (!validpassword) {
                ui.displayMessage("Ugyldigt kodeord");
                addUser();
            }
        } else {
            addUser();
        }
    }

    public void registerUser(String username, String password) {
        User user = new User(username, password);
        db.saveUserData(username, password);
    }

    public void removeUser() {
        String i = ui.getInput("Er du sikker du vil fjerne din konto ud, bro?\nTast 1 hvis du gerne vil slette din konto:\nTast 2 hvis du ikke vil slette din konto:");
        if (i.equals("1")) {
            String uname = ui.getInput("Indtast brugernavn: ");
            String pword = ui.getInput("Indtast kodeord: ");
            terminateUser(uname, pword);
        } else if (i.equals("2")) {
            mainMenu();
        }
    }

    public void terminateUser(String username, String password) {
        User userToRemove = new User(username, password);
        for (User currentUser : users) {
            if (currentUser.equals(userToRemove)) {
            }
        }

        for (User user : users) {
            users.remove(user);
            userData.remove(user.toString());
        }
        io.saveUserData(users);
    }

    public void displayGenre() {
        genreList.add("1: Drama");
        genreList.add("2: Crime");
        genreList.add("3: Biography");
        genreList.add("4: History");
        genreList.add("5: Romance");
        genreList.add("6: War");
        genreList.add("7: Adventure");
        genreList.add("8: Family");
        genreList.add("9: Mystery");
        genreList.add("10: Sport");
        genreList.add("11: Thriller");
        genreList.add("12: Music/Musical");
        genreList.add("13: Sci-Fi");
        genreList.add("14: Horror");
        genreList.add("15: Film-Noir");
        genreList.add("16: Fantasy");

        String output = "";
        for (int i = 0; i < genreList.size(); i++) {
            output = output + genreList.get(i) + "\n";
        }
        ui.displayMessage(output);
    }

    public void displayMovies() {
        //-----------Printer listen af movies i en pen format------------
        List<Movie> movies = movie.movieSeparator();
        for (Movie m : movies) {
            ui.displayMessage(m.toString());
        }
    }

    public void displaySeries() {
        //-----------Printer listen af SERIES i en pen format------------
        List<Series> serie = series.seriesSeparator();
        for (Series s : serie) {
            ui.displayMessage(s.toString());
        }
    }


    public void displayMediaContent() {
        for (MediaContent mc : mediaContents) {
            ui.displayMessage(mc.toString());
        }

        if (mediaContents.isEmpty()) {
            ui.displayMessage("Nixen bixen");
        }
    }

    public void searchByName() {
        String input = ui.getInput("Indtast navn på titler for at vælge/søge");
        String[] inputTitles = input.split(", "); // Split the user input into an array of genres

        boolean found = false;

        for (MediaContent m : mediaContents) {
            String[] mediaContentTitles = m.getTitle().split(", "); // Split movie genres into an array
            boolean matchFound = false;

            // Check if any movie genre matches any of the input genres
            for (String title : inputTitles) {
                for (String mediaContentTitle : mediaContentTitles) {
                    if (mediaContentTitle.equalsIgnoreCase(title)) {
                        matchFound = true;
                    }
                }
                if (matchFound) {
                    break;
                }
            }

            if (matchFound) {
                ui.displayMessage("Medie fundet: ");
                ui.displayMessage(m.toString());
                mediaOptions(m);
                found = true;
            }
        }

        if (!found) {
            mediaNotFoundOptions();

        }
    }

    public void searchByGenre() {
        String input = ui.getInput("Indtast genrenavn for at søge i genre");
        String[] inputGenres = input.split(", "); // Split the user input into an array of genres

        boolean found = false;

        for (MediaContent m : mediaContents) {
            String[] mediaContentGenres = m.getGenre().split(", "); // Split movie genres into an array
            boolean matchFound = false;

            // Check if any movie genre matches any of the input genres
            for (String genre : inputGenres) {
                for (String mediaContentGenre : mediaContentGenres) {
                    if (mediaContentGenre.equalsIgnoreCase(genre)) {
                        matchFound = true;
                    }
                }
                if (matchFound) {
                    break;
                }
            }

            if (matchFound) {
                ui.displayMessage(m.toString());
                found = true;
            }
        }
        searchByName();


        if (!found) {
            mediaNotFoundOptions();
            displayGenre();
        }
    }


    public void displayWatchedList() {
        ui.displayMessage("\nDu har set: ");
        for (String userWatchedList : io.readMyWatchedList(userInputUsername, mediaContentData)) {
            io.saveMyListData(userInputUsername, watchedList.getWatchedList());
            ui.displayMessage(userWatchedList);
        }

        if (mediaContents.isEmpty()) {
            ui.displayMessage("Nixen Bixen");
        }
    }

    public void displayMyList() {
        ui.displayMessage("\nDu har tilføjet følgende til din liste: ");
        for (String userMyList : io.readMyList(userInputUsername, mediaContentData)) {
            io.saveMyListData(userInputUsername, myList.getMyList());
            ui.displayMessage(userMyList);
        }
        if (mediaContents.isEmpty()) {
            ui.displayMessage("Nixen bixen");
        }
    }


    public void mediaOptions(Media m) {
        String userInput = ui.getInput("\nVælg en funktion:" +
                "\n1) Afspil valgte medie" +
                "\n2) Tilføj medie til min liste" +
                "\n3) Gå tilbage til main menu");

        if (userInput.equals("1")) {
            watchedList.addToWatchedList(mediaContents.get(mediaContents.indexOf(m)));
            io.saveWatchedListData(userInputUsername, watchedList.getWatchedList());

            String s = ui.getInput("*Afspiller medie*\n" +
                    "Tryk 1 for at pause\n" +
                    "Tryk 2 for at forlade");
            if (s.equals("1")) {
                String f = ui.getInput("Medie er på pause\n" +
                        "Tryk 1 for at gentage\n" +
                        "Tryk 2 for at forlade");
                if (f.equals("1")) {
                    ui.displayMessage("*Afspiller medie*");
                } else if (f.equals("2")) {
                    mainMenu();
                }
            }
        }

        if (userInput.equals("2")) {
            myList.addToMyList(mediaContents.get(mediaContents.indexOf(m)));
            io.saveMyListData(userInputUsername, myList.getMyList());
            ui.displayMessage("Medie tilføjet: " + m);
            mediaOptions(m);
        }

        if (userInput.equals("3")) {
            mainMenu();
        }
    }

    public void mediaNotFoundOptions() {
        String input = ui.getInput("Vælg en funktion:" +
                "\n1) Fremvis vores katalog" +
                "\n2) Søg igen" +
                "\n)3 Gå tilbage til hovedmenu");
        if (input.equalsIgnoreCase("1")) {
            displayMovies();
        } else if (input.equalsIgnoreCase("2")) {
            searchByName();
        } else if (input.equalsIgnoreCase("3")) {
            mainMenu();
        }
    }
}