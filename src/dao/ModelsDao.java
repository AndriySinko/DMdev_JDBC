package dao;

public class ModelsDao {
    private static final ModelsDao INSTANCE = new ModelsDao();
    private ModelsDao(){
    }

    public static ModelsDao getInstance(){
        return INSTANCE;
    }
}
