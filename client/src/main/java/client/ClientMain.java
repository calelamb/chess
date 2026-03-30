package client;

import chess.*;
import exception.AlreadyTakenException;
import exception.BadRequestException;
import exception.DataAccessException;
import exception.UnauthorizedException;
import ui.PreloginRepl;
import ui.ServerFacade;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientMain {
    public static void main(String[] args) throws UnauthorizedException, BadRequestException, URISyntaxException,
            IOException, InterruptedException, AlreadyTakenException, DataAccessException {
        ServerFacade sf = new ServerFacade("http://localhost:8080");
        PreloginRepl pl = new PreloginRepl(sf);
        pl.run();
    }
}
