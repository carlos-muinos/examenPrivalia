package examen.privalia.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvParser{

    public static Collection manageFile() throws ClassNotFoundException, IOException, NullPointerException {
        try{
            BufferedReader fileContent = getFileContent();
            List<String> parsedFileContent = parseFileContent(fileContent);

            return(parsedFileContent);

        }catch (NullPointerException e){
            throw (e);
        } catch (ClassNotFoundException e) {
            throw (e);
        } catch (IOException e) {
            throw (e);
        }
    }

    public static BufferedReader getFileContent() throws ClassNotFoundException {
        Class cls = Class.forName("examen.privalia.util.CsvParser");
        ClassLoader cLoader = cls.getClassLoader();

        InputStream input = cLoader.getResourceAsStream("stocks-ITX.csv");
        BufferedReader fileContent = new BufferedReader(new InputStreamReader(input));
        return(fileContent);
    }

    public static List parseFileContent(BufferedReader fileContent) throws IOException {
        List<String> parsedFileContent = new ArrayList<>();
        String lineContent;
        while((lineContent = fileContent.readLine()) != null) {
            StringTokenizer columnContent = new StringTokenizer(lineContent,";");
            while (columnContent.hasMoreTokens())
            {
                parsedFileContent.add(columnContent.nextToken());
            }
        }

        List<String> sortedFileContent = sortFileContent(parsedFileContent);

        return(sortedFileContent);
    }


    public static List sortFileContent(List fileContent){
        List<String> copyFileContent = new ArrayList<String>(fileContent);
        List<String> sortedFileContent = new ArrayList<>();

        for (int n = fileContent.size()-1; n >= 0; n--){
            sortedFileContent.add(copyFileContent.get(copyFileContent.indexOf(fileContent.get(n))));
        }
        return(sortedFileContent);
    }
}