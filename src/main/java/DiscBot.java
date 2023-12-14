import events.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import io.github.cdimascio.dotenv.Dotenv;

import javax.security.auth.login.LoginException;

public class DiscBot {
    public static void main(String[] args) throws LoginException{
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("DISCORD_BOT_TOKEN");        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);

      JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new ReadyEventListener(), new MessageEventListener(), new InteractionEventListener())
                .build();

      jda.upsertCommand("say-hi", "It says Hi").setGuildOnly(true).queue();
        jda.upsertCommand("todo", "Triggers the todo list").setGuildOnly(true).queue();
        jda.upsertCommand("rules", "This will tell you the rules").setGuildOnly(true).queue();
        jda.upsertCommand("joke", "This will tell you a random joke").setGuildOnly(true).queue();
        jda.upsertCommand("meme", "This will generate a random meme").setGuildOnly(true).queue();
        jda.upsertCommand("help", "All the commands available").setGuildOnly(true).queue();

    }
}
