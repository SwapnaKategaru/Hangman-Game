import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GuessTheMovie {
    static boolean wonGame = false;
    static String randomMovie;
    static int wrongGuesses = 0;
    static StringBuilder hiddenMovie;
    static HashSet<Character> uniqueLetters;
    static int uniqueLetterCount;
    static List<String> wrongLetters = new ArrayList<>();

    public static void main(String[] args) {
        int chances = 5;
        System.out.println("PLAY - GUESS THE MOVIE");
        String filePath = "/home/swapna/Documents/Hangman-Game/src/main/resources/movies.txt";
        try {
            List<String> movieList = Files.readAllLines(Paths.get(filePath));
            Random random = new Random();
            randomMovie = movieList.get(random.nextInt(movieList.size()));
            String movie = randomMovie;
            uniqueLetters = getUniqueLetters(movie);
            uniqueLetterCount = uniqueLetters.size();
            System.out.println("I've randomly picked a movie name, try to guess it. ");
            hiddenMovie = new StringBuilder(movie.replaceAll(".", "_"));
            System.out.println(hiddenMovie);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<(uniqueLetterCount + chances); i++) {
            if (uniqueLetters.isEmpty()) {
                wonGame = true;
                System.out.println("Awesome !!! You WON !!!");
                break;
            }
            System.out.print("Guess a letter : ");
            Scanner scanner = new Scanner(System.in);
            String letter = scanner.next();
            char letterGuess = letter.charAt(0);
            if (wrongLetters.contains(letter) || hiddenMovie.toString().contains(letter)) {
                System.out.printf("You've already used '%c'. Please try some other letter. ", letterGuess);
                i--;
                continue;
            } else {
                System.out.println("YOUR GUESS : " + letter);
            }

            if (randomMovie.contains(letter)) {
                uniqueLetters.remove(letterGuess);
                for (int j = 0; j < randomMovie.length(); j++) {
                    if (randomMovie.charAt(j) == letterGuess) {
                        hiddenMovie.setCharAt(j, letterGuess);
                    }
                }
                System.out.println(hiddenMovie);
            } else {
                wrongGuesses += 1;
                wrongLetters.add(letter);
                System.out.printf("You've guessed %d letters wrong : %s%n", wrongLetters.size(), wrongLetters);
                System.out.println(hiddenMovie);
                if (wrongGuesses >= chances) {
                    System.out.println("You've LOST !!\nMovie is - " + randomMovie);
                    break;
                }
            }
        }
    }

    public static HashSet<Character> getUniqueLetters(String word) {
        word = word.replaceAll("\\s+", "").toLowerCase();
        HashSet<Character> uniqueLetters = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                uniqueLetters.add(c);
            }
        }
        return uniqueLetters;
    }

}
