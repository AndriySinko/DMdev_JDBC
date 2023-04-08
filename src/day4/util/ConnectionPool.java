package day4.util;

import day1.util.PropertiesUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    /*
    Так как открывание/создание новых соедениний с базой данных очень дорогостоющая функция, на практике используеются
    пулы соиденений, что-то типа списков/очередей соеденинений. Работает так что при вызове метода который должен
    создавать соедение, оно нам будет возвращаться с уже заготовленного пула(в джаве есть специальные библиотеки,
    с этими пулами), выполнять наши команды, и обратно возвращаться в пул. Но все же таким образо мы не закрываем еще
    сами соединения, а мы знаем что их обязательно нужно закрывать. Тогда у нас есть метод который проходится по всем
    соеденения и закрывает их. Хоть в джаве существуют для этого библиотеки, сейчас мы рассмотрим как работает такой пул
    Обычно пул в себя вмещает 5-10 соеденений.
     */
    private static final String USER_KEY = "db.username";
    private static final String URL_KEY = "db.url";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool;
    private static List<Connection> sourceConnections;

    static {
        loadDriver();
        initConnectionPool();
    }

    private ConnectionPool() {
    }
    private static Connection open(){
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        var poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        var size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open();
            var proxyConnection = (Connection)
                    Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")
                            ? pool.add((Connection) proxy)
                            : method.invoke(connection, args));

            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }

    public static Connection get(){
        try {
            return pool.take();
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void closePool(){
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
