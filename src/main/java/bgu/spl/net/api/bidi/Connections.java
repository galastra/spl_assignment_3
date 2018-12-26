package bgu.spl.net.api.bidi;

public interface Connections<T> {

    /**
     * sends a message T to all active client represented by the given connId
     * @param connId id of the connection
     * @param msg message
     */
    void send(int connId,T msg);

    /**
     * send a message T to all acrtive clients.
     * This includes clients that has not yet completed log-in by the
     * BGS protocol.
     * (Remember, Connections T belongs to the server pattern
     * implementation, not the protocol
     * @param msg message
     */
    void broadcast(T msg);

    /**
     * removes active client connId from map
     * @param connId id of the connection
     */
    void disconnect(int connId);
}
