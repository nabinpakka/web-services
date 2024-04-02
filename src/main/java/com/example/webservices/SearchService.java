package com.example.webservices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.stereotype.Service;
@Service
public class SearchService {

    //specify the library index file.
    public static String indexFileLocation = "/Users/hubbleloo/Desktop/web-services/src/main/resources/static" +
            "" + File.separator + "index";
    public static String indexFileName = "Index.txt";
    //a flag to exit the program
    public static boolean exit=false;
    public String searchTermInIndex(String searchKeyword){
        File file = new File(indexFileLocation + File.separator + indexFileName);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {

                String [] spiltWords =  st.split("\\|.\\|");
                if(spiltWords[0].equals(searchKeyword)){
                    System.out.println("Match Find on Index File!");
                    System.out.println("Sending following result to the client side:");
                    System.out.println(st);
                    return st;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
