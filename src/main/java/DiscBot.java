import events.MessageEventListener;
import events.ReadyEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class DiscBot {
    public static void main(String[] args) throws LoginException{
        final String TOKEN = "MTE4NDUxMTI1OTExOTQ1MjIzNQ.GvHb-w.bmkHPcJ5yokYypnOlY1qRPkFxgCx1fwQUPX4as";
        JDABuilder jdaBuilder = JDABuilder.createDefault(TOKEN);

      JDA jda = jdaBuilder
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new ReadyEventListener(), new MessageEventListener())
                .build();

      jda.upsertCommand("slash-cmd", "This is a slash command").setGuildOnly(true).queue();
    }
}
