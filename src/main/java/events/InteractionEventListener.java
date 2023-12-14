package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.lang.System;

import org.json.JSONArray;
import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

public class InteractionEventListener extends ListenerAdapter {
    private boolean isWaitingForTodo = false;
    private final OkHttpClient client = new OkHttpClient();
    private final Dotenv dotenv = Dotenv.load();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "say-hi":
                event.reply("hellooooo").queue();
                break;
            case "todo":
                isWaitingForTodo = true;
                event.reply("Please enter your to-do list tasks separated by commas.").queue();
                break;
            case "rules":
                event.reply("1. Do not curse. 2. No sexist jokes allowed.").queue();
                break;
            case "joke":
                fetchJoke(event);
                break;
            case "meme":
                fetchProgrammingMeme(event);
                break;
            case "help":
                sendHelpMessage(event);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + event.getName());
        }
    }
//HELP

    private void sendHelpMessage(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Bot Help");
        embedBuilder.setDescription("This is the help message for the bot commands.");
        embedBuilder.addField("say-hi", "Says hello", false);
        embedBuilder.addField("todo", "Helps you write a todo list", false);
        embedBuilder.addField("rules", "Rules of the server", false);
        embedBuilder.addField("joke", "Tells you a dad joke", false);
        embedBuilder.addField("meme", "Shows you a funny programming meme", false);

        embedBuilder.setColor(0x00ff00);

        event.replyEmbeds(embedBuilder.build()).queue();
    }

    //MEMES
    private void fetchProgrammingMeme(SlashCommandInteractionEvent event) {
        String rapidApiKey = dotenv.get("RAPID_API_KEY");

        Request request = new Request.Builder()
                .url("https://programming-memes-images.p.rapidapi.com/v1/memes")
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", "programming-memes-images.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                event.reply("Failed to fetch a programming meme :(").queue();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    event.reply("Failed to fetch a programming meme :(").queue();
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();
                JSONArray memeArray = new JSONArray(responseData);

                if (memeArray.length() > 0) {
                    JSONObject meme = memeArray.getJSONObject(0);
                    String memeUrl = meme.getString("image");

                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("Programming Meme");
                    embedBuilder.setImage(memeUrl);
                    embedBuilder.setColor(0x00ff00);

                    event.replyEmbeds(embedBuilder.build()).queue();
                } else {
                    event.reply("No programming memes found :(").queue();
                }
            }
        });
    }

    //JOKES
    private void fetchJoke(SlashCommandInteractionEvent event) {
        String rapidApiKey = dotenv.get("RAPID_API_KEY");

        Request request = new Request.Builder()
                .url("https://dad-jokes-by-api-ninjas.p.rapidapi.com/v1/dadjokes?limit=1")
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", "dad-jokes-by-api-ninjas.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                event.reply("I failed to fetch a joke but the bigger joke here is YOU xD").queue();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    event.reply("I failed to fetch a joke but the bigger joke here is YOU xD").queue();
                    throw new IOException("Unexpected code " + response);
                }

                String responseData = response.body().string();

                try {
                    JSONArray jokesArray = new JSONArray(responseData);

                    StringBuilder jokeBuilder = new StringBuilder();
                    for (int i = 0; i < jokesArray.length(); i++) {
                        JSONObject jokeObject = jokesArray.getJSONObject(i);
                        String joke = jokeObject.getString("joke");
                        jokeBuilder.append(joke).append("\n");
                    }

                    event.reply(jokeBuilder.toString()).queue();

                } catch (Exception e) {
                    event.reply("Failed to parse the jokes :(").queue();
                    e.printStackTrace();
                }
            }
        });
    }

    //TODO
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || !isWaitingForTodo) {
            return;
        }

        String messageContent = event.getMessage().getContentRaw();
        if (event.getChannel().getType().isGuild() && event.getChannel().getId().equals(event.getChannel().getId())) {
            if (!messageContent.equalsIgnoreCase("cancel")) {
                String[] tasks = messageContent.split(",");
                List<String> taskList = Arrays.asList(tasks);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Todo List");
                embedBuilder.setDescription("Here's your todo list:");

                for (int i = 0; i < taskList.size(); i++) {
                    embedBuilder.addField("Task " + (i + 1), taskList.get(i), false);
                }

                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            } else {
                event.getChannel().sendMessage("To-do list creation canceled.").queue();
            }
        }

        isWaitingForTodo = false;
    }
}
