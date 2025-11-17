package managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public abstract class JsonManager {
    protected String filePath;
    public JsonManager(String filePath) {
        this.filePath = filePath;
    }
    public abstract void saveToJson();
    public abstract void loadFromJson();
    protected <T> List<T> readFile(Type type) {
        Gson gson=new Gson();
        try(Reader reader = new FileReader(filePath)) {
            if (reader.ready()) {
                List<T> data = gson.fromJson(reader, type);
                return data;
            }
        } catch(FileNotFoundException e){

        }
        catch(IOException e){

        }
        catch(JsonSyntaxException e){

        }
        catch(Exception e){

        }
        return new ArrayList<>();
    }


    protected <T> void writeFile(List<T> data){
        Gson gson=new Gson();
        try(Writer writer=new FileWriter(filePath)){
            gson.toJson(data,writer);
        }
        catch (IOException e){

        }
        catch (Exception e) {

        }
    }
}
