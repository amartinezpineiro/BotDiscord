import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.util.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Instant;

public class ExampleBot {

    public static void main(String[] args) {
        //Se crea una variable String para almacenar el token
        final String token = Token.getToken();

        //Se crea el cliente utilizado el token
        final DiscordClient client = DiscordClient.create(token);

        //Se crea una pasarela usando el cliente
        final GatewayDiscordClient gateway = client.login().block();

        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .title("Yoda")
                .image("attachment://bot_bbyoda.jpg")
                .build();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {

            //Se crea una variable Message para recivir el mensage recivido
            final Message message = event.getMessage();

            //Comprobacion de si el mensaje recivido equivale a !ping
            if ("!ping".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();
                //Se crea el mensaje de respuesta Pong!
                channel.createMessage("Pong!").block();
            }
            //Comprobacion de si el mensaje recivido equivale a !embed
            if ("!embed".equals(message.getContent())) {
                final MessageChannel channel = message.getChannel().block();

                InputStream fileAsInputStream = null;
                try {
                    fileAsInputStream = new FileInputStream("bot_bbyoda.jpg");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                ;
                channel.createMessage(MessageCreateSpec.builder()
                        .content("content? content")
                        .addFile("bot_bbyoda.jpg", fileAsInputStream)
                        .addEmbed(embed)
                        .build()).subscribe();
            }
        });
        //Se cierra la pasarela
        gateway.onDisconnect().block();
    }
}
