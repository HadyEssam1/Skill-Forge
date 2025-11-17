package managers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonManager<T> {

    protected List<T> data = new ArrayList<>();
    protected final String filePath;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected final TypeReference<List<T>> typeReference;

    public JsonManager(String filePath, TypeReference<List<T>> typeReference) {
        this.filePath = filePath;
        this.typeReference = typeReference;
        load();
    }

    public final void load() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                save();
                throw new Exception("File not found");
            }
            if (file.length() == 0) {
                save();
                throw new Exception("there is no data");
            }

            data = mapper.readValue(file, typeReference);

        } catch (Exception e) {
            e.printStackTrace();
            data = new ArrayList<>();
        }
    }

    public final void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (Exception ignored) {}
    }

    public List<T> getAll() {
        return data;
    }

    public void add(T obj) {
        data.add(obj);
        save();
    }

    public void delete(T obj) {
        data.remove(obj);
        save();
    }
}
