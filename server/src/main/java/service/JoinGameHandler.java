package service;

import server.Handler;

public class JoinGameHandler extends Handler {

    private final JoinGameService j;

    public JoinGameHandler(JoinGameService j) {
        this.j = j;
    }
}
