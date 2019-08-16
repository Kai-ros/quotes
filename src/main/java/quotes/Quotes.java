package quotes;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Quotes
{

    public String[] tags;
    public String author;
    public String likes;
    public String text;
    public String quote;

    public Quotes() {}

    public Quotes(String text)
    {
        this.tags = null;
        this.author = "Ron Swanson";
        this.likes = null;
        this.text = text;
    }

    public Quotes(String[] tags, String author, String likes, String text)
    {
        this.tags = tags;
        this.author = author;
        this.likes = likes;
        this.text = text;
    }

    public static String readFromFile(String path) throws Exception
    {
        Gson gson = new Gson();

        BufferedReader file = new BufferedReader(new FileReader(path));
        Quotes[] quotesFromFiles = gson.fromJson(file, Quotes[].class);

        int randomIndex = (int)(Math.random() * quotesFromFiles.length);
        String randomQuote = quotesFromFiles[randomIndex].toString();

        return randomQuote;
    }

    public static Quotes readFromAPI(String urlPath, String backupFilePath)
    {
        StringBuilder content = new StringBuilder();
        Quotes newQuote = null;

        try
        {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            System.out.println(connection.getResponseCode());

            // synchronous: Java is going to be working on running line 15 for a while.
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = input.readLine()) != null) {
                content.append(inputLine);
            }
            input.close();

            newQuote = new Quotes(content.toString().replaceAll("]$|^\\[",""));

            try
            {
                writeToFile(backupFilePath, newQuote);
            }
            catch (Exception errorFile)
            {
                System.err.println(errorFile);
            }
        }
        catch (IOException errorAPI)
        {
//            System.err.println(errorAPI);

            System.err.println("No internet connection.");
            try
            {
                newQuote = new Quotes(readFromFile(backupFilePath));
            }
            catch (Exception errorFile)
            {
                System.err.println(errorFile);
            }
        }

        return newQuote;
    }

    public static void writeToFile(String filepath, Quotes quote) throws Exception
    {
        Gson gson = new Gson();

        BufferedReader file = new BufferedReader(new FileReader(filepath));

        TypeToken<ArrayList<Quotes>> token = new TypeToken<ArrayList<Quotes>>(){};

        ArrayList<Quotes> quotesFromFiles = gson.fromJson(file, token.getType());

        quotesFromFiles.add(quote);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

        writer.write(gson.toJson(quotesFromFiles));

        writer.close();
        file.close();
    }

    @Override
    public String toString()
    {
        String outputMessage = "Quote from: " + this.author + " - " + this.text;

        return outputMessage;
    }

}
