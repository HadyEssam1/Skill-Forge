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

    public JsonManager(String filePath, TypeReference<List<T>> typeReference) throws Exception {
        this.filePath = filePath;
        this.typeReference = typeReference;
        load();
    }

    public List<T> load() throws Exception {
        try {
         File file= new File(filePath);
         if(!file.exists())
         {
             throw new Exception("file not found");
         }
         else if (file.length()==0)
         {
             data =new ArrayList<>();
         }
         else {
             data = mapper.readValue(file,typeReference);
         }
        } catch (Exception e) {
            throw new Exception("error : Loading File");
        }
        return null;
    }

    public final void save() throws Exception {
        try {
            File f= new File(filePath);
            mapper.writerWithDefaultPrettyPrinter().writeValue(f, data);
        } catch (Exception ignored) {
            throw new Exception("Data Can't be Saved");
        }
    }

    public List<T> getAll() {
        return data;
    }

    public void add(T obj) {
        data.add(obj);
    }

    public void delete(T obj) {
        data.remove(obj);
    }
    protected abstract String getEntityId(T entity);

    public void update(T entityToUpdate) throws Exception {
        boolean found=false;
        for (int i=0;i<this.data.size();i++) {
            if (getEntityId(this.data.get(i)).equals(getEntityId(entityToUpdate))) {
                this.data.set(i,entityToUpdate);
                found=true;
                break;
            }
        }
        if (found){
            save();
        }
        else{
            throw new Exception("Error: Entity not found for update.");
        }
    }
}
