package ui;

import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import model.AuthData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class PreloginRepl {
    ServerFacade s;
    Scanner scanner;

    public PreloginRepl(ServerFacade s) {
        this.s = s;
        this.scanner = new Scanner(System.in);
    }

    public void run() throws UnauthorizedException, BadRequestException, URISyntaxException, IOException,
            InterruptedException, AlreadyTakenException, DataAccessException {
        while (true) {
            System.out.print("[LOGGED OUT] >>> ");
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "help" -> {
                    System.out.println("register <username> <password> <email>");
                    System.out.println("login <username> <password>");
                    System.out.println("quit");
                    System.out.println("help");
                }
                case "quit" -> {
                    return;
                }

                case "login" -> {
                    try {
                        AuthData auth = s.loginUser(tokens[1], tokens[2]);
                        PostloginRepl pl = new PostloginRepl(s, auth);
                        pl.run();
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                case "register" -> {
                    try {
                        AuthData auth = s.registerUser(tokens[1], tokens[2], tokens[3]);
                        PostloginRepl pl = new PostloginRepl(s, auth);
                        pl.run();
                    } catch (Exception e) {
                        System.out.print("error: " + e.getMessage());
                    }
                }

                default -> System.out.print("Unknown Command");

            }
        }
    }
}
