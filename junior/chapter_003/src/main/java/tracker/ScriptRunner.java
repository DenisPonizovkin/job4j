package tracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ScriptRunner {

    private final Connection connection;
    private final String path;

    public ScriptRunner(Connection connection, String path) {
        this.connection = connection;
        this.path = path;
    }

    public List<String> run() throws IOException {
        List<String> errors = new ArrayList<>();
		try (Stream<String> stream = Files.lines(Paths.get(path))) {
			stream.forEach(line -> {
                try {
                    connection.createStatement().execute(line);
                } catch (SQLException e) {
                    errors.add(e.getMessage());
                }
            });
		}
		return errors;
    }
}
